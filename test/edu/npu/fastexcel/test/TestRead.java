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
package edu.npu.fastexcel.test;

import java.io.File;

import junit.framework.TestCase;
import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.SheetReadAdapter;
import edu.npu.fastexcel.Workbook;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class TestRead extends TestCase {
	private void basicDump(File file,int ssttype) throws ExcelException{
		System.out.println(file.getAbsolutePath()+"--------------------------");
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		workBook.setSSTType(ssttype);
		workBook.open();
		Sheet s;
		s = workBook.getSheet(0);
		System.out.println("SHEET:" + s);
		for (int i = s.getFirstRow(); i <s.getLastRow(); i++) {
			System.out.print(i + "#");
			for (int j = s.getFirstColumn(); j <s.getLastColumn(); j++) {
				System.out.print("," + s.getCell(i, j));
				s.getCell(i, j);
			}
			System.out.println();
		}
		workBook.close();
	}
	private void eventDump(File file,int ssttype) throws ExcelException{
		System.out.println(file.getAbsolutePath()+"--------------------------");
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		workBook.setSSTType(ssttype);
		workBook.open();
		workBook.getSheet(0, new SheetReadAdapter() {
			public void onCell(int row, int col, String content) {
//				System.out.println(row + "," + col + "," + content);
			}
		});
		workBook.close();
	}
	public void testBasicDump1() throws ExcelException{
		basicDump(new File("/Users/yama/Desktop/a1.xls")
						,BIFFSetting.SST_TYPE_DEFAULT);
	}
	public void testBasicDump2() throws ExcelException{
		basicDump(new File("/Users/yama/Desktop/big2.xls")
						,BIFFSetting.SST_TYPE_TEMPFILE);
	}
	public void testBasicDump3() throws ExcelException{
		basicDump(new File("/Users/yama/Desktop/big2.xls")
						,BIFFSetting.SST_TYPE_TEMPFILE_WITH_CACHE);
	}
	public void testEventDump1() throws ExcelException{
		eventDump(new File("/Users/yama/Desktop/big2.xls")
						,BIFFSetting.SST_TYPE_DEFAULT);
	}
	public void testEventDump2() throws ExcelException{
		eventDump(new File("/Users/yama/Desktop/big2.xls")
						,BIFFSetting.SST_TYPE_TEMPFILE);
	}
	public void testEventDump3() throws ExcelException{
		eventDump(new File("/Users/yama/Desktop/big2.xls")
						,BIFFSetting.SST_TYPE_TEMPFILE_WITH_CACHE);
	}
}
