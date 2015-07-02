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
import edu.npu.fastexcel.biff.parser.cell.RKParser;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * -- 027EH 027EH 027EH 027EH
 * this record represents a cell that contains an RK value (encoded integer or 
 * floating-point value). If a floating-point value cannot be encoded to an RK
 * value, a NUMBER record  will be written. This record replaces the record
 * INTEGER written in BIFF2.
 * record RK, BIFF3-BIFF8:
 * offset Size Contents
 *  	2 	Index to row
 *  	2 	Index to column
 *  	2 	Index to XF record 
 *  	4 	RK value
 * </pre>
 * 
 * @see RKParser
 * @see NumUtil
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class RKRecord extends CellRecord {
	private int rk;/* 6 4 RK value */

	/**
	 * @param bytes
	 */
	public RKRecord() {
		super(new byte[10 + RECORD_HEADER_LENGTH]);
		setType(Types.RK);
		setContentLength(10);
	}

	/**
	 * 
	 * @param sst
	 *            the sst to set
	 * @see NumUtil#getRKValue(int)
	 */
	public void setRK(int rk) {
		this.rk = rk;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.record.Record#getBytes()
	 */
	public byte[] getBytes() {
		setCell();
		NumUtil.getFourBytes(rk, bytes, RECORD_HEADER_LENGTH + 6);
		return bytes;
	}
}
