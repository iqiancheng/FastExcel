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
 * This is just a slow implemention of <code>Writer</code>
 * 
 * @see MappedIOReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-22
 */
public class RandomAccessWriter implements Writer {
	/* Excel File */
	private File file;
	private RandomAccessFile raf;

	/**
	 * Default constructor.
	 * 
	 * @param file
	 *            file to read.
	 */
	public RandomAccessWriter(File file) {
		this.file = file;
	}

	public void close() throws WriteException {
		try {
			raf.close();
		} catch (IOException e) {
			throw new WriteException(e);
		}
	}

	public int position() throws WriteException {
		try {
			return (int) raf.getFilePointer();
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new WriteException(e);
		}
	}

	/**
	 * 
	 */
	public void open() throws WriteException {
		try {
			if (file.exists()) {
				file.delete();
			}
			raf = new RandomAccessFile(file, "rw");
		} catch (IOException e) {
			if (raf != null) {
				try {
					raf.close();
				} catch (IOException e1) {
					// give up;
				}
			}
			throw new WriteException(e);
		}
	}

	public void seek(int offset) throws WriteException {
		try {
			raf.seek(offset);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new WriteException(e);
		}
	}

	public void flush() throws WriteException {

	}

	public void write(byte[] b) throws WriteException {
		try {
			raf.write(b);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new WriteException(e);
		}
	}

	public void write(byte[] b, int offset, int len) throws WriteException {
		try {
			raf.write(b, offset, len);
		} catch (IOException e) {
			try {
				raf.close();
			} catch (IOException e1) {}
			throw new WriteException(e);
		}
	}

}
