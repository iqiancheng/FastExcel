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

import java.io.File;
import java.io.IOException;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.ParserFactory;
import edu.npu.fastexcel.biff.parser.RecordParser;
import edu.npu.fastexcel.biff.record.ReadOnlyRecord;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.compound.CompoundFileReader;
import edu.npu.fastexcel.compound.DirectoryEntry;
import edu.npu.fastexcel.compound.io.ReadException;
import edu.npu.fastexcel.compound.io.Reader;

/**
 * The Excel file format is named BIFF (Binary Interchange File Format). It is
 * used to store all types of documents:
 * <ul>
 * <li>worksheet documents</li>
 * <li>workbook documents</li>
 * <li>workspace documents</li>
 * </ul>
 * There are different versions of this file format, depending on the version of
 * Excel that has written the file, and depending on the document type. The
 * following table shows which Excel version writes which file format for
 * worksheet and workbook documents:
 * <table>
 * <tr>
 * <td>Excel version</td>
 * <td>BIFF version</td>
 * <td>Document type</td>
 * </tr>
 * <tr>
 * <td>Excel 2.x</td>
 * <td>BIFF2</td>
 * <td>Worksheet</td>
 * </tr>
 * <tr>
 * <td>Excel 3.0</td>
 * <td>BIFF3</td>
 * <td>Worksheet</td>
 * </tr>
 * <tr>
 * <td>Excel 4.0</td>
 * <td>BIFF4</td>
 * <td>Worksheet</td>
 * </tr>
 * <tr>
 * <td>Excel 5.0</td>
 * <td>BIFF5</td>
 * <td>Workbook</td>
 * </tr>
 * <tr>
 * <td>Excel 7.0</td>
 * <td>BIFF5</td>
 * <td>Workbook</td>
 * </tr>
 * <tr>
 * <td>Excel 8.0</td>
 * <td>BIFF8</td>
 * <td>Workbook</td>
 * </tr>
 * <tr>
 * <td>Excel 9.0</td>
 * <td>BIFF8</td>
 * <td>Workbook</td>
 * </tr>
 * <tr>
 * <td>Excel 10.0</td>
 * <td>BIFF8</td>
 * <td>Workbook</td>
 * </tr>
 * <tr>
 * <td>Excel 11.0</td>
 * <td>BIFF8</td>
 * <td>Workbook</td>
 * </tr>
 * </table>
 * BIFF8 contains major changes towards older BIFF versions, for instance the
 * handling of Unicode strings.</br> All document types and BIFF versions can be
 * stored in a simple stream file, most of them are always stored this way.The
 * only exception are BIFF5-BIFF8 workbook documents, which are usually stored
 * as compound document files . If these documents are stored as stream files,
 * the entire file consists of the Workbook stream (BIFF8) only.<br/> A workbook 
 * document with several sheets (BIFF5-BIFF8) is
 * usually stored using the compound document file format(also known as OLE2
 * storage file format or Microsoft Office compatible storage file format). It
 * contains several streams for different types of data. A complete
 * documentation of the format of compound document files can be found at
 * http://sc.openoffice.org/compdocfileformat.pdf. <br/> <strong>Current Version
 * of FastExcel reader only handle BIFF8 format.</strong><br/> For more
 * information see <a href="http://sc.openoffice.org/excelfileformat.pdf">
 * http://sc.openoffice.org/excelfileformat.pdf</a>.
 * 
 * @see Record
 * @see RecordParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class BIFFReader {

	/* Excel File */
	private File file;
	private ParserFactory parserFactory;
	private WorkBookStream workBookStream;
	private CompoundFileReader compoundFile;
	private WorkBookGlobalsStream workBookGlobalsStream;
	boolean isOpen;
	/* Workbook stream reader */
	Reader reader;
	ParserContextImpl context;
	BIFFSetting setting;

	/**
	 * Default constructor
	 **/
	public BIFFReader(File file) {
		this.file = file;
		isOpen = false;
		parserFactory = new ParserFactory();
		context = new ParserContextImpl();
		setting = new BIFFSetting();
		context.setting = setting;
	}

	/**
	 * Return file size in bytes.
	 * 
	 * @return
	 */
	public long getFileSize() {
		return file.length();
	}

	/**
	 * @return the setting
	 */
	public BIFFSetting getSetting() {
		return setting;
	}

	/**
	 * Set file to open.
	 * 
	 * @param f
	 *            file to open.
	 */
	public void setFile(File f) {
		if (isOpen) {
			throw new IllegalStateException(
					"file already opened.close it first.");
		}
		this.file = f;
	}

	/**
	 */
	public File getFile() {
		return this.file;
	}

	/**
	 * Open Excel file.
	 * 
	 * @throws ReadException
	 */
	public void open() throws ReadException {
		if (!isOpen) {
			compoundFile = new CompoundFileReader(file);
			compoundFile.open();
			workBookStream = new WorkBookStream(compoundFile);
			if (compoundFile.getReadableStream(DirectoryEntry.WORKBOOK_NAME) == null
                    && compoundFile.getReadableStream(DirectoryEntry.WORKBOOK_NAME_OTHER) == null) {
				throw new ReadException("Can not find Workbook stream.");
			}
			this.reader = workBookStream.reader;
			context.stream = workBookStream;
			context.reader=this.reader;
			isOpen = true;
		}
		// all OK .ready to read
	}

	public void close() throws ReadException {
		if (isOpen) {
			reader.close();
			compoundFile.close();
			isOpen = false;
			try {
				workBookGlobalsStream.close();
			} catch (ExcelException e) {
				throw new ReadException(e);
			}
		}
	}

	/**
	 * 
	 * @throws ParserException
	 * @throws ReadException
	 */
	public void readStream(SubStream s) throws ParserException, ReadException {
		ReadOnlyRecord record = new ReadOnlyRecord();
		RecordReader rr = new DefaultRecordReader(s, reader);
		rr.open();
		context.streamReader = rr;
		//
		while (!s.eof) {
			rr.nextRecord(record);
			// parser.
			//dump(record,1);
			parserFactory.parse(record, context);
		}
	}

	/**
	 * Read workBook Globals stream.
	 * 
	 * @throws ParserException
	 * @throws ReadException
	 * @throws ExcelException 
	 */
	public void readWorkBookGlobals() 
		throws ParserException,ReadException,ExcelException {
		workBookGlobalsStream = new WorkBookGlobalsStream(setting);
		workBookStream.addSubStream(workBookGlobalsStream);
		workBookStream.setCurrentStream(workBookGlobalsStream);
		workBookGlobalsStream.offset = 0;
		readStream(workBookGlobalsStream);
		//
//		dumpWorkbookGlobals(workBookGlobalsStream);
	}

	/**
	 * @throws IOException
	 * @throws ParserException
	 * @throws ReadException
	 * 
	 */
	public void readSheet(AbstractSheetStream stream) throws ParserException,
			ReadException {
		workBookStream.setCurrentStream(stream);
		readStream(stream);
	}

	/* for debug */
	void dumpWorkbookGlobals(WorkBookGlobalsStream ws) throws ExcelException {
		System.out.println("WORKBOOKGLOBALS:");
		System.out.println("SST:");
		for (int i = 0; i < ws.sstCount(); i++) {
			System.out.println(i + "--" + ws.getSST(i));
		}
		for (int i = 0; i < ws.sheetCount(); i++) {
			System.out.println(i + "--" + ws.getSheetStream(i));
		}
	}

	/* for debug */
	void dump(Record record, long offset) {
		System.out.println(Integer.toHexString(record.getType()) + "--"
				+ Types.getTypeName(record.getType()) + "--"
				+ record.getLength() + "---" + offset);
	}

	/**
	 * @return the workBookStream
	 */
	public WorkBookStream getWorkBookStream() {
		return workBookStream;
	}

	public WorkBookGlobalsStream getWorkBookGlobalsStream() {
		return workBookGlobalsStream;
	}

}
