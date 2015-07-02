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

import java.util.ArrayList;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * Most of the Excel streams or substreams are divided into records. Each record
 * contains specific data for the various contents or features in a document. It
 * consists of a header specifying the record type and size, followed by the
 * record data.
 * 
 * <pre>
 * Common structure of a BIFF record:
 * Offset Size Contents  
 * 0 	2 	Identifier  
 * 2 	2 	Size of the following data (sz) } Record header  
 * 4 	sz	Record data
 * </pre>
 * 
 * The maximum size of the record data is limited and depends on the BIFF
 * version. If the size of the record data exceeds the current limit, one or
 * more CONTINUE records will be added. Inside a CONTINUE record the data of the
 * previous record continues as usual.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public abstract class Record {
	/**
	 * The BIFF record header is 4 bytes length.
	 */
	public static final int RECORD_HEADER_LENGTH = 4;
	public static final int OFFSET = RECORD_HEADER_LENGTH;
	/**
	 * Maximum data size of a record in BIFF8
	 */
	public static final int MAX_RECORD_LENGTH = 8228;
	/**
	 * bytes of this record.
	 * 
	 */
	protected byte bytes[];

	public int position;
	/* continue list */
	private ArrayList continueList;

	public Record(byte bytes[]) {
		this.bytes = bytes;
		continueList = new ArrayList();
	}

	
	/*
	 * Set record 's type.
	 */
	protected void setType(int type) {
		NumUtil.getTwoBytes(type, bytes, 0);
	}

	/*
	 * Set record 's length.
	 */
	protected void setContentLength(int len) {
		NumUtil.getTwoBytes(len, bytes, 2);
	}

	/**
	 * Return type of this BIFF record.
	 * 
	 * @return Type of BIFF record
	 */
	public int getType() {
		return NumUtil.getInt(bytes[0], bytes[1]);
	}

	/**
	 * Return record content length of this BIFF record.
	 * 
	 * @return Length of this BIFF record data.
	 */
	public int getContentLength() {
		return NumUtil.getInt(bytes[2], bytes[3]);
	}

	/**
	 * Return length of this record.NOTE:length=record length+header length.
	 * 
	 * @return Record length.
	 */
	public int getLength() {
		return getContentLength() + RECORD_HEADER_LENGTH;
	}

	/**
	 * Add a continue record.
	 * 
	 * @param r
	 *            continue record.
	 */
	public void addContinue(Record r) {
		continueList.add(r);
	}

	/**
	 * Remove all continue record.
	 */
	public void clearContinues() {
		continueList.clear();
	}

	/**
	 * Get continue record by index.
	 * 
	 * @param index
	 *            continue reocrd's index
	 * @return the continue
	 */
	public Record getContinue(int index) {
		return (Record) continueList.get(index);
	}

	/**
	 * Get continue record list's size.
	 * 
	 * @return the size.
	 */
	public int continueCount() {
		return continueList.size();
	}

	/**
	 * Get bytes of this record.
	 * 
	 * @return the bytes.
	 */
	public byte[] getBytes() {
		return bytes;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "{" + Types.getTypeName(getType()) + "}";
	}

}
