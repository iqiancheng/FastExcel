/**
 * 
 */
package edu.npu.fastexcel.biff.write;

import edu.npu.fastexcel.biff.Dimension;
import edu.npu.fastexcel.compound.io.WriteException;

/**
 * @author <a href="guoyanyan002@pingan.com.cn">yAma</a>
 * 2009-3-31
 */
public interface SheetWriter {
	static final int UNUSED_COLUMN=-1;//unused column
	public static final int MAX_ROW =  (1 << 16);
	public static final int MAX_COLUMN = 1 << 8;
	public static final int MAX_CELL_STRING_LEN=1<<16;

	public int getIndex();
	public Dimension getDimension();
	public void setCell(int row,int col,String str) throws WriteException;
	public void addRow(String []cell)throws WriteException;
	public String getName();
	//
	
}
