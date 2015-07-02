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

import edu.npu.fastexcel.biff.read.DefaultSheetStream;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
class ReadableSheet implements Sheet {

	private DefaultSheetStream sheetStream;

	/* package constructor */
	ReadableSheet(DefaultSheetStream stream) {
		this.sheetStream = stream;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getName()
	 */
	public String getName() {
		return sheetStream.getName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getIndex()
	 */
	public int getIndex() {
		return sheetStream.getIndex();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getCell(int, int)
	 */
	public String getCell(int row, int col) throws ExcelException {
		return sheetStream.getCell(row, col);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getRow(int)
	 */
	public String[] getRow(int row) throws ExcelException {
		return sheetStream.getRow(row);
	}
	public short getFirstColIndex(int row) throws ExcelException {
		return sheetStream.getFirstCol(row);
	}
	public short getLastColIndex(int row) throws ExcelException {
		return sheetStream.getLastCol(row);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getFirstColumn()
	 */
	public int getFirstColumn() {
		return sheetStream.getFirstColumn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getFirstRow()
	 */
	public int getFirstRow() {
		return sheetStream.getFirstRow();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getLastColumn()
	 */
	public int getLastColumn() {
		return sheetStream.getLastColumn();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Sheet#getLastRow()
	 */
	public int getLastRow() {
		return sheetStream.getLastRow();
	}

	public String toString() {
		return sheetStream.toString();
	}

	public void setCell(int row, int col, String str) {
		throw new IllegalStateException("Readable Sheet can not setCell");
	}
	public void setIndex(int index) {
		throw new IllegalStateException("Readable Sheet can not setIndex");
	}
	public void setName() {
		throw new IllegalStateException("Readable Sheet can not setName");
	}
	public void setRow(int row, String[] s) {
		throw new IllegalStateException("Readable Sheet can not setRow");
	}

	public void setName(String name) {
		throw new IllegalStateException("Readable Sheet can not setName");
	}

	public void addRow(String[] s) throws ExcelException {
		throw new IllegalStateException("Readable Sheet can not addRow");
	}	
}
