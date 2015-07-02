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
package edu.npu.fastexcel.biff.record.cell;

import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * <pre>
 * Cell record in BIFF8: 
 * Offset Size Contents 
 * 0 	2	Index to row 
 * 2 	2 	Index to column 
 * 4 	2 	Index to XF record other information
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public abstract class CellRecord extends Record {

	protected int row;/* 0 2 Index to row */
	protected int column;/* 2 2 Index to column */
	protected int xf;/* 4 2 Index to XF record */

	/**
	 * @param bytes
	 */
	public CellRecord(byte[] bytes) {
		super(bytes);
	}

	/**
	 * @param row
	 *            the row to set
	 */
	public void setRow(int row) {
		this.row = row;
	}

	/**
	 * @param column
	 *            the column to set
	 */
	public void setColumn(int column) {
		this.column = column;
	}

	/**
	 * @param xf
	 *            the xf to set
	 */
	public void setXF(int xf) {
		this.xf = xf;
	}

	/*
	 * write row,column,xf.
	 */
	protected void setCell() {
		NumUtil.getTwoBytes(row, bytes, 0 + RECORD_HEADER_LENGTH);
		NumUtil.getTwoBytes(column, bytes, 2 + RECORD_HEADER_LENGTH);
		NumUtil.getTwoBytes(xf, bytes, 4 + RECORD_HEADER_LENGTH);
	}

}
