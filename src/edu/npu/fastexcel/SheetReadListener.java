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
package edu.npu.fastexcel;

/**
 * Event-based callback interface for reading sheet. 
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-16
 */
public interface SheetReadListener {
	/**
	 * When reading row.
	 * @param row the row index 
	 * @param firstcol first column of this row
	 * @param lastcol last column of this row
	 */
	void onRow(int row, int firstcol, int lastcol);
	/**
	 * When reading dimension.
	 * @param firstrow first row of this sheet
	 * @param lastrow  last row of this sheet
	 * @param firstcol first column of this sheet
	 * @param lastcol last column of this sheet
	 */
	void onDimension(int firstrow, int lastrow, int firstcol, int lastcol);
	/**
	 * When reading cell.
	 * @param row the row index of this cell
	 * @param col the column index of this cell
	 * @param content cell content in string
	 */
	void onCell(int row, int col, String content);
}
