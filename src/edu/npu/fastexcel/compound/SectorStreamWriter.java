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

import edu.npu.fastexcel.compound.io.WriteException;
import edu.npu.fastexcel.compound.io.Writer;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-1
 */
public class SectorStreamWriter implements Writer {

	private int[] sectorChain;
	private int chainSize = 0;
	private CompoundFileWriter file;
	private Writer writer;
	private int sectorSize;// sector size
	private int ssz;// power of sector size
	private static final int ARRAY_GROW_SIZE = 1024;
	private int position;
	DirectoryEntry directoryEntry;

	final int getLastSID() {
		return sectorChain[chainSize - 1];
	}

	final int getFirstSID() {
		return sectorChain.length == 0 ? -1 : sectorChain[0];
	}

	final void addSID(int sid) {
		if (chainSize == sectorChain.length) {
			int newArray[] = new int[sectorChain.length + ARRAY_GROW_SIZE];
			System.arraycopy(sectorChain, 0, newArray, 0, sectorChain.length);
			sectorChain = newArray;
		}
		sectorChain[chainSize++] = sid;
	}

	final boolean sidChainEmpty() {
		return chainSize == 0;
	}

	public SectorStreamWriter(String name, CompoundFileWriter file,
			int sectorSize, DirectoryEntry e) {
		sectorChain = new int[1024];
		chainSize = 0;
		this.file = file;
		this.writer = file.writer;
		position = 0;
		this.ssz = sectorSize;
		this.sectorSize = (1 << ssz);
		this.directoryEntry = e;
	}

	public void close() throws WriteException {
		file.sectorStreamClosed(this);
		if (directoryEntry == null) {
			return;
		}
		directoryEntry.secID = getFirstSID();
		directoryEntry.streamSize = chainSize << ssz;
	}

	public void flush() throws WriteException {

	}

	public void open() throws WriteException {
		position = 0;
	}

	public void seek(int pos) throws WriteException {
		this.position = pos;
	}

	public void write(byte[] b) throws WriteException {
		int p = position >> ssz;// get number of sector
		int off = position % sectorSize;
		int byteLeft = b.length;
		int pos = 0;
		int p2 = 0;
		int len = 0;
		if (p == chainSize) {
			file.allocSector(this);
		}
		while (byteLeft > 0) {
			if (p == chainSize) {
				file.allocSector(this);
			}
			pos = CompoundFileWriter.HEADER_SIZE + (sectorChain[p] << ssz)
					+ off;
			len = sectorSize - off;
			len = len > byteLeft ? byteLeft : len;
			writer.seek(pos);
			writer.write(b, p2, len);
			p2 += len;
			byteLeft -= len;
			off = 0;
			p++;
		}
		position += b.length;
	}

	public void write(byte[] b, int offset, int len) throws WriteException {

	}

	public int position() {
		return position;
	}

}
