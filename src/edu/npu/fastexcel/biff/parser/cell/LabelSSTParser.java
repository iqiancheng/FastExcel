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

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * --	--	--	-- 00FDH
 * his record represents a cell that contains a string. It replaces the LABEL record
 * sed in BIFF2-BIFF5.
 * ecord LABELSST, BIFF8:
 * ffset Size Contents
 *  	2 	Index to row
 *  	2 	Index to column
 *  	2 	Index to XF record
 *  	4 	Index into SST record
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class LabelSSTParser extends CellParser {

	/**
	 */
	public LabelSSTParser() {
		super(Types.LABELSST);
	}

	/**
	 * 
	 */
	public void parse() throws ParserException {
		int iSST;
		iSST = NumUtil.getInt(b[off + 6], b[off + 7], b[off + 8], b[off + 9]);
		try {
			sheetStream.setContent(r, c, iSST);
		} catch (ExcelException e) {
			throw new ParserException(e);
		}
		// Index into SST record
	}
}
