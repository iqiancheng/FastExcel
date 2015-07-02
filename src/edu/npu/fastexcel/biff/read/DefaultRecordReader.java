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
package edu.npu.fastexcel.biff.read;

import edu.npu.fastexcel.biff.record.ReadOnlyRecord;
import edu.npu.fastexcel.biff.record.Readable;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.compound.io.ReadException;
import edu.npu.fastexcel.compound.io.Reader;

/**
 * Default implemention of <code>RecordReader</code>.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-24
 */
public class DefaultRecordReader implements RecordReader {

	private SubStream stream;

	private int position;
	private Reader reader;
	private ReadOnlyRecord lastRecord;
	byte header[];

	public DefaultRecordReader(SubStream s, Reader reader) {
		this.stream = s;
		this.reader = reader;
		position = stream.offset;
		lastRecord = new ReadOnlyRecord();
		header = new byte[Record.RECORD_HEADER_LENGTH];
	}

	public boolean nextRecord(Readable record) throws ReadException {
		if (stream.eof) {
			return false;
		}
		reader.seek(position);
		int r = reader.read(header);

		if (r == -1) {
			return false;
		}
		reader.seek(position);
		// record's full length
		int len = NumUtil.getInt(header[2], header[3])
				+ Record.RECORD_HEADER_LENGTH;
		byte bb[] = new byte[len];
		record.setBytes(bb);
		r = reader.read(bb);
		if (r == -1) {
			return false;
		}
		position += len;
		lastRecord.setBytes(bb);
		return true;
	}

	public void rewind() throws ReadException {
		position = stream.offset;
	}

	public void setStream(SubStream s) {
		this.stream = s;
	}

	public void skip(int num) throws ReadException {
		Readable r = new ReadOnlyRecord();
		for (int i = 0; i < num; i++) {
			nextRecord(r);
		}
	}

	public int getPosition() {
		return position;
	}

	public void undoNext() throws ReadException {
		if (lastRecord != null) {
			position -= lastRecord.getBytes().length;
		}
	}

	public void close() throws ReadException {
		reader.close();
	}

	public void open() throws ReadException {
		reader.open();
	}
}
