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
package edu.npu.fastexcel.biff.write;

import java.io.File;

import edu.npu.fastexcel.biff.record.RecordFactory;
import edu.npu.fastexcel.biff.record.globals.BoundSheetRecord;
import edu.npu.fastexcel.biff.record.globals.SSTRecord;
import edu.npu.fastexcel.compound.CompoundFileWriter;
import edu.npu.fastexcel.compound.DirectoryEntry;
import edu.npu.fastexcel.compound.io.WriteException;
import edu.npu.fastexcel.compound.io.Writer;

/**
 * The BIFF file writer.
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-4
 */
public class BIFFWriter {

	public static final int MAX_SHEET_COUNT = 3;
	/* Excel File */
	private File file;
	/* Workbook stream writer */
	Writer writer;
	/* The compound file writer */
	CompoundFileWriter compoundFileWriter;
	RecordFactory factory;

	BoundSheetRecord sheets[];
	SSTRecord sstRecord;
	WorkBookGlobalsStreamWriter workbookGlobalsStreamWriter;
	
	
	
	public BIFFWriter(File file) {
		this.file = file;
		factory = RecordFactory.getInstance();
	}
	
	/**
	 * @return the workbookGlobalsStreamWriter
	 */
	public WorkBookGlobalsStreamWriter getWorkbookGlobalsStreamWriter() {
		return workbookGlobalsStreamWriter;
	}

	/**
	 * Open this workbook and ready to write
	 * 
	 * @throws WriteException
	 *             throws when some error occur.
	 */
	public WorkBookGlobalsStreamWriter open() throws WriteException {
		compoundFileWriter = new CompoundFileWriter(file);
		compoundFileWriter.open();
		writer = 
			compoundFileWriter.createWriteableStream(DirectoryEntry.WORKBOOK_NAME);
		workbookGlobalsStreamWriter
						=new WorkBookGlobalsStreamWriter(writer,factory);
		workbookGlobalsStreamWriter.open();
		return workbookGlobalsStreamWriter;
	}
	
	/**
	 * @return the file
	 */
	public File getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}
	/**
	 * Close this workbook.
	 * 
	 * @throws WriteException
	 *             throws when some error occur.
	 */
	public void close() throws WriteException {
		workbookGlobalsStreamWriter.close();
		writer.close();
		compoundFileWriter.close();
	}
}
