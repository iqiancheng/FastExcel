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
package edu.npu.fastexcel.common.util;

import java.io.UnsupportedEncodingException;

import edu.npu.fastexcel.biff.parser.globals.BoundSheetParser;
import edu.npu.fastexcel.biff.parser.globals.SSTParser;

/**
 * StringUtil is is utilities for converting little-endian byte to little-endian
 * UNICODE string.
 * 
 * <pre>
 * The string consists of the character count (as usual an 8-bit value or a 16-bit value),
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
 * [2 or 3]	2 	(optional, only if richtext=1) Number of Rich-Text formatting runs (rt)
 * [var.] 	4 	(optional, only if phonetic=1) Size of Asian phonetic settings block (in bytes, sz)
 * [var. 	ln  Character array (8-bit characters or 16-bit characters, dependent on ccompr)
 * [var.] 	 (optional, only if richtext=1) List of rt formatting runs 
 * [var.] 	sz 	(optional, only if phonetic=1) Asian Phonetic Settings Block (see below)
 *</pre>
 * 
 * @see BoundSheetParser
 * @see SSTParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public final class StringUtil {

	public static final String UNICODE_ENCODING = "UnicodeLittle";
	/* mask of string types */
	public static final int STRING_MASK_CCOMPR = 0x01;
	public static final int STRING_MASK_PHONETIC = 0x04;
	public static final int STRING_MASK_RICHTEXT = 0x08;

	/**
	 * Private default constructor to prevent instantiation
	 */
	private StringUtil() {
	}

	/**
	 * Get ASCII string from byte.
	 * 
	 * @param bytes
	 *            the string byte
	 * @param offset
	 *            offset to read
	 * @param len
	 *            length of string
	 * @return the string
	 */
	public static String getASCIIString(byte bytes[], int offset, int len) {
		String s = new String(bytes, offset, len);
		return s;
	}

	/**
	 * Get UNICODE String.
	 * 
	 * @param bytes
	 *            the string byte
	 * @param offset
	 *            offset to read
	 * @param len
	 *            length of string
	 * @return the string
	 */
	public static String getUnicodeString(byte bytes[], int offset, int len) {
		try {
			return new String(bytes, offset, len * 2, UNICODE_ENCODING);
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}

	/**
	 * Get BIFF format UNICODE bytes from string.
	 * 
	 * @param str
	 *            the original string.
	 * @param b
	 *            result holder.
	 * @see SSTParser
	 */
	public static byte[] getBIFFBytes(String str) {
		byte b[];
		int ln = str.length();
		byte f = 0x01;// not contain Asian phonetic settings
		// not contain Rich-Text settings
		// 16 bit
		byte[] bb = getUnicodeBytes(str);
		b = new byte[3 + bb.length];
		NumUtil.getTwoBytes(ln, b, 0);
		b[2] = f;
		System.arraycopy(bb, 0, b, 3, bb.length);
		return b;

	}

	public static int getBIFFUnicodeString(byte b[], int offset, StringBuffer sb) {
		int from = offset;
		int ln = NumUtil.getInt(b[offset], b[offset + 1]);// character count
		int f = b[offset + 2];// flag
		offset += 3;
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
			rt = NumUtil.getInt(b[offset], b[offset + 1]);
			incr += 2;
		}
		if (!phonetic) {
			sz = NumUtil.getInt(b[offset + incr], b[offset + incr + 1],
					b[offset + incr + 2], b[offset + incr + 3]);
			incr += 4;
		}
		offset += incr;
		//
		if (!ccompr) {
			// 16bit
			name = getUnicodeString(b, offset, ln);
		} else {
			// 8bit
			name = getASCIIString(b, offset, ln);
		}
		if (!richtext) {
			offset += rt * 4;
		}
		if (!phonetic) {
			offset += sz;
		}
		sb.append(name);
		return offset - from;
	}

	/**
	 * @return String of BIFF String record
	 */
	public static String getString(byte b[], int offset) {
		int len = b[offset];
		int f = b[offset + 1];
		String name;
		if (f == 0) {// Standard ASCII encoding
			name = StringUtil.getASCIIString(b, offset + 2, len);
		} else {// little endian Unicode encoding
			name = StringUtil.getUnicodeString(b, offset + 2, len);
		}
		return name;
	}

	/**
	 * Join all array elements as one single string
	 * 
	 * @param s
	 *            the array
	 * @return the string
	 */
	public static String join(int s[]) {
		if(s==null){
			return "{null}";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < s.length; i++) {
			if (i % 16 == 0 && i != 0)
				sb.append("\n");
			sb.append(s[i] + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Join all array elements as one single string
	 * 
	 * @param s
	 *            the array
	 * @return the string
	 */
	public static String join(Object s[]) {
		if(s==null){
			return "{null}";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i] + ",\n");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Join all array elements as one single string
	 * 
	 * @param s
	 *            the array
	 * @return the string
	 */
	public static String join(String s[]) {
		if (s == null) {
			return "null";
		}
		if (s.length == 0) {
			return "{}";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < s.length; i++) {
			sb.append(s[i] + ",");
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Join all array elements as one single string
	 * 
	 * @param s
	 *            the array
	 * @return the string
	 */
	public static String join(byte s[]) {
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		for (int i = 0; i < s.length; i++) {
			if (i % 16 == 0 && i != 0)
				sb.append("\n");
			sb.append(s[i] + ",");

		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Join all array elements as one single string
	 * 
	 * @param s
	 *            the array
	 * @return the string
	 */
	public static String joinHex(byte s[]) {
		StringBuffer sb = new StringBuffer();
		String ss = "";
		sb.append("{");
		for (int i = 0; i < s.length; i++) {
			if (i % 16 == 0 && i != 0)
				sb.append("\n");
			ss = "0" + Integer.toHexString(s[i]);
			sb.append(ss.substring(ss.length() - 2) + ",");

		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("}");
		return sb.toString();
	}

	/**
	 * Dump byte array as hex string.
	 * 
	 * @param bb
	 *            the byte array
	 */
	public static String dumpBytes(byte bb[]) {
		StringBuffer result = new StringBuffer(bb.length * 4);
		String ss;
		int c = 8;// 8 byte one line
		int lineCount = bb.length / c;
		int t = bb.length % c;
		if (t != 0) {
			lineCount++;
		}
		for (int i = 0; i < lineCount; i++) {
			result.append(i * c + "\t:");
			int tt = c;
			if (t != 0 && i == lineCount - 1) {
				tt = t;
			}
			char cc[] = new char[tt];
			for (int j = 0; j < tt; j++) {
				ss = "0" + Integer.toHexString(bb[i * c + j]);
				result.append(ss.substring(ss.length() - 2) + ",");
				cc[j] = (char) bb[i * c + j];
			}
			for (int k = 0; k < c - tt; k++) {
				result.append("--,");// fill hole with '--'
			}
			result.append("\t:");
			result.append(cc);
			result.append("\n");
		}
		return result.toString();
	}

	public static String fill(String s, int count) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < count; i++) {
			sb.append(s);
		}
		return sb.toString();
	}

	//
	/**
	 * Gets the bytes of the specified string. This will simply return the ASCII
	 * values of the characters in the string
	 * 
	 * @param s
	 *            the string to convert into bytes
	 * @return the ASCII values of the characters in the string
	 */
	public static byte[] getBytes(String s) {
		return s.getBytes();
	}

	public static int getBytes(String s, byte bytes[], int offset) {
		byte[] bb = getBytes(s);
		System.arraycopy(bb, 0, bytes, offset, bb.length);
		return bb.length;
	}

	/**
	 * Converts the string into a little-endian array of Unicode bytes
	 * 
	 * @param s
	 *            the string to convert
	 * @return the unicode values of the characters in the string
	 */
	public static byte[] getUnicodeBytes(String s) {
		try {
			byte[] b = s.getBytes(UNICODE_ENCODING);
			// Sometimes this method writes out the unicode
			// identifier
			if (b.length == (s.length() * 2 + 2)) {
				byte[] b2 = new byte[b.length - 2];
				System.arraycopy(b, 2, b2, 0, b2.length);
				b = b2;
			}
			return b;
		} catch (UnsupportedEncodingException e) {
			// Fail silently
			return null;
		}
	}

	/**
	 * Converts the string into a little-endian array of UNICODE bytes
	 * 
	 * @param s
	 *            the string to convert
	 * @param bytes
	 *            result holder.
	 * @param offset
	 *            offset to result
	 * @return the UNICODE bytes's length.
	 */
	public static int getUnicodeBytes(String s, byte bytes[], int offset) {
		byte[] bb = getUnicodeBytes(s);
		System.arraycopy(bb, 0, bytes, offset, bb.length);
		return bb.length;
	}

}
