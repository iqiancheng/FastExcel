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
 * -- 027EH 027EH 027EH 027EH
 * this record represents a cell that contains an RK value (encoded integer or 
 * floating-point value). If a floating-point value cannot be encoded to an RK
 * value, a NUMBER record  will be written. This record replaces the record
 * INTEGER written in BIFF2.
 * record RK, BIFF3-BIFF8:
 * offset Size Contents
 *  	2 	Index to row
 *  	2 	Index to column
 *  	2 	Index to XF record 
 *  	4 	RK value
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class RKParser extends CellParser {

	public RKParser() {
		super(Types.RK2);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int RK = NumUtil.getInt(b[off + 6], b[off + 7], b[off + 8], b[off + 9]);// RK
		// value
		double value = NumUtil.getRKValue(RK);
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
