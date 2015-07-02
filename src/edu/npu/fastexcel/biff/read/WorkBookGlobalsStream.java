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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.biff.parser.globals.BuiltinFormats;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.parser.globals.XF;

/**
 * The substream that contains the global information of a workbook is called
 * the Workbook Globals Substream. It is part of the Workbook Stream of
 * BIFF5-BIFF8 workbooks.
 * 
 * @see WorkBookStream
 * @see DefaultSheetStream
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class WorkBookGlobalsStream extends SubStream {
	public static final int WORKBOOK_GLOBALS_POS = 512;
	private List sheetList;
	private SST sstHolder;
	private ArrayList XF;
	private HashMap formatMap;
	private BIFFSetting setting;

	private int totalString;

	private int dateMode;// base date,specify by DATEMODE record.

	// value 0 means basedate from 1900,1,1
	// value 1 means bsaedate from 1904,1,1

	/**
	 * @throws ExcelException 
	 */
	public WorkBookGlobalsStream(BIFFSetting setting) throws ExcelException {
		this.setting=setting;
		sheetList = new ArrayList();
		switch (this.setting.getSSTType()) {
		case BIFFSetting.SST_TYPE_DEFAULT:
			sstHolder=new ArraySST();
			break;
		case BIFFSetting.SST_TYPE_TEMPFILE:
			sstHolder=new FileSST();
			break;
		case BIFFSetting.SST_TYPE_TEMPFILE_WITH_CACHE:
			sstHolder=new CacheFileSST();
			break;
		default:
			sstHolder=new ArraySST();
			break;
		}
		XF = new ArrayList(64);
		formatMap = new HashMap(64);
		this.offset = 512;
		dateMode = 0;
	}

	/**
	 * Get <code>SheetStream</code> by index.index is 0-based.
	 * 
	 * @param index
	 *            index of sheet.
	 * @return the sheet
	 */
	public SheetStream getSheetStream(int index) {
		if (index < 0 || index >= sheetList.size()) {
			return null;
		} else {
			return (SheetStream) sheetList.get(index);
		}
	}

	/**
	 * @return size of sheet list
	 */
	public int sheetCount() {
		return sheetList.size();
	}

	/**
	 * @return sheet names
	 */
	public String[] sheetNames() {
		String names[] = new String[sheetList.size()];
		for (int i = 0; i < sheetList.size(); i++) {
			SheetStream ss = (SheetStream) sheetList.get(i);
			names[i] = ss.name;
		}
		return names;
	}

	/**
	 * @return all sheet as array
	 */
	public SheetStream[] getSheets() {
		return (SheetStream[]) sheetList.toArray(new SheetStream[sheetList
				.size()]);
	}

	/**
	 * Get <code>SheetStream</code> by name
	 * 
	 * @param name
	 *            Name of sheet.
	 * @return the sheet
	 */
	public SheetStream getSheetStream(String name) {
		for (int i = 0; i < sheetList.size(); i++) {
			SheetStream ss = (SheetStream) sheetList.get(i);
			if (name.equals(ss.name)) {
				return ss;
			}
		}
		return null;
	}
	/**
	 * flush shared string table
	 * @throws ExcelException 
	 */
	public void flush() throws ExcelException{
		sstHolder.flush();
	}
	public void close() throws ExcelException{
		sstHolder.clear();
	}
	/**
	 * Add a <code>SheetStream</code> to workbook
	 * 
	 * @param sheet
	 */
	public void addSheet(SheetStream sheet) {
		sheet.index = sheetList.size();
		sheetList.add(sheet);
	}

	/**
	 * @return SST entries count.
	 * @throws ExcelException 
	 */
	public int sstCount() throws ExcelException {
		return sstHolder.size();
	}

	/**
	 * @return the totalString
	 */
	public int getTotalString() {
		return totalString;
	}

	/**
	 * @param totalString
	 *            the totalString to set
	 */
	public void setTotalString(int totalString) {
		this.totalString = totalString;
	}

	/**
	 * Return SST value which index is 'index'
	 * 
	 * @param index
	 *            index of SST entry
	 * @return value of SST entry
	 * @throws ExcelException 
	 */
	public String getSST(int index) throws ExcelException {
		return sstHolder.getString(index);
	}

	/**
	 * Add a SST entry to Share String Table.
	 * 
	 * @param index
	 *            index of entry
	 * @param str
	 *            entry's value
	 * @throws ExcelException 
	 */
	public void addSST(String str) throws ExcelException {
		sstHolder.addString(str);
	}
	/**
	 *Add a XF to globals stream.
	 * 
	 * @param xf
	 *            the XF.
	 */
	public void addXF(XF xf) {
		XF.add(xf);
	}

	/**
	 * Get XF by index.
	 * 
	 * @param index
	 *            index of XF
	 * @return the XF
	 */
	public XF getXF(int index) {
		return (XF) XF.get(index);
	}
	/**
	 * Add a format with type to workbook globals stream.
	 * @see Format
	 * @param index
	 *            format's index.
	 *@param type
	 *            format's type 
	 * @param format
	 *            format string.
	 */
	public void addFormat(int index,int type,String format) {
		formatMap.put(index + "", new Format(index, type, format));
	}
	/**
	 * Get format string by index.
	 * 
	 * @param index
	 *            index of format string.
	 * @return format string.
	 */
	public Format getFormat(int index) {
		Format f = (Format) formatMap.get("" + index);
		if (f == null) {
			return BuiltinFormats.getFormat(index);
		}
		return f;
	}

	/**
	 * @return the dateMode
	 */
	public int getDateMode() {
		return dateMode;
	}

	/**
	 * @param dateMode
	 *            the dateMode to set
	 */
	public void setDateMode(int dateMode) {
		this.dateMode = dateMode;
	}

	/**
	 * Return stream's name
	 */
	public String getName() {
		return STREAM_WORKBOOK_GLOBALS;
	}
}
