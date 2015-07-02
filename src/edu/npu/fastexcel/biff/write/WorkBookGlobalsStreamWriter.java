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
package edu.npu.fastexcel.biff.write;

import java.util.ArrayList;

import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.biff.record.RecordFactory;
import edu.npu.fastexcel.biff.record.globals.BoundSheetRecord;
import edu.npu.fastexcel.biff.record.globals.SSTRecord;
import edu.npu.fastexcel.compound.io.WriteException;
import edu.npu.fastexcel.compound.io.Writer;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-28
 */
public class WorkBookGlobalsStreamWriter implements StreamWriter {
	/*The maximum number of characters permissible for a sheet name*/ 
	private final static int MAX_SHEET_NAME_LEN = 31;
	/* The illegal characters for a sheet name*/
	private final static char[] ILLEGAL_CHARACTERS_OF_SHEET_NAME = 
		new char[] {'*', ':', '?', '\\'}; 
	
	/*the OLE stream writer*/
	private Writer writer;
	private SSTRecord sstRecord;
	private ArrayList sheetStreamWriters;
	private RecordFactory factory;
	
	
	public WorkBookGlobalsStreamWriter(Writer writer,RecordFactory factory) {
		this.writer=writer;
		this.factory=factory;
		sheetStreamWriters=new ArrayList();
		sstRecord=new SSTRecord();
	}
	
	/**
	 * @return the sheetStreamWriters
	 */
	public ArrayList getSheetStreamWriters() {
		return sheetStreamWriters;
	}

