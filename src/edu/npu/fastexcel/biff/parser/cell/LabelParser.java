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
package edu.npu.fastexcel.biff.parser.cell;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0004H 0204H 0204H 0204H 0204H
 * This record represents a cell that contains a string. In BIFF8 it is usually 
 * replaced by the LABELSST record.Excel still uses this record, if it copies 
 * unformatted text cells to the clipboard.
 * Record LABEL, BIFF8:
 * Offset Size Contents
 * 0 	2 	Index to row
 * 2 	2 	Index to column
 * 4 	2 	Index to XF record 
 * 6 	var. 	Unicode string, 16-bit string length
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class LabelParser extends CellParser {

	/**
	 */
	public LabelParser() {
		super(Types.LABEL);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		String str;
		StringBuffer sb = new StringBuffer();
		StringUtil.getBIFFUnicodeString(b, off + 6, sb);
		str = sb.toString();
		sheetStream.setContent(r, c, str);
		// System.err.println("**[LABEL]:"+r+","+c+","+str);
	}
}
