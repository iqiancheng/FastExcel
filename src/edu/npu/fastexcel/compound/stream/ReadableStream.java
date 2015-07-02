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
package edu.npu.fastexcel.compound.stream;

import edu.npu.fastexcel.compound.io.Reader;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-28
 */
public class ReadableStream extends Stream {
	/**
	 * Stream reader.
	 */
	protected Reader reader;

	public ReadableStream(String name) {
		super(name);
	}

	/**
	 * @return the reader
	 */
	public Reader getReader() {
		return reader;
	}

	/**
	 * @param reader
	 *            the reader to set
	 */
	public void setReader(Reader reader) {
		this.reader = reader;
	}
}
