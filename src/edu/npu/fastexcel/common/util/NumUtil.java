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

/**
 * The <code>NumUtil</code> is utilities for converting little-endian byte to
 * number. <br/>
 * 
 * <pre>
 * An RK value is an encoded integer or floating-point value. RK values have a 
 * size of 4 bytes and are used to decrease file size for floating-point values.
 * Structure of an RK value (32-bit value), BIFF3-BIFF8:
 * Bit Mask Contents
 * 0 	00000001H 	0 = Value not changed 1 = Encoded value is multiplied by 100
 * 1 	00000002H 	0 = Floating-point value 1 = Signed integer value
 * 31-2 	FFFFFFFCH 	Encoded value
 * If bit 1 is cleared, the encoded value represents the 30 most significant bits 
 * of an IEEE 754 floating-point value (64-bit double precision). The 34 least 
 * significant bits must be set to zero. If bit 1 is set, the encoded value 
 * represents a signed 30-bit integer value. To get the correct integer, the 
 * encoded value has to be shifted right arithmetically by 2 bits. If bit 0 is set, 
 * the decoded value (both integer and floating-point) must be divided by 100 to 
 * get the final result.
 * </pre>
 * 
 * @see StringUtil
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class NumUtil {

	public static final int RK_MASK_BIT0 = 0x00000001;
	public static final int RK_MASK_BIT1 = 0x00000002;
	public static final int RK_MASK_OTHER = 0xFFFFFFFC;

	/**
	 * Return a RK Format number value. An RK value is an encoded integer or
	 * floating-point value. RK values have a size of 4 bytes and are used to
	 * decrease file size for floating-point values. Structure of an RK value
	 * (32-bit value), BIFF3-BIFF8:<br/>
	 * <table>
	 * <tr>
	 * <td>Bit</td>
	 * <td>Mask</td>
	 * <td>Contents</td>
	 * </tr>
	 * <tr>
	 * <td>0</td>
	 * <td>00000001H</td>
	 * <td>0 = Value not changed 1 = Encoded value is multiplied by 100</td>
	 * </tr>
	 * <tr>
	 * <td>1</td>
	 * <td>00000002H</td>
	 * <td>0 = Floating-point value 1 = Signed integer value</td>
	 * </tr>
	 * <tr>
	 * <td>31-2</td>
	 * <td>FFFFFFFCH</td>
	 * <td>Encoded value</td>
	 * </tr>
	 * </table>
	 * If bit 1 is cleared, the encoded value represents the 30 most significant
	 * bits of an IEEE 754 floating-point value (64-bit double precision). The
	 * 34 least significant bits must be set to zero. If bit 1 is set, the
	 * encoded value represents a signed 30-bit integer value. To get the
	 * correct integer, the encoded value has to be shifted right arithmetically
	 * by 2 bits. If bit 0 is set, the decoded value (both integer and
	 * floating-point) must be divided by 100 to get the final result. Examples:
	 * <table>
	 * <tr>
	 * <td>RK value</td>
	 * <td>Type</td>
	 * <td>Div 100</td>
	 * <td>Encoded value</td>
	 * <td>Decoded value</td>
	 * <td>Result</td>
	 * </tr>
	 * <tr>
	 * <td>3FF00000H</td>
	 * <td>float</td>
	 * <td>no</td>
	 * <td>3FF00000H</td>
	 * <td>3FF0000000000000H = 1.0</td>
	 * <td>1.0</td>
	 * </tr>
	 * <tr>
	 * <td>3FF00001H</td>
	 * <td>float</td>
	 * <td>yes</td>
	 * <td>3FF00000H</td>
	 * <td>3FF0000000000000H = 1.0</td>
	 * <td>0.01</td>
	 * </tr>
	 * <tr>
	 * <td>004B5646H</td>
	 * <td>integer</td>
	 * <td>no</td>
	 * <td>004B5644H</td>
	 * <td>0012D591H = 1234321</td>
	 * <td>1234321</td>
	 * </tr>
	 * <tr>
	 * <td>004B5647H</td>
	 * <td>integer</td>
	 * <td>yes</td>
	 * <td>004B5644H</td>
	 * <td>0012D591H = 1234321</td>
	 * <td>12343.21</td>
	 * </tr>
	 * </table>
	 * 
	 * @param RK the RK Format.
	 */
	public static double getRKValue(int number) {
		//NOTE CODE FROM POI
		//THANS TO maojianke001@pingan.com.cn
		long rawNumber = number;

		// mask off the two low-order bits, 'cause they're not part of
		// the number
		rawNumber = rawNumber >> 2;
		double rvalue = 0;
		if ((number & 0x02) == 0x02) {
			// ok, it's just a plain ol' int; we can handle this
			// trivially by casting
			rvalue = (double) (rawNumber);
		} else {
			// also trivial, but not as obvious ... left shift the
			// bits high and use that clever static method in Double
			// to convert the resulting bit image to a double
			rvalue = Double.longBitsToDouble(rawNumber << 34);
		}
		if ((number & 0x01) == 0x01) {

			// low-order bit says divide by 100, and so we do. Why?
			// 'cause that's what the algorithm says. Can't fight city
			// hall, especially if it's the city of Redmond
			rvalue /= 100;
		}
		return rvalue;
	}

	/**
	 * Gets an int from two bytes
	 * 
	 * @param b2
	 *            the second byte
	 * @param b1
	 *            the first byte
	 * @return The integer value
	 */
	public static int getInt(byte b1, byte b2) {
		int i1 = b1 & 0xff;
		int i2 = b2 & 0xff;
		int val = i2 << 8 | i1;
		return val;
	}
	/**
	 * Gets an int from four bytes, doing all the necessary swapping
	 * 
	 * @param b1
	 *            a byte
	 * @param b2
	 *            a byte
	 * @param b3
	 *            a byte
	 * @param b4
	 *            a byte
	 * @return the integer value represented by the four bytes
	 */
	public static int getInt(byte b1, byte b2, byte b3, byte b4) {
		int i1 = getInt(b1, b2);
		int i2 = getInt(b3, b4);
		int val = i2 << 16 | i1;
		return val;
	}

	/**
	 * Gets a two byte array from an integer
	 * 
	 * @param i
	 *            the integer
	 * @return the two bytes
	 */
	public static void getTwoBytes(int i, byte[] b) {
		b[0] = (byte) (i & 0xff);
		b[1] = (byte) ((i & 0xff00) >> 8);
	}

	/**
	 * Gets a four byte array from an integer
	 * 
	 * @param i
	 *            the integer
	 * @return a four byte array
	 */
	public static byte[] getFourBytes(int i) {
		byte[] bytes = new byte[4];
		int i1 = i & 0xffff;
		int i2 = (i & 0xffff0000) >> 16;
		getTwoBytes(i1, bytes, 0);
		getTwoBytes(i2, bytes, 2);
		return bytes;
	}

	/**
	 * Converts an integer into two bytes, and places it in the array at the
	 * specified position.
	 * 
	 * @param target
	 *            the array to place the byte data into
	 * @param pos
	 *            the position at which to place the data
	 * @param i
	 *            the integer value to convert
	 */
	public static void getTwoBytes(int i, byte[] target, int pos) {
		target[pos] = (byte) (i & 0xff);
		target[pos + 1] = (byte) ((i & 0xff00) >> 8);
	}

	/**
	 * Converts an integer into four bytes, and places it in the array at the
	 * specified position .
	 * 
	 * @param target
	 *            the array which is to contain the converted data
	 * @param pos
	 *            the position in the array in which to place the data
	 * @param i
	 *            the integer to convert
	 */
	public static void getFourBytes(int i, byte[] target, int pos) {
		byte[] bytes = getFourBytes(i);
		target[pos] = bytes[0];
		target[pos + 1] = bytes[1];
		target[pos + 2] = bytes[2];
		target[pos + 3] = bytes[3];
	}

	public static void fill(byte b, byte[] target, int pos, int len) {
		for (int i = 0; i < len; i++) {
			target[pos + i] = b;
		}
	}

	/**
	 * Gets the IEEE value from the byte array passed in
	 * 
	 * @param pos
	 *            the position in the data block which contains the double value
	 * @param data
	 *            the data block containing the raw bytes
	 * @return the double value converted from the raw data
	 */
	public static double getIEEEDouble(byte[] data, int pos) {
		int num1 = getInt(data[pos], data[pos + 1], data[pos + 2],
				data[pos + 3]);
		int num2 = getInt(data[pos + 4], data[pos + 5], data[pos + 6],
				data[pos + 7]);

		// Long.parseLong doesn't like the sign bit, so have to extract this
		// information and put it in at the end. (Acknowledgment: thanks
		// to Ruben for pointing this out)
		boolean negative = ((num2 & 0x80000000) != 0);
		// Thanks to Lyle for the following improved IEEE double processing
		long val = ((num2 & 0x7fffffff) * 0x100000000L)
				+ (num1 < 0 ? 0x100000000L + num1 : num1);
		double value = Double.longBitsToDouble(val);
		if (negative) {
			value = -value;
		}
		return value;
	}
}
