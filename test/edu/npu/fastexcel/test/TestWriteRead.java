/**
 * 
 */
package edu.npu.fastexcel.test;

import java.io.File;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

import junit.framework.TestCase;

/**
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a>
 * 2009-4-24
 */
public class TestWriteRead extends TestCase{
	public void testWriteRead() throws ExcelException{
		File f=new File("/home/yama/a.xls");
		write(f);
		read(f);
	}
	public void testWriteRead2() throws ExcelException{
		File f=new File("/home/yama/a.xls");
		writeStream(f);
		read(f);
	}
	public void testWriteEmpty() throws ExcelException{
		File f=new File("/home/yama/a2.xls");
		writeEmpty(f);
		read(f);
	}
	public void writeEmpty(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addSheet("SheetA");
		wb.close();
	}
	public void write(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addSheet("SheetA中文");
		for(int i=0;i<100;i++){
			sheet.setCell(i,(int)0, "aaa中文");
			sheet.setCell(i,(int)1, "bbb");
			sheet.setCell(i,(int)2, "ccc");
		}
		wb.close();
	}
	public void writeStream(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addStreamSheet("SheetA中文");
		for(int i=0;i<100;i++){
			sheet.addRow(new String[]{"aaa","vvv中文","ccc"});
		}
		wb.close();
	}
	public void read(File file) throws ExcelException{
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
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
	
}
