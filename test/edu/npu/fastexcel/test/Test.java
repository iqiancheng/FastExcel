package edu.npu.fastexcel.test;

import java.io.File;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.SheetReadAdapter;
import edu.npu.fastexcel.Workbook;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.common.util.StringUtil;

public class Test {

	/**
	 * @param args
	 * @throws ExcelException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws ExcelException, InterruptedException {
		File f=new File("/Users/yama/Downloads/test.xls");
		basicDump(f,BIFFSetting.SST_TYPE_TEMPFILE);
		//eventDump(f,BIFFSetting.SST_TYPE_TEMPFILE);
	}
	private static void basicDump(File file,int ssttype) throws ExcelException{
		System.out.println(file.getAbsolutePath()+"--------------------------");
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		workBook.setSSTType(ssttype);
		workBook.open();
		workBook.addUserDefineFormat(176, Format.TYPE_CURRENCY, "Y#,##");
		
		Sheet s;
		s = workBook.getSheet(0);
		
		System.out.println("SHEET:" + s);
		for (int i = s.getFirstRow(); i <s.getLastRow(); i++) {
			System.out.println(i+"#"+
					s.getFirstColIndex(i)+"--"+
					s.getLastColIndex(i)+"--"+
					StringUtil.join(s.getRow(i)));
		}
		workBook.close();
	}
	private static void eventDump(File file,int ssttype) throws ExcelException{
		System.out.println(file.getAbsolutePath()+"--------------------------");
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		workBook.setSSTType(ssttype);
		workBook.open();
		workBook.getSheet(0, new SheetReadAdapter() {
			public void onDimension(int firstrow, int lastrow, int firstcol,
					int lastcol) {
				System.out.println(firstrow+","+lastrow+","+firstcol+","+lastcol);
			}
			public void onCell(int row, int col, String content) {
				System.out.println(row + "," + col + "," + content);
			}
		});
		workBook.close();
	}
	public static void basicWriteBig(File file) throws ExcelException{
		String basic="aaaaa中文aaa";
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		Sheet sheet=wb.addSheet("SheetA");
		for(int i=0;i<60000;i++){
			sheet.setCell(i, 0, basic+0+i);
			sheet.setCell(i,1, basic+1+i);
			sheet.setCell(i, 2, basic+2+i);
			sheet.setCell(i, 3, basic+3+i);
			sheet.setCell(i, 4, basic+0+i);
			sheet.setCell(i, 5, basic+5+i);
			sheet.setCell(i, 6, basic+6+i);
			sheet.setCell(i, 7, basic+7+i);
		}
		wb.close();
	}
	public static void streamWriteBig(File file) throws ExcelException{
		Workbook wb=FastExcel.createWriteableWorkbook(file);
		wb.open();
		String basic="aaaaa中文aaa但是房价开始的就分开是得分开始的积分开始的积分开始的积分开时的房价开始的放假时打开飞";
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
