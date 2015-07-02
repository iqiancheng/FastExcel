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
package edu.npu.fastexcel;

import edu.npu.fastexcel.biff.write.FileSheetStreamWriter;

/**
 * 
 * Sheet interface is a high level abstraction of Excel Workbook.<br/>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-1
 */

public interface Sheet {

	/**
	 * @return name of this sheet.
	 */
	public abstract String getName();
	/**
	 *@return index of this sheet
	 */
	public abstract int getIndex();
	/**
	 * Get cell content.
	 * @param row
	 *            cell's row index.
	 * @param col
	 *            cell's column index.
	 * @return cell content.
	 * @see edu.npu.fastexcel.biff.read.DefaultSheetStream#getCell(int, int)
	 */
	public abstract String getCell(int row, int col)throws ExcelException;
	/**
	 * 
	 * @param row
	 * @param col
	 * @param str
	 * @throws ExcelException
	 */
	public abstract void setCell(int row,int col,String str)
		throws ExcelException;
	/**
	 * Get row by index.
	 * @param row row index.
	 * @return array of this row.
	 */
	public abstract String[] getRow(int row)throws ExcelException;
	/**
	 * Get first column index of row.
	 * @param row the row index.
	 * @return the first column index of row.
	 */
	public abstract short getFirstColIndex(int row)throws ExcelException;
	/**
	 * Get last column index of row.
	 * @param row the row index.
	 * @return the last column index of row.
	 */
	public abstract short getLastColIndex(int row)throws ExcelException;
	/**
	 * @param row
	 * @param s
	 * @throws ExcelException
	 */
	public abstract void setRow(int row,String []s)throws ExcelException;
	/**
	 * Add row to this sheet.
	 * NOTE this is a stream interface.
	 * @param s the row strings
	 * @see FileSheetStreamWriter
	 * @throws ExcelException
	 */
	public abstract void addRow(String []s)throws ExcelException;
	/**
	 * @return first column index of this sheet
	 */
	public abstract int getFirstColumn();
	/**
	 * @return first row index of this sheet
	 */
	public abstract int getFirstRow();
	/**
	 * @return last column index of this sheet
	 */
	public abstract int getLastColumn();

	/**
	 * @return last row index of this sheet
	 */
	public abstract int getLastRow();

}
