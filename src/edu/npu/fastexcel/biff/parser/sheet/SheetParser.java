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

import edu.npu.fastexcel.biff.parser.ParserContext;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.globals.WorkBookParser;
import edu.npu.fastexcel.biff.read.AbstractSheetStream;
import edu.npu.fastexcel.biff.record.Record;

/**
 * Record Order in a BIFF8 Workbook Stream.</br> Sheet Substream
 * <table>
 * <tr>
 * <td>TYPE</td>
 * <td>REQUIRE</td>
 * <td>REPEAT</td>
 * <td>PARSER</td>
 * <tr>
 * <tr>
 * <td>BOF Type = workbook globals</td>
 * <td>Y</td>
 * <td>!</td>
 * <td><code>BOFParser</code></td>
 * <tr>
 * <tr>
 * <td>INDEX</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Calculation Settings Block</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>PRINTHEADERS</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>PRINTGRIDLINES</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>GRIDSET</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>GUTS</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>DEFAULTROWHEIGHT</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>SHEETPR</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Page Settings Block</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Worksheet Protection Block</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>DEFCOLWIDTH</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>COLINFO</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>SORT</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>DIMENSION</td>
 * <td>Y</td>
 * <td>!</td>
 * <td><code>DimensionParser</code></td>
 * <tr>
 * <tr>
 * <td>Row Blocks</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Worksheet View Settings Block</td>
 * <td>Y</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>STANDARDWIDTH</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>MERGEDCELLS</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>LABELRANGES</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>PHONETICPR</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Conditional Formatting Table</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Hyperlink Table</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>Data Validity Table</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>SHEETLAYOUT</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>SHEETPROTECTION</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>RANGEPROTECTION</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>EOF</td>
 * <td>Y</td>
 * <td>!</td>
 * <td><code>EOFParser</code></td>
 * </tr>
 * </table>
 * All cells in the Cell Table are divided into blocks of 32 consecutive rows,
 * called Row Blocks. The first Row Block starts with the first used row in that
 * sheet. Inside each Row Block there will occur ROW records describing the
 * properties of the rows, and cell records with all the cell contents in this
 * Row Block.
 * <table>
 * <tr>
 * <td>TYPE</td>
 * <td>REQUIRE</td>
 * <td>REPEAT</td>
 * <td>PARSER</td>
 * <tr>
 * <tr>
 * <td>ROW</td>
 * <td>Y</td>
 * <td>!!</td>
 * <td><code>RowParser</code></td>
 * <tr>
 * <tr>
 * <td>Cell Block(s)</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>DBCELL</td>
 * <td>Y</td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * </table>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public abstract class SheetParser extends WorkBookParser {

	protected AbstractSheetStream sheetStream;

	public SheetParser(int type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse(edu.npu.fastexcel
	 * .biff.record.Record, edu.npu.fastexcel.biff.parser.ParserContext)
	 */
	public void parse(Record record, ParserContext context)
			throws ParserException {
		this.sheetStream = (AbstractSheetStream) context.getStream()
				.getCurrentStream();
		super.parse(record, context);
	}
}
