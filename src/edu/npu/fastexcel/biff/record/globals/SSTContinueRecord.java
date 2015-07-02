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
package edu.npu.fastexcel.biff.record.globals;


import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.record.ContinueRecord;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-2-13
 */
public class SSTContinueRecord extends ContinueRecord {
	
	
	SSTContinueRecord next;
	int currentRecordLength;
	
	public SSTContinueRecord() {
		super();
		next=null;
		currentRecordLength=RECORD_HEADER_LENGTH;
	}
	public SSTContinueRecord getLast(){
		SSTContinueRecord temp=this;
		while(temp.next!=null){
			temp=temp.next;
		}
		return temp;
	}
	public boolean hasNext(){
		return next!=null;
	}
	public byte[] getBytes() {
		byte t[]=bytes;
		bytes=new byte[4];
		setType(Types.CONTINUE);
		return t;
	}
	public byte [] getAllBytes(){
		byte bb[]=new byte[0];
		SSTContinueRecord temp=this;
		while(temp!=null){
			byte mbb[]=temp.getBytes();
			byte nbb[]=new byte[mbb.length+bb.length];
			System.arraycopy(bb, 0, nbb, 0, bb.length);
			System.arraycopy(mbb, 0, nbb, bb.length,mbb.length);
			bb=nbb;
			temp=temp.next;
		}
		return bb;
	}
	
	public void addString(String str){
		int charLeft=str.length();
		if(currentRecordLength+5>MAX_RECORD_LENGTH){
			//need continue
			SSTContinueRecord cr=new SSTContinueRecord();
			cr.addString(str);
			this.next=cr;
			return;
		}
		currentRecordLength+=3;
		int spaceLeft=(MAX_RECORD_LENGTH-currentRecordLength)/2;
		if(spaceLeft>=charLeft){
			addByte(StringUtil.getBIFFBytes(str));
			currentRecordLength+=charLeft*2;
		}else{
			byte bb[]=StringUtil.getBIFFBytes(str);
			byte result[]=new byte[3+spaceLeft*2];
			System.arraycopy(bb,0,result,0,result.length);
			addByte(result);
			charLeft-=spaceLeft;
			SSTContinueRecord r=new SSTContinueRecord();
			this.next=r;
			r.addSubString(str, charLeft);
		}
	}
	
	public void addSubString(String str,int charLeft){
		currentRecordLength+=1;//1 means UNICODE flag
		byte b[]={0x01};
		addByte(b);
		
		int spaceLeft=(MAX_RECORD_LENGTH-currentRecordLength)/2;
		int len=str.length();
		int index=(len-charLeft);
		byte bb[]=StringUtil.getBIFFBytes(str);
		if(spaceLeft>=charLeft){
			byte nbb[]=new byte[charLeft*2];
			System.arraycopy(bb,3+index*2,nbb,0,nbb.length);
			addByte(nbb);
			currentRecordLength+=charLeft*2;
		}else{
			byte nbb[]=new byte[spaceLeft*2];
			charLeft-=spaceLeft;
			System.arraycopy(bb, 3+index*2, nbb, 0, nbb.length);
			addByte(nbb);
			currentRecordLength+=spaceLeft*2;
			SSTContinueRecord r=new SSTContinueRecord();
			this.next=r;
			r.addSubString(str, charLeft);
		}
		
	}
	
	public static void main(String[] args) {
		SSTContinueRecord sr=new SSTContinueRecord();
		StringBuffer sb=new StringBuffer();
		for(int i=0;i<6000;i++){
			sb.append("A");
		}
		
		//sr.addString(sb.toString());
		sr.addString("VVVVVVV");
		sr.addString("CCC");
		
		sr.getLast();
		String str=StringUtil.dumpBytes(sr.getAllBytes());
		System.out.println(str);
	}
}
