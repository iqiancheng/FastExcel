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
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0009H 0209H 0409H 0809H 0809H
 * The BOF record is the first record of any kind of stream or substream.
 * Blow is the format of BIFF8's BOF.
 * offset length
 * 0  	2 BIFF version (always 0600H for BIFF8)
 * 2  	2 Type of the following data: 
 * 			0005H = Workbook globals
 * 			0006H = Visual Basic module
 * 			0010H = Sheet or dialogue
 * 			0020H = Chart
 * 			0040H = Macro sheet
 * 			0100H = Workspace (BIFF8W only)
 * 4  	2 Build identifier, must not be 0
 * 6  	2 Build year, must not be 0
 * 8  	4 File history flags
 * 12 	4 Lowest Excel version that can read all records in this file
 * </pre>
 * 
 * Current Version of BIFFReader only support BIFF8 format,that means Excel
 * 97,2000,XP,2003 can be supported.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class BOFParser extends RecordParser {

	public static final int BIFF_VERSION_5 = 0x0500;
	public static final int BIFF_VERSION_8 = 0x0600;
	public static final int BIFF_VERSION_8X = 0x0600;
	//
	public static final int BIFF_TYPE_WORKBOOK_GLOBALS = 0x05;
	public static final int BIFF_TYPE_VB = 0x06;
	public static final int BIFF_TYPE_SHEET_OR_DIALOGUE = 0x10;
	public static final int BIFF_TYPE_CHART = 0x20;
	public static final int BIFF_TYPE_MARCO_SHEET = 0x40;
	public static final int BIFF_TYPE_WORKSPACE = 0x100;

	/**
	 * 
	 */
	public BOFParser() {
		super(Types.BOF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.biff.parser.RecordParser#parse(edu.npu.fastexcel.biff
	 * .record.Record, edu.npu.fastexcel.biff.parser.ParserContext)
	 */
	public void parse(Record record, ParserContext c) throws ParserException {
		byte b[] = record.getBytes();
		int off = Record.OFFSET;
		int version = NumUtil.getInt(b[off], b[off + 1]);// version
		if (version != BIFF_VERSION_8) {// TODO implement other version.
			throw new ParserException("wrong BIFF version.");
		}
		int type = NumUtil.getInt(b[off + 2], b[off + 3]);// type
		if (type == BIFF_TYPE_WORKBOOK_GLOBALS) {
			// globals
			c.getStream().getCurrentStream().setVersion(version);
		} else if (type == BIFF_TYPE_SHEET_OR_DIALOGUE) {
			// sheet
			c.getStream().getCurrentStream().setVersion(version);
		} else {
			// ignore
		}
	}

}
