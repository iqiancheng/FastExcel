/* 
 *FastExcel,(c) copyright 2009 yAma<guooscar@gmail.com>.  
 *WEB: http://fastexcel.sourceforge.net
 *
 * This library is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY
 * or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public
 * License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301,
 * USA.
 */
/**
 * 
 */
package edu.npu.fastexcel.compound;

import java.io.File;

import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;
import edu.npu.fastexcel.compound.io.RandomAccessWriter;
import edu.npu.fastexcel.compound.io.WriteException;
import edu.npu.fastexcel.compound.io.Writer;

/**
 * The OLE2 file format writer.<br/>
 * For more information see <a href="http://sc.openoffice.org/compdocfileformat.pdf">
 * http://sc.openoffice.org/compdocfileformat.pdf</a>.
 * @see CompoundFileReader
 * @see SectorStreamWriter
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-1
 */
public class CompoundFileWriter {
	//TODO add summary information writer
	/*
	 * sector identifier.a negative value means this sector is a special one.
	 */
	/*
	 * Free SecID Free sector, may exist in the file, but is not part of any
	 * stream
	 */
	private static final int SID_FREE = -1;
	private static final byte FREE[] = NumUtil.getFourBytes(SID_FREE);
	/* End Of Chain SecID Trailing SecID in a SecID chain */
	private static final int SID_EOC = -2;
	private static final byte EOC[] = NumUtil.getFourBytes(SID_EOC);

	/* SAT SecID Sector is used by the sector allocation table */
	private static final int SID_SAT = -3;
	/* MSAT SecID Sector is used by the master sector allocation table */
	private static final int SID_MSAT = -4;
	/*
	 * Magic number of compound file.
	 */
	private static final byte MAGIC[] = { (byte) 0xd0, (byte) 0xcf,
			(byte) 0x11, (byte) 0xe0, (byte) 0xa1, (byte) 0xb1, (byte) 0x1a,
			(byte) 0xe1 };
	/**
	 * Size of header.
	 */
	public static final int HEADER_SIZE = 512;
	private static final int FIRST_MSAT_POS = 76;

	private static final byte FREE512[] = new byte[512];
	private static final byte ZERO512[] = new byte[512];
	static {
		NumUtil.fill((byte)0xff,FREE512, 0, 512);
		NumUtil.getFourBytes(SID_EOC,FREE512,512-4);	
	}
	Writer writer;
	//private File file;
	private byte header[];
	protected int ssz;// size of sector. offset 30 ,size 2
	protected int sssz;// size of short sector. offset 32,size 2
	private int minSizeOfStream;// Minimum size of a standard stream
	private int firstSSATSID;//
	private int ssatCount;

	private int firstMSATSID;
	private int msatSectorCount=0;
	//
	private int firstDirSID;

	private int lastMSATSID;
	private int msatCount;// msat entry count
	private int lastSATSID;
	private int globalSectorIndex;

	protected int sidCountPreSector;
	private byte[] fourBytesHolder = new byte[4];
	private SectorStreamWriter dirStreamWriter;

	private DirectoryEntry[] directoryEntries;
	private int dirCount;
	private DirectoryEntry lastDir;
	//
	int newMsatInSector=0;//new msat sector
	//
	//
	private DirectoryEntry rootStorage;

	public CompoundFileWriter(File file) {
		//this.file = file;
		writer = new RandomAccessWriter(file);

		firstDirSID = SID_EOC;
		firstMSATSID = SID_EOC;
		firstSSATSID = SID_EOC;
		//
		msatCount = 0;
		msatSectorCount=0;
		lastMSATSID = -1;

		lastSATSID = -1;
		globalSectorIndex = 0;
		//
		ssz = 9;// 512 byte
		sssz = 6;// 64 byte
		//
		sidCountPreSector = 1 << (ssz - 2);
		/* root storage */
		rootStorage = new DirectoryEntry();
		rootStorage.name = DirectoryEntry.ROOT_ENTRY_NAME;
		rootStorage.leftDID = -1;
		rootStorage.rightDID = -1;
		rootStorage.rootDID = 1;
		rootStorage.type = DirectoryEntry.TYPE_ROOT_STORAGE;
		rootStorage.color = DirectoryEntry.COLOR_BLACK;
		rootStorage.streamSize = 0;
		rootStorage.secID = SID_EOC;
		//
		dirCount = 0;
		directoryEntries = new DirectoryEntry[32];// max directory size
		directoryEntries[dirCount] = rootStorage;
		dirCount++;
		dirStreamWriter = new SectorStreamWriter("dirwriter", this, ssz, null);
	}

	/**
	 * Open Compound File Writer.
	 * 
	 * @throws WriteException
	 */
	public void open() throws WriteException {
		writer.open();
		writeHeader();
		dirStreamWriter.open();
		writeRootStorage();
	}

	/**
	 * Close writer.write all content to disk.
	 * 
	 * @throws WriteException
	 */
	public void close() throws WriteException {
		writeAllDirectoryEntry();
		dirStreamWriter.close();
		updateHeader();// rewrite header
		writer.flush();
		writer.close();
	}

