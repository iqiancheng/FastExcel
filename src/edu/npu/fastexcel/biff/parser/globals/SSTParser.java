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
package edu.npu.fastexcel.biff.parser.globals;

import edu.npu.fastexcel.ExcelException;
import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.read.RecordReader;
import edu.npu.fastexcel.biff.record.ReadOnlyRecord;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;
import edu.npu.fastexcel.compound.io.ReadException;

/**
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * --	--	--	--	00FCH
 * this record contains a list of all strings used anywhere in the workbook. 
 * each string occurs only once. The workbook uses indexes into the list to 
 * reference the strings.
 * record SST, BIFF8:
 * offset 	Size 	Contents
 *  	4 	Total number of strings in the workbook (see below)
 *  	4 	Number of following strings (nm)
 *  	var.	List of nm Unicode strings, 16-bit string length 
 * 	The first field of the SST record counts the total occurrence of
 * 	strings in the workbook. For instance, the string "AAA" is used 3
 * 	times and the string "BBB" is used 2 times. The first field contains 
 * 	5 and the second field contains 2, followed by the two strings.
 * </pre>
 * 
 * <hr/>
 * 
 * <pre>
 * the string consists of the character count (as usual an 8-bit value or a 16-bit value),
 * option flags, the character array and optional formatting information. In general, 
 * the option flags field occurs also for empty strings. But in a few records, this
 * field is omitted, if the string is empty. This is mentioned at the respective place.
 * offset Size Contents
 *  	1 or 2 	Length of the string (character count, ln)
 *  or 2 	1 	Option flags:
 * 	Bit Mask Contents
 * 	0 	01H Character compression (ccompr):
 * 			02 = Compressed (8-bit characters)
 * 			12 = Uncompressed (16-bit characters)
 * 	2 	04H Asian phonetic settings (phonetic):
 * 			02 = Does not contain Asian phonetic settings
 * 			12 = Contains Asian phonetic settings
 * 	3 	08H Rich-Text settings (richtext):
 * 			02 = Does not contain Rich-Text settings
 * 			12 = Contains Rich-Text settings
 * 2 or 3]	2 	(optional, only if richtext=1) Number of Rich-Text formatting runs (rt)
 * var.] 	4 	(optional, only if phonetic=1) Size of Asian phonetic settings block (in bytes, sz)
 * var. 	ln or 2+6*ln Character array (8-bit characters or 16-bit characters, dependent on ccompr)
 * var.] 	4+6*7	rt (optional, only if richtext=1) List of rt formatting runs 
 * var.] 	sz 	(optional, only if phonetic=1) Asian Phonetic Settings Block (see below)
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class SSTParser extends WorkBookParser {
	/* mask of string types */
	public static final int STRING_MASK_CCOMPR = 0x01;
	public static final int STRING_MASK_PHONETIC = 0x04;
	public static final int STRING_MASK_RICHTEXT = 0x08;
	public SSTParser() {
		super(Types.SST);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int total = NumUtil.getInt(b[off], b[off + 1], b[off + 2], b[off + 3]);
		// four byte represent total
		int nm = NumUtil.getInt(b[off + 4], b[off + 5], b[off + 6], b[off + 7]);
		// number of strings in workbook
		int t = off + 8;
		//
		try {
			getSSTEntries(t, nm, b);
			workBookGlobalsStream.setTotalString(total);
		} catch (ReadException e) {
			throw new ParserException(e);
		}
		
	}

	private final void getSSTEntries(int offset, int nm, byte bytes[]) 
		throws ReadException{
		ResultHolder rh = new ResultHolder();
		rh.offset = offset;
		rh.bytes = bytes;
		rh.recordReader=context.getStreamReader();
		try {
			for (int i = 0; i < nm; i++) {
				workBookGlobalsStream.addSST(getSSTString(rh));	
			}
		} catch (ExcelException e) {
			throw new ReadException(e);
		}
	}

	/**
	 * Just Result Holder
	 */
	private class ResultHolder {
		int index;
		int currentIndex;
		int offset;
		byte[] bytes;
		RecordReader recordReader;
		public ResultHolder() {
			index = 0;
			currentIndex=0;
			offset = 0;
		}

		public int isSpanBreak(int byteToRead) {
			byte bb[] = bytes;
			return ((offset + byteToRead) - bb.length);
		}

		public byte[] getByte() throws ReadException {
			if(currentIndex==index){
				return bytes;
			}else{
				ReadOnlyRecord record = new ReadOnlyRecord();
				recordReader.nextRecord(record);
				if(record.getType()!=Types.CONTINUE){
					recordReader.undoNext();
					return new byte[0];
				}
				bytes=record.getBytes();
				currentIndex++;
				return bytes;
			}
			
		}

		public int maxCharLen(boolean ascii) {
			if (ascii){
				return bytes.length - offset;
			}
			return (bytes.length - offset) / 2;
		}
		private boolean hasContinue() throws ReadException{
			ReadOnlyRecord record = new ReadOnlyRecord();
			recordReader.nextRecord(record);
			boolean result=(record.getType()==Types.CONTINUE);
			recordReader.undoNext();
			return result;
		}
		public int continueToRead(boolean ascii, int charLeft) 
			throws ReadException {
			if (ascii) {
				return !hasContinue() ? charLeft : Math.min(
						charLeft, bytes.length - offset);
			} else {
				return !hasContinue() ? charLeft : Math.min(
						charLeft, (bytes.length - offset) / 2);
			}
		}
		public String toString() {
			return "ResultHolder [currentIndex=" + currentIndex + ", index="
					+ index + ", offset=" + offset + "]";
		}
	}

	/*
	 * get string of record
	 */
	private final String getString(boolean ascii, int len, 
			ResultHolder holder) throws ReadException {
		int byteLen = len;
		byte bb[] = holder.getByte();
		if (!ascii) {
			byteLen *= 2;
		}
		int span = holder.isSpanBreak(byteLen);
		if (span <= 0) {
			String str;
			if (ascii) {
				str = StringUtil.getASCIIString(bb, holder.offset, len);
				holder.offset += len;
			} else {
				str = StringUtil.getUnicodeString(bb, holder.offset, len);
				holder.offset += len * 2;
			}
			if (span == 0) {
				holder.index++;
				holder.offset = 4;
			}
			return str;
		} else {
			String str;
			int maxChar = holder.maxCharLen(ascii);

			if (ascii) {
				str = StringUtil.getASCIIString(bb, holder.offset, maxChar);
			} else {
				str = StringUtil.getUnicodeString(bb, holder.offset, maxChar);
			}

			String continueStr = getContinueString(holder, len - maxChar);
			return str + continueStr;
		}

	}

	/* get string from continue record */
	private final String getContinueString(ResultHolder holder, int len) 
		throws ReadException {
		StringBuffer sb = new StringBuffer();

		int charLeft = len;
		while (charLeft > 0) {
			holder.index++;
			holder.offset = 4;
			byte bb[] = holder.getByte();
			if (bb[holder.offset] == 0x00) {// ascii string
				holder.offset += 1;
				int read = holder.continueToRead(true, charLeft);
				sb.append(StringUtil.getASCIIString(bb, holder.offset, read));
				holder.offset += read;
				charLeft -= read;
			} else {
				holder.offset += 1;
				int read = holder.continueToRead(false, charLeft);
				sb.append(StringUtil.getUnicodeString(bb, holder.offset, read));
				holder.offset += read * 2;
				charLeft -= read;
			}
		}
		return sb.toString();
	}

	/* get SST string */
	private final String getSSTString(ResultHolder holder) 
		throws ReadException {
		byte b[] = holder.getByte();
		int ln = NumUtil.getInt(b[holder.offset], b[holder.offset + 1]);// character
		// count
		int f = b[holder.offset + 2];// flag
		holder.offset += 3;
		boolean ccompr = (f & STRING_MASK_CCOMPR) == 0;// true means 8-bit
		// characters
		// false means 16-bit
		boolean phonetic = (f & STRING_MASK_PHONETIC) == 0;
		// true means Does not contain Asian phonetic settings
		// false means Contains Asian phonetic settings
		boolean richtext = (f & STRING_MASK_RICHTEXT) == 0;
		// true means Does not contain Rich-Text settings
		// false means Contains Rich-Text settings

		int incr = 0, rt = 0, sz = 0;
		String name = "";
		if (!richtext) {
			rt = NumUtil.getInt(b[holder.offset], b[holder.offset + 1]);
			incr += 2;
		}
		if (!phonetic) {
			sz = NumUtil.getInt(b[holder.offset + incr], b[holder.offset + incr
					+ 1], b[holder.offset + incr + 2], b[holder.offset + incr
					+ 3]);
			incr += 4;
		}
		holder.offset += incr;
		//
		if (!ccompr) {
			// 16bit
			name = getString(false, ln, holder);
		} else {
			// 8bit
			name = getString(true, ln, holder);
		}
		if (!richtext) {
			holder.offset += rt * 4;
		}
		if (!phonetic) {
			holder.offset += sz;
		}
		int ll = holder.offset - holder.getByte().length;
		// MICROSOFT is a piece of shit!
		if (ll >= 0) {
			holder.index++;
			holder.offset = ll + 4;
		}
		return name;
	}
}
