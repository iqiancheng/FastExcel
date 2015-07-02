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
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * --	--	--	 00BDH 00BDH
 * his record represents a cell range containing RK value cells. All cells are 
 * located in the same row.Record MULRK, BIFF5-BIFF8:
 * offset Size Contents
 *  	2 Index to row
 *  	2 Index to first column (fc)
 *  	6*nc List of nc=lc-fc+1 XF/RK structures. Each XF/RK contains:
 * 	Offset Size Contents
 * 	0 	2 	Index to XF record 
 * 	2 	4 	RK value 
 * 6+6*nc	2 	Index to last column (lc)
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class MulRKParser extends MulCellParser {

	/**
	 * 
	 */
	public MulRKParser() {
		super(Types.MULRK);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.cell.MulCellParser#parse()
	 */
	public void parse() throws ParserException {
		super.parse();
		int now = off + 4;
		XF x;
		for (int i = 0; i < nc; i++) {
			int xf = NumUtil.getInt(b[now + (i * 6)], b[now + (i * 6) + 1]);
			int RK = NumUtil.getInt(b[now + (i * 6) + 2], b[now + (i * 6) + 3],
					b[now + (i * 6) + 4], b[now + (i * 6) + 5]);
			double value = NumUtil.getRKValue(RK);
			x = workBookGlobalsStream.getXF(xf);
			if (x != null) {
				Format f = workBookGlobalsStream.getFormat(x.getFormat());
				if (f != null) {
					String str = f.format(new Double(value),
							workBookGlobalsStream, context.getSetting());
					sheetStream.setContent(ir, (fc + i), str);
				} else {
					sheetStream.setContent(ir, (fc + i), value + "");
				}
			} else {
				sheetStream.setContent(ir, (fc + i), value + "");
			}
		}
	}
}
