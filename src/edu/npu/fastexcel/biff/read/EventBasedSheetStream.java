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

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.SheetReadListener;

/**
 * A event-based implemention of <code>AbstractSheetStream</code>.<br/> This
 * stream did not store anything,it just pass the event to
 * <code>SheetReadListener</code>.It's very fast!!.
 * 
 * @see DefaultSheetStream
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class EventBasedSheetStream extends AbstractSheetStream {

	private SheetReadListener listener;
	
	public EventBasedSheetStream(SheetStream stream,WorkBookGlobalsStream wgs) {
		super(stream,wgs);
		eof = false;
	}

	/**
	 * @return the listener
	 */
	public SheetReadListener getListener() {
		return listener;
	}

	/**
	 * @param listener
	 *            the listener to set
	 */
	public void setListener(SheetReadListener listener) {
		this.listener = listener;
	}

	/**
	 * @param firstRow
	 *            the firstRow to set
	 */
	public void setDimension(int rf, int rl, int cf, int cl) {
		listener.onDimension(rf, rl, cf, cl);
	}

	public void setRow(int row, int cf, int cl) {
		listener.onRow(row, cf, cl);
	}

	public void setContent(int row, int col, int sstIndex) throws ExcelException {
		listener.onCell(row, col, workBookGlobalsStream.getSST(sstIndex));
	}

	public void setContent(int row, int col, String str) {
		listener.onCell(row, col, str);
	}
	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{" + "name:");
		sb.append(name + ",index:");
		sb.append(index + ",firstRow:");
		sb.append(offset);
		sb.append("}");
		return sb.toString();
	}

	public short getFirstCol(int row) {
		throw new IllegalStateException("not support");
	}

	public short getLastCol(int row) {
		throw new IllegalStateException("not support");
	}


}
