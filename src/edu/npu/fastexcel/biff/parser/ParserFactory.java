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
package edu.npu.fastexcel.biff.parser;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.cell.BoolerrParser;
import edu.npu.fastexcel.biff.parser.cell.Formula2Parser;
import edu.npu.fastexcel.biff.parser.cell.FormulaParser;
import edu.npu.fastexcel.biff.parser.cell.LabelParser;
import edu.npu.fastexcel.biff.parser.cell.LabelSSTParser;
import edu.npu.fastexcel.biff.parser.cell.MulBlankParser;
import edu.npu.fastexcel.biff.parser.cell.MulRKParser;
import edu.npu.fastexcel.biff.parser.cell.NumberParser;
import edu.npu.fastexcel.biff.parser.cell.RKParser;
import edu.npu.fastexcel.biff.parser.cell.RStringParser;
import edu.npu.fastexcel.biff.parser.globals.BoundSheetParser;
import edu.npu.fastexcel.biff.parser.globals.DateModeParser;
import edu.npu.fastexcel.biff.parser.globals.FormatParser;
import edu.npu.fastexcel.biff.parser.globals.SSTParser;
import edu.npu.fastexcel.biff.parser.globals.StyleParser;
import edu.npu.fastexcel.biff.parser.globals.XFParser;
import edu.npu.fastexcel.biff.parser.sheet.DimensionParser;
import edu.npu.fastexcel.biff.parser.sheet.IndexParser;
import edu.npu.fastexcel.biff.parser.sheet.RowParser;
import edu.npu.fastexcel.biff.record.Record;

/**
 * Factory of RecordParser.
 * 
 * @see RecordParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class ParserFactory {
	RecordParser unknowParser = new UnknowParser();
	RecordParser bofParser = new BOFParser();
	RecordParser eofParser = new EOFParser();
	RecordParser bsParser = new BoundSheetParser();
	RecordParser sstParser = new SSTParser();
	RecordParser indexParser = new IndexParser();
	RecordParser dimensionParser = new DimensionParser();
	RecordParser rowParser = new RowParser();
	RecordParser labelSSTParser = new LabelSSTParser();
	RecordParser rkParser = new RKParser();
	RecordParser mulRKParser = new MulRKParser();
	RecordParser labelParser = new LabelParser();
	RecordParser boolerrParser = new BoolerrParser();
	RecordParser xfParser = new XFParser();
	RecordParser formatParser = new FormatParser();
	RecordParser dateModeParser = new DateModeParser();
	RecordParser styleParser = new StyleParser();
	RecordParser mulBlankParser = new MulBlankParser();
	RecordParser numberParser = new NumberParser();
	RecordParser rStringParser = new RStringParser();
    RecordParser formulaParser = new FormulaParser();
    RecordParser formula2Parser = new Formula2Parser();

	public ParserFactory() {
	}
	public void parse(Record r, ParserContext c) throws ParserException {
        switch (r.getType()) {
		case Types.ROW:
			rowParser.parse(r, c);
			break;
		case Types.LABELSST:
			labelSSTParser.parse(r, c);
			break;
		case Types.RK2:
			rkParser.parse(r, c);
			break;
		case Types.NUMBER:
			numberParser.parse(r, c);
			break;
		case Types.RSTRING:
			rStringParser.parse(r, c);
			break;
		case Types.MULRK:
			mulRKParser.parse(r, c);
			break;
		case Types.LABEL:
			labelParser.parse(r, c);
			break;
		case Types.BOOLERR:
			boolerrParser.parse(r, c);
			break;
		case Types.BOF:
			bofParser.parse(r, c);
			break;
		case Types.EOF:
			eofParser.parse(r, c);
			break;
		case Types.BOUNDSHEET:
			bsParser.parse(r, c);
			break;
		case Types.SST:
			sstParser.parse(r, c);
			break;
		case Types.INDEX:
			indexParser.parse(r, c);
			break;
		case Types.DIMENSION:
			dimensionParser.parse(r, c);
			break;
		case Types.XF:
			xfParser.parse(r, c);
			break;
		case Types.FORMAT:
			formatParser.parse(r, c);
			break;
		case Types.DATEMODE:
			dateModeParser.parse(r, c);
			break;
		case Types.STYLE:
			styleParser.parse(r, c);
			break;
		case Types.MULBLANK:
			mulBlankParser.parse(r, c);
			break;
        case Types.FORMULA:
            formulaParser.parse(r, c);
            break;
        case Types.FORMULA2:
            formula2Parser.parse(r, c);
            break;
		default:
			unknowParser.parse(r, c);
			break;
		}
	}
}
