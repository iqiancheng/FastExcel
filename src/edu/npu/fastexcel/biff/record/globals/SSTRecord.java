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
package edu.npu.fastexcel.biff.record.globals;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.globals.SSTParser;
import edu.npu.fastexcel.biff.record.ContinueRecord;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8 
 * --	--	--	--	 00FCH
 * </pre>
 * 
 * This record contains a list of all strings used anywhere in the workbook.
 * Each string occurs only once. The workbook uses indexes into the list to
 * reference the strings. Record SST, BIFF8:
 * 
 * <pre>
 * offset 	Size 	Contents 
 * 0  	4 	Total number of strings in the workbook (see below) 
 * 4 	4 	Number of following strings (nm) 
 * 8 	var.	List of nm Unicode strings, 16-bit string length  The first field
 * 			of the SST record counts the total occurrence of strings in the 
 * 			workbook. For instance, the string AAA is used 3 times and the 
 * 			string BBB is used 2 times. The first field contains  5 and the 
 * 			second field contains 2, followed by the two strings.
 * </pre>
 * 
 * <hr/>
 * 
 * <pre>
 * The string consists of the character count (as usual an 8-bit value or a 
 * 16-bit value), option flags, the character array and optional formatting 
 * information. In general,  the option flags field occurs also for empty strings.
 * But in a few records, this field is omitted, if the string is empty. This is
 * mentioned at the respective place. 
 * offset Size Contents 
 * 1 or 2 	Length of the string (character count, ln)  
 * 2 	1 	Option flags: 
 * 				Bit Mask Contents 
 * 				0 	01H Character compression (ccompr): 
 * 										02 = Compressed (8-bit characters) 
 * 										12 = Uncompressed (16-bit characters) 
 * 				2 	04H Asian phonetic settings (phonetic): 
 * 										02 = Does not contain Asian phonetic 
 * 											settings 12 = Contains Asian phonetic
 * 											 settings 
 * 				3 	08H Rich-Text settings (richtext):
 * 										02 = Does not contain Rich-Text settings
 * 										12 = Contains Rich-Text settings 
 * [2 or 3]		2 	(optional, only if richtext=1) Number of Rich-Text formatting runs (rt) 
 * [var.]		4 	(optional, only if phonetic=1) Size of Asian phonetic settings block (in bytes,sz) 
 * [var.] 		ln  Character array (8-bit characters or 16-bit characters, dependent on ccompr) 
 * [var.] 		(optional, only if richtext=1) List of rt formatting runs  
 * [var.] 		sz 	(optional, only if phonetic=1) Asian Phonetic Settings Block (see below)
 * </pre>
 * 
 * @see StringUtil
 * @see ContinueRecord
 * @see SSTParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2009-2-12
 * 
 */
public class SSTRecord extends Record {

	/**
	 * Inner struct of Shared String.
	 * 
	 * @author <a href="guooscar@gmail.com">yAma</a> 2009-2-12
	 */
	public class InnerString implements Comparable {
		int id;
		String str;

		public int compareTo(Object o) {
			InnerString other = (InnerString) o;
			if (this.str.equals(other.str)) {
				this.id = other.id;
				return 0;
			}
			if (this.id == -1) {
				return -1;
			}
			return id - other.id;
		}
		
		
		public String toString() {
			return id + "-" + str;
		}

	}

	/**
	 * 
	 * @author <a href="guooscar@gmail.com">yAma</a>2009-2-12
	 */
	public class ByteReader {
		SSTRecord record;
		Iterator iterator;
		boolean first = true;
		
		int charLeft;
		byte buffer[];
		String string;
		int crl;
		SSTContinueRecord continueRecord;
		
		public ByteReader(SSTRecord record) {
			this.record = record;
			this.iterator = record.stringSet.iterator();
			buffer=record.getBytes();
			crl=buffer.length;
		}
		public byte[] read() {	
			byte r[];
			boolean lastState=true;
			while(iterator.hasNext()){
				String str=((InnerString)iterator.next()).str;
				if(addString(str)){
					r=buffer;
					buffer=new byte[0];
					lastState=true;
					return r;
				}else{
					lastState=false;
				}
			}
			if(!lastState){
				lastState=true;//
				return continueRecord==null?buffer:continueRecord.getAllBytes();
			}else{
				return null;
			}
		}
		public boolean addString(String str){
			if(continueRecord==null){
				if(crl+5>MAX_RECORD_LENGTH){
					//need continue
					continueRecord=new SSTContinueRecord();
					continueRecord.addString(str);
					if(continueRecord.hasNext()){
						addToBuffer(continueRecord.getAllBytes());
						continueRecord=continueRecord.getLast();
					}
					return true;
				}
				string=str;
				byte temp[]=StringUtil.getBIFFBytes(str);
				charLeft=string.length();
				crl+=3;
				int spaceLeft=(MAX_RECORD_LENGTH-crl)/2;
				
				if(spaceLeft>=charLeft){
					addToBuffer(temp);
					crl+=charLeft*2;
					charLeft=0;
					return false;
				}else{
					//need continue
					byte tt[]=new byte[3+spaceLeft*2];
					System.arraycopy(temp, 0, tt, 0,tt.length);
					addToBuffer(tt);
					charLeft-=spaceLeft;
					continueRecord=new SSTContinueRecord();
					continueRecord.addSubString(str,charLeft);
					if(continueRecord.hasNext()){
						addToBuffer(continueRecord.getAllBytes());
						continueRecord=continueRecord.getLast();
					}
					return true;
				}
			}else{
				continueRecord.addString(str);
				if(continueRecord.hasNext()){
					addToBuffer(continueRecord.getAllBytes());
					continueRecord=continueRecord.getLast();
					return true;
				}else{
					return false;
				}
			}
		}
		
