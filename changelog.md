FastExcel change log
NOTE:
	the '*' means change
	the '+' means add
	the '-' means remove

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.5.1  2010-12-25 HeDYn<hedyn@foxmail.com>		
--------------------------------------------------------------------------------	
edu.npu.fastexcel.compound.DirectoryEntry 
中增加 String WORKBOOK_NAME_OTHER = "Book"; 
另解决了读取到的数值型结果的公式值与显示的不一致的问题。
与0.5相比修改了以下几个类
edu.npu.fastexcel.compound.DirectoryEntry
edu.npu.fastexcel.biff.read.BIFFReader
edu.npu.fastexcel.biff.read.WorkBookStream
edu.npu.fastexcel.biff.parser.cell.FormulaParser
edu.npu.fastexcel.biff.parser.globals.Format
 
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.5  2010-12-2 HeDYn<hedyn@foxmail.com>		
--------------------------------------------------------------------------------	
1.add formula support HeDYn<hedyn@foxmail.com>		

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.4.4  2010-1-10 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.fix bug: that when writing excel,the dimension 's last row is wrong.
thanks to 高雨峰 <sillyfox008@sina.com>
	
	
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.4.3  2009-11-5 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.fix bug:date format and number format error.

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.4.2  2009-5-19 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.fix bug:ClassCastException when open workbookGlobalStream
	Thanks to maojianke001@pingan.com.cn

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.4.1  2009-5-13 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------	
1.fix bug:can not delete file after workbook closed.
  Thanks to allan <allan@dreamtech.com.cn>
  
  
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.4  2009-4-26 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------	
1.add SST,ArraySST,FileSST,CacheFileSST
2.change DefaultSheetStream
3.add setSSTType in BIFFSetting
4.add getFirstColIndex and getLastColIndex method

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3.4  2009-4-22 yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------	
1.fix bug:setup wrong dimension when writing excel.
2.fix bug:open generated excel using Microsoft Excel,change nothing,save it,open
again. 
-edu.npu.fastexcel.biff.write.SheetStreamWriter
*edu.npu.fastexcel.biff.write.FileSheetStreamWriter
*edu.npu.fastexcel.biff.write.DefaultSheetStreamWriter
*edu.npu.fastexcel.biff.write.WorkBookGlobalsStreamWriter

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3.4  2009-3-31  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------	
1.add a stream write interface.	
	
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3.3  2009-3-18  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.fix bug:NumUtil.getRKValue.Thanks to maojianke001@pingan.com.cn		

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3.2  2009-2-25  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.add date format testing	
	
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3.1  2009-2-23  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.add format 22(yyyy-M-d HH:mm)
2.add user defined format interface
	
	
--------------------------------------------------------------------------------
CHANGE IN VERSION 0.3  2009-2-17  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.add write interface
2.fix some bugs of compound reader
3.fix some bugs of date format and number format

--------------------------------------------------------------------------------
CHANGE IN VERSION 0.2  2009-1-16  yAma<guooscar@gmail.com>
--------------------------------------------------------------------------------
1.add a event-based read interface.
	*edu.npu.fastexcel.ReadableWorkbook
	+edu.npu.fastexcel.SheetReadAdapter
	+edu.npu.fastexcel.SheetReadListener
2.add ant build file
3.add change log file
4.add license declare

	