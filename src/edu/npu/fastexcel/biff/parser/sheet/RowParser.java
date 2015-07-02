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
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0008H 0208H 0208H 0208H 0208H
 * </pre>
 * 
 * This record contains the properties of a single row in a sheet. Rows and
 * cells in a sheet are divided into blocks of 32 rows.
 * 
 * <pre>
 * Offset Size Contents
 *  	2 	Index of this row
 *  	2 	Index to column of the first cell which is described by a cell record
 *  	2 	Index to column of the last cell which is described by a cell record, 
 * 	increased by 1
 *  	2 	Bit Mask Contents
 * 	14-0 	7FFFH 	Height of the row, in twips = 1/20 of a point
 * 	15 	8000H 	0 = Row has custom height; 1 = Row has default height
 * 	8 	2 	Not used
 * 	10	2 	In BIFF3-BIFF4 this field contains a relative offset to calculate stream
 * 		 position of the first
 * 		cell record for this row . In BIFF5-BIFF8 this field is not used anymore, but 
 * 		the DBCELL record  instead.
 * 	12 	4 	Option flags and default row formatting:
 * 		Bit Mask Contents
 * 	2-0 	00000007H 	Outline level of the row
 * 	4 	00000010H 	1 = Outline group starts or ends here (depending on where
 * 		the outline buttons are located, see SHEETPR record,
 * 		, and is collapsed
 * 	5 	00000020H 	1 = Row is hidden (manually, or by a filter or outline group)
 * 	6 	00000040H 	1 = Row height and default font height do not match
 * 	7 	00000080H 	1 = Row has explicit default format (fl)
 * 	8 	00000100H 	Always 1
 * 	27-16 	0FFF0000H 	If fl = 1: Index to default XF record 
 * 	28 	10000000H 	1 = Additional space above the row. This flag is set, if the
 * 		upper border of at least one cell in this row or if the lower
 * 		border of at least one cell in the row above is formatted with
 * 		a thick line style. Thin and medium line styles are not taken
 * 		into account.
 * 	29 	20000000H 	1 = Additional space below the row. This flag is set, if the
 * 		lower border of at least one cell in this row or if the upper
 * 		border of at least one cell in the row below is formatted with
 * 		a medium or thick line style. Thin line styles are not taken
 * 		into account.
 * 	30 	40000000H 	1 = Show phonetic text for all cells in this row (BIFF8 only)
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class RowParser extends SheetParser {

	public RowParser() {
		super(Types.ROW);
	}

	public void parse() throws ParserException {
		//
		int ir = NumUtil.getInt(b[off], b[off + 1]);// index of row
		int fc = NumUtil.getInt(b[off + 2], b[off + 3]);
		// Index to column of the first cell which is described by a cell record
		int lc = NumUtil.getInt(b[off + 4], b[off + 5]);
		// int to column of the last cell which is described by a cell record,
		// increased by 1
		// System.err.println("**[ROW]:"+ir+","+fc+","+lc);
		sheetStream.setRow(ir, fc, lc);
	}

}
