/**
 * 
 */
package edu.npu.fastexcel.biff.read;

import edu.npu.fastexcel.ExcelException;

/**
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a>
 * 2009-4-23
 */
public interface SST {
	/**
	 * Add a string to shared string table.
	 * @param str the string
	 */
	void addString(String str)throws ExcelException;
	/**
	 * Retrieves string from shared string table by index.
	 * @param index the string's index
	 * @return the string.
	 */
	String getString(int index)throws ExcelException;
	/**
	 * Retrieves count of strings.
	 * @return the count of strings.
	 */
	int size()throws ExcelException;
	void clear()throws ExcelException;
	void flush()throws ExcelException;
}
