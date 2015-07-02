/**
 * 
 */
package edu.npu.fastexcel.biff.read;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

import edu.npu.fastexcel.ExcelException;

/**
 * Shared String Table is very big in excel,A way to save memory is to use
 * temporary files.{@link ArraySST} is the default implemention of SST, it 's
 * fast but need more memory.
 * 
 * @see ArraySST
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a> 2009-4-23
 */
public class FileSST implements SST {

	private int count;
	private int[] indexTable;
	private int[] offsetTable;
	//this value is very important 
	private static final int INIT_INDEX_SIZE = 8192;
	private static final int GROW_SIZE = 8192;
	private File tempFile;
	private int currentPos;
	private RandomAccessFile raf;
	/**
	 * @throws ExcelException 
	 * 
	 */
	public FileSST() throws ExcelException {
		count = 0;
		currentPos=0;
		indexTable = new int[INIT_INDEX_SIZE];
		offsetTable = new int[INIT_INDEX_SIZE];
		String fileName = "SST" + System.currentTimeMillis();
		try {
			tempFile = File.createTempFile(fileName, ".tmp");
			raf=new RandomAccessFile(tempFile, "rw");
		} catch (IOException e) {
			//seem something wrong.use ArraySST instead of TempFileSST
			deleteTempFile();
			throw new ExcelException(e);
		}

	}
	private void deleteTempFile(){
		if(tempFile!=null&&tempFile.exists()){
			tempFile.delete();
		}
	}
	public void addString(String str) throws ExcelException {
		try {
			byte[] ss=str.getBytes();
			raf.write(ss);
			if(count>=indexTable.length){
				int grown[]=new int[indexTable.length+GROW_SIZE];
				System.arraycopy(indexTable, 0, grown, 0, indexTable.length);
				indexTable=grown;
				int grown2[]=new int[offsetTable.length+GROW_SIZE];
				System.arraycopy(offsetTable, 0, grown2, 0, offsetTable.length);
				offsetTable=grown2;
			}
			indexTable[count]=currentPos;
			offsetTable[count]=ss.length;
			currentPos+=ss.length;
			count++;
		} catch (IOException e) {
			deleteTempFile();
			throw new ExcelException(e);
		}
	}
	public void flush() throws ExcelException{
	}
	public String getString(int index) throws ExcelException {
		if(index<0||index>=count){
			return null;
		}
		int pos=indexTable[index];
		int len=offsetTable[index];
		byte bb[]=new byte[len];
		try {
			raf.seek(pos);
			raf.read(bb);
		} catch (IOException e) {
			deleteTempFile();
			throw new ExcelException(e);
		}
		return new String(bb);
	}

	public int size() {
		return count;
	}

	public void clear() throws ExcelException {
		try {
			raf.close();
		} catch (IOException e) {
			throw new ExcelException(e);
		}finally{
			tempFile.delete();
		}		
	}
}
