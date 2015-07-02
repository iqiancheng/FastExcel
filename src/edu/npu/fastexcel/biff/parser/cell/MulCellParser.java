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

import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.sheet.SheetParser;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public abstract class MulCellParser extends SheetParser {
	protected int ir;// index to row
	protected int fc;// index to first column
	protected int lc;// index to last column
	protected int nc;// total number of column

	/**
	 * @param type
	 */
	public MulCellParser(int type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		ir = NumUtil.getInt(b[off], b[off + 1]);// Index to row
		fc = NumUtil.getInt(b[off + 2], b[off + 3]);// Index to first column
		byte high = b[off + record.getContentLength() - 2];
		byte low = b[off + record.getContentLength() - 1];
		if (high == 0x00) {
			lc = low;
		} else {
			lc = NumUtil.getInt(high, low);
		}
		nc = (lc - fc + 1);
	}

}
