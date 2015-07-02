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
package edu.npu.fastexcel.biff.record.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.globals.BoundSheetParser;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 *
 * This record is located in the Workbook Globals Substream and represents a sheet
 * inside the workbook. One SHEET record is written for each sheet. It stores the
 * sheet name and a stream offset to the BOF record of the respective Sheet 
 * Substream within the Workbook Stream.
 * <pre> 
 * Record SHEET, BIFF5-BIFF8: 
 * Offset	Size	Contents 
 * 0 	4 	Absolute stream position of the BOF record of the sheet represented  
 * 			by this record. This field is never encrypted in protected files. 
 * 4 	1 	Sheet state:  0 = Visible 1 = Hidden 2 = Very hidden. Can only be set
 * 			 programmatically, e.g. with a Visual Basic macro. It is not possible
 * 			 to make such a sheet visible  via the user interface. 
 * 5 	1 	Sheet type:  00H = Worksheet 02H = Chart 06H = Visual Basic module 
 * 6 	var. 	Sheet name:  
 * 				BIFF5: Byte string, 8-bit string length  
 * 				BIFF8: Unicode string, 8-bit string length
 * </pre>
 * 
 * @see BoundSheetParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class BoundSheetRecord extends Record {
	/*
	 * Sheet name.NOTE:In BIFF8,max record 's date size is 8224 byte, so if we
	 * do not use continue record the max size of sheet name should be
	 * (8224-6)/2=4059
	 */
	private String name;
	/*
	 * Absolute stream position of the BOF record of the sheet represented by
	 * this record. This field is never encrypted in protected files
	 */
	private int streamPosition;

	/**
	 * @param name
	 */
	public BoundSheetRecord() {
		super(new byte[0]);
		name = "default";
	}

	/**
	 * Set sheet's name.
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
		int len = name.length() * 2 + 6 + RECORD_HEADER_LENGTH + 2;/*
																	 * 2 means
																	 * the
																	 * string
																	 * flag and
																	 * length
																	 */
		bytes = new byte[len];
		// since we know the length of this record.
	}

	/**
	 * Set absolute position of sheet substream.
	 * 
	 * @param pos
	 */
	public void setStreamPosition(int pos) {
		this.streamPosition = pos;
	}

	/*
	 * 0 4Absolute stream position of the BOF record of the sheet representedby
	 * this record. This field is never encrypted in protected files.
	 */
	private void setStreamPosition() {
		NumUtil.getFourBytes(streamPosition, bytes, RECORD_HEADER_LENGTH + 0);
	}

	/*
	 * 4 1 Sheet state: 0 = Visible 1 = Hidden 2= Very hidden.Can only be
	 * set programmatically, e.g. with a Visual Basic macro. It is not possible
	 * to make such a sheet visible via the user interface.
	 */
	private void setSheetState(int sheetState) {
		bytes[4 + RECORD_HEADER_LENGTH] = BoundSheetParser.SHEET_STATE_VISIBLE;
	}

	/*
	 * 5 1 Sheet type: 00H = Worksheet 02H = Chart 06H = Visual Basic module
	 * NOTE current just support 00H Worksheet.
	 */
	private void setSheetType(int type) {
		bytes[5 + RECORD_HEADER_LENGTH] = BoundSheetParser.SHEET_TYPE_WORKSHEET;
	}

	/*
	 * Sheet name. 6 var. Sheet name: BIFF5: Byte string, 8-bit string length
	 * BIFF8: Unicode string, 8-bit string length NOTE:In BIFF8,max record 's
	 * date size is 8224 byte, so if we do not use continue record the max size
	 * of sheet name should be (8224-6)/2=4059
	 */
	private void setName() {
		bytes[6 + RECORD_HEADER_LENGTH] = (byte) name.length();
		bytes[6 + 1 + RECORD_HEADER_LENGTH] = 0x01;
		StringUtil.getUnicodeBytes(name, bytes, 6 + 2 + RECORD_HEADER_LENGTH);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.record.Record#getBytes()
	 */
	public byte[] getBytes() {
		setType(Types.BOUNDSHEET);
		setContentLength(bytes.length - RECORD_HEADER_LENGTH);
		setStreamPosition();
		setSheetState(-1);
		setSheetType(-1);
		setName();
		return super.getBytes();
	}
	public String toString() {
		return "{BOUNDSHEET:"+name+","+position+","+streamPosition+"}";
	}
}
