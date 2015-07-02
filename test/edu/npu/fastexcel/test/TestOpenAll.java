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
import edu.npu.fastexcel.Workbook;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class TestOpenAll extends TestCase {
	private void openAll(File dir,int ssttype) throws ExcelException{
		File ff[]=dir.listFiles();
		for(int i=0;i<ff.length;i++){
			if(ff[i].getName().endsWith(".xls")){
				open(ff[i], ssttype);
			}
		}
	}
	private void open(File file,int ssttype) throws ExcelException{
		long t1=System.currentTimeMillis();
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(file);
		workBook.setSSTType(ssttype);
		workBook.open();
		workBook.close();
		long t2=System.currentTimeMillis();
		System.out.println(ssttype+"--"+file.getAbsolutePath()+"\t\t\t\t\t"+(t2-t1));	
	}
	public void testBasicDump1() throws ExcelException{
		openAll(new File("/home/yama/")
						,BIFFSetting.SST_TYPE_DEFAULT);
	}
	public void testBasicDump2() throws ExcelException{
		openAll(new File("/home/yama/")
						,BIFFSetting.SST_TYPE_TEMPFILE);
	}
	public void testBasicDump3() throws ExcelException{
		openAll(new File("/home/yama/")
						,BIFFSetting.SST_TYPE_TEMPFILE_WITH_CACHE);
	}
}
