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
package edu.npu.fastexcel.compound;

import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * the size of each directory entry is exactly 128 bytes. 
 * contents of the directory entry structure:
 * offset Size Contents
 *  	64 Character array of the name of the entry, always 16-bit Unicode characters, with trailing
 * 	zero character (results in a maximum name length of 31 characters)
 * 4 	2 Size of the used area of the character buffer of the name (not character count), including
 * 	the trailing zero character (e.g. 12 for a name with 5 characters: (5+1)*2 = 12)
 * 6 	1 Type of the entry: 
 * 	00H = Empty 
 * 	03H = LockBytes (unknown)
 * 	01H = User storage 
 * 	04H = Property (unknown)
 * 	02H = User stream 
 * 	05H = Root storage
 * 7 	1 Node colour of the entry: 00H = Red 01H = Black
 * 8 	4 DirID of the left child node inside the red-black tree of all direct members of the parent
 * 	storage ,  if there is no left child
 * 2 	4 DirID of the right child node inside the red-black tree of all direct members of the parent
 * 	storage ,  if there is no right child
 * 6 	4 DirID of the root node entry of the red-black tree of all storage members , otherwise
 * 0 	16 Unique identifier, if this is a storage (not of interest in the following, may be all 0)
 * 6 	4 User flags (not of interest in the following, may be all 0)
 * 00 	8 Time stamp of creation of this entry . Most implementations do not write a valid
 * 	time stamp, but fill up this space with zero bytes.
 * 08 	8 Time stamp of last modification of this entry . Most implementations do not write
 * 	a valid time stamp, but fill up this space with zero bytes.
 * 16 	4 SecID of first sector or short-sector, if this entry refers to a stream , SecID of first
 * 	sector of the short-stream container stream , if this is the root storage entry, 0
 * 	otherwise
 * 20 	4 Total stream size in bytes, if this entry refers to a stream , total size of the shortstream
 * 	container stream , if this is the root storage entry, 0 otherwise
 * 24 	4 Not used
 * </pre>
 * 
 * @see CompoundFileReader
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-26
 */
public class DirectoryEntry {

	public final static String ROOT_ENTRY_NAME = "Root Entry";
	public final static String WORKBOOK_NAME = "Workbook";
    public final static String WORKBOOK_NAME_OTHER = "Book";
	public final static String SUMMARY_INFORMATION_NAME = "\005SummaryInformation";
	public final static String DOCUMENT_SUMMARY_INFORMATION_NAME = "\005DocumentSummaryInformation";

	//
	public static final int TYPE_ROOT_STORAGE = 0x05;
	public static final int TYPE_PROPERTY = 0x04;
	public static final int TYPE_LOCK_BYTES = 0x03;
	public static final int TYPE_USER_STREAM = 0x02;
	public static final int TYPE_USER_STORAGE = 0x01;
	public static final int TYPE_EMPTY = 0x00;

	public static final int COLOR_RED = 0x00;
	public static final int COLOR_BLACK = 0x01;
	public static final int DIR_LEN = 128;
	//
	protected String name;
	protected int type;
	protected int color;
	protected int leftDID;
	protected int rightDID;
	protected int rootDID;
	protected int secID;
	protected int streamSize;
	protected byte bytes[];
	protected int id;

	//

	/* package private */
	DirectoryEntry(byte b[]) {
		int nameLen = NumUtil.getInt(b[64], b[65]);
		name = StringUtil.getUnicodeString(b, 0, nameLen / 2);
		if (name.trim().length() == 0) {
			return;
		}
		name = name.substring(0, name.length() - 1);// delete null-terminal at
		// end of name
		type = b[66];
		color = b[67];
		leftDID = NumUtil.getInt(b[68], b[69], b[70], b[71]);
		rightDID = NumUtil.getInt(b[72], b[73], b[74], b[75]);
		rootDID = NumUtil.getInt(b[76], b[77], b[78], b[79]);
		secID = NumUtil.getInt(b[116], b[117], b[118], b[119]);
		streamSize = NumUtil.getInt(b[120], b[121], b[122], b[123]);
		this.bytes = b;
	}

	public DirectoryEntry() {
		bytes = new byte[DIR_LEN];
	}

	public byte[] toBytes() {
		StringUtil.getUnicodeBytes(name, bytes, 0);// write name
		NumUtil.getTwoBytes(name.length() * 2 + 2, bytes, 64);
		bytes[66] = (byte) type;
		bytes[67] = (byte) color;
		NumUtil.getFourBytes(leftDID, bytes, 68);
		NumUtil.getFourBytes(rightDID, bytes, 72);
		NumUtil.getFourBytes(rootDID, bytes, 76);
		NumUtil.getFourBytes(secID, bytes, 116);
		NumUtil.getFourBytes(streamSize, bytes, 120);
		return bytes;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("name:" + name + ",");
		sb.append("type:" + type + ",");
		sb.append("color:" + color + ",");
		sb.append("leftDID:" + leftDID + ",");
		sb.append("rightDID:" + rightDID + ",");
		sb.append("rootDID:" + rootDID + ",");
		sb.append("shortStreamContainerSID:" + secID + ",");
		sb.append("shortStreamContainerSize:" + streamSize);
		sb.append("}");
		return sb.toString();
	}
}
