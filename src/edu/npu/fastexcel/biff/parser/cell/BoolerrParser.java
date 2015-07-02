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

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0005H 0205H 0205H 0205H 0205H
 * This record represents a Boolean value or error value cell.
 * Record BOOLERR, BIFF3-BIFF8:
 * Offset Size Contents
 * 0 	2 	Index to row
 * 2 	2 	Index to column
 * 4 	2 	Index to XF record 
 * 6 	1 	Boolean or error value (type depends on the following byte)
 * 7 	1 	0 = Boolean value; 1 = Error code
 * If the value field is a Boolean value, it will contain 0 for FALSE and 1 for TRUE
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class BoolerrParser extends CellParser {
	/**
	 */
	public BoolerrParser() {
		super(Types.BOOLERR);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		boolean value;
		// boolean f=b[off+6]==1;
		value = b[off + 7] == 1;
		sheetStream.setContent(r, c, value+"");
	}
}