	/**
	 * Create a writeable stream.e.g. a Workbook stream for excel
	 * 
	 * @param streamName
	 * @return
	 */
	public Writer createWriteableStream(String streamName) {
		DirectoryEntry e = new DirectoryEntry();
		e.id = dirCount++;
		e.name = streamName;
		e.color = DirectoryEntry.COLOR_BLACK;
		e.leftDID = -1;
		e.rightDID = -1;
		e.rootDID = -1;
		e.type = DirectoryEntry.TYPE_USER_STREAM;
		directoryEntries[e.id] = e;
		if (lastDir == null) {
			lastDir = e;
		} else {
			if (lastDir.leftDID == -1) {
				lastDir.leftDID = e.id;
				e.color = lastDir.color == DirectoryEntry.COLOR_BLACK ? DirectoryEntry.COLOR_RED
						: DirectoryEntry.COLOR_BLACK;
			} else if (lastDir.rightDID == -1) {
				lastDir.rightDID = e.id;
				e.color = lastDir.color == DirectoryEntry.COLOR_BLACK ? DirectoryEntry.COLOR_RED
						: DirectoryEntry.COLOR_BLACK;
				lastDir = e;
			}
		}
		SectorStreamWriter w = new SectorStreamWriter(streamName + " ", this,
				ssz, e);
		return w;
	}
	
	/**
	 * Allocate
	 * 
	 * @return
	 * @throws WriteException
	 */
	final void allocSector(SectorStreamWriter w) throws WriteException {
		int next = globalSectorIndex;
		int satCount = (msatCount) * sidCountPreSector;
		// compute we have how many
		// sector allocation table entry.
		int needChangeSATSID = -1;// do not forget last sid in sector.
		if (satCount <= next) {
			
			needChangeSATSID = lastSATSID;
			// we need to allocate new sat entry
			lastSATSID = globalSectorIndex++;
			// write to msat
			writeMSAT(lastSATSID);
			writeSAT(lastSATSID, SID_SAT, lastSATSID);
		}

		next = globalSectorIndex++;// we lost next sid,recompute it.
		writer.seek(getSectorPos(next));// fill this sector
		writer.write(ZERO512);// this is not a good choice

		if (w.sidChainEmpty()) {
			writeSAT(next, SID_EOC, lastSATSID);
			w.addSID(next);
		} else {
			int last = w.getLastSID();
			writeSAT(last, next, needChangeSATSID == -1 ? lastSATSID
					: needChangeSATSID);
			// NOTE if the sector allocation table span two different sector.
			writeSAT(next, SID_EOC, lastSATSID);
			w.addSID(next);
		}
	}

	/*
	 * 
	 */
	private final void writeSAT(int sid, int value, int satSID)
			throws WriteException {
		int off = getSectorPos(satSID);
		int t = sid % sidCountPreSector;
		off += t * 4;
		NumUtil.getFourBytes(value, fourBytesHolder, 0);
		writer.seek(off);
		writer.write(fourBytesHolder);
	}

	/*
	 * 
	 */
	private final void writeMSAT(int sid) throws WriteException {
		// compute offset
		if (msatCount <109) { // about MAGIC number
			// see openoffice's excel format.
			int off = FIRST_MSAT_POS + msatCount * 4;
			NumUtil.getFourBytes(sid, fourBytesHolder, 0);
			writer.seek(off);
			writer.write(fourBytesHolder);
			writer.write(EOC);
		} else {
			//allocate new sector for msat 
			if(firstMSATSID==SID_EOC){
				lastMSATSID = globalSectorIndex++;// allocate new msat sector
				firstMSATSID = lastMSATSID;
				newMsatInSector=0;
				msatSectorCount++;
				writeSAT(lastMSATSID, SID_MSAT, lastSATSID);
				//
				int it = getSectorPos(lastMSATSID);// TODO need enhancement
				writer.seek(it);
				writer.write(FREE512);
				
			}else if(newMsatInSector==(sidCountPreSector-1)){
				int temp=lastMSATSID;
				lastMSATSID = globalSectorIndex++;// allocate new msat sector
				newMsatInSector=0;
				msatSectorCount++;
				writeSAT(lastMSATSID, SID_MSAT, lastSATSID);
				//
				int pos = getSectorPos(temp);//write last one
				pos += (sidCountPreSector-1) * 4;//last pos
				writer.seek(pos);//
				NumUtil.getFourBytes(lastMSATSID, fourBytesHolder, 0);
				writer.write(fourBytesHolder);
				int it = getSectorPos(lastMSATSID);// TODO need enhancement
				writer.seek(it);
				writer.write(FREE512);
				
			}
			int pos = getSectorPos(lastMSATSID);
			pos += newMsatInSector * 4;
			newMsatInSector++;
			writer.seek(pos);
			NumUtil.getFourBytes(sid, fourBytesHolder, 0);
			writer.write(fourBytesHolder);
		}
		msatCount++;
	}

