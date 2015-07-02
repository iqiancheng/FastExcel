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
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.parser.globals.XF;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0003H 0203H 0203H 0203H 0203H
 * This record represents a cell that contains a floating-point value.
 * Offset 	Size 	Contents
 * 0 	2 	Index to row
 * 2 	2 	Index to column
 * 4 	2 	Index to XF record
 * 6 	8 	IEEE 754 floating-point value (64-bit double precision)
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class NumberParser extends CellParser {

	/**
	 * 
	 */
	public NumberParser() {
		super(Types.NUMBER);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		double value = NumUtil.getIEEEDouble(b, off + 6);
		XF x = workBookGlobalsStream.getXF(xf);
		if (x != null) {
			Format f = workBookGlobalsStream.getFormat(x.getFormat());
			if (f != null) {
				String str = f.format(new Double(value), workBookGlobalsStream,
						context.getSetting());
				sheetStream.setContent(r, c, str);
				return;
			}
		}
		sheetStream.setContent(r, c, value + "");
	}

}
