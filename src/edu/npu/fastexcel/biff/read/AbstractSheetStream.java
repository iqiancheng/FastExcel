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
package edu.npu.fastexcel.biff.read;

import edu.npu.fastexcel.ExcelException;

/**
 * The whole worksheet document consists of the Worksheet Stream . All records
 * of the worksheet are enclosed by a leading BOF record and a trailing EOF
 * record. The stream contains all information about the worksheet,for instance
 * sheet type (general sheet, chart, macro sheet), sheet dimension, view
 * settings, a font list, a list of defined names and external references, of
 * course the contents and formats of all cells, row heights, column widths,
 * etc.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-16
 */
public abstract class AbstractSheetStream extends SubStream {

	// NOTE:If someone would like to extend fastexcel, let fastexcel support
	// more features,this class is a good example.maybe u need a sheet stream
	// which need to support font ,then change this class,add
	// setFont(int r,int c,Font f) method (maybe) and implement it in subclass

	protected int index; // index of this sheet
	protected String name; // name of this sheet
	protected int state; // state of this sheet
	protected int type; // type of this sheet.(chart,sheet...)
	protected WorkBookGlobalsStream workBookGlobalsStream;
	
	public static final int MAX_ROW =  (1 << 16);
	public static final int MAX_COLUMN = 1 << 8;
	
	public AbstractSheetStream(SheetStream stream,WorkBookGlobalsStream wgs) {
		super();
		this.workBookGlobalsStream=wgs;
		this.index = stream.index;
		this.name = stream.name;
		this.state = stream.state;
		this.type = stream.type;
		this.offset = stream.offset;
	}

	// here is the interface of sheetStream.
	// NOTE all parsers may call these methods.
	/**
	 * Set dimension of this sheet.
	 * 
	 * @param rf
	 *            first row
	 * @param rl
	 *            last row
	 * @param cf
	 *            first column
	 * @param cl
	 *            last column
	 */
	public abstract void setDimension(int rf, int rl, int cf, int cl);

	/**
	 * Set cell content.
	 * 
	 * @param row
	 *            row index of this cell.
	 * @param col
	 *            column index of this cell.
	 * @param sst
	 *            SST index  of this cell.
	 * @throws ExcelException 
	 */
	public abstract void setContent(int row, int col, int sst) 
		throws ExcelException;
	public abstract void setContent(int row, int col, String str);
	
	/**
	 * Set row dimension.
	 * 
	 * @param row
	 *            row index.
	 * @param cf
	 *            first column index of this row.
	 * @param cl
	 *            last column index of this row.
	 */
	public abstract void setRow(int row, int cf, int cl);
	public abstract short getFirstCol(int row)throws ExcelException;
	public abstract short getLastCol(int row)throws ExcelException;
	//
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @param index
	 *            the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the state
	 */
	public int getState() {
		return state;
	}

	/**
	 * @param state
	 *            the state to set
	 */
	public void setState(int state) {
		this.state = state;
	}

}
