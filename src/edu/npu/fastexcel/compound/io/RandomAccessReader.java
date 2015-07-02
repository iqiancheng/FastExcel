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
package edu.npu.fastexcel.compound.io;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * This is just a slow implemention of <code>Reader</code>
 * 
 * @see MappedIOReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-22
 */
public class RandomAccessReader implements Reader {
	/* Excel File */
	private File file;
	private RandomAccessFile raf;

	/**
	 * Default constructor.
	 * 
	 * @param file
	 *            file to read.
	 */
	public RandomAccessReader(File file) {
		this.file = file;
	}

	public void close() throws ReadException {
		try {
			raf.close();
		} catch (IOException e) {
			throw new ReadException(e);
		}
	}

	public int position() throws ReadException {
		try {
			return (int) raf.getFilePointer();
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}
	}

	/**
	 * 
	 */
	public void open() throws ReadException {
		try {
			raf = new RandomAccessFile(file, "r");
		} catch (IOException e) {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e1) {
					// give up;
				}
			}
			throw new ReadException(e);
		}
	}

	public void seek(int offset) throws ReadException {
		try {
			raf.seek(offset);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}
	}

	public byte read() throws ReadException {
		try {
			return raf.readByte();
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}
	}

	public int read(byte[] b) throws ReadException {
		try {
			return raf.read(b);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}
	}

	public int read(byte[] b, int offset, int length) throws ReadException {
		try {
			return raf.read(b, offset, length);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}

	}

	public long length() throws ReadException {
		try {
			return raf.length();
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new ReadException(e);
		}
	}

}
