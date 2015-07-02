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

import edu.npu.fastexcel.biff.read.WorkBookStream;
import edu.npu.fastexcel.biff.record.Record;

/**
 * Default implemention of RecordParser
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class DefaultParser extends RecordParser {
	protected Record record;
	protected ParserContext context;
	protected WorkBookStream workBookStream;

	/**
	 * 
	 * @param type
	 */
	public DefaultParser(int type) {
		super(type);
	}

	public void parse(Record record, ParserContext context)
			throws ParserException {
		this.record = record;
		this.context = context;
		this.workBookStream = context.getStream();
		b = record.getBytes();
		off = Record.OFFSET;
	}

}
