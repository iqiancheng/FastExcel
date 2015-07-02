/**
 * 
 */
package edu.npu.fastexcel.test;

import java.io.File;

import junit.framework.TestCase;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

/**
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-2-16
 */
public class TestWrite extends TestCase{

	private void basicWrite(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addSheet("SheetA");
		sheet.setCell(1, 2, "aaa");
		wb.close();
	}
	private void streamWrite(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addStreamSheet("SheetA");
		for(int i=0;i<101;i++){
			sheet.addRow(new String[]{"aaa","bbb","ccc"});
		}
		wb.close();
	}
	
	public void testWrite1() throws ExcelException{
		basicWrite(new File("/Users/yama/Desktop/a1.xls"));
	}
	public void testWrite2() throws ExcelException{
		streamWrite(new File("/Users/yama/Desktop/a2.xls"));
	}
	public void testCompare() throws ExcelException{
		//basicWriteBig(new File("/Users/yama/Desktop/big1.xls"));
		streamWriteBig(new File("/Users/yama/Desktop/big2.xls"));
	}
	public void basicWriteBig(File file) throws ExcelException{
		String basic="aaaaa中文aaa";
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addSheet("SheetA");
		for(int i=0;i<60000;i++){
			sheet.setCell(i, 0, basic+0+i);
			sheet.setCell(i, 1, basic+1+i);
			sheet.setCell(i, 2, basic+2+i);
			sheet.setCell(i, 3, basic+3+i);
			sheet.setCell(i, 4, basic+0+i);
			sheet.setCell(i, 5, basic+5+i);
			sheet.setCell(i, 6, basic+6+i);
			sheet.setCell(i, 7, basic+7+i);
		}
		wb.close();
	}
	public void streamWriteBig(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		String basic="aaaaa中文aaa";
		Sheet sheet=wb.addStreamSheet("SheetA");
		String ss[]=new String[8];
		for(int i=0;i<30000;i++){
			ss[0]=basic+0+i;
			ss[1]=basic+1+i;
			ss[2]=basic+2+i;
			ss[3]=basic+3+i;
			ss[4]=basic+0+i;
			ss[5]=basic+5+i;
			ss[6]=basic+6+i;
			ss[7]=basic+7+i;
			sheet.addRow(ss);
		}
		wb.close();
	}
}
