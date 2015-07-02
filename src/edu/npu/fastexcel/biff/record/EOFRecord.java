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
package edu.npu.fastexcel.biff.record;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.EOFParser;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8 
 * 000AH 000AH 000AH 000AH 000AH 
 * This record has no content. It indicates the end of a record block with 
 * leading BOF record. This could be the end of the workbook globals, a worksheet,
 * a chart, etc.
 * </pre>
 * @see EOFParser
 * @see BOFRecord
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class EOFRecord extends Record {
	/**
	 * All EOF record are same.
	 */
	public static final EOFRecord EOF = new EOFRecord();

	/*
	 */
	EOFRecord() {
		super(new byte[4]);
		setType(Types.EOF);
		setContentLength(0);
	}
}
