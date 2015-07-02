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
package edu.npu.fastexcel.biff.parser.sheet;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 *<pre>
 * 	BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0000H 0200H 0200H 0200H 0200H
 *</pre>
 * 
 * This record contains the range address of the used area in the current sheet.
 * 
 * <pre>
 * offset Size Contents
 *  	4 	Index to first used row
 *  	4 	Index to last used row, increased by 1
 *  	2 	Index to first used column
 * 0 	2 	Index to last used column, increased by 1
 * 2 	2 	Not used
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class DimensionParser extends SheetParser {

	public DimensionParser() {
		super(Types.DIMENSION);
	}

	/**
	 */
	public void parse() throws ParserException {
		//
		int rf = NumUtil.getInt(b[off], b[off + 1], b[off + 2], b[off + 3]);
		int rl = NumUtil.getInt(b[off + 4], b[off + 5], b[off + 6], b[off + 7]);
		int cf = NumUtil.getInt(b[off + 8], b[off + 9]);
		int cl = NumUtil.getInt(b[off + 10], b[off + 11]);
		//
		sheetStream.setDimension(rf, rl, cf, cl);
		//
		// System.err.println("**[DIMEMSION]:"+rf+","+rl+","+cf+","+cl);
	}

}
