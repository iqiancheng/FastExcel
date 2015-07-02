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
package edu.npu.fastexcel.biff.write;

import java.util.Arrays;
import java.util.Iterator;
import java.util.TreeSet;

import edu.npu.fastexcel.biff.Dimension;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.biff.record.RecordFactory;
import edu.npu.fastexcel.biff.record.cell.LabelSSTRecord;
import edu.npu.fastexcel.biff.record.sheet.DimensionRecord;
import edu.npu.fastexcel.biff.record.sheet.RowRecord;
import edu.npu.fastexcel.common.util.StringUtil;
import edu.npu.fastexcel.compound.io.WriteException;

/**
 * thanks to 高雨峰 <sillyfox008@sina.com>
 * fix bug that when writing excel,the dimension 's last row is wrong.
 *	
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-1-22
 */
public class DefaultSheetStreamWriter implements StreamWriter,SheetWriter{
	
	
	private int position;
	/*name of this sheet*/
	private String name;
	private Dimension dimension;
	private TreeSet recordSet;
	private int index;
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	void setIndex(int index) {
		this.index = index;
	}
	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}
	WorkBookGlobalsStreamWriter workBookGlobalsStreamWriter;
	RecordFactory factory;
	
	public DefaultSheetStreamWriter(WorkBookGlobalsStreamWriter writer,
			RecordFactory factory) {
		this.workBookGlobalsStreamWriter=writer;
		this.factory=factory;
		dimension=new Dimension();
	}
	public void open() throws WriteException {
		recordSet=new TreeSet();
		dimension.firstRow=MAX_ROW;
        dimension.firstCol=MAX_COLUMN;
		dimension.lastCol=-1;
		dimension.lastRow=0;
	}
	public void close() throws WriteException {
		
	}
	/**
	 * @return the position
	 */
	public int position() throws WriteException{
		return position;
	}
	/**
	 * Write record to workbook stream.
	 */
	public void flush() throws WriteException {
		position=workBookGlobalsStreamWriter.position();
		writeCommonStart();
		writeDimension();
		writeCell();
		writeCommonEnd();
	}
	/**
	 * 
	 */
	public void addRow(String[] cell) throws WriteException {
		throw new IllegalStateException("Not Support Yet");
	}
	/**
	 * Add a cell
	 * @param row
	 * @param col
	 * @param str
	 * @throws WriteException
	 */
	public void setCell(int row,int col,String str) throws WriteException{
		if(row<0||col<0||row>MAX_ROW||col>MAX_COLUMN){
			throw new WriteException("Bad dimension:"+row+"-"+col);
		}
		if(str==null||str.trim().equals("")){
			return;//can not add null string to SST 
		}
		if(str.length()>MAX_CELL_STRING_LEN){
			throw new WriteException("Cell string length should less than "
					+MAX_CELL_STRING_LEN);
		}
		int index=workBookGlobalsStreamWriter.addSharedString(str);
		setCell(row, col, index);
	}
	private void setDimension(int row,int col){
		if(row<dimension.firstRow){
			dimension.firstRow=row;
		}
		//thanks to 高雨峰 <sillyfox008@sina.com>
		//fix bug that when writing excel,the dimension 's last row is wrong.
		if(row>=dimension.lastRow){
			dimension.lastRow=(row+1);
		}
		if(col<dimension.firstCol){
			dimension.firstCol=col;
		}
		if(col>=dimension.lastCol){
			dimension.lastCol=(col+1);
		}
	}
	private void setCell(int row,int col,int sst){
		setDimension(row, col);
		Row r=new Row();
		r.column.index=row;
		if(recordSet.contains(r)){
			//
			r.setCell(row, col, sst);
		}else{
			r.column.firstColumn=col;
			r.column.lastColumn=col;
			r.column.array=new int[1];
			r.column.array[0]=sst;
			recordSet.add(r);
		}
	}
	/*
	 *Write common records at the beginning of sheet stream
	 */
	private void writeCommonStart() throws WriteException{
		writeRecord(factory.getSheetBOF());
		writeRecord(factory.getCalcMode());
		writeRecord(factory.getRefMode());
		writeRecord(factory.getIteration());
		writeRecord(factory.getDelta());
		writeRecord(factory.getSaveRecalc());
		writeRecord(factory.getPrintHeaders());
		writeRecord(factory.getGridSet());
		writeRecord(factory.getGuts());
		writeRecord(factory.getDefaultRowHeight());
		writeRecord(factory.getWsBool());
		writeRecord(factory.getHeader());
		writeRecord(factory.getFooter());
		writeRecord(factory.getHCenter());
		writeRecord(factory.getVCenter());
		writeRecord(factory.getSetup());
		writeRecord(factory.getDefColWidth());
	}
	
	/*
	 *Write common records at the end of sheet stream 
	 */
	private void writeCommonEnd() throws WriteException{
		writeRecord(factory.getWindow2());
		writeRecord(factory.getWeid1());
		writeRecord(factory.getButtonPropertySet());
		writeRecord(factory.getEOF());
	}
	private void writeDimension() throws WriteException{
		DimensionRecord dr=new DimensionRecord();
        if(recordSet.isEmpty()){
            dr.setFirstColumn(0);
            dr.setFirstRow(0);
            dr.setLastColumn(0);
            dr.setLastRow(0);
        }else{
            dr.setFirstColumn(dimension.firstCol);
            dr.setFirstRow(dimension.firstRow);
            dr.setLastRow(dimension.lastRow);
            dr.setLastColumn(dimension.lastCol);    
        }
        writeRecord(dr);
	}
	
	private void writeCell() throws WriteException{
		RowRecord rowRecord=new RowRecord();
		LabelSSTRecord labelSSTRecord=new LabelSSTRecord();
		Iterator it=recordSet.iterator();
		Row row;
		while(it.hasNext()){
			row=(Row) it.next();
			rowRecord.setFirstColumn(row.column.firstColumn);
			rowRecord.setLastColumn(row.column.lastColumn);
			rowRecord.setIndex(row.column.index);
			writeRecord(rowRecord);
			labelSSTRecord.setRow(row.column.index);
			labelSSTRecord.setXF(0);
			for(int i=0;i<row.column.array.length;i++){
				if(row.column.array[i]!=UNUSED_COLUMN){
					labelSSTRecord.setSST(row.column.array[i]);
					labelSSTRecord.setColumn(i+row.column.firstColumn);
					writeRecord(labelSSTRecord);
				}
			}
		}
	}
	/*write record to workbook stream*/
	private void writeRecord(Record record) throws WriteException{
		workBookGlobalsStreamWriter.writeRecord(record);
	}
	
	/*
	 * set sheet's name
	 */
	void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DefaultSheetStreamWriter other = (DefaultSheetStreamWriter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	
	class ContentHolder{
		int array[];
		int index;
		int firstColumn;
		int lastColumn;
		public ContentHolder() {
			array=new int[0];
			firstColumn=MAX_COLUMN;
		}
	}
	/**
	 * 
	 * @author <a href="guooscar@gmail.com">yAma</a>
	 * 2009-2-16
	 */
	class Row implements Comparable{
		
		
		ContentHolder column;
		public Row() {
			column=new ContentHolder();
		}
		public void setCell(int row,int col,int sst){
			/*A           B          C
			 *------|-----------|--------
			 *     fc           lc
			 */
			if(column.firstColumn>col){
				//state A
				column.firstColumn=col;
				int nn[]=new int[column.lastColumn-column.firstColumn+1];
				Arrays.fill(nn, UNUSED_COLUMN);
				System.arraycopy(column.array, 0, nn,1, column.array.length);
				column.array=nn;
				column.array[0]=sst;
			}else if(column.lastColumn<col){
				//state C
				column.lastColumn=col;
				int nn[]=new int[column.lastColumn-column.firstColumn+1];
				Arrays.fill(nn, UNUSED_COLUMN);
				System.arraycopy(column.array, 0, nn,0, column.array.length);
				column.array=nn;
				column.array[column.array.length-1]=sst;
			}else{
				column.array[col-column.firstColumn]=sst;
			}
		}
		
		public int compareTo(Object o) {
			Row other = (Row) o;
			if(other.column.index==this.column.index){
				this.column=other.column;
				return 0;
			}
			return this.column.index-other.column.index;
		}
		public String toString() {
			return column.index+","+column.firstColumn+","+column.lastColumn
			+"--"+StringUtil.join(column.array)+"\n";
		}
	}
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append(dimension);
		sb.append("\n"+recordSet);
		return sb.toString();
	}
	
	
	
}
