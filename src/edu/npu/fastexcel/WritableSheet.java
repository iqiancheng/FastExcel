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

import edu.npu.fastexcel.biff.write.SheetWriter;
import edu.npu.fastexcel.compound.io.WriteException;
import edu.npu.fastexcel.compound.stream.WritableStream;

/**
 * @see WritableStream
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-2-16
 */
public class WritableSheet implements Sheet {

	SheetWriter sheetStreamWriter;
	
	WritableSheet(SheetWriter writer) {
		this.sheetStreamWriter=writer;
	}
	
	

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getFirstColumn()
	 */
	public int getFirstColumn() {
		return sheetStreamWriter.getDimension().firstCol;
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getFirstRow()
	 */
	public int getFirstRow() {
		return sheetStreamWriter.getDimension().firstRow;
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getIndex()
	 */
	public int getIndex() {
		return sheetStreamWriter.getIndex();
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getLastColumn()
	 */
	public int getLastColumn() {
		return sheetStreamWriter.getDimension().lastCol;
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getLastRow()
	 */
	public int getLastRow() {
		return sheetStreamWriter.getDimension().lastRow;
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getName()
	 */
	public String getName() {
		return sheetStreamWriter.getName();
	}

	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getRow(int)
	 */
	public String[] getRow(int row) {
		throw new IllegalStateException("Writable Sheet can not getRow");
	}
	/* (non-Javadoc)
	 * @see edu.npu.fastexcel.Sheet#getCell(int, int)
	 */
	public String getCell(int row, int col) {
		throw new IllegalStateException("Writable Sheet can not getCell");
	}
	/**
	 * 
	 */
	public void setCell(int row, int col, String str) throws ExcelException {
		try {
			sheetStreamWriter.setCell(row, col, str);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}
	public void setRow(int row, String[] s) throws ExcelException {
		// TODO implement
		if (s == null) {
			return;
		}
		try {
			for (int i = 0; i < s.length; i++) {
				sheetStreamWriter.setCell(row, i, s[i]);
			}
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}
	public void addRow(String[] s) throws ExcelException {
		if (s == null) {
			return;
		}
		try {
			sheetStreamWriter.addRow(s);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}
	public short getFirstColIndex(int row) throws ExcelException {
		throw new IllegalStateException("Writable Sheet can not getFirstColIndex");
	}
	public short getLastColIndex(int row) throws ExcelException {
		throw new IllegalStateException("Writable Sheet can not getLastColIndex");
	}

}