	/*
	 * 
	 */
	void sectorStreamClosed(SectorStreamWriter w) {
		if (w == this.dirStreamWriter) {
			firstDirSID = w.getFirstSID();
		} else {}
		
	}
	/* 
	 */
	final int nextGlobalSectorIndex() {
		return globalSectorIndex++;
	}

	/*
	 * return absolute position of sector which specified by sid
	 */
	final int getSectorPos(int sid) {
		return (sid << ssz) + HEADER_SIZE;
	}

	/*
	 * 
	 */
	private void writeRootStorage() throws WriteException {
		writeDirectoryEntry(rootStorage);
	}

	/*
	 * 
	 */
	private void writeDirectoryEntry(DirectoryEntry e) throws WriteException {
		dirStreamWriter.seek(DirectoryEntry.DIR_LEN * e.id);
		dirStreamWriter.write(e.toBytes());
	}

	private void writeAllDirectoryEntry() throws WriteException {
		dirStreamWriter.seek(0);
		for (int i = 0; i < dirCount; i++) {
			dirStreamWriter.write(directoryEntries[i].toBytes());
		}
	}

	/*
	 * update header
	 */
	private void updateHeader() throws WriteException {
		header = new byte[76];
		System.arraycopy(MAGIC, 0, header, 0, MAGIC.length);
		// magic
		NumUtil.fill((byte) 0x00, header, 8, 16);// not used
		header[24] = 0x3e;
		header[25] = 0x00;
		header[26] = 0x03;
		header[27] = 0x00;
		header[28] = (byte) 0xfe;
		header[29] = (byte) 0xff;
		NumUtil.getTwoBytes(ssz, header, 30);
		NumUtil.getTwoBytes(sssz, header, 32);
		NumUtil.fill((byte) 0x00, header, 34, 10);// not used
		NumUtil.getFourBytes(msatCount, header, 44);// sector count of sat store
		NumUtil.getFourBytes(firstDirSID, header, 48);// dir's first sid.
		NumUtil.fill((byte) 0x00, header, 52, 4);// not used
		NumUtil.getFourBytes(minSizeOfStream, header, 56);// min size of
		// standard stream;
		NumUtil.getFourBytes(firstSSATSID, header, 60);// first sector of short
		// stream
		NumUtil.getFourBytes(ssatCount, header, 64);// ssat count
		NumUtil.getFourBytes(firstMSATSID, header, 68);//
		NumUtil.getFourBytes(msatSectorCount, header, 72);//
		writer.seek(0);
		writer.write(header);
	}

	/*
	 * write header
	 */
	private void writeHeader() throws WriteException {
		header = new byte[HEADER_SIZE];
		System.arraycopy(MAGIC, 0, header, 0, MAGIC.length);
		// magic
		NumUtil.fill((byte) 0x00, header, 8, 16);// not used
		header[24] = 0x00;
		header[25] = 0x3e;
		header[26] = 0x00;
		header[27] = 0x03;
		header[28] = (byte) 0xfe;
		header[29] = (byte) 0xff;
		NumUtil.getTwoBytes(ssz, header, 30);
		NumUtil.getTwoBytes(sssz, header, 32);
		NumUtil.fill((byte) 0x00, header, 34, 10);// not used
		NumUtil.getFourBytes(msatCount, header, 44);// sector count of sat store
		NumUtil.getFourBytes(firstDirSID, header, 48);// //dir's first sid.
		NumUtil.fill((byte) 0x00, header, 52, 4);// not used
		NumUtil.getFourBytes(minSizeOfStream, header, 56);// min size of
		// standard stream;
		NumUtil.getFourBytes(firstSSATSID, header, 60);// first sector of short
		// stream
		NumUtil.getFourBytes(ssatCount, header, 64);// ssat count
		NumUtil.getFourBytes(firstMSATSID, header, 68);//
		NumUtil.getFourBytes(msatSectorCount, header, 72);//

		int t = FIRST_MSAT_POS;
		for (int i = 0; i < 109; i++) {
			header[t] = FREE[0];
			header[t + 1] = FREE[1];
			header[t + 2] = FREE[2];
			header[t + 3] = FREE[3];
			t += 4;
		}
		//
		writer.seek(0);
		writer.write(header);
	}

	public static void main(String[] args) throws WriteException {

		CompoundFileWriter w = new CompoundFileWriter(new File("d:/test.xls"));
		w.open();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 5000; i++) {
			String ss = StringUtil.fill("" + i, 512);
			sb.append(ss);
		}
		Writer sw = w
				.createWriteableStream(DirectoryEntry.SUMMARY_INFORMATION_NAME);
		Writer sw2 = w
				.createWriteableStream(DirectoryEntry.DOCUMENT_SUMMARY_INFORMATION_NAME);
		sw.open();
		sw.write(sb.toString().getBytes());
		sw.close();
		sw2.open();
		sw2.write(sb.toString().getBytes());
		sw2.close();
		//
		w.close();
	}
}
