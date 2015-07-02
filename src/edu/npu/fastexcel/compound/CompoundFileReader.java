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
import edu.npu.fastexcel.compound.io.MappedIOReader;
import edu.npu.fastexcel.compound.io.ReadException;
import edu.npu.fastexcel.compound.io.Reader;
import edu.npu.fastexcel.compound.stream.ReadableStream;

/**
 * Compound document files work similar to real file systems. They contain a
 * number of independent data streams (like files in a file system) which are
 * organised in a hierarchy of storages (like sub directories in a file system).
 * Storages and streams are named. The names of all storages and streams that
 * are direct members of a storage must be different. Names of streams or
 * storages that are members of different storages may be equal.Each compound
 * document file contains a root storage that is the direct or indirect parent
 * of all other storages and streams.</br> For more information see 
 * <a href="http://sc.openoffice.org/compdocfileformat.pdf">
 * http://sc.openoffice.org/compdocfileformat.pdf</a>.
 * @see CompoundFileWriter
 * @see SectorStreamReader
 * @see DirectoryEntry
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-26
 */
public class CompoundFileReader {
	//TODO add summary information reader
	/*
	 * sector identifier.a negative value means this sector is a special one.
	 */
	/**
	 * Free SecID Free sector, may exist in the file, but is not part of any
	 * stream
	 */
	public static final int SID_FREE = -1;
	/**
	 * End Of Chain SecID Trailing SecID in a SecID chain
	 */
	public static final int SID_EOC = -2;
	/**
	 * SAT SecID Sector is used by the sector allocation table
	 */
	public static final int SID_SAT = -3;
	/**
	 * MSAT SecID Sector is used by the master sector allocation table
	 */
	public static final int SID_MSAT = -4;
	/**
	 * Magic number of compound file.
	 * */
	protected static final byte MAGIC[] = { (byte) 0xd0, (byte) 0xcf,
			(byte) 0x11, (byte) 0xe0, (byte) 0xa1, (byte) 0xb1, (byte) 0x1a,
			(byte) 0xe1 };
	/**
	 * Size of header.
	 */
	public static final int HEADER_SIZE = 512;

	/* ole format file */
	protected File file;
	/* reader */
	protected Reader reader;
	//
	protected byte[] header;
	protected int ssz;// size of sector. offset 30 ,size 2
	protected int sssz;// size of short sector. offset 32,size 2

	protected int sectorsOfSAT;
	// Total number of sectors used for the sector allocation table.

	protected int firstDirSID;
	// SecID of first sector of the directory stream

	protected int minSizeOfStream;
	// Minimum size of a standard stream

	protected int firstSSATSID;
	// SecID of first sector of the short-sector allocation table

	protected int sectorsOfSSAT;
	// Total number of sectors used for the short-sector allocation table

	protected int firstMSATSID;
	// SecID of first sector of the master sector allocation table

	protected int sectorsOfMSAT;
	// Total number of sectors used for the master sector allocation table
	//

	protected int[] msat;// master sector allocation table.
	protected int[] sat;// sector allocation table.
	protected int[] ssat;// mini sector allocation table.

	private DirectoryEntry[] directoryEntries;
	private SectorStreamReader shortStreamReader;

	/**
	 * 
	 */
	public CompoundFileReader(File file) {
		header = new byte[512];// header size must be 512 byte.
		// little-endian.
		reader = new MappedIOReader(file);
		//BUG:
		//reader = new RandomAccessReader(file);
	}

	/**
	 * Open this file.
	 * 
	 * @throws ReadException
	 * */
	public void open() throws ReadException {
		reader.open();
		readHeader();
		readMSAT();
		readSAT();
		readSSAT();
		readDir();
		readShortSteamContainer();
	}

	/**
	 * Close this file.
	 * 
	 * @throws ReadException
	 */
	public void close() throws ReadException {
		reader.close();
	}

