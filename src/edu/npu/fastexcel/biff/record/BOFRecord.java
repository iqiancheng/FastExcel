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
package edu.npu.fastexcel.biff.record;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.BOFParser;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8 0009H 0209H 0409H 0809H 0809H The BOF record is the first record of any kind of stream or substream. Blow is the format of BIFF8's BOF. offset length 0  	2 BIFF version (always 0600H for BIFF8) 2  	2 Type of the following data:  0005H = Workbook globals 0006H = Visual Basic module 0010H = Sheet or dialogue  0020H = Chart 0040H = Macro sheet 0100H = Workspace (BIFF8W only) 4  	2 Build identifier, must not be 0 6  	2 Build year, must not be 0 8  	4 File history flags 12 	4 Lowest Excel version that can read all records in this file
 * </pre>
 * 
 * Current Version of BIFFWriter only support BIFF8 format,that means Excel
 * 97,2000,XP,2003 can be supported.
 * 
 * @see BOFParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class BOFRecord extends Record {

	public static final BOFRecord BIFF_TYPE_WORKBOOK_GLOBALS = new BOFRecord(
			0x05);
	public static final BOFRecord BIFF_TYPE_VB = new BOFRecord(0x06);
	public static final BOFRecord BIFF_TYPE_SHEET_OR_DIALOGUE = new BOFRecord(
			0x10);
	public static final BOFRecord BIFF_TYPE_CHART = new BOFRecord(0x20);
	public static final BOFRecord BIFF_TYPE_MARCO_SHEET = new BOFRecord(0x40);
	public static final BOFRecord BIFF_TYPE_WORKSPACE = new BOFRecord(0x100);

	/**
	 * Constructor.
	 * 
	 * @param type
	 *            BOF type must be <code>BOFParser</code>.BIFF_TYPE_*.
	 * @see BOFParser
	 */
	private BOFRecord(int type) {
		super(new byte[20]);
		setBOFType(type);
	}

	/*
	 * Set BIFF version,now just support BIFF8
	 */
	private void setVersion() {
		NumUtil.getTwoBytes(BOFParser.BIFF_VERSION_8, bytes,
				0 + RECORD_HEADER_LENGTH);

	}

	/*
	 * Set BOF type must be BOFParser.BIFF_TYPE_.
	 */
	private void setBOFType(int type) {
		NumUtil.getTwoBytes(type, bytes, 2 + RECORD_HEADER_LENGTH);
	}

	/*
	 * Other information.not important,ignores.
	 */
	private void setOthers() {
		NumUtil.fill((byte) 0x01, bytes, 4 + RECORD_HEADER_LENGTH, 12);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.record.Record#getBytes()
	 */
	public byte[] getBytes() {
		setVersion();
		setType(Types.BOF);
		setContentLength(16);
		setOthers();
		return bytes;
	}
}
