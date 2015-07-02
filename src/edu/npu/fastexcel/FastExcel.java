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

/**
 * <a href="http://fastexcel.sourceforge.net">FastExcel</a>:<strong>FAST</strong>
 * and <strong>TINY</strong> excel read/write component.<br/> 
 * change log:
 * <ul>
 * <li>create file 2008-12-26</li>
 * <li>version 0.1 first release 2009-1-1</li>
 * <li>version 0.2 add event-based interface. 2009-1-16</li>
 * <li>version 0.3 add write interface.2009-2-16</li>
 * <li>version 0.3.1 add user define format interface.2009-2-23</li>
 * <li>version 0.3.2 add date format testing.2009-2-25</li>
 * <li>version 0.3.3 fix bug:NumUtil.getRKValue.
 * Thanks to maojianke001@pingan.com.cn. 2009-3-18</li>
 * <li>version 0.3.4 add a stream write interface.2009-3-31</li>
 * <li>version 0.3.5 fix bugs.Thanks XUJIANFENG004@pingan.com.cn.2009-4-22</li>
 * <li>version 0.4
 * 		<ol>add SST,ArraySST,FileSST,CacheFileSST</ol>
 *		<ol>change DefaultSheetStream</ol>
 *		<ol>add setSSTType in BIFFSetting</ol>
 *	2009-4-26
 * </li>
 * <li>version 0.4.1 fix bug:can not delete file after workbook closed.
 * Thanks to allan <allan@dreamtech.com.cn>. 2009-5-13</li>
 * <li>version 0.4.2 fix bug:ClassCastException when open workbookGlobalStream
 * Thanks to maojianke001@pingan.com.cn 2009-5-19</li>
 * <li>
 * version 0.4.3 fix bug:date format and number format error. 2009-11-5
 * </li>
 * <li>
 * version 0.4.4
 * fix bug: that when writing excel,the dimension 's last row is wrong.
 * thanks to 高雨峰 <sillyfox008@sina.com> 2010-1-10
 * 
 * version 0.5
 * add formula support HeDYn<hedyn@foxmail.com>
 * </li>
 * </ul>
 * For my love LIMIAO
 * @author <a href="guooscar@gmail.com">yAma</a> 
 * @version 0.4.4
 */
public class FastExcel {
	/**
	 * create a readable workbook from given file
	 * @param file the file name
	 * @return the readable workbook
	 */
	public static Workbook createReadableWorkbook(File file) {
		ReadableWorkbook workbook = new ReadableWorkbook(file);
		return workbook;
	}
	/**
	 * create a writable workbook from given file
	 * @param file the file name
	 * @return the writable workbook
	 */
	public static Workbook createWriteableWorkbook(File file) {
		return new WritableWorkbook(file);
	}
}
