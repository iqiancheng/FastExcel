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

import java.io.File;

import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;
import edu.npu.fastexcel.compound.io.ReadException;
import edu.npu.fastexcel.compound.io.Reader;
import edu.npu.fastexcel.compound.stream.ReadableStream;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-28
 */
public class SummaryInformation {

	private ReadableStream summaryInfoStream;
	private CompoundFileReader compoundFile;
	private Reader reader;

	//
	/* summary information filed */

	//
	/**
	 * Constructor
	 * 
	 * @param file
	 *            The compound file.note this file should already be opened.
	 */
	public SummaryInformation(CompoundFileReader file) {
		this.compoundFile = file;
		summaryInfoStream = file
				.getReadableStream(DirectoryEntry.SUMMARY_INFORMATION_NAME);
		if (summaryInfoStream == null) {
			return;
		}
		reader = summaryInfoStream.getReader();

	}

	/**
	 * 
	 * @throws ReadException
	 */
	public void load() throws ReadException {
		if (reader == null) {
			return;// summary information did not present.
		}
		reader.open();
		int length = summaryInfoStream.getSize();
		byte bb[] = new byte[length];
		reader.read(bb);
		//
		int secCount = NumUtil.getInt(bb[24], bb[25], bb[26], bb[27]);
		int now = 28;
		int secLen = 20;
		int secs[] = new int[secCount];
		for (int i = 0; i < secCount; i++) {
			int secoff = NumUtil.getInt(bb[now + 16], bb[now + 17],
					bb[now + 18], bb[now + 19]);
			secs[i] = secoff;
			now += secLen;
		}
		for (int i = 0; i < secs.length; i++) {
			// read section
			int off = secs[i];
			int seclen = NumUtil.getInt(bb[off], bb[off + 1], bb[off + 2],
					bb[off + 3]);
			int propCount = NumUtil.getInt(bb[off + 4], bb[off + 5],
					bb[off + 6], bb[off + 7]);
			
			int n = off + 8;
			for (int j = 0; j < propCount; j++) {
				int id = NumUtil.getInt(bb[n], bb[n + 1], bb[n + 2], bb[n + 3]);
				int offset = NumUtil.getInt(bb[n + 4], bb[n + 5], bb[n + 6],
						bb[n + 7]);
				n += 8;
				readProperty(bb, id, offset);
			}

		}

		//
		reader.close();
	}

	/*
	 * 02 2 byte signed integer 
	 * 03 4 byte signed integer 
	 * 13 4 byte unsigned integer 
	 * 1e null-terminated string prepended by dword string length 
	 * 40 Filetime (64-bit value representing the number of 100-nanosecond
	 * 		intervals since January 1, 1601) 
	 * 47 Clipboard format
	 */
	private void readProperty(byte b[], int id, int offset) {
		int type = NumUtil.getInt(b[offset], b[offset + 1], b[offset + 2],
				b[offset + 3]);
		System.err.println("----id:" + id + " type:" + type);

		switch (type) {
		case 0x02:
			int value = NumUtil.getInt(b[offset + 4], b[offset + 5]);
			System.err.println(type + "," + value);
			break;
		case 0x03:
			value = NumUtil.getInt(b[offset + 4], b[offset + 5], b[offset + 6],
					b[offset + 7]);
			System.err.println(type + "," + value);
			break;
		case 0x13:
			value = NumUtil.getInt(b[offset + 4], b[offset + 5], b[offset + 6],
					b[offset + 7]);
			System.err.println(type + "," + value);
			break;
		case 0x1e:
			int len = NumUtil.getInt(b[offset + 4], b[offset + 5],
					b[offset + 6], b[offset + 7]);
			String ss = StringUtil.getASCIIString(b, offset + 8, len);
			System.err.println(type + "," + ss);
			break;
		case 0x40:
			System.out.println("----time");
			break;
		case 0x47:
			break;
		default:

			break;
		}
	}

	public static void main(String[] args) throws ReadException {
		CompoundFileReader file = new CompoundFileReader(new File("d:/aa.xls"));
		file.open();
		System.out.println(file);
		SummaryInformation information = new SummaryInformation(file);
		information.load();
	}
}
