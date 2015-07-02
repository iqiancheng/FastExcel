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

import edu.npu.fastexcel.biff.parser.ParserContext;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.sheet.SheetParser;
import edu.npu.fastexcel.biff.read.AbstractSheetStream;
import edu.npu.fastexcel.biff.read.SubStream;
import edu.npu.fastexcel.biff.read.WorkBookGlobalsStream;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * A Cell Block is in most cases simply a single cell record. In BIFF2 it may be
 * preceded by an IXFE record containing the index to an XF record. Structure of
 * a common Cell Block in a Row Block, BIFF2-BIFF8
 * <table>
 * <tr>
 * <td>TYPE</td>
 * <td>REQUIRE</td>
 * <td>REPEAT</td>
 * <td>PARSER</td>
 * <tr>
 * <tr>
 * <td>IXFE Index to XF (BIFF2 only)</td>
 * <td></td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>BLANK</td>
 * <td></td>
 * <td>!</td>
 * <td><code>BlankParser</code></td>
 * <tr>
 * <tr>
 * <td>BOOLERR</td>
 * <td></td>
 * <td>!</td>
 * <td><code>BoolerrParser</code></td>
 * <tr>
 * <tr>
 * <td>INTEGER (BIFF2 only)</td>
 * <td></td>
 * <td>!</td>
 * <td></td>
 * <tr>
 * <tr>
 * <td>LABEL</td>
 * <td></td>
 * <td>!</td>
 * <td><code>LabelParser</code></td>
 * <tr>
 * <tr>
 * <td>LABELSST</td>
 * <td></td>
 * <td>!</td>
 * <td><code>LabelSSTParser</code></td>
 * <tr>
 * <tr>
 * <td>MULBLANK</td>
 * <td></td>
 * <td>!</td>
 * <td><code>MulBlankParser</code></td>
 * <tr>
 * <tr>
 * <td>MULRK</td>
 * <td></td>
 * <td>!</td>
 * <td><code>MulRKParser</code></td>
 * <tr>
 * <tr>
 * <td>NUMBER</td>
 * <td></td>
 * <td>!</td>
 * <td><code>NumberParser</code></td>
 * <tr>
 * <tr>
 * <td>RK</td>
 * <td></td>
 * <td>!</td>
 * <td><code>RKParser</code></td>
 * <tr>
 * <tr>
 * <td>RSTRING</td>
 * <td></td>
 * <td>!</td>
 * <td><code>RStringParser</code></td>
 * <tr>
 * </table>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public abstract class CellParser extends SheetParser {

	protected int r;// index to row
	protected int c;// index to col
	protected int xf;// index to XF

	/**
	 * @param type
	 */
	public CellParser(int type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.biff.parser.sheet.SheetParser#parse(edu.npu.fastexcel
	 * .biff.record.Record, edu.npu.fastexcel.biff.parser.ParserContext)
	 */
	public void parse(Record record, ParserContext context)
			throws ParserException{
		this.record = record;
		this.context = context;
		this.workBookStream = context.getStream();
		this.workBookGlobalsStream = (WorkBookGlobalsStream) context
				.getStream().getSubStream(SubStream.STREAM_WORKBOOK_GLOBALS);
		this.sheetStream = (AbstractSheetStream) context.getStream()
				.getCurrentStream();
		b = record.getBytes();
		off = Record.OFFSET;
		r = NumUtil.getInt(b[off], b[off + 1]);
		c = NumUtil.getInt(b[off + 2], b[off + 3]);
		xf = NumUtil.getInt(b[off + 4], b[off + 5]);
		parse();

	}
}
