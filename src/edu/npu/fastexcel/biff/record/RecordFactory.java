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

import edu.npu.fastexcel.biff.parser.globals.BuiltinFormats;
import edu.npu.fastexcel.biff.record.cell.LabelSSTRecord;
import edu.npu.fastexcel.biff.record.cell.RKRecord;
import edu.npu.fastexcel.biff.record.globals.BoundSheetRecord;
import edu.npu.fastexcel.biff.record.globals.DateModeRecord;
import edu.npu.fastexcel.biff.record.globals.FormatRecord;
import edu.npu.fastexcel.biff.record.globals.SSTRecord;
import edu.npu.fastexcel.biff.record.globals.XFRecord;
import edu.npu.fastexcel.biff.record.sheet.DimensionRecord;

/**
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-4
 */
public class RecordFactory {

	private BoundSheetRecord boundSheet;
	private FormatRecord format;
	private SSTRecord sst;
	private XFRecord xf;
	private DimensionRecord dimension;
	private LabelSSTRecord label;
	private RKRecord rk;

	/**
	 * @author <a href="guooscar@gmail.com">yAma</a> 2009-1-16
	 */
	static class InstanceHolder {
		static RecordFactory instance = new RecordFactory();
	}

	private RecordFactory() {
		boundSheet = new BoundSheetRecord();
		format = new FormatRecord();
		format.setIndex(0);
		format.setFormatString(BuiltinFormats.getFormat(0).getContent());
		sst = new SSTRecord();
		xf = new XFRecord();
		xf.setFont(0);
		xf.setFormat(0);
		dimension = new DimensionRecord();
		label = new LabelSSTRecord();
		rk = new RKRecord();
	}

	/**
	 * @return dimension
	 */
	public Record getDimension() {
		return dimension;
	}

	public Record getFont() {
		return null;
	}

	public Record getCountry() {
		return null;
	}

	public Record getSST() {
		return sst;
	}

	public Record getDateMode1900() {
		return DateModeRecord.DATE_MODE_1899;
	}

	public Record getRK() {
		return rk;
	}

	public Record getIndex() {
		return null;
	}

	/**
	 * @return format
	 */
	public Record getFormat() {
		return format;
	}

	public Record getXF() {
		return xf;
	}

	/**
	 * @return label
	 */
	public Record getLabel() {
		return label;
	}

	public Record getWriteAccess() {
		return null;
	}

	public Record getCodePage() {
		return null;
	}

	/**
	 * @return boundSheet
	 */
	public Record getBoundSheet() {
		return boundSheet;
	}

	public Record getGlobalsBOF() {
		return BOFRecord.BIFF_TYPE_WORKBOOK_GLOBALS;
	}

	public Record getEOF() {
		return EOFRecord.EOF;
	}

	public Record getSheetBOF() {
		return BOFRecord.BIFF_TYPE_SHEET_OR_DIALOGUE;
	}

	public Record getInterFaceEnd() {
		return NotImplementRecord.INTERFACEEND;
	}

	public Record getInterFaceHdr() {
		return NotImplementRecord.INTERFACEHDR;
	}

	public Record getMms() {
		return NotImplementRecord.MMS;
	}

	public Record getDsf() {
		return NotImplementRecord.DSF;
	}

	public Record getTabId() {
		return NotImplementRecord.TABID;
	}

	public Record getButtonPropertySet() {
		return NotImplementRecord.BUTTONPROPERTYSET;
	}

	public Record getFngroupCount() {
		return NotImplementRecord.FNGROUPCOUNT;
	}

	public Record getWindowProtect() {
		return NotImplementRecord.WINDOWPROTECT;
	}

	public Record getProtect() {
		return NotImplementRecord.PROTECT;
	}

	public Record getPassword() {
		return NotImplementRecord.PASSWORD;
	}

	public Record getProt4Rev() {
		return NotImplementRecord.PROT4REV;
	}

	public Record getWindow1() {
		return NotImplementRecord.WINDOW1;
	}

	public Record getBackup() {
		return NotImplementRecord.BACKUP;
	}

	public Record getHideObj() {
		return NotImplementRecord.HIDEOBJ;
	}

	/**
	 * @return dateMode
	 */
	public Record getDateMode() {
		return DateModeRecord.DATE_MODE_1899;
	}

	public Record getPrecision() {
		return NotImplementRecord.PRECISION;
	}

	public Record getRefreShall() {
		return NotImplementRecord.REFRESHALL;
	}

	public Record getBookbool() {
		return NotImplementRecord.BOOKBOOL;
	}

	public Record getCalcMode() {
		return NotImplementRecord.CALCMODE;
	}

	public Record getRefMode() {
		return NotImplementRecord.REFMODE;
	}

	public Record getIteration() {
		return NotImplementRecord.ITERATION;
	}

	public Record getDelta() {
		return NotImplementRecord.DELTA;
	}

	public Record getSaveRecalc() {
		return NotImplementRecord.SAVERECALC;
	}

	public Record getPrintHeaders() {
		return NotImplementRecord.PRINTHEADERS;
	}

	public Record getPrintGridLines() {
		return NotImplementRecord.PRINTGRIDLINES;
	}

	public Record getGridSet() {
		return NotImplementRecord.GRIDSET;
	}

	public Record getGuts() {
		return NotImplementRecord.GUTS;
	}

	public Record getDefaultRowHeight() {
		return NotImplementRecord.DEFAULTROWHEIGHT;
	}

	public Record getWsBool() {
		return NotImplementRecord.WSBOOL;
	}

	public Record getHeader() {
		return NotImplementRecord.HEADER;
	}

	public Record getFooter() {
		return NotImplementRecord.FOOTER;
	}

	public Record getHCenter() {
		return NotImplementRecord.HCENTER;
	}

	public Record getVCenter() {
		return NotImplementRecord.VCENTER;
	}

	public Record getSetup() {
		return NotImplementRecord.SETUP;
	}

	public Record getDefColWidth() {
		return NotImplementRecord.DEFCOLWIDTH;
	}

	public Record getWindow2() {
		return NotImplementRecord.WINDOW2;
	}

	public Record getWeid1() {
		return NotImplementRecord.WEIRD1;
	}

	public static RecordFactory getInstance() {
		return InstanceHolder.instance;
	}
}