	/*
	 * Read header of this file.
	 */
	private void readHeader() throws ReadException {
		reader.read(header);
		// check magic
		for (int i = 0; i < MAGIC.length; i++) {
			if (header[i] != MAGIC[i]) {
				throw new ReadException("Bad File Format.");
			}
		}
		//
		ssz = NumUtil.getInt(header[30], header[31]);
		sssz = NumUtil.getInt(header[32], header[33]);
		sectorsOfSAT = NumUtil.getInt(
				header[44], header[45], header[46],header[47]);
		firstDirSID = NumUtil.getInt(
				header[48], header[49], header[50],header[51]);
		minSizeOfStream = NumUtil.getInt(
				header[56], header[57], header[58],header[59]);
		firstSSATSID = NumUtil.getInt(
				header[60], header[61], header[62],header[63]);
		sectorsOfSSAT = NumUtil.getInt(
				header[64], header[65], header[66],header[67]);
		firstMSATSID = NumUtil.getInt(
				header[68], header[69], header[70],header[71]);
		sectorsOfMSAT = NumUtil.getInt(
				header[72], header[73], header[74],header[75]);
		// //
	}

	public int getSectorSize() {
		return 1 << ssz;
	}

	public int getSizeOfShortSector() {
		return 1 << sssz;
	}

	/*
	 * read sector allocation table.
	 */
	private void readSAT() throws ReadException {
		int number = getSectorSize() / 4;
		sat = new int[sectorsOfSAT * number];
		int j = 0;
		for (int i = 0; i < sectorsOfSAT; i++) {
			int sid = msat[i];
			byte b[] = readSector(sid);
			int start = 0;
			for (int k = 0; k < number; k++) {
				sat[j++] = NumUtil.getInt(b[start], b[start + 1], b[start + 2],
						b[start + 3]);
				start += 4;
			}
		}
	}

	/*
	 * read short sector allocation table.
	 */
	private void readSSAT() throws ReadException {
		int ssatChain[] = getSectorChain(firstSSATSID, false);
		if (ssatChain.length == 0) {
			return;// empty chain
		}
		SectorStreamReader ss = new SectorStreamReader(reader, ssatChain, ssz);
		ss.open();
		byte bb[] = new byte[ssatChain.length * getSectorSize()];
		ss.read(bb);
		ssat = new int[bb.length / 4];
		for (int i = 0; i < bb.length / 4; i++) {
			ssat[i] = NumUtil.getInt(bb[i << 2], bb[(i << 2) + 1],
					bb[(i << 2) + 2], bb[(i << 2) + 3]);
		}
		ss.close();
	}

	/*
	 * Get sector chain from start sid.
	 * 
	 * @param sid first sid of this chain.
	 * 
	 * @param is a mini sector chain.
	 */
	private int[] getSectorChain(int sid, boolean mini) {
		int[] at;
		at = mini ? ssat : sat;
		if (sid == SID_EOC) {
			return new int[0];
		}
		int t = 0;
		int t2 = sid;
		while (true) {
			t++;
			t2 = at[t2];
			if (t2 == SID_EOC) {
				break;
			}
		}
		int rr[] = new int[t];
		t = 0;
		t2 = sid;
		while (true) {
			rr[t++] = t2;
			t2 = at[t2];
			if (t2 == SID_EOC) {
				break;
			}
		}
		return rr;
	}

	/* read directory entries. */
	private void readDir() throws ReadException {
		int dirchain[] = getSectorChain(firstDirSID, false);
		if (dirchain.length == 0) {
			throw new ReadException("can not find directory stream.");
		}
		SectorStreamReader dirStream = new SectorStreamReader(reader, dirchain,
				ssz);
		dirStream.open();
		byte[] bb = new byte[dirchain.length * getSectorSize()];
		dirStream.read(bb);
		int numberEntries = bb.length / 128;
		directoryEntries = new DirectoryEntry[numberEntries];
		for (int i = 0; i < numberEntries; i++) {
			byte b[] = new byte[128];
			System.arraycopy(bb, i * 128, b, 0, 128);
			directoryEntries[i] = new DirectoryEntry(b);
		}
		dirStream.close();
	}

	/*
	 * read
	 */
	private void readShortSteamContainer() throws ReadException {
		DirectoryEntry e = getDirectoryEntry(DirectoryEntry.ROOT_ENTRY_NAME);
		if (e == null) {
			throw new ReadException("Can not find Root Entry.");
		}
		int chain[] = getSectorChain(e.secID, false);
		shortStreamReader = new SectorStreamReader(reader, chain, ssz);
	}

