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
package edu.npu.fastexcel.biff.read;

import java.util.ArrayList;
import java.util.HashMap;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.biff.Dimension;


/**
 * A default implemention of <code>AbstractSheetStream</code>.
 * DefaultSheetStream store all sheet content(cell string,dimension information
 * etc.).Another fast version is EventBasedSheetStream which supports more
 * flexible API.
 * 
 * @see EventBasedSheetStream
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class DefaultSheetStream extends AbstractSheetStream {

	
	private Dimension dimension;
	//
	private ArrayList innerStringTable;
	private int[]contentOffset;
	private HashMap contentMap;
	/**
	 * 
	 */
	public DefaultSheetStream(SheetStream stream,WorkBookGlobalsStream wgs) {
		super(stream,wgs);
		dimension=new Dimension();
		contentMap=new HashMap();
		contentOffset=new int[0];
		innerStringTable=new ArrayList();
		eof = false;
	}
	private final String getContent(int index) throws ExcelException{
		if(index>=0){
			return workBookGlobalsStream.getSST(index);
		}else{
			return (String) innerStringTable.get((index*-1)-1);
		}
	}
	

	/**
	 * Get cell content as string
	 * 
	 * @param r
	 *            row of cell
	 * @param c
	 *            column of cell
	 * @return cell string
	 * @throws ExcelException 
	 */
	public String getCell(int row, int col) throws ExcelException {
		if(row<dimension.firstRow
				||col<dimension.firstCol
				||row>dimension.lastRow
				||col>dimension.lastCol){
			throw new ExcelException("no such cell:"+row+","+col);
		}
		row =  (row & 0xFFFF);
		col =  (col & 0xFFFF);
		int c = row << 16 | col;
		Integer sindex=(Integer) contentMap.get(new Integer(c));
		if(sindex==null){
			return null;
		}
		int index=sindex.intValue();
		return getContent(index);
	}

	/**
	 * Get row by index.
	 * 
	 * @param row
	 *            row index.
	 * @return row content.
	 * @throws ExcelException 
	 */
	public String[] getRow(int row) throws ExcelException {
		if(row<dimension.firstRow||row>dimension.lastRow){
			throw new ExcelException("no such row:"+row);
		}
		int index = row - dimension.firstRow;
		int r=contentOffset[index];
		if(r==0){
			return null;
		}
		int cl=r&0x0000FFFF;
		int cf=(r&0xFFFF0000)>>16;
		int count=cl-cf;
		String ss[]=new String[count];
		for(int i=0;i<count;i++){
			row =  (row & 0xFFFF);
			int col =  ((cf+i) & 0xFFFF);
			int c = row << 16 | col;
			Integer sindex=(Integer) contentMap.get(new Integer(c));
			if(sindex==null){
				continue;
			}
			ss[i]=getContent(sindex.intValue());
		}
		return ss;
	}
	public short getFirstCol(int row) throws ExcelException {
		if(row<dimension.firstRow||row>dimension.lastRow){
			throw new ExcelException("no such row:"+row);
		}
		int index = row - dimension.firstRow;
		int r=contentOffset[index];
		if(r==0){
			return 0;
		}
		int cf=(r&0xFFFF0000)>>16;
		return (short)cf ;
	}
	public short getLastCol(int row) throws ExcelException {
		if(row<dimension.firstRow||row>dimension.lastRow){
			throw new ExcelException("no such row:"+row);
		}
		int index = row - dimension.firstRow;
		int r=contentOffset[index];
		if(r==0){
			return 0;
		}
		int cl=r&0x0000FFFF;
		return (short) cl;
	}
	/**
	 * @param firstRow
	 *            the firstRow to set
	 */
	public void setDimension(int rf, int rl, int cf, int cl) {
		/*some WTF excels has more than one dimension,so take first one,ignore 
		 * others*/
		if(!dimension.isEmpty()){
			return;
		}
		dimension.firstRow = rf;
		dimension.firstCol = cf;
		dimension.lastRow = rl;
		dimension.lastCol = cl;
		contentOffset=new int[rl-rf+1];
	}

	/**
	 * Setup row attribute. The first element of row array is a mix number which
	 * high 16 bits is cf and low 16 bits is cl.
	 * 
	 * @param row
	 *            row index.
	 * @param cf
	 *            first column index of this row.
	 * @param cl
	 *            last column index of this row.
	 */
	public void setRow(int row, int cf, int cl) {
		int index = row - dimension.firstRow;
		cf = (cf & 0xFFFFFFFF);
		cl =  (cl & 0xFFFFFFFF);
		int c = cf << 16 | cl;
		contentOffset[index]= c;
	}

	/**
	 * Setup content of cell.
	 * 
	 * @param row
	 *            cell row index.
	 * @param col
	 *            cell column index.
	 * @param sstIndex
	 *            sst index of cell content.
	 */
	public void setContent(int row, int col, int sstIndex) {
		if (row < dimension.firstRow 
			|| row > dimension.lastRow 
			|| col < dimension.firstCol
			|| col > dimension.lastCol) {
			return;
		}
		row =  (row & 0xFFFF);
		col =  (col & 0xFFFF);
		int c = row << 16 | col;
		contentMap.put(new Integer(c), new Integer(sstIndex));
	}
	public void setContent(int row, int col, String str) {
		innerStringTable.add(str);
		row =  (row & 0xFFFF);
		col =  (col & 0xFFFF);
		int c = row << 16 | col;
		contentMap.put(new Integer(c),new Integer(innerStringTable.size()*-1));
	}
	/**
	 * @return the lastRow
	 */
	public int getLastRow() {
		return dimension.lastRow;
	}

	/**
	 * @return the firstColumn
	 */
	public int getFirstColumn() {
		return dimension.firstCol;
	}
	/**
	 * @return the firstRow
	 */
	public int getFirstRow() {
		return dimension.firstRow;
	}
	/**
	 * @return the lastColumn
	 */
	public int getLastColumn() {
		return dimension.lastCol;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{" + "name:");
		sb.append(name + ",index:");
		sb.append(index + ",firstRow:");
		sb.append(dimension.firstRow + ",lastRow:");
		sb.append(dimension.lastRow + ",firstColumn:");
		sb.append(dimension.firstCol + ",lastColumn:");
		sb.append(dimension.lastCol + ",offset:");
		sb.append(offset);
		sb.append("}");
		return sb.toString();
	}
	

	
}
