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
package edu.npu.fastexcel.biff.read;

import edu.npu.fastexcel.biff.record.Readable;
import edu.npu.fastexcel.compound.io.ReadException;

/**
 * RecordReader read record from stream.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-24
 */
public interface RecordReader {
	/**
	 * Set stream to read.
	 * 
	 * @param s
	 *            the stream
	 */
	public void setStream(SubStream s);

	/**
	 * Put last record back in to stream.
	 * 
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public void undoNext() throws ReadException;

	/**
	 * Get next record.
	 * 
	 * @param record
	 *            result holder.
	 * @return true if has more record else return false.
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public boolean nextRecord(Readable record) throws ReadException;

	/**
	 * Rewind the stream.
	 * 
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public void rewind() throws ReadException;

	/**
	 * Skip total num records.
	 * 
	 * @param num
	 *            count to skip.
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public void skip(int num) throws ReadException;

	/**
	 * @return current position of stream.
	 */
	public int getPosition();

	/**
	 * Open this reader.
	 * 
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public void open() throws ReadException;

	/**
	 * Close this reader.
	 * 
	 * @throws ReadException
	 *             when something wrong throws
	 */
	public void close() throws ReadException;

}
