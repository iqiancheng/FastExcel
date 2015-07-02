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
package edu.npu.fastexcel.biff.record.cell;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.cell.LabelSSTParser;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 **<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * --	--	--	--	00FDH
 * this record represents a cell that contains a string. It replaces the LABEL 
 * record used in BIFF2-BIFF5.
 * record LABELSST, BIFF8:
 * offset Size Contents
 *  	2 	Index to row
 *  	2 	Index to column
 *  	2 	Index to XF record
 *  	4 	Index into SST record
 *</pre>
 * 
 * @see LabelSSTParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class LabelSSTRecord extends CellRecord {
	private int sst;/* 6 4 Index into SST record */

	/**
	 * 
	 */
	public LabelSSTRecord() {
		super(new byte[10 + RECORD_HEADER_LENGTH]);
		setType(Types.LABELSST);
		setContentLength(10);
	}

	/**
	 * @param sst
	 *            the sst to set
	 */
	public void setSST(int sst) {
		this.sst = sst;
	}

	public byte[] getBytes() {
		setCell();
		NumUtil.getFourBytes(sst, bytes, RECORD_HEADER_LENGTH + 6);
		return bytes;
	}
	public String toString() {
		return "{LABELSST:"+row+","+column+","+sst+"}";
	}
}
