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
package edu.npu.fastexcel.biff.parser.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 001EH 001EH 041EH 041EH 041EH
 * This record contains information about a number format. All FORMAT records 
 * occur together in a sequential list.In BIFF2-BIFF4 other records referencing
 * a FORMAT record contain a zero-based index into this list. From BIFF5 on
 * the FORMAT record contains the index itself that will be used by other records.
 * Record FORMAT, BIFF8:
 * Offset	Size	Contents
 * 0	2 	Format index used in other records
 * 2	var. 	Number format string (Unicode string, 16-bit string length)
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-26
 */
public class FormatParser extends WorkBookParser {

	public FormatParser() {
		super(Types.FORMAT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int index = NumUtil.getInt(b[off], b[off + 1]);
		StringBuffer sb = new StringBuffer();
		StringUtil.getBIFFUnicodeString(b, off + 2, sb);
		String ss=sb.toString();
		ss=Format.replaceBad(ss);
		boolean isDate=Format.isDateFormat(ss);
		boolean isTime=Format.isTimeFormat(ss);
		if(isDate&&isTime){
			workBookGlobalsStream.addFormat(index,Format.TYPE_DATE_TIME ,ss);
			return;
		}
		if(isDate){
			workBookGlobalsStream.addFormat(index,Format.TYPE_DATE ,ss);
			return;
		}
		if(isTime){
			workBookGlobalsStream.addFormat(index,Format.TYPE_TIME ,ss);
			return;
		}
		//todo
		workBookGlobalsStream.addFormat(index, Format.TYPE_DECIMAL, ss);
	}

}
