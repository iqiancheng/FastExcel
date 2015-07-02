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
import java.util.List;

import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.write.BIFFWriter;
import edu.npu.fastexcel.biff.write.SheetWriter;
import edu.npu.fastexcel.biff.write.WorkBookGlobalsStreamWriter;
import edu.npu.fastexcel.compound.io.WriteException;

/**
 * @see WritableSheet
 * @author <a href="guooscar@gmail.com">yAma</a>2009-2-16
 */
class WritableWorkbook implements Workbook {
	private File file;
	private BIFFWriter biffWriter;
	WorkBookGlobalsStreamWriter workbookGlobalsStreamWriter;

	/**
	 * Create workbook using exists file.
	 * 
	 * @param file
	 *            excel file.
	 */
	public WritableWorkbook(File file) {
		this.file = file;
		biffWriter = new BIFFWriter(file);
	}

	public Sheet addStreamSheet(String name) throws ExcelException {
		if (workbookGlobalsStreamWriter == null) {
			throw new IllegalStateException("Open Workbook first.");
		}
		SheetWriter ssw;
		try {
			ssw = workbookGlobalsStreamWriter.addSheet(name, true);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
		return new WritableSheet(ssw);
	}

	public Sheet addSheet(String name) throws ExcelException {
		if (workbookGlobalsStreamWriter == null) {
			throw new IllegalStateException("Open Workbook first.");
		}
		SheetWriter ssw;
		try {
			ssw = workbookGlobalsStreamWriter.addSheet(name, false);
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
		return new WritableSheet(ssw);
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
		biffWriter.setFile(file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#open()
	 */
	public void open() throws ExcelException {
		if (workbookGlobalsStreamWriter != null) {
			throw new IllegalStateException("Workbook already opened.");
		}
		try {
			workbookGlobalsStreamWriter = biffWriter.open();
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#close()
	 */
	public void close() throws ExcelException {
		if (workbookGlobalsStreamWriter == null) {
			throw new IllegalStateException("Open Workbook first.");
		}
		try {
			biffWriter.close();
		} catch (WriteException e) {
			throw new ExcelException(e);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#sheetCount()
	 */
	public int sheetCount() {
		if (workbookGlobalsStreamWriter != null) {
			throw new IllegalStateException("Open Workbook first.");
		}
		return biffWriter.getWorkbookGlobalsStreamWriter()
				.getSheetStreamWriters().size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.WorkBook#sheetNames()
	 */
	public String[] sheetNames() {
		if (workbookGlobalsStreamWriter != null) {
			throw new IllegalStateException("Open Workbook first.");
		}
		List l = biffWriter.getWorkbookGlobalsStreamWriter()
				.getSheetStreamWriters();
		String names[] = new String[l.size()];
		for (int i = 0; i < names.length; i++) {
			names[i] = ((SheetWriter) l.get(i)).getName();
		}
		return names;
	}

	public void getSheet(int index, SheetReadListener l) throws ExcelException {
		throw new IllegalStateException("Writable Workbook can not getSheet.");
	}

	public void getSheet(String name, SheetReadListener l)
			throws ExcelException {
		throw new IllegalStateException("Writable Workbook can not getSheet.");
	}

	public Sheet getSheet(int index) throws ExcelException {
		throw new IllegalStateException("Writable Workbook can not getSheet.");
	}

	public Sheet getSheet(String name) throws ExcelException {
		throw new IllegalStateException("Writable Workbook can not getSheet.");
	}

	public void enableFormatNumber() {
		throw new IllegalStateException(
				"Writable Workbook can not enableFormatNumber.");
	}

	public void disableFormatNumber() {
		throw new IllegalStateException(
				"Writable Workbook can not disableFormatNumber.");
	}

	public void enableFormatDate() {
		throw new IllegalStateException(
				"Writeable Workbook can not enableFormatDate.");
	}

	public void disableFormatDate() {
		throw new IllegalStateException(
				"Writable Workbook can not disableFormatDate.");
	}

	public void enableCommonDateFormat() {
		throw new IllegalStateException(
				"Writable Workbook can not enableCommonDateFormat.");
	}

	public void disableCommonDateFormat() {
		throw new IllegalStateException(
				"Writable Workbook can not disableCommonDateFormat.");
	}

	public void setCommonDateFormat(SimpleDateFormat dateFormat) {
		throw new IllegalStateException(
				"Writable Workbook can not setCommonDateFormat.");
	}

	public void addUserDefineFormat(int index, int type, String formatStr)
			throws ExcelException {
		throw new IllegalStateException(
				"Writable Workbook can not addUserDefineFormat.");
	}

	public Format getFormat(int index) throws ExcelException {
		throw new IllegalStateException("Writable Workbook can not getFormat.");
	}

	public void setSSTType(int type) {
		throw new IllegalStateException(
				"Writable Workbook can not set sst type.");
	}

}
