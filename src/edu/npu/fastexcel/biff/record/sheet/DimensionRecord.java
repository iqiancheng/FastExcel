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
package edu.npu.fastexcel.biff.record.sheet;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.sheet.DimensionParser;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8 
 * 0000H 0200H 0200H 0200H 0200H
 * </pre>
 * This record contains the range address of the used area in the current sheet. 
 * <pre>
 * Offset Size Contents 
 * 0 	4 	Index to first used row 
 * 4 	4 	Index to last used row, increased by 1 
 * 8 	2 	Index to first used column 
 * 10 	2 	Index to last used column, increased by 1 
 * 12 	2 	Not used
 * </pre>
 * 
 * @see DimensionParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class DimensionRecord extends Record {
	private int firstRow;/* Index to first used row */
	private int lastRow;/* Index to last used row, increased by 1 */
	private int firstColumn;/* Index to first used column */
	private int lastColumn;/* Index to last used column, increased by 1 */

	/**
	 * 
	 */
	public DimensionRecord() {
		super(new byte[14 + RECORD_HEADER_LENGTH]);
		setContentLength(14);
		setType(Types.DIMENSION);
	}

	/**
	 * @param firstRow
	 *            the firstRow to set
	 */
	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	/**
	 * @param lastRow
	 *            the lastRow to set
	 */
	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	/**
	 * @param firstColumn
	 *            the firstColumn to set
	 */
	public void setFirstColumn(int firstColumn) {
		this.firstColumn = firstColumn;
	}

	/**
	 * @param lastColumn
	 *            the lastColumn to set
	 */
	public void setLastColumn(int lastColumn) {
		this.lastColumn = lastColumn;
	}

	public byte[] getBytes() {
		NumUtil.getFourBytes(firstRow, bytes, RECORD_HEADER_LENGTH + 0);
		NumUtil.getFourBytes(lastRow, bytes, RECORD_HEADER_LENGTH + 4);
		NumUtil.getTwoBytes(firstColumn, bytes, RECORD_HEADER_LENGTH + 8);
		NumUtil.getTwoBytes(lastColumn, bytes, RECORD_HEADER_LENGTH + 10);
		return bytes;
	}
	public String toString() {
		return "{DIMENSION:"+firstRow+","+lastRow+","+firstColumn+","+lastColumn+"}";
	}
}
