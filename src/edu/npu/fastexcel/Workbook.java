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

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;

import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.read.ArraySST;
import edu.npu.fastexcel.biff.read.CacheFileSST;
import edu.npu.fastexcel.biff.read.FileSST;
import edu.npu.fastexcel.biff.read.SST;

/**
 * Workbook interface is a high level abstraction of Excel Workbook.<br/> Our
 * goal is to use java read the excel file quickly, and this process did not
 * need a lot of memory. The final results will be displayed in the form of
 * string, we do not care about the other excel file formats such as fonts,
 * colors, and so on. <br/>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-1
 */
public interface Workbook {

	/**
	 * @return the file
	 */
	File getFile();

	/**
	 * @param file
	 *            the file to set
	 */
	void setFile(File file);

	/**
	 * Open workbook.
	 * 
	 * @throws IOException
	 *              when file not exists or some other exception occurs.
	 * @throws ExcelException 
	 * 				throws when some error
	 */
	void open() throws ExcelException;

	/**
	 * Close workbook.
	 * 
	 * @throws ExcelException
	 */
	void close() throws ExcelException;

	/**
	 * @return total number of sheets
	 */
	int sheetCount();

	/**
	 * @return all sheet's name
	 */
	String[] sheetNames();
	
	/**
	 * Add sheet to workbook.
	 * NOTE:this is a stream interface.
	 * @param name
	 * @return
	 * @throws Exception
	 */
	Sheet addStreamSheet(String name)throws ExcelException;
	
	/**
	 * Add sheet to workbook
	 * 
	 * @param name
	 *            name of this sheet
	 * @return the sheet
	 * @throws ExcelException
	 *             throws when some error
	 */
	Sheet addSheet(String name) throws ExcelException;

	/**
	 * Get sheet by index.FastExcel will pass read event to
	 * {@link SheetReadListener}
	 * 
	 * @param index
	 *            index of sheet
	 * @param l
	 *            the SheetReadListener
	 * @throws ExcelException
	 *             throws when some error
	 */
	void getSheet(int index, SheetReadListener l) throws ExcelException;

	/**
	 * Get sheet by name.FastExcel will pass read event to
	 * {@link SheetReadListener}
	 * 
	 * @param name
	 *            name of sheet
	 * @param l
	 *            the SheetReadListener
	 * @throws ExcelException
	 *             throws when some error
	 */
	void getSheet(String name, SheetReadListener l)
			throws ExcelException;

	/**
	 * Get sheet by index
	 * 
	 * @param index
	 *            of sheet
	 * @return the sheet
	 * @throws ExcelException
	 *             throws when some error
	 */
	Sheet getSheet(int index) throws ExcelException;

	/**
	 * Get sheet by name
	 * 
	 * @param name
	 *            name of sheet
	 * @return the sheet
	 * @throws ExcelException
	 *             throws when some error
	 */
	Sheet getSheet(String name) throws ExcelException;

	/**
	 * Enable format number using excel cell format.
	 */
	void enableFormatNumber();

	/**
	 * Disable format number using excel cell format.
	 */
	void disableFormatNumber();

	/**
	 * Enable format date using excel cell format.
	 */
	void enableFormatDate();

	/**
	 * Disable format date using excel cell format.
	 */
	void disableFormatDate();

	/**
	 * Enable format date using common date format
	 * 
	 * @see setCommonDateFormat
	 */
	void enableCommonDateFormat();

	/**
	 * Disable format date using common date format
	 */
	void disableCommonDateFormat();
	/**
	 * @see SST
	 * @see ArraySST
	 * @see CacheFileSST
	 * @see FileSST
	 * @see BIFFSetting
	 * @param type 
	 */
	void setSSTType(int type);

	/**
	 * Set common date format.this format will be used to format all date cell.
	 * 
	 * @param dateFormat
	 *            the date format.
	 */
	void setCommonDateFormat(SimpleDateFormat dateFormat);
	/**
	 * Return the Excel Format by index.
	 * @param index format's index
	 * @return the format
	 * @throws ExcelException throws when workbook did not open.
	 */
	Format getFormat(int index)throws ExcelException;
	/**
	 * Add a user defined format to workbook.<br/>
	 * You can invoke this method to add your own format,fastexcel will use
	 * this format string to format specify cell.<br/>
	 * NOTE:user defined format will replace default format.
	 * @param index the format's index,format index must >=0
	 * @param type see <code>Format</code> 
	 * @param formatStr the format string
	 * @throws ExcelException throws when workbook did not open.
	 */
	void addUserDefineFormat(int index,int type,String formatStr) 
		throws ExcelException;

}
