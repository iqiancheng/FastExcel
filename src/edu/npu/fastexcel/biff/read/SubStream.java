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
package edu.npu.fastexcel.biff.read;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.compound.CompoundFileReader;

/**
 * A workbook document with several sheets (BIFF5-BIFF8) is usually stored using
 * the compound document file format(also known as OLE2 storage file format or
 * Microsoft Office compatible storage file format). It contains several streams
 * for different types of data. This class represent this stream.
 * 
 * @see CompoundFileReader
 * @see BIFFReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public abstract class SubStream {
	public static final String STREAM_WORKBOOK_GLOBALS = "WORKBOOK_GLOBALS";
	public static final String STREAM_VB = "VB";
	public static final String STREAM_SHEET = "SHEET";
	public static final String STREAM_CHART = "CHART";
	public static final String STREAM_WORKSPACE = "WORKSPACE";
	/*
	 * if stream met EOF.
	 */

	protected volatile boolean eof;
	/* version of the stream */

	protected int version;
	/* offset to beginning of file */

	protected int offset;

	/**
	 * Default Constructor
	 */
	public SubStream() {
		eof = false;
		offset = 0;
	}

	/**
	 * @return the offset
	 */
	public int getOffset() {
		return offset;
	}

	/**
	 * @param offset
	 *            the offset to set
	 */
	public void setOffset(int offset) {
		this.offset = offset;
	}

	/**
	 * @return name of this stream
	 */
	public abstract String getName();

	/**
	 * @return the eof
	 */
	public boolean isEof() {
		return eof;
	}

	/**
	 * @param eof
	 *            the eof to set
	 */
	public void setEof(boolean eof) {
		this.eof = eof;
	}

	/**
	 * @return the version
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(int version) {
		this.version = version;
	}
	/**
	 * Close this stream.
	 */
	public void close()throws ExcelException{
		
	}
	/**
	 * flush this steam
	 * @throws ExcelException 
	 */
	public void flush() throws ExcelException{
		
	}
}
