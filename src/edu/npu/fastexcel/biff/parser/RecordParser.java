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
package edu.npu.fastexcel.biff.parser;

import edu.npu.fastexcel.biff.read.RecordReader;
import edu.npu.fastexcel.biff.record.Record;

/**
 * Most of the Excel streams or substreams are divided into records. Each record
 * contains specific data for the various contents or features in a document. It
 * consists of a header specifying the record type and size, followed by the
 * record data.
 * 
 * <pre>
 * Common structure of a BIFF record:
 *  Offset 	Size 	Contents  
 *  0	2	Identifier  
 *  2 	2 	Size of the following data (sz)  Record header  
 *  4 	sz	Record data
 * </pre>
 * 
 * The maximum size of the record data is limited and depends on the BIFF
 * version. If the size of the record data exceeds the current limit, one or
 * more CONTINUE records will be added. Inside a CONTINUE record the data of the
 * previous record continues as usual.<br/> For low memory use,we didn't store
 * record,we just parse it.If the record is a control record.record parser
 * control the parser stream or change something like close the steam if we met
 * EOF record.If the record is a content record like SST record the parser will
 * parse it and store the content to somewhere.
 * 
 * @see Record
 * @see RecordReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public abstract class RecordParser {

	protected int type;// which record type the parser can parse
	protected byte b[];// bytes of this record.
	protected int off;// offset to content

	/**
	 * 
	 */
	public RecordParser(int type) {
		this.type = type;
	}

	/**
	 * Parse the given record.
	 * 
	 * @param record
	 * @throws ParserException
	 *             when some error happened.
	 */
	public abstract void parse(Record record, ParserContext context)
			throws ParserException;

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

}
