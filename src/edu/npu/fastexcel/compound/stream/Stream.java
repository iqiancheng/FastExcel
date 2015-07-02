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
package edu.npu.fastexcel.compound.stream;

import edu.npu.fastexcel.compound.DirectoryEntry;

/**
 * @see DirectoryEntry
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-28
 */
public abstract class Stream {
	/**
	 * It's root storage.
	 */
	public static final int TYPE_ROOT_STORAGE = 0x05;
	/**
	 * Unknow
	 */
	public static final int TYPE_PROPERTY = 0x04;
	public static final int TYPE_LOCK_BYTES = 0x03;
	/**
	 * It's user stream.
	 */
	public static final int TYPE_USER_STREAM = 0x02;
	/**
	 * It's user storage.
	 */
	public static final int TYPE_USER_STORAGE = 0x01;
	public static final int TYPE_EMPTY = 0x00;

	/* Name of this stream */

	protected String name;
	/*
	 * type of this streamsee TYPE_XXX constants.
	 */

	protected int type;
	/*
	 * size of stream.
	 */

	protected int size;

	/**
	 * Default Constructor
	 * 
	 * @param name
	 *            name of this stream.
	 */
	public Stream(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size
	 *            the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

}
