/**
 * 
 */
package edu.npu.fastexcel.biff.read;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.LinkedList;
import java.util.TreeMap;

import edu.npu.fastexcel.ExcelException;

/**
 * Shared String Table is very big in excel,A way to save memory is to use
 * temporary files.{@link ArraySST} is the default implemention of SST, it 's
 * fast but need more memory.
 * 
 * @see ArraySST
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a> 2009-4-23
 */
public class CacheFileSST implements SST {

	private int count;
	private int[] indexTable;
	private int[] offsetTable;
	private static final int INIT_INDEX_SIZE = 8192;
	private static final int GROW_SIZE = 8192;
	private File tempFile;
	private int currentPos;
	private RandomAccessFile raf;
	private StringCache cache;	
	private int cacheSize;
	

	/**
	 * @throws ExcelException 
	 * 
	 */
	public CacheFileSST() throws ExcelException {
		count = 0;
		currentPos=0;
		indexTable = new int[INIT_INDEX_SIZE];
		offsetTable= new int[INIT_INDEX_SIZE];
		cache=new StringCache(256);
		cacheSize=256;
		String fileName = "SST" + System.currentTimeMillis();
		try {
			tempFile = File.createTempFile(fileName, ".tmp");
			raf=new RandomAccessFile(tempFile, "rw");
		} catch (IOException e) {
			//seem something wrong.use ArraySST instead of TempFileSST
			throw new ExcelException(e);
		}

	}
	/**
	 * @return the cacheSize
	 */
	public int getCacheSize() {
		return cacheSize;
	}

	/**
	 * @param cacheSize the cacheSize to set
	 */
	public void setCacheSize(int cacheSize) {
		this.cacheSize = cacheSize;
		cache.size=cacheSize;
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
			throw new ExcelException(e);
		}
	}
	public void flush() throws ExcelException{
		
	}
	public String getString(int index) throws ExcelException {
		if(index<0||index>=count){
			return null;
		}
		String result=cache.get(index);
		if(result!=null){
			return result;
		}
		int pos=indexTable[index];
		int len=offsetTable[index];
		byte bb[]=new byte[len];
		try {
			raf.seek(pos);
			raf.read(bb);
		} catch (IOException e) {
			throw new ExcelException(e);
		}
		result=new String(bb);
		cache.put(index, result);
		return result;
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
	/**
	 * 
	 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a>
	 * 2009-4-23
	 */
	class StringCache{
		int size;
		LinkedList fifoList=new LinkedList();
		TreeMap keySet=new TreeMap();
		public StringCache(int size) {
			this.size=size;
		}
		void put(int index,String str){
			if(keySet.size()>size){
				String head=(String) fifoList.removeFirst();
				keySet.remove(head);//if cache full remove fist
			}
			keySet.put(""+index, str);
			fifoList.add(""+index);
		}
		String get(int index){
			return (String) keySet.get(""+index);
		}
	}
}
