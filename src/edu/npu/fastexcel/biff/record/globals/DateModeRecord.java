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
package edu.npu.fastexcel.biff.record.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.globals.DateModeParser;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0022H 0022H 0022H 0022H 0022H 
 * </pre>
 * This record specifies the base date for displaying date values. All dates are
 * stored as count of days past this base date. In BIFF2-BIFF4 this record is 
 * part of the Calculation Settings Block . In BIFF5-BIFF8 it is stored in the
 * Workbook Globals Substream. Record DATEMODE, 
 * <pre>
 * BIFF2-BIFF8: 
 * Offset Size Contents 
 * 0 	2 	0 = Base date is 1899-Dec-31 (the cell value 1 represents 1900-Jan-01)
 * 			1 = Base date is 1904-Jan-01 (the cell value 1 represents 1904-Jan-02)
 * </pre>
 * 
 * @see Format
 * @see DateModeParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class DateModeRecord extends Record {
	/**
	 * A 1904 based date mode
	 * 
	 * @see DateModeParser#MODE_BASE_1904
	 */
	public static final DateModeRecord DATE_MODE_1904 = new DateModeRecord(
			DateModeParser.MODE_BASE_1904);
	/**
	 * A 1900 based date mode
	 * 
	 * @see DateModeParser#MODE_BASE_1899
	 */
	public static final DateModeRecord DATE_MODE_1899 = new DateModeRecord(
			DateModeParser.MODE_BASE_1899);

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            date mode
	 * @see DateModeParser#MODE_BASE_1899
	 * @see DateModeParser#MODE_BASE_1904
	 */
	DateModeRecord(int type) {
		super(new byte[RECORD_HEADER_LENGTH + 2]);
		setType(Types.DATEMODE);
		setContentLength(2);
		NumUtil.getTwoBytes(type, bytes, RECORD_HEADER_LENGTH);
	}

}