	/* read master sector allocation table */
	private void readMSAT() throws ReadException {
		int msatSize = 109 + (sectorsOfMSAT * getSectorSize()) / 4;
		msat = new int[msatSize];
		int start = 76;
		for (int i = 0; i < 109; i++) {
			msat[i] = NumUtil.getInt(
					header[start],header[start+1],header[start+2],header[start+3]);
			start += 4;
		}
		int temp = firstMSATSID;
		int tt = 109;
		while (true) {
			int t[] = readMSAT(temp);
			if (t == null) {
				break;
			} else {
				System.arraycopy(t, 0, msat, tt, t.length - 1);
				tt += t.length - 1;
				temp = t[t.length - 1];
			}
		}
	}

	/* read master sector allocation table from sector */
	private int[] readMSAT(int sid) throws ReadException {
		if (sid == SID_EOC) {
			return null;
		} else {
			int number = getSectorSize() / 4;
			byte[] b = readSector(sid);
			int ii[] = new int[number];
			int start = 0;
			int i=0;
			for (;i < number; i++) {
				ii[i] = NumUtil.getInt(
						b[start], b[start + 1], b[start + 2],b[start + 3]);
				start += 4;
			}
			return ii;
		}
	}

	/*
	 * read sector and return sector's content.just use for read
	 * header,msat,sat.
	 */
	private byte[] readSector(int sid) throws ReadException {
		byte b[] = new byte[512];
		reader.seek(getSectorPos(sid));
		reader.read(b);
		return b;
	}

	/* return absolute position of this sector. */
	private final int getSectorPos(int sid) {
		return HEADER_SIZE + sid * getSectorSize();
	}

	/**
	 * Get a stream reader by name.
	 * 
	 * @param entryName
	 *            stream's name.
	 * @return if stream exists return stream reader otherwise return null.
	 */
	protected SectorStreamReader getSectorStreamReader(String entryName) {
		DirectoryEntry e = getDirectoryEntry(entryName);
		if (e == null) {
			return null;
		}
		SectorStreamReader sc;
		if (isMiniSector(e.streamSize)) {
			sc = new SectorStreamReader(shortStreamReader, getSectorChain(
					e.secID, true), sssz, 0);
		} else {
			sc = new SectorStreamReader(reader, getSectorChain(e.secID, false),
					ssz);
		}
		return sc;
	}

	private final boolean isMiniSector(int streamSize) {
		return streamSize < minSizeOfStream;
	}

	/**
	 * Get directory entry by name.
	 * 
	 * @param entryName
	 *            entry's name.
	 * @return if not exists return null.
	 */
	public DirectoryEntry getDirectoryEntry(String entryName) {
		for (int i = 0; i < directoryEntries.length; i++) {
			if (directoryEntries[i].name.equalsIgnoreCase(entryName)) {
				return directoryEntries[i];
			}
		}
		return null;
	}

	/**
	 * Get a readable stream by entry name.
	 * 
	 * @param streamName
	 *            directory entry's name
	 * @return if not exist return null.
	 */
	public ReadableStream getReadableStream(String streamName) {
		SectorStreamReader sr = getSectorStreamReader(streamName);
		if (sr == null) {
			return null;// no such entry.
		}
		ReadableStream stream = new ReadableStream(streamName);
		stream.setReader(sr);
		DirectoryEntry de = getDirectoryEntry(streamName);
		stream.setType(de.type);
		stream.setSize(de.streamSize);
		return stream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("ssz:" + ssz + ",");
		sb.append("sssz:" + sssz + ",");
		sb.append("sectorsOfSAT:" + sectorsOfSAT + ",");
		sb.append("firstDirSID:" + firstDirSID + ",");
		sb.append("minSizeOfStream:" + minSizeOfStream + ",");
		sb.append("firstSSATSID:" + firstSSATSID + ",");
		sb.append("sectorsOfSSAT:" + sectorsOfSSAT + ",");
		sb.append("firstMSATSID:" + firstMSATSID + ",");
		sb.append("sectorsOfMSAT:" + sectorsOfMSAT + ",");
		sb.append("\nMSAT:" + StringUtil.join(msat));
		sb.append("\nSAT:" + StringUtil.join(sat));
		sb.append("\nSSAT:" + StringUtil.join(ssat));
		sb.append("\nDIR:" + StringUtil.join(directoryEntries));
		sb.append("}");
		return sb.toString();
	}

}
