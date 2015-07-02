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

import edu.npu.fastexcel.compound.io.ReadException;
import edu.npu.fastexcel.compound.io.Reader;

/**
 * A Compound File is made up of a number of virtual streams. These are
 * collections of data that behave as a linear stream, although their on-disk
 * format may be fragmented. Virtual streams can be user data, or they can be
 * control structures used to maintain the file. Note that the file itself can
 * also be considered a virtual stream.<br/> All allocations of space within a
 * Compound File are done in units called sectors. The size of a sector is
 * definable at creation time of a Compound File, but for the purposes of this
 * document will be 512 bytes. A virtual stream is made up of a sequence of
 * sectors.
 * 
 * @see CompoundFileReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-26
 */
class SectorStreamReader implements Reader {
	private Reader reader;
	private int[] sectorChain;
	private int position;
	private int length;
	private int sectorSize;// sector size
	private int ssz;// power of sector size
	private int offset;

	/*
	 * package privateonly CompoundFile use.
	 */
	SectorStreamReader(Reader reader, int[] chain, int sectorSize, int offset) {
		this.sectorChain = chain;
		this.reader = reader;
		this.ssz = sectorSize;
		this.sectorSize = (1 << ssz);
		this.position = 0;
		this.offset = offset;
	}

	public SectorStreamReader(Reader reader, int[] chain, int sectorSize) {
		this(reader, chain, sectorSize, CompoundFileReader.HEADER_SIZE);
	}

	public void close() throws ReadException {
		// do nothing
	}

	public long length() throws ReadException {
		return length;
	}

	/**
	 * compute stream's size
	 */
	public void open() throws ReadException {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.compound.io.Reader#position()
	 */
	public int position() throws ReadException {
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.compound.io.Reader#read(byte[])
	 */
	public int read(byte[] b) throws ReadException {
		int p = position >> ssz;// get number of sector
		int off = position % sectorSize;
		int byteLeft = b.length;
		int pos = 0;
		int p2 = 0;
		int len = 0;
		int chainLen=sectorChain.length;
		if (p >= chainLen) {
			return -1;// end of the stream.
		}
		while (byteLeft > 0&&p<chainLen) {
			pos = offset + (sectorChain[p] << ssz) + off;
			len = sectorSize - off;
			len = len > byteLeft ? byteLeft : len;
			reader.seek(pos);
			reader.read(b, p2, len);
			p2 += len;
			byteLeft -= len;
			off = 0;
			p++;
		}
		position += b.length;
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.compound.io.Reader#read(byte[], int, int)
	 */
	public int read(byte[] b, int offset, int length) throws ReadException {
		byte bb[] = new byte[length];
		read(bb);
		System.arraycopy(bb, 0, b, offset, length);
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.compound.io.Reader#seek(int)
	 */
	public void seek(int pos) throws ReadException {
		position = pos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.compound.io.Reader#read()
	 */
	public byte read() throws ReadException {
		int p = position >> ssz;// get number of sector
		int off = position % sectorSize;
		int pos = 0;
		pos = offset + (sectorChain[p] << ssz) + off;
		reader.seek(pos);
		position += 1;
		return reader.read();
	}
}