		private void addToBuffer(byte b[]){
			byte nb[]=new byte[b.length+buffer.length];
			System.arraycopy(buffer, 0, nb, 0,buffer.length);
			System.arraycopy(b, 0, nb,buffer.length,b.length);
			buffer=nb;
		}
		
	}

	////////////////////////////////////////////////////////////////////////////

	private int totalString;
	private int len;// length of this record
	private int uniqueString;
	private Set stringSet;

	/* current record 's length,just for computing SST record's length */
	private int currentRecordLength;

	/**
	 * 
	 */
	public SSTRecord() {
		super(new byte[0]);
		len = 0;
		totalString = 0;
		uniqueString = -1;
		currentRecordLength = RECORD_HEADER_LENGTH + 8;
		stringSet = new TreeSet();
	}

	/**
	 * Add a string to share string table.<br/> NOTE:In order to facilitate the
	 * program, we all use UNICODE string, and without any additional
	 * information, so a length of x string has x*2+3 bytes, '3' here refers to
	 * the string length(2 byte) and the UNICODE flag(1 byte).
	 * 
	 * @param str
	 *            the string.
	 * @return index of this string in share string table.
	 */
	public int addString(String str) {
		totalString++;
		InnerString is = new InnerString();
		is.str = str;
		is.id = -1;
		if (stringSet.contains(is)) {
			return is.id;
		} else {
			is.id = ++uniqueString;
			stringSet.add(is);
			if(is.id!=uniqueString){
				uniqueString--;//it's a bug???
			}else{
				computeLength(str);// compute byte added.
			}
			return is.id;
		}
	}
	public ByteReader getByteReader(){
		return new ByteReader(this);
	}
	
	private void computeLength(String str) {
		if(currentRecordLength==-1){
			return;
		}
		if (currentRecordLength + 5 > MAX_RECORD_LENGTH) {// first character and
															// string
			// header should be in same record
			// need continue
			len += currentRecordLength;
			currentRecordLength = -1;// since we have got sst record's length
			return;
		}
		currentRecordLength += 3; // 3' here refers to the string length(2 byte)
									// and
		// the UNICODE flag(1 byte)
		int charLeft = str.length();
		int spaceLeft = (MAX_RECORD_LENGTH - currentRecordLength) / 2;
		while (charLeft > 0) {
			if (spaceLeft >= charLeft) {
				currentRecordLength += charLeft * 2;// space enough
				charLeft = 0;
			} else {
				charLeft -= spaceLeft;// need continue
				currentRecordLength += spaceLeft * 2;
				len += currentRecordLength;
				// continue
				currentRecordLength = -1;// continue record's header,1 means
				// UNICODE flag.
				return;
			}
		}
	}

	/**
	 * Return record header's content in bytes. NOTE:fetch all content via
	 * ByteReader
	 */
	public byte[] getBytes() {
		if (len == 0) {
			len = currentRecordLength;
		}
		bytes = new byte[RECORD_HEADER_LENGTH + 8];
		setType(Types.SST);
		setContentLength(len - RECORD_HEADER_LENGTH);
		NumUtil.getFourBytes(totalString, bytes, RECORD_HEADER_LENGTH);
		NumUtil.getFourBytes(uniqueString+1, bytes, RECORD_HEADER_LENGTH + 4);
		return bytes;
	}

	public String toString() {
		if (len == 0) {
			len = currentRecordLength;
		}
		StringBuffer sb = new StringBuffer();
		sb.append("uniqueStrings:" + (uniqueString+1) + "\n");
		sb.append("totalStrings:" + totalString + "\n");
		sb.append("len:" + len + "\n");
		Iterator it = stringSet.iterator();
		while (it.hasNext()) {
			sb.append(it.next() + "\n");
		}
		return sb.toString();
	}
}
