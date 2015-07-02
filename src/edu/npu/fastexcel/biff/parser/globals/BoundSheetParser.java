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
import edu.npu.fastexcel.biff.read.SheetStream;
import edu.npu.fastexcel.biff.read.WorkBookGlobalsStream;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * this record is located in the Workbook Globals Substream and represents a sheet
 * inside the workbook. One SHEET record is written for each sheet. It stores the
 * sheet name and a stream offset to the BOF record of the respective
 * sheet Substream within the Workbook Stream.
 * record SHEET, BIFF5-BIFF8:
 * offset Size Contents
 *  	4 	Absolute stream position of the BOF record of the sheet represented 
 * 		by this record. This field is never encrypted in protected files.
 *  	1 	Sheet state: 
 * 			0 = Visible
 * 			1 = Hidden
 * 			2 = Very hidden. Can only be set programmatically, e.g. with a
 * 			Visual Basic macro. It is not possible to make such a sheet visible 
 * 			via the user interface.
 *  	1 	Sheet type: 
 * 			00H = Worksheet
 * 			02H = Chart
 * 			06H = Visual Basic module
 *  	var. 	Sheet name: 
 * 				BIFF5: Byte string, 8-bit string length 
 * 				BIFF8: Unicode string, 8-bit string length
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a>
 * @see WorkBookGlobalsStream 2008-11-20
 */
public class BoundSheetParser extends WorkBookParser {

	public static final int SHEET_STATE_VISIBLE = 0;
	public static final int SHEET_STATE_HIDDEN = 1;
	public static final int SHEET_STATE_VERYHIDDEN = 2;
	//
	public static final int SHEET_TYPE_WORKSHEET = 0x00;
	public static final int SHEET_TYPE_CHART = 0x02;
	public static final int SHEET_TYPE_VB = 0x06;

	//
	/**
	 */
	public BoundSheetParser() {
		super(Types.BOUNDSHEET);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		//
		int offset = NumUtil.getInt(b[off], b[off + 1], b[off + 2], b[off + 3]);// four
		// byte
		// represent
		// sheet
		// offset
		int state = b[off + 4];// state;
		int type = b[off + 5];
		String name = StringUtil.getString(b, off + 6);
		// NOTE if we read excel via event-based API
		// we need to set the sheet stream to EventBasedSheetStream.
		SheetStream ss = new SheetStream();
		ss.setName(name);
		ss.setOffset(offset);// offset to first bof
		ss.setState(state);
		ss.setType(type);
		workBookGlobalsStream.addSheet(ss);
	}

}
