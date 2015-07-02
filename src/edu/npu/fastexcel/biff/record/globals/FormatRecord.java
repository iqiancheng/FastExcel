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
package edu.npu.fastexcel.biff.record.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.parser.globals.FormatParser;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8 
 * 001EH 001EH 041EH 041EH 041EH
 * </pre>
 * This record contains information about a number format. All FORMAT records  
 * occur together in a sequential list.In BIFF2-BIFF4 other records referencing
 * a FORMAT record contain a zero-based index into this list. From BIFF5 on the 
 * FORMAT record contains the index itself that will be used by other records. 
 * Record FORMAT, BIFF8: 
 * <pre>
 * Offset	Size	Contents 
 * 0	2 	Format index used in other records 
 * 2	var. 	Number format string (Unicode string, 16-bit string length)
 * </pre>
 * 
 * @see Format
 * @see FormatParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class FormatRecord extends Record {

	private String formatString;/* Number format string */
	private int index;/* Format index used in other records */

	/**
	 * 
	 */
	public FormatRecord() {
		super(new byte[0]);
	}

	/**
	 * Set index of this record.
	 * 
	 * @param index
	 *            index of this record.
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * Set format string of this record.
	 * 
	 * @param formatString
	 *            format record of this record.
	 */
	public void setFormatString(String formatString) {
		this.formatString = formatString;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.record.Record#getBytes()
	 */
	public byte[] getBytes() {
		byte bb[] = new byte[0];
		bb = StringUtil.getBIFFBytes(formatString);
		int len = bb.length + 2 + 4;
		bytes = new byte[len];
		System.arraycopy(bb, 0, bytes, RECORD_HEADER_LENGTH + 2, bb.length);
		//
		NumUtil.getTwoBytes(index, bytes, RECORD_HEADER_LENGTH);
		setType(Types.FORMAT);
		setContentLength(len - 4);
		return bytes;
	}

}
