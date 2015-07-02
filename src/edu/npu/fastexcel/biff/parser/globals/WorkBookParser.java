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

import edu.npu.fastexcel.biff.parser.DefaultParser;
import edu.npu.fastexcel.biff.parser.ParserContext;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.read.SubStream;
import edu.npu.fastexcel.biff.read.WorkBookGlobalsStream;
import edu.npu.fastexcel.biff.record.Record;

/**
 * Record Order in a BIFF8 Workbook Stream.</br> Workbook Globals Substream
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
 * <td>File Protection Block</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>CODEPAGE</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>DSF</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>TABID</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>FNGROUPCOUNT</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>Workbook Protection Block</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>WINDOW1</td>
 * <td>Y</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>BACKUP</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>HIDEOBJ</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>DATEMODE</td>
 * <td>N</td>
 * <td>!</td>
 * <td><code>DateModeParser</code></td>
 * </tr>
 * <tr>
 * <td>PRECISION</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>REFRESHALL</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>BOOKBOOL</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>FONT</td>
 * <td>Y</td>
 * <td>!!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>FORMAT</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td><code>FormatParser</code>
 * </tr>
 * <tr>
 * <td>XF</td>
 * <td>Y</td>
 * <td>!!</td>
 * <td><code>XFParser</code></td>
 * </tr>
 * <tr>
 * <td>STYLE</td>
 * <td>Y</td>
 * <td>!!</td>
 * <td><code>StyleParser</code></td>
 * </tr>
 * <tr>
 * <td>PALETTE</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>USESELFS</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>SHEET</td>
 * <td>Y</td>
 * <td>!!</td>
 * <td><code>BoundSheetParser</code></td>
 * </tr>
 * <tr>
 * <td>COUNTRY</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>Link Table</td>
 * <td>N</td>
 * <td>!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>DEFINEDNAME</td>
 * <td>N</td>
 * <td>!!</td>
 * <td></td>
 * </tr>
 * <tr>
 * <td>Shared String Table</td>
 * <td>N</td>
 * <td>!</td>
 * <td><code>SSTParser</code></td>
 * </tr>
 * <tr>
 * <td>EOF</td>
 * <td>Y</td>
 * <td>!</td>
 * <td><code>EOFParser</code></td>
 * </tr>
 * </table>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public abstract class WorkBookParser extends DefaultParser {
	protected WorkBookGlobalsStream workBookGlobalsStream;

	/**
	 * @param type
	 */
	public WorkBookParser(int type) {
		super(type);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.biff.parser.DefaultParser#parse(edu.npu.fastexcel.biff
	 * .record.Record, edu.npu.fastexcel.biff.parser.ParserContext)
	 */
	public void parse(Record record, ParserContext context)
			throws ParserException {
		super.parse(record, context);
		workBookGlobalsStream = (WorkBookGlobalsStream) context.getStream()
				.getSubStream(SubStream.STREAM_WORKBOOK_GLOBALS);
		parse();
	}

	public abstract void parse() throws ParserException;
}
