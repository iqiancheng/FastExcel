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

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.record.Record;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class UnknowParser extends RecordParser {
	/**
	 * 
	 */
	public UnknowParser() {
		super(Types.UNKNOWN);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * edu.npu.fastexcel.biff.parser.RecordParser#parse(edu.npu.fastexcel.biff
	 * .record.Record, edu.npu.fastexcel.biff.parser.ParserContext)
	 */
	public void parse(Record record, ParserContext c) throws ParserException {
		// TODO implement this
	}

}
