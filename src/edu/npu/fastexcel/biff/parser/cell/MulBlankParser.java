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
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * -- 	--	-- 00BEH 00BEH
 * This record represents a cell range of empty cells. All cells are located in
 * the same row.
 * Record MULBLANK, BIFF5-BIFF8:
 * Offset 	Size 	Contents
 * 0 	2 	Index to row
 * 2 	2 	Index to first column (fc)
 * 4 	2*nc 	List of nc=lc-fc+1 16-bit indexes to XF records
 * 4+2+6*7nc 	2 	Index to last column (lc)
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class MulBlankParser extends MulCellParser {

	/**
	 * 
	 */
	public MulBlankParser() {
		super(Types.MULBLANK);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.cell.MulCellParser#parse()
	 */
	public void parse() throws ParserException {
		super.parse();
		// code here is get every blank cell's XF index.we just care content.so
		// ignore this
		for (int i = 0; i < nc; i++) {
			// int xf=NumUtil.getInt(b[now+(i*2)], b[now+(i*2)+1]);
			// read xf
			sheetStream.setContent(ir, fc + i, "");
		}

	}
}
