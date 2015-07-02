/**
 * 
 */
package edu.npu.fastexcel.biff.read;

import java.util.ArrayList;

import edu.npu.fastexcel.ExcelException;

/**
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a>
 * 2009-4-23
 */
public class ArraySST implements SST {
	private ArrayList sstList;
	public ArraySST() {
		sstList=new ArrayList(512);
	}
	public void addString(String str) throws ExcelException{
		sstList.add(str);
	}
	public String getString(int index) throws ExcelException{
		if (index >= sstList.size() || index < 0)
			return null;
		return (String) sstList.get(index);
	}
	public void flush()throws ExcelException{
		
	}
	public int size()throws ExcelException {
		return sstList.size();
	}
	public void clear() throws ExcelException {
		sstList.clear();
	}
}
