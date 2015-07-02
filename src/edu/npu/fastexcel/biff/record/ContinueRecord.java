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
package edu.npu.fastexcel.biff.record;

import edu.npu.fastexcel.biff.Types;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 003CH 003CH 003CH 003CH 003CH
 * </pre>
 * 
 * Whenever the content of a record exceeds the given limits (see table), the
 * record must be split. Several CONTINUE records containing the additional data
 * are added after the parent record.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-3
 */
public class ContinueRecord extends Record {

	/**
	 * Default Constructor.
	 * 
	 * @param b
	 *            the content byte
	 */
	public ContinueRecord() {
		super(new byte[RECORD_HEADER_LENGTH]);
		setType(Types.CONTINUE);
	}
	
	public void addByte(byte b[]){
		byte newbytes[]=new byte[b.length+bytes.length];
		System.arraycopy(bytes, 0, newbytes, 0, bytes.length);
		System.arraycopy(b, 0, newbytes, bytes.length,b.length);
		bytes=newbytes;
		setContentLength(getContentLength()+b.length);
	}
	
}
