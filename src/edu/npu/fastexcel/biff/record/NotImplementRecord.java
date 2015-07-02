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
package edu.npu.fastexcel.biff.record;

import edu.npu.fastexcel.biff.Types;

/**
 * For writing excel,we didn't care some record,so we just use a default byte
 * array instead.
 * 
 * @see Types
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class NotImplementRecord extends Record {

	// next is workbook globals record
	/**
	 * unknown
	 */
	public static final NotImplementRecord INTERFACEHDR = new NotImplementRecord(
			new byte[] { (byte) 0xe1, 0x00, 0x02, 0x00, (byte) 0xb0, 0x04 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord MMS = new NotImplementRecord(
			new byte[] { (byte) 0xc1, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord INTERFACEEND = new NotImplementRecord(
			new byte[] { (byte) 0xe2, 0x00, 0x00, 0x00 });
	//

	/**
	 *This record specifies if the BIFF8 workbook document contains an
	 * additional BIFF5 Workbook Stream with the name. Here the default value is
	 * 0 = Only the BIFF8 Workbook stream is present
	 **/
	public static final NotImplementRecord DSF = new NotImplementRecord(
			new byte[] { 0x61, 0x01, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord TABID = new NotImplementRecord(
			new byte[] { 0x3d, 0x01, 0x06, 0x00, 0x01, 0x00, 0x02, 0x00, 0x03,
					0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord BUTTONPROPERTYSET = new NotImplementRecord(
			new byte[] { (byte) 0xba, 0x01, 0x0f, 0x00, 0x0c, 0x00, 0x00, 0x54,
					0x68, 0x69, 0x73, 0x57, 0x6f, 0x72, 0x6b, 0x62, 0x6f, 0x6f,
					0x6b });
	/**
	 * unknown
	 */
	public static final NotImplementRecord FNGROUPCOUNT = new NotImplementRecord(
			new byte[] { (byte) 0x9c, 0x00, 0x02, 0x00, 0x0e, 0x00 });

	/**
	 * This record is part of the worksheet/workbook protection . It determines
	 * whether the window configuration of this document is protected. Window
	 * protection is not active, if this record is omitted.
	 */
	public static final NotImplementRecord WINDOWPROTECT = new NotImplementRecord(
			new byte[] { 0x19, 0x00, 0x02, 0x00, 0x00, 0x00 });

	/**
	 * This record is part of the worksheet/workbook protection .It specifies
	 * whether a worksheet or a workbook is protected against modification.
	 * Protection is not active, if this record is omitted.
	 */
	public static final NotImplementRecord PROTECT = new NotImplementRecord(
			new byte[] { 0x12, 0x00, 0x02, 0x00, 0x00, 0x00 });

	/**
	 * This record is part of the worksheet/workbook protection . It stores a
	 * 16-bit hash value, calculated from the worksheet or workbook protection
	 * password.
	 */
	public static final NotImplementRecord PASSWORD = new NotImplementRecord(
			new byte[] { 0x13, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord PROT4REV = new NotImplementRecord(
			new byte[] { (byte) 0xaf, 0x01, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord PROT4REVPASS = new NotImplementRecord(
			new byte[] { (byte) 0xbc, 0x01, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record contains general settings for the document window and global
	 * workbook settings (BIFF5-BIFF8).
	 */
	public static final NotImplementRecord WINDOW1 = new NotImplementRecord(
			new byte[] { 0x3d, 0x00, 0x12, 0x00, (byte) 0xe0, 0x01, 0x78, 0x00,
					0x39, 0x21, (byte) 0xb2, 0x11, 0x38, 0x00, 0x00, 0x00,
					0x00, 0x00, 0x01, 0x00, 0x58, 0x02 });
	/**
	 * This record contains a Boolean value determining whether Excel makes a
	 * backup of the file while saving
	 */
	public static final NotImplementRecord BACKUP = new NotImplementRecord(
			new byte[] { 0x40, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record specifies whether and how to show objects in the workbook.
	 *<ul>
	 *<li>0 = Show all objects</li>
	 *<li>1 = Show placeholders</li>
	 *<li>2 = Do not show objects</li>
	 *</ul>
	 **/
	public static final NotImplementRecord HIDEOBJ = new NotImplementRecord(
			new byte[] { (byte) 0x8d, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record stores if formulas use the real cell values for calculation
	 * or the values displayed on the screen. In BIFF2-BIFF4 this record is part
	 * of the Calculation Settings Block. In BIFF5-BIFF8 it is stored in the
	 * Workbook Globals Substream.
	 *<ul>
	 *<li>0 = Use displayed values</li>
	 *<li>1 = Use real cell values</li>
	 *<ul>
	 **/
	public static final NotImplementRecord PRECISION = new NotImplementRecord(
			new byte[] { 0x0e, 0x00, 0x02, 0x00, 0x01, 0x00 });
	/**
	 *unknown
	 */
	public static final NotImplementRecord REFRESHALL = new NotImplementRecord(
			new byte[] { (byte) 0xb7, 0x01, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record contains a Boolean value determining whether to save values
	 * linked from external workbooks (CRN records). In BIFF3 and BIFF4 this
	 * option is stored in the SHEETPR record.
	 *<ul>
	 *<li>0 = Save external linked values</li>
	 *<li>1 = Do not save external linked values</li>
	 *<ul>
	 */
	public static final NotImplementRecord BOOKBOOL = new NotImplementRecord(
			new byte[] { (byte) 0xda, 0x00, 0x02, 0x00, 0x00, 0x00 });

	// below is sheet record

	/**
	 * This record is part of the Calculation Settings Block . It specifies
	 * whether to calculate formulas manually,automatically or automatically
	 * except for multiple table operations.
	 * <ul>
	 * <li>automatically except for multiple table operations.</li>
	 * <li>0 = manually.</li>
	 * <li>1 = automatically (default)</li>
	 * </ul>
	 * */
	public static final NotImplementRecord CALCMODE = new NotImplementRecord(
			new byte[] { 0x0d, 0x00, 0x02, 0x00, 0x01, 0x00 });
	/**
	 * This record is part of the Calculation Settings Block . It specifies the
	 * maximum number of times the formulas should be iteratively calculated.
	 * This is a fail-safe against mutually recursive formulas locking up a
	 * spreadsheet application. Value is Maximum number of iterations allowed in
	 * circular references
	 **/
	public static final NotImplementRecord CALCCOUNT = new NotImplementRecord(
			new byte[] { 0x0c, 0x00, 0x02, 0x00, 0x64, 0x00 });
	/**
	 * This record is part of the Calculation Settings Block . It stores which
	 * method is used to show cell addresses in formulas.
	 *<ul>
	 *<li>The RC mode uses numeric indexes for rows and columns, for
	 * example R(1)C(-1), or R1C1:R2C2.0 means RC mode.</li>
	 *<li>The A1 mode uses characters for columns and numbers for rows, for
	 * example B1, or $A$1:$B$2.1 means RC mode.</li>
	 *</ul>
	 **/
	public static final NotImplementRecord REFMODE = new NotImplementRecord(
			new byte[] { 0x0f, 0x00, 0x02, 0x00, 0x01, 0x00 });
	/**
	 *This record is part of the Calculation Settings Block . It stores if
	 * iterations are allowed while calculating recursive formulas.
	 *<ul>
	 *<li>0 = Iterations off</li>
	 *<li>1 = Iterations on</li>
	 *</ul>
	 **/
	public static final NotImplementRecord ITERATION = new NotImplementRecord(
			new byte[] { 0x11, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record is part of the Calculation Settings Block . It stores the
	 * maximum change of the result to exit an iteration. Value is Maximum
	 * change in iteration (IEEE 754 floating-point value, 64-bit double
	 * precision).
	 **/
	public static final NotImplementRecord DELTA = new NotImplementRecord(
			new byte[] { 0x10, 0x00, 0x08, 0x00, (byte) 0xfc, (byte) 0xa9,
					(byte) 0xf1, (byte) 0xd2, 0x4d, 0x62, 0x50, 0x3f });
	/**
	 * This record is part of the Calculation Settings Block . It contains the
	 * "Recalculate before save" option in Excel's calculation settings
	 * dialogue.
	 * <ul>
	 * <li>0 = Do not recalculate</li>
	 * <li>1 = Recalculate before saving the document</li>
	 * </ul>
	 */
	public static final NotImplementRecord SAVERECALC = new NotImplementRecord(
			new byte[] { 0x5f, 0x00, 0x02, 0x00, 0x01, 0x00 });
	/**
	 *This record stores if the row and column headers (the areas with row
	 * numbers and column letters) will be printed.
	 * <ul>
	 * <li>0 = Do not print row/column headers</li>
	 * <li>1 = Do not print row/column headerst</li>
	 * </ul>
	 */
	public static final NotImplementRecord PRINTHEADERS = new NotImplementRecord(
			new byte[] { 0x2a, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record stores if sheet grid lines will be printed.
	 * <ul>
	 * <li>0 = Do not print sheet grid lines</li>
	 * <li>1 = Print sheet grid lines</li>
	 * </ul>
	 **/
	public static final NotImplementRecord PRINTGRIDLINES = new NotImplementRecord(
			new byte[] { 0x2b, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record specifies if the option to print sheet grid lines has ever
	 * been changed.
	 * <ul>
	 * <li>0 = Print grid lines option never changed</li>
	 * <li>1 = Print grid lines option changed</li>
	 * </ul>
	 * **/
	public static final NotImplementRecord GRIDSET = new NotImplementRecord(
			new byte[] { (byte) 0x82, 0x00, 0x02, 0x00, 0x01, 0x00 });
	/**
	 *This record contains information about the layout of outline
	 * symbols.<br/> 0 2 Width of the area to display row outlines (left of the
	 * sheet), in pixel<br/> 2 2 Height of the area to display column outlines
	 * (above the sheet), in pixel<br/> 4 2 Number of visible row outline levels
	 * (used row levels + 1; or 0, if not used)<br/> 6 2 Number of visible
	 * column outline levels (used column levels + 1; or 0, if not used)<br/>
	 * */
	public static final NotImplementRecord GUTS = new NotImplementRecord(
			new byte[] { (byte) 0x80, 0x00, 0x08, 0x00, 0x00, 0x00, 0x00, 0x00,
					0x00, 0x00, 0x00, 0x00 });

	/**
	 * This record specifies the default height and default flags for rows that
	 * do not have a corresponding ROW record.
	 * */
	public static final NotImplementRecord DEFAULTROWHEIGHT = new NotImplementRecord(
			new byte[] { 0x25, 0x02, 0x04, 0x00, 0x00, 0x00, 0x1d, 0x01 });
	/**
	 *unknown
	 **/
	public static final NotImplementRecord WSBOOL = new NotImplementRecord(
			new byte[] { (byte) 0x81, 0x00, 0x02, 0x00, (byte) 0xc1, 0x04 });
	/**
	 * This record is part of the Page Settings Block . It specifies the page
	 * header string for the current worksheet. If this record is not present or
	 * completely empty (record size is 0), the sheet does not contain a page
	 * header.
	 * */
	public static final NotImplementRecord HEADER = new NotImplementRecord(
			new byte[] { 0x14, 0x00, 0x00, 0x00 });
	/**
	 * This record is part of the Page Settings Block . It specifies the page
	 * footer string for the current worksheet. If this record is not present or
	 * completely empty (record size is 0), the sheet does not contain a page
	 * footer.
	 * */
	public static final NotImplementRecord FOOTER = new NotImplementRecord(
			new byte[] { 0x15, 0x00, 0x00, 0x00 });
	/**
	 * This record is part of the Page Settings Block . It specifies if the
	 * sheet is centered horizontally when printed.
	 **/
	public static final NotImplementRecord HCENTER = new NotImplementRecord(
			new byte[] { (byte) 0x83, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record is part of the Page Settings Block . It specifies if the
	 * sheet is centered vertically when printed.
	 **/
	public static final NotImplementRecord VCENTER = new NotImplementRecord(
			new byte[] { (byte) 0x84, 0x00, 0x02, 0x00, 0x00, 0x00 });
	/**
	 * This record is part of the Page Settings Block . It stores the page
	 * format settings of the current sheet. The pages may be scaled in percent
	 * or by using an absolute number of pages. This setting is located in the
	 * SHEETPR record. If pages are scaled in percent, the scaling factor in
	 * this record is used, otherwise the Fit to pages values. One of the
	 * "Fit to pages" values may be 0. In this case the sheet is scaled to fit
	 * only to the other value.
	 */
	public static final NotImplementRecord SETUP = new NotImplementRecord(
			new byte[] { (byte) 0xa1, 0x00, 0x22, 0x00, 0x00, 0x00, 0x1d, 0x01,
					0x01, 0x00, 0x01, 0x00, 0x01, 0x00, 0x04, 0x00, 0x00, 0x00,
					0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
					(byte) 0xe0, 0x3f, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
					(byte) 0xe0, 0x3f, 0x00, 0x00 });

	/**
	 * This record specifies the default column width for columns that do not
	 * have a specific width set using the records.
	 */
	public static final NotImplementRecord DEFCOLWIDTH = new NotImplementRecord(
			new byte[] { 0x55, 0x00, 0x02, 0x00, 0x08, 0x00 });
	/**
	 * This record contains additional settings for the document window
	 * (BIFF2-BIFF4) or for the window of a specific worksheet (BIFF5-BIFF8). It
	 * is part of the Sheet View Settings Block
	 */
	public static final NotImplementRecord WINDOW2 = new NotImplementRecord(
			new byte[] { 0x3e, 0x02, 0x12, 0x00, (byte) 0xb6, 0x06, 0x00, 0x00,
					0x00, 0x00, 0x40, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00,
					0x00, 0x00, 0x00, 0x00 });
	/**
	 * unknown
	 */
	public static final NotImplementRecord WEIRD1 = new NotImplementRecord(
			new byte[] { (byte) 0xef, 0x00, 0x06, 0x00, 0x05, 0x00, 0x37, 0x00,
					0x00, 0x00 });

	/**
	 * @param bytes
	 */
	public NotImplementRecord(byte[] bytes) {
		super(bytes);
	}

}
