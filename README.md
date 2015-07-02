#FastExcel
FastExcel is a FAST and TINY excel read/write component.It's FAST and TINY.

FastExcel is content-based,that means we just care about the content of excel. So FastExcel just read the cell string and other important information,Some Properities like color,font are not supported.Because we don't need to read,parse,store these additional information so FastExcel just need little memory.

- Reading and Writing support for Excel '97(-2003) (BIFF8) file format.
- Low level structures for BIFF(Binary Interchange File Format).
- Low level structures for compound document file format (also known as "OLE2 storage file format" or "Microsoft - Office compatible storage file format").
- API for creating, reading excel file.

Lastest Version 0.5.1	2010-12-25
##DOWNLOAD
FastExcel 0.5.1 Release

[DOWNLOAD][release]
##Example
**Basic Read**
```java
	public void testDump() throws ExcelException {
		Workbook workBook;
		workBook = FastExcel.createReadableWorkbook(new File("test.xls"));
		workBook.setSSTType(BIFFSetting.SST_TYPE_DEFAULT);//memory storage
		workBook.open();
		Sheet s;
		s = workBook.getSheet(0);
		System.out.println("SHEET:"+s);
		for (int i = s.getFirstRow(); i < s.getLastRow(); i++) {
		System.out.print(i+"#");
		for (int j = s.getFirstColumn(); j < s.getLastColumn(); j++) {
			System.out.print(","+s.getCell(i, j));
		}
		System.out.println();
		}
		workBook.close();
	}
```
**Event-based Read**
```java
  public void testEventDump() throws ExcelException {
		Workbook workBook;
		workBook = FastExcel
				.createReadableWorkbook(new File("test.xls"));
		workBook.open();
		workBook.getSheet(0, new SheetReadAdapter() {
			public void onCell(int row, int col, String content) {
				System.out.println(row + "," + col + "," + content);
			}
		});
		workBook.close();
	}
```
**Basic Write**
```java
  public void testWrite() throws ExcelException{
		File f=new File("write.xls");
		Workbook wb=FastExcel.createWriteableWorkbook(f);
		wb.open();
		Sheet sheet=wb.addSheet("sheetA");
		sheet.setCell(1, 2, "some string");
		wb.close();
	}
	
```
**Stream Write**
```java
  public void testStreamWrite() throws Exception{
		File f=new File("write1.xls");
		Workbook wb=FastExcel.createWriteableWorkbook(f);
		wb.open();
		Sheet sheet=wb.addStreamSheet("SheetA");
		sheet.addRow(new String[]{"aaa","bbb"});
		wb.close();
	}
```


##How it works
A workbook document with several sheets (BIFF5-BIFF8) is usually stored using the compound document file format (also known as "OLE2 storage file format" or "Microsoft Office compatible storage file format"). It contains several streams for different types of data.Depending on the document type, different names are used for the stream(s) they contain. BIFF5-BIFF8 workbook documents that are stored in a compound document file contain a stream in the root storage called the Workbook Stream. The name of this stream in the compound document file is "Book" for BIFF5 workbooks, and "Workbook" for BIFF8 workbooks.If a BIFF5-BIFF8 workbook document is stored as stream file, the entire stream is called the Workbook Stream. In BIFF5-BIFF8 Workbook Streams, the Workbook Globals Substream is not the leading part of the stream. It is followed by all Sheet Substreams in order of the sheets that are in the document. Common structure of a BIFF5-BIFF8 Workbook Stream:

- Workbook Globals Substream (required)
- First Sheet Substream (required)
- Second Sheet Substream (optional)
- Third Sheet Substream (optional)

Most of the Excel streams or substreams are divided into records. Each record contains specific data for the various contents or features in a document. It consists of a header specifying the record type and size, followed by the record data. Common structure of a BIFF record:


Offset  |Size	  |Contents
:-------|:-----:|:-------
0	    |2   	|Identifier
2	    |2  	|Size of the following data (sz)
4	    |sz  	|Record data

To read excel file.FastExcel parses these records and build an inner struct of excel file.The Record Parsers:

- BOFParser
- EOFParser
- BoundSheetParser
- SSTParser
- IndexParser
- DimensionParser
- RowParser
- LabelSSTParser
- RKParser
- MulRKParser
- LabelParser
- BoolerrParser
- XFParser
- FormatParser
- DateModeParser
- StyleParser
- MulBlankParser
- NumberParser
- RStringParser

##What's New
add formula support.

##License
It runs on the Java 2 Platform (JDK 1.4 or later) .FastExcel is licensed under the terms of the GNU Lesser General Public Licence (LGPL).  A copy of the licence is included in the distribution.

###Contract 

- yAma <guooscar@gmail.com>
- HeDYn <hedyn@foxmail.com>
- 千橙 <im.qiancheng@gmail.com>

###References
1. <http://en.wikipedia.org/wiki/Microsoft_Excel>
2. <http://sc.openoffice.org/excelfileformat.pdf>
3. <http://sc.openoffice.org/compdocfileformat.pdf>
4. <http://blog.csdn.net/liangjingbo/archive/2008/09/03/2874959.aspx>

[release]: https://github.com/iqiancheng/FastExcel/releases
