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
package edu.npu.fastexcel.biff.parser.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0022H 0022H 0022H 0022H 0022H
 * This record specifies the base date for displaying date values. All dates are
 * stored as count of days past this base date. In BIFF2-BIFF4 this record is part
 * of the Calculation Settings Block . In BIFF5-BIFF8 it is stored in the 
 * Workbook Globals Substream.
 * Record DATEMODE, BIFF2-BIFF8:
 * Offset Size Contents
 * 0 	2 	0 = Base date is 1899-Dec-31 (the cell value 1 represents 1900-Jan-01)
 * 			1 = Base date is 1904-Jan-01 (the cell value 1 represents 1904-Jan-02)
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class DateModeParser extends WorkBookParser {

	public static final int MODE_BASE_1899 = 0;
	public static final int MODE_BASE_1904 = 1;

	/**
	 * 
	 */
	public DateModeParser() {
		super(Types.DATEMODE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int mode = NumUtil.getInt(b[off], b[off + 1]);
		workBookGlobalsStream.setDateMode(mode);
		// System.err.println("[DATEMODE]:"+mode);
	}
}