	/**
	 * Add a shared string to workbook globals stream.
	 * @param str the string.
	 * @return string's shared string index. 
	 */
	public int addSharedString(String str)throws WriteException{
		return sstRecord.addString(str);
	}
	/*
	 * check sheet name.
	 * sheet name 's length should less than 31
	 * sheet name can not start with '
	 * sheet name can not contains * : ? \
	 */
	private void checkSheetName(String sheetName)throws WriteException{
		if(sheetName==null||sheetName.trim().equals("")){
			throw new WriteException("Sheet name cannot be null.");
		}
		sheetName=sheetName.trim();
		if(sheetName.length()>MAX_SHEET_NAME_LEN){
			throw new WriteException("Sheet name's length should less than 31.");
		}
		if(sheetName.charAt(0) == '\''){
			throw new WriteException("Sheet name cannot start with \'");
		}
		for(int i=0;i<ILLEGAL_CHARACTERS_OF_SHEET_NAME.length;i++){
			if(sheetName.indexOf(ILLEGAL_CHARACTERS_OF_SHEET_NAME[i])!=-1){
				throw new WriteException("Sheet name cannot contain:"
						+ILLEGAL_CHARACTERS_OF_SHEET_NAME[i]);
			}
		}
		
	}
	/**
	 * Add a sheet with given name.
	 * @param sheetName the sheet name
	 * @param stream indicate whether or not this sheet is stream-based
	 * @return the sheet stream writer
	 * @throws WriteException when something error
	 */
	public SheetWriter addSheet(String sheetName,boolean stream)throws WriteException{
		checkSheetName(sheetName);
		SheetWriter sheetWriter;
		if(stream){
			FileSheetStreamWriter ssw=new FileSheetStreamWriter(this,factory);
			ssw.setName(sheetName);
			ssw.setIndex(sheetStreamWriters.size());
			ssw.open();
			sheetWriter=ssw;
		}else{
			DefaultSheetStreamWriter ssw=new DefaultSheetStreamWriter(this,factory);
			ssw.setName(sheetName);
			ssw.setIndex(sheetStreamWriters.size());
			ssw.open();
			sheetWriter=ssw;
		}
		if(sheetStreamWriters.contains(sheetWriter)){
			throw new WriteException("Sheet:"+sheetName+" already exists.");
		}
		sheetStreamWriters.add(sheetWriter);
		return sheetWriter;
	}
	/**
	 * Open this Workbook Globals Stream writer.Write common records.
	 */
	public void open() throws WriteException {
		writeCommonStart();
	}
	/**
	 * Return the position of writer.
	 */
	public int position() throws WriteException {
		return writer.position();
	}
	/**
	 * Write Sheet record,shared string table record.
	 * Write sheet stream.
	 * Close writer.
	 */
	public void close() throws WriteException {
		int sheetPos=writer.position();
		writeSheets();
		
		writeSST();
		writeCommonEnd();
		//write sheets
		for(int i=0;i<sheetStreamWriters.size();i++){
			StreamWriter sw=((StreamWriter) sheetStreamWriters.get(i));
			sw.flush();
			sw.close();
		}
		//update sheet
		writer.seek(sheetPos);
		writeSheets();
		writer.close();
	}
	/**
	 * do nothing
	 */
	public void flush() throws WriteException {
	}
	public void writeRecord(Record record) throws WriteException{
		writer.write(record.getBytes());
	}
	public void writeRecord(byte []record) throws WriteException{
		writer.write(record);
	}
	private void writeSST() throws WriteException{
		SSTRecord.ByteReader reader=sstRecord.getByteReader();
		byte bb[];
		while((bb=reader.read())!=null){
			writer.write(bb);
		}
	}
	private void writeCommonStart() throws WriteException{
		writer.write(factory.getGlobalsBOF().getBytes());
		writer.write(factory.getInterFaceHdr().getBytes());
		writer.write(factory.getMms().getBytes());
		writer.write(factory.getInterFaceEnd().getBytes());
		/*
		 * TODO Not implement
		 *writer.write(factory.getWriteAccess().getBytes());
		 *writer.write(factory.getCodePage().getBytes());
		 */
		writer.write(factory.getDsf().getBytes());
		writer.write(factory.getTabId().getBytes());
		writer.write(factory.getButtonPropertySet().getBytes());
		writer.write(factory.getFngroupCount().getBytes());
		writer.write(factory.getWindowProtect().getBytes());
		writer.write(factory.getProtect().getBytes());
		writer.write(factory.getPassword().getBytes());
		writer.write(factory.getProt4Rev().getBytes());
		writer.write(factory.getWindow1().getBytes());
		writer.write(factory.getBackup().getBytes());
		writer.write(factory.getHideObj().getBytes());
		writer.write(factory.getDateMode1900().getBytes());
		writer.write(factory.getPrecision().getBytes());
		writer.write(factory.getRefreShall().getBytes());
		writer.write(factory.getBookbool().getBytes());
		/*
		 *TODO Not implement 
		 *writer.write(factory.getFont().getBytes());
		 */
		writer.write(factory.getFormat().getBytes());
		writer.write(factory.getXF().getBytes());
		//
		//we did not implement MINI stream yet.
        //so just padding... = =||
		//why XF format? because Microsoft Excel will remove all
		//null record before saving.
		//Thanks to XUJIANFENG004@pingan.com.cn for this bug
        for(int i=0;i<2048;i++){
            writer.write(factory.getXF().getBytes());
            
        }
		
	}
	private void writeCommonEnd() throws WriteException{
		writeRecord(factory.getEOF());
		writeRecord(factory.getEOF());
		writeRecord(factory.getEOF());
		writeRecord(factory.getEOF());
		//why four EOF???
		//some thing cover the last EOF record.so just write more
	}
	/*
	 * Write BOUNDSHEET records
	 */
	private void writeSheets() throws WriteException{
		for(int i=0;i<sheetStreamWriters.size();i++){
			StreamWriter sw=(StreamWriter) sheetStreamWriters.get(i);
			SheetWriter sheetWriter=(SheetWriter) sheetStreamWriters.get(i);
			BoundSheetRecord bsr=new BoundSheetRecord();
			bsr.setName(sheetWriter.getName());
			bsr.setStreamPosition(sw.position());
			writer.write(bsr.getBytes());
		}
	}
	
}
