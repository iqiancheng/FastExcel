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
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * --	--	--	 00D6H 00D6H
 * This record stores a formatted text cell (Rich-Text). In BIFF8 it is usually 
 * replaced by the LABELSST record.Excel still uses this record, if it copies 
 * formatted text cells to the clip board.
 * Record RSTRING, BIFF8:
 * Offset Size Contents
 * 0	2 	Index to row
 * 2	2 	Index to column
 * 4 	2 	Index to XF record 
 * 6 	sz 	Unformatted Unicode string, 16-bit string length 
 * 6+sz 	2 	Number of Rich-Text formatting runs (rt)
 * 8+sz 	4*rt 	List of rt formatting runs
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class RStringParser extends CellParser {

	/**
	 * @param type
	 */
	public RStringParser() {
		super(Types.RSTRING);
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int len = NumUtil.getInt(b[off + 6], b[off + 7]);
		String str = StringUtil.getUnicodeString(b, off + 8, len / 2);
		sheetStream.setContent(r, c, str);
	}

}
