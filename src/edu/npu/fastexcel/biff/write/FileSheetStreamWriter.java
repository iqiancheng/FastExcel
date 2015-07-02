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

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import edu.npu.fastexcel.biff.Dimension;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.biff.record.RecordFactory;
import edu.npu.fastexcel.biff.record.cell.LabelSSTRecord;
import edu.npu.fastexcel.biff.record.sheet.DimensionRecord;
import edu.npu.fastexcel.biff.record.sheet.RowRecord;
import edu.npu.fastexcel.compound.io.WriteException;

/**
 * The FileSheetStreamWriter use Template file to store sheet stream. so it
 * needs little memory but slow and unstable,:) your choice.
 * NOTE:FileSheetStreamWriter just support iteration/stream operation.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-22
 */
public class FileSheetStreamWriter implements StreamWriter, SheetWriter {
	
	private int position;
	/* name of this sheet */
	private String name;
	private Dimension dimension;
	private int index;
	/* the temp file which stored records */
	private File tempFile;
	private BufferedOutputStream bos;
	private int currentRow;
	LabelSSTRecord labelSSTRecord = new LabelSSTRecord();
	RowRecord rowRecord = new RowRecord();
	

	WorkBookGlobalsStreamWriter workBookGlobalsStreamWriter;
	RecordFactory factory;

	public FileSheetStreamWriter(WorkBookGlobalsStreamWriter writer,
			RecordFactory factory) {
		this.workBookGlobalsStreamWriter = writer;
		this.factory = factory;
		dimension = new Dimension();
	}

	public void open() throws WriteException {
		String fileName =
			"sheet" + "_" + name + "_"+ System.currentTimeMillis();
		try {
			tempFile = File.createTempFile(fileName, ".tmp");
			bos=new BufferedOutputStream(new FileOutputStream(tempFile));
		} catch (IOException e) {
			throw new WriteException("Can not create temp file:" + fileName
					+ ".tmp", e);
		}
		currentRow=0;
		//
		dimension.firstRow=0;
        dimension.firstCol=0;
		dimension.lastCol = 0;
		dimension.lastRow = 0;
	}

	public void close() throws WriteException {
		try {
			bos.close();
		} catch (IOException e) {
			throw new WriteException("Can not close temp file.", e);
		}finally{
			tempFile.delete();
		}
	}

	/**
	 * @return the position
	 */
	public int position() throws WriteException {
		return position;
	}

	/**
	 * Write record to workbook stream.
	 */
	public void flush() throws WriteException {
		position = workBookGlobalsStreamWriter.position();
		writeCommonStart();
		writeDimension();
		writeCell();
		writeCommonEnd();
	}
	
	/**
	 * 
	 */
	public void addRow(String[] cell) throws WriteException {
		if(cell==null||cell.length==0){
			return;
		}
		if(currentRow>MAX_ROW||cell.length>MAX_COLUMN){
			throw new WriteException("Bad Dimension."+currentRow+","+cell.length);
		}
		int rowcell[]=new int[cell.length];
		Arrays.fill(rowcell, UNUSED_COLUMN);
		for(int i=0;i<cell.length;i++){
			if(cell[i]==null||cell[i].trim().length()==0){
				continue;
			}
			if(cell[i].length()>MAX_CELL_STRING_LEN){
				throw new WriteException("Cell string length should less than "
							+MAX_CELL_STRING_LEN);
			}
			int index = workBookGlobalsStreamWriter.addSharedString(cell[i]);
			rowcell[i]=index;
		}
		addRow0(currentRow, rowcell);
		currentRow++;
		
	}
	private void addRow0(int row,int sst[])throws WriteException {
		setDimension(row, (sst.length-1));
		rowRecord.setFirstColumn(0);
		rowRecord.setLastColumn(sst.length-1);
		rowRecord.setIndex(row);
		try {
			bos.write(rowRecord.getBytes());
			for(int i=0;i<sst.length;i++){
				if(sst[i]==UNUSED_COLUMN){
					continue;
				}
				labelSSTRecord.setColumn(i);
				labelSSTRecord.setRow(row);
				labelSSTRecord.setSST(sst[i]);
				bos.write(labelSSTRecord.getBytes());
			}
		} catch (IOException e) {
			tempFile.delete();
			throw new WriteException("Add row :"+row+" error",e);
		}
	}
	/**
	 * Add a cell
	 * 
	 * @param row
	 * @param col
	 * @param str
	 * @throws WriteException
	 */
	public void setCell(int row, int col, String str) throws WriteException {
		throw new IllegalStateException("Not Support Yet");
	}

	private void setDimension(int row, int col) {
		if(col>=dimension.lastCol){
			dimension.lastCol=(col+1);
		}
	}

	

	/*
	 * Write common records at the beginning of sheet stream
	 */
	private void writeCommonStart() throws WriteException {
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
	 * Write common records at the end of sheet stream
	 */
	private void writeCommonEnd() throws WriteException {
		writeRecord(factory.getWindow2());
		writeRecord(factory.getWeid1());
		writeRecord(factory.getButtonPropertySet());
		writeRecord(factory.getEOF());
	}

	private void writeDimension() throws WriteException {
		DimensionRecord dr=new DimensionRecord();
		dimension.lastRow=currentRow;
        dr.setFirstColumn(dimension.firstCol);
        dr.setFirstRow(dimension.firstRow);
        dr.setLastRow(dimension.lastRow);
        dr.setLastColumn(dimension.lastCol);
        writeRecord(dr);
	}

	private void writeCell() throws WriteException {
		BufferedInputStream bis=null;
		try {
			bos.flush();//flush
			bis = new BufferedInputStream(
					new FileInputStream(tempFile));
			final int BUFFER_SIZE=512;
			byte[] buffer = new byte[BUFFER_SIZE];
			int byteSize=0;
			while ((byteSize=bis.read(buffer)) != -1) {
				if(byteSize==BUFFER_SIZE){
					workBookGlobalsStreamWriter.writeRecord(buffer);
				}else{
					byte bb[]=new byte[byteSize];
					System.arraycopy(buffer,0, bb, 0,byteSize);
					workBookGlobalsStreamWriter.writeRecord(bb);
				}
			}
		} catch (IOException e) {
			if(bis!=null){
				try {bis.close();} catch (IOException ee) {}
			}
			tempFile.delete();
			throw new WriteException(e);
		}
	}

	/* write record to workbook stream */
	private void writeRecord(Record record) throws WriteException {
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
	void setIndex(int index) {
		this.index = index;
	}

	/**
	 * @return the dimension
	 */
	public Dimension getDimension() {
		return dimension;
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileSheetStreamWriter other = (FileSheetStreamWriter) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(dimension);
		return sb.toString();
	}
}
