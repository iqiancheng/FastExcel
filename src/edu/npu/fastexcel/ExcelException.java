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

/**
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-2-16
 */
public class ExcelException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public ExcelException(String msg) {	
		super(msg);
	}
	public ExcelException(String msg,Throwable e) {
		super(msg, e);
	}
	public ExcelException(Throwable e) {
		super(e);
	}
}
