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
package edu.npu.fastexcel.dev;

import java.io.File;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.FastExcel;
import edu.npu.fastexcel.Sheet;
import edu.npu.fastexcel.Workbook;

/**
 * Just a Develop Tool.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-18
 */
public class ExcelDump {
	public static void main(String[] args) {
		if (args.length < 2) {
			usage();
			System.exit(1);
		}
		String option = args[0];
		if (option.equals("-d")) {
			String s = args[1];
			s = s.trim();
			if (s.indexOf('@') == -1 || s.indexOf('@') == s.length() - 1) {
				usage();
				System.exit(1);
			}
			String sheet = s.substring(0, s.indexOf('@'));
			String file = s.substring(s.indexOf('@') + 1, s.length());
			int r = dump(file, sheet);
			System.exit(r);
		}
		if (option.equals("-s")) {
			String s = args[1];
			s = s.trim();
			int r = dumpSheet(s);
			System.exit(r);
		}

	}

	public static int dumpSheet(String file) {
		File f = new File(file);
		if (!f.exists()) {
			System.err.println("Can not find this file:" + file);
			System.exit(-1);
		}
		Workbook wb = FastExcel.createReadableWorkbook(f);
		try {
			wb.open();
			String names[] = wb.sheetNames();
			for (int i = 0; i < names.length; i++) {
				System.out.println(names[i]);
			}
			;
			wb.close();
		} catch (ExcelException e) {
			System.err.println("Error:" + e.getMessage());
			return -1;
		} 
		return 0;
	}

	public static int dump(String file, String sheet) {
		int r = 0;
		File f = new File(file);
		if (!f.exists()) {
			System.err.println("Can not find this file:" + file);
			System.exit(-1);
		}
		Workbook wb = FastExcel.createReadableWorkbook(f);
		try {
			wb.open();
			Sheet ss = wb.getSheet(sheet);
			if (ss == null) {
				System.err.println("Can not find this sheet:" + sheet);
				System.exit(-1);
			}
			int lastRow = ss.getLastRow();
			int i = ss.getFirstRow();
			for (; i <= lastRow; i++) {
				String row[] = ss.getRow(i);
				if (row == null) {
					System.out.println();
					continue;
				}
				for (int j = 0; j < row.length; j++) {
					System.out.print(row[j] == null ? "" : row[j]);
					if (j != row.length - 1) {
						System.out.print(",");
					} else {
						System.out.println();
					}
				}
			}
			wb.close();
		} catch (ExcelException e) {
			System.err.println("Error:" + e.getMessage());
			return -1;
		} 
		return r;
	}

	public static void usage() {
		System.out.println("Usage:ExcelDump [-d sheet@file ][-s file]");
	}
}
