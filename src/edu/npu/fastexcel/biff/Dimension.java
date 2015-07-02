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
package edu.npu.fastexcel.biff;

import edu.npu.fastexcel.biff.parser.sheet.DimensionParser;
import edu.npu.fastexcel.biff.record.sheet.DimensionRecord;

/**
 * The sheet dimension.Max row is 65536 ,max column is 256
 * @see DimensionParser
 * @see DimensionRecord
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-22
 */
public class Dimension {
	public int firstRow;
	public int firstCol;
	public int lastRow;
	public int lastCol;
	/**
	 * Return whether or not the dimension is 0,0,0,0
	 * @return true if dimension is 0,0,0,0 else false 
	 */
	public boolean isEmpty(){
		return firstCol==0&&firstRow==0&&lastCol==0&&lastRow==0;
	}
	public String toString() {
		return firstCol + "," + lastCol + "," + firstRow + "," + lastRow + "\n";
	}
}