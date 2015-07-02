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

import java.io.File;
import java.text.SimpleDateFormat;

import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.read.BIFFReader;
import edu.npu.fastexcel.biff.read.DefaultSheetStream;
import edu.npu.fastexcel.biff.read.EventBasedSheetStream;
import edu.npu.fastexcel.biff.read.SubStream;
import edu.npu.fastexcel.biff.read.WorkBookGlobalsStream;
import edu.npu.fastexcel.compound.io.ReadException;

/**
 * @see ReadableSheet
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
class ReadableWorkbook implements Workbook {
	private File file;
	private BIFFReader biffReader;// the BIFF file reader.
	//
	private WorkBookGlobalsStream globalsStream;

	/**
	 * Create workbook using exists file.
	 * 
	 * @param file
	 *            excel file.
	 */
	public ReadableWorkbook(File file) {
		this.file = file;
		biffReader = new BIFFReader(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#getFile()
	 */
	public File getFile() {
		return file;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#setFile(java.io.File)
	 */
	public void setFile(File file) {
		this.file = file;
		biffReader.setFile(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#open()
	 */
	public void open() throws ExcelException {
		try {
			biffReader.open();
			biffReader.readWorkBookGlobals();
			globalsStream = 
				(WorkBookGlobalsStream) biffReader.getWorkBookStream()
					.getSubStream(SubStream.STREAM_WORKBOOK_GLOBALS);
		} catch (ReadException e) {
			throw new ExcelException(e);
		} catch (ParserException e) {
			throw new ExcelException(e);
		}

	}
	public Format getFormat(int index) throws ExcelException{
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		return globalsStream.getFormat(index);
	}
	public void addUserDefineFormat(int index,int type,String formatStr) 
		throws ExcelException{
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		if(index<0){
			throw new IllegalArgumentException("Format index must >=0");
		}
		globalsStream.addFormat(index, type,formatStr);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#close()
	 */
	public void close() throws ExcelException {
		try {
			biffReader.close();
		} catch (ReadException e) {
			throw new ExcelException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#sheetCount()
	 */
	public int sheetCount() {
		return globalsStream == null ? 0 : globalsStream.sheetCount();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#sheetNames()
	 */
	public String[] sheetNames() {
		return globalsStream.sheetNames();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Workbook#getSheet(int,
	 * edu.npu.fastexcel.SheetReadListener)
	 */
	public void getSheet(int index, SheetReadListener l) throws ExcelException {
		if (index < 0 || index >= globalsStream.sheetCount()) {
			throw new ExcelException("can not find this sheet,index=" + index);
		}
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		EventBasedSheetStream ss = new EventBasedSheetStream(
				globalsStream.getSheetStream(index),globalsStream);
		ss.setListener(l);
		try {
			biffReader.readSheet(ss);
		} catch (ReadException e) {
			throw new ExcelException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.Workbook#getSheet(java.lang.String,
	 * edu.npu.fastexcel.SheetReadListener)
	 */
	public void getSheet(String name, SheetReadListener l)
			throws ExcelException {
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		EventBasedSheetStream ss = new EventBasedSheetStream(globalsStream
				.getSheetStream(name),globalsStream);
		if (ss == null) {
			throw new ExcelException("can not find this sheet,name=" + name);
		}
		ss.setListener(l);
		try {
			biffReader.readSheet(ss);
		} catch (ReadException e) {
			throw new ExcelException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#getSheet(int)
	 */
	public Sheet getSheet(int index) throws ExcelException {
		if (index < 0 || index >= globalsStream.sheetCount()) {
			throw new ExcelException("can not find this sheet,index=" + index);
		}
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		DefaultSheetStream ss = new DefaultSheetStream(globalsStream
				.getSheetStream(index),globalsStream);
		if (ss == null) {
			throw new ExcelException("can not find this sheet,index=" + index);
		}
		try {
			biffReader.readSheet(ss);
		} catch (ReadException e) {
			throw new ExcelException(e);
		}
		return new ReadableSheet(ss);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#getSheet(java.lang.String)
	 */
	public Sheet getSheet(String name) throws ExcelException {
		if (globalsStream == null) {
			throw new ExcelException(
					"Workbook did not open.Invoke open() first");
		}
		DefaultSheetStream stream = new DefaultSheetStream(globalsStream
				.getSheetStream(name),globalsStream);
		if (stream == null) {
			throw new ExcelException("can not find this sheet,name=" + name);
		}

		try {
			biffReader.readSheet(stream);
		} catch (ReadException e) {
			throw new ExcelException(e);
		}
		return new ReadableSheet(stream);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#enableFormatNumber()
	 */
	public void enableFormatNumber() {
		biffReader.getSetting().setFormatNumber(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#disableFormatNumber()
	 */
	public void disableFormatNumber() {
		biffReader.getSetting().setFormatNumber(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#enableFormatDate()
	 */
	public void enableFormatDate() {
		biffReader.getSetting().setFormatDate(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#disableFormatDate()
	 */
	public void disableFormatDate() {
		biffReader.getSetting().setFormatDate(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#enableCommonDateFormat()
	 */
	public void enableCommonDateFormat() {
		biffReader.getSetting().setCommonDateFormat(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#disableCommonDateFormat()
	 */
	public void disableCommonDateFormat() {
		biffReader.getSetting().setCommonDateFormat(false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.WorkBook#setCommonDateFormat(java.text.SimpleDateFormat
	 * )
	 */
	public void setCommonDateFormat(SimpleDateFormat dateFormat) {
		biffReader.getSetting().setDateFormat(dateFormat);
	}

	public Sheet addSheet(String name) {
		throw new IllegalStateException("Readable Workbook can not add sheet.");
	}

	public Sheet addStreamSheet(String name) throws ExcelException {
		throw new IllegalStateException("Readable Workbook can not add sheet.");
	}
	public void setSSTType(int type) {
		biffReader.getSetting().setSSTType(type);
	}

}
