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
package edu.npu.fastexcel.biff;

import java.util.HashMap;

/**
 * Types of BIFF record.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class Types {
	/**
	 * Beginning Of File
	 */
	public static final int BOF = 0x809;
	/**
	 * Ending Of File
	 */
	public static final int EOF = 0x0a;
	/**
	   */
	public static final int BOUNDSHEET = 0x85;
	/**
	   */
	public static final int SUPBOOK = 0x1ae;
	/**
	   */
	public static final int EXTERNSHEET = 0x17;
	/**
	   */
	public static final int DIMENSION = 0x200;
	/**
	   */
	public static final int BLANK = 0x201;
	/**
	   */
	public static final int MULBLANK = 0xbe;
	/**
	   */
	public static final int ROW = 0x208;
	/**
	   */
	public static final int NOTE = 0x1c;
	/**
	   */
	public static final int TXO = 0x1b6;
	/**
	   */
	public static final int RK = 0x7e;
	/**
	   */
	public static final int RK2 = 0x27e;
	/**
	   */
	public static final int MULRK = 0xbd;
	/**
	   */
	public static final int INDEX = 0x20b;
	/**
	   */
	public static final int DBCELL = 0xd7;
	/**
	   */
	public static final int SST = 0xfc;
	/**
	   */
	public static final int COLINFO = 0x7d;
	/**
	   */
	public static final int EXTSST = 0xff;
	/**
	   */
	public static final int CONTINUE = 0x3c;
	/**
	   */
	public static final int LABEL = 0x204;
	/**
	   */
	public static final int RSTRING = 0xd6;
	/**
	   */
	public static final int LABELSST = 0xfd;
	/**
	   */
	public static final int NUMBER = 0x203;
	/**
	   */
	public static final int NAME = 0x18;
	/**
	   */
	public static final int TABID = 0x13d;
	/**
	   */
	public static final int ARRAY = 0x221;
	/**
	   */
	public static final int STRING = 0x207;
	/**
	   */
	public static final int FORMULA = 0x406;
	/**
	   */
	public static final int FORMULA2 = 0x6;
	/**
	   */
	public static final int SHAREDFORMULA = 0x4bc;
	/**
	   */
	public static final int FORMAT = 0x41e;
	/**
	   */
	public static final int XF = 0xe0;
	/**
	   */
	public static final int BOOLERR = 0x205;
	/**
	   */
	public static final int INTERFACEHDR = 0xe1;
	/**
	   */
	public static final int SAVERECALC = 0x5f;
	/**
	   */
	public static final int INTERFACEEND = 0xe2;
	/**
	   */
	public static final int XCT = 0x59;
	/**
	   */
	public static final int CRN = 0x5a;
	/**
	   */
	public static final int DEFCOLWIDTH = 0x55;
	/**
	   */
	public static final int DEFAULTROWHEIGHT = 0x225;
	/**
	   */
	public static final int WRITEACCESS = 0x5c;
	/**
	   */
	public static final int WSBOOL = 0x81;
	/**
	   */
	public static final int CODEPAGE = 0x42;
	/**
	   */
	public static final int DSF = 0x161;
	/**
	   */
	public static final int FNGROUPCOUNT = 0x9c;
	/**
	   */
	public static final int FILTERMODE = 0x9b;
	/**
	   */
	public static final int AUTOFILTERINFO = 0x9d;
	/**
	   */
	public static final int AUTOFILTER = 0x9e;
	/**
	   */
	public static final int COUNTRY = 0x8c;
	/**
	   */
	public static final int PROTECT = 0x12;
	/**
	   */
	public static final int SCENPROTECT = 0xdd;
	/**
	   */
	public static final int OBJPROTECT = 0x63;
	/**
	   */
	public static final int PRINTHEADERS = 0x2a;
	/**
	   */
	public static final int HEADER = 0x14;
	/**
	   */
	public static final int FOOTER = 0x15;
	/**
	   */
	public static final int HCENTER = 0x83;
	/**
	   */
	public static final int VCENTER = 0x84;
	/**
	   */
	public static final int FILEPASS = 0x2f;
	/**
	   */
	public static final int SETUP = 0xa1;
	/**
	   */
	public static final int PRINTGRIDLINES = 0x2b;
	/**
	   */
	public static final int GRIDSET = 0x82;
	/**
	   */
	public static final int GUTS = 0x80;
	/**
	   */
	public static final int WINDOWPROTECT = 0x19;
	/**
	   */
	public static final int PROT4REV = 0x1af;
	/**
	   */
	public static final int PROT4REVPASS = 0x1bc;
	/**
	   */
	public static final int PASSWORD = 0x13;
	/**
	   */
	public static final int REFRESHALL = 0x1b7;
	/**
	   */
	public static final int WINDOW1 = 0x3d;
	/**
	   */
	public static final int WINDOW2 = 0x23e;
	/**
	   */
	public static final int BACKUP = 0x40;
	/**
	   */
	public static final int HIDEOBJ = 0x8d;
	/**
	   */
	public static final int NINETEENFOUR = 0x22;
	/**
	   */
	public static final int PRECISION = 0xe;
	/**
	   */
	public static final int BOOKBOOL = 0xda;
	/**
	   */
	public static final int FONT = 0x31;
	/**
	   */
	public static final int MMS = 0xc1;
	/**
	   */
	public static final int CALCMODE = 0x0d;
	/**
	   */
	public static final int CALCCOUNT = 0x0c;
	/**
	   */
	public static final int REFMODE = 0x0f;
	/**
	   */
	public static final int TEMPLATE = 0x60;
	/**
	   */
	public static final int OBJPROJ = 0xd3;
	/**
	   */
	public static final int DELTA = 0x10;
	/**
	   */
	public static final int MERGEDCELLS = 0xe5;
	/**
	   */
	public static final int ITERATION = 0x11;
	/**
	   */
	public static final int STYLE = 0x293;
	/**
	   */
	public static final int USESELFS = 0x160;
	/**
	   */
	public static final int VERTICALPAGEBREAKS = 0x1a;
	/**
	   */
	public static final int HORIZONTALPAGEBREAKS = 0x1b;
	/**
	   */
	public static final int SELECTION = 0x1d;
	/**
	   */
	public static final int HLINK = 0x1b8;
	/**
	   */
	public static final int OBJ = 0x5d;
	/**
	   */
	public static final int MSODRAWING = 0xec;
	/**
	   */
	public static final int MSODRAWINGGROUP = 0xeb;
	/**
	   */
	public static final int LEFTMARGIN = 0x26;
	/**
	   */
	public static final int RIGHTMARGIN = 0x27;
	/**
	   */
	public static final int TOPMARGIN = 0x28;
	/**
	   */
	public static final int BOTTOMMARGIN = 0x29;
	/**
	   */
	public static final int EXTERNNAME = 0x23;
	/**
	   */
	public static final int PALETTE = 0x92;
	/**
	   */
	public static final int PLS = 0x4d;
	/**
	   */
	public static final int SCL = 0xa0;
	/**
	   */
	public static final int PANE = 0x41;
	/**
	   */
	public static final int WEIRD1 = 0xef;
	/**
	   */
	public static final int SORT = 0x90;
	/**
	   */
	public static final int CONDFMT = 0x1b0;
	/**
	   */
	public static final int CF = 0x1b1;
	/**
	   */
	public static final int DV = 0x1be;
	/**
	   */
	public static final int DVAL = 0x1b2;
	/**
	   */
	public static final int BUTTONPROPERTYSET = 0x1ba;
	// Chart types
	/**
	   */
	public static final int FONTX = 0x1026;
	/**
	   */
	public static final int IFMT = 0x104e;
	/**
	   */
	public static final int FBI = 0x1060;
	/**
	   */
	public static final int ALRUNS = 0x1050;
	/**
	   */
	public static final int SERIES = 0x1003;
	/**
	   */
	public static final int SERIESLIST = 0x1016;
	/**
	   */
	public static final int SBASEREF = 0x1048;
	/**
	   */
	public static final int UNKNOWN = 0xffff;
	/**
	   * 
	   */
	public static final int U1C0 = 0x1c0;
	/**
	   * 
	   */
	public static final int U1C1 = 0x1c1;
	/**
	   */
	public static final int DATEMODE = 0x22;

	private static HashMap recordNames = new HashMap(50);

	/**
	 * Return Record type name which specify by int number.
	 * 
	 * @param type
	 *            type's int value
	 * @return Record type name.
	 */
	public static String getTypeName(int type) {
		return (String) recordNames.get(type + "");
	}

	static {
		recordNames.put(Types.BOF + "", "BOF");
		recordNames.put("" + Types.EOF, "EOF");
		recordNames.put("" + Types.FONT, "FONT");
		recordNames.put("" + Types.SST, "SST");
		recordNames.put("" + Types.LABELSST, "LABELSST");
		recordNames.put("" + Types.WRITEACCESS, "WRITEACCESS");
		recordNames.put("" + Types.FORMULA, "FORMULA");
		recordNames.put("" + Types.FORMULA2, "FORMULA");
		recordNames.put("" + Types.XF, "XF");
		recordNames.put("" + Types.MULRK, "MULRK");
		recordNames.put("" + Types.NUMBER, "NUMBER");
		recordNames.put("" + Types.BOUNDSHEET, "BOUNDSHEET");
		recordNames.put("" + Types.CONTINUE, "CONTINUE");
		recordNames.put("" + Types.FORMAT, "FORMAT");
		recordNames.put("" + Types.EXTERNSHEET, "EXTERNSHEET");
		recordNames.put("" + Types.INDEX, "INDEX");
		recordNames.put("" + Types.DIMENSION, "DIMENSION");
		recordNames.put("" + Types.ROW, "ROW");
		recordNames.put("" + Types.DBCELL, "DBCELL");
		recordNames.put("" + Types.BLANK, "BLANK");
		recordNames.put("" + Types.MULBLANK, "MULBLANK");
		recordNames.put("" + Types.RK, "RK");
		recordNames.put("" + Types.RK2, "RK");
		recordNames.put("" + Types.COLINFO, "COLINFO");
		recordNames.put("" + Types.LABEL, "LABEL");
		recordNames.put("" + Types.SHAREDFORMULA, "SHAREDFORMULA");
		recordNames.put("" + Types.CODEPAGE, "CODEPAGE");
		recordNames.put("" + Types.WINDOW1, "WINDOW1");
		recordNames.put("" + Types.WINDOW2, "WINDOW2");
		recordNames.put("" + Types.MERGEDCELLS, "MERGEDCELLS");
		recordNames.put("" + Types.HLINK, "HLINK");
		recordNames.put("" + Types.HEADER, "HEADER");
		recordNames.put("" + Types.FOOTER, "FOOTER");
		recordNames.put("" + Types.INTERFACEHDR, "INTERFACEHDR");
		recordNames.put("" + Types.MMS, "MMS");
		recordNames.put("" + Types.INTERFACEEND, "INTERFACEEND");
		recordNames.put("" + Types.DSF, "DSF");
		recordNames.put("" + Types.FNGROUPCOUNT, "FNGROUPCOUNT");
		recordNames.put("" + Types.COUNTRY, "COUNTRY");
		recordNames.put("" + Types.TABID, "TABID");
		recordNames.put("" + Types.PROTECT, "PROTECT");
		recordNames.put("" + Types.SCENPROTECT, "SCENPROTECT");
		recordNames.put("" + Types.OBJPROTECT, "OBJPROTECT");
		recordNames.put("" + Types.WINDOWPROTECT, "WINDOWPROTECT");
		recordNames.put("" + Types.PASSWORD, "PASSWORD");
		recordNames.put("" + Types.PROT4REV, "PROT4REV");
		recordNames.put("" + Types.PROT4REVPASS, "PROT4REVPASS");
		recordNames.put("" + Types.BACKUP, "BACKUP");
		recordNames.put("" + Types.HIDEOBJ, "HIDEOBJ");
		recordNames.put("" + Types.NINETEENFOUR, "1904");
		recordNames.put("" + Types.PRECISION, "PRECISION");
		recordNames.put("" + Types.BOOKBOOL, "BOOKBOOL");
		recordNames.put("" + Types.STYLE, "STYLE");
		recordNames.put("" + Types.EXTSST, "EXTSST");
		recordNames.put("" + Types.REFRESHALL, "REFRESHALL");
		recordNames.put("" + Types.CALCMODE, "CALCMODE");
		recordNames.put("" + Types.CALCCOUNT, "CALCCOUNT");
		recordNames.put("" + Types.NAME, "NAME");
		recordNames.put("" + Types.MSODRAWINGGROUP, "MSODRAWINGGROUP");
		recordNames.put("" + Types.MSODRAWING, "MSODRAWING");
		recordNames.put("" + Types.OBJ, "OBJ");
		recordNames.put("" + Types.USESELFS, "USESELFS");
		recordNames.put("" + Types.SUPBOOK, "SUPBOOK");
		recordNames.put("" + Types.LEFTMARGIN, "LEFTMARGIN");
		recordNames.put("" + Types.RIGHTMARGIN, "RIGHTMARGIN");
		recordNames.put("" + Types.TOPMARGIN, "TOPMARGIN");
		recordNames.put("" + Types.BOTTOMMARGIN, "BOTTOMMARGIN");
		recordNames.put("" + Types.HCENTER, "HCENTER");
		recordNames.put("" + Types.VCENTER, "VCENTER");
		recordNames.put("" + Types.ITERATION, "ITERATION");
		recordNames.put("" + Types.DELTA, "DELTA");
		recordNames.put("" + Types.SAVERECALC, "SAVERECALC");
		recordNames.put("" + Types.PRINTHEADERS, "PRINTHEADERS");
		recordNames.put("" + Types.PRINTGRIDLINES, "PRINTGRIDLINES");
		recordNames.put("" + Types.SETUP, "SETUP");
		recordNames.put("" + Types.SELECTION, "SELECTION");
		recordNames.put("" + Types.STRING, "STRING");
		recordNames.put("" + Types.FONTX, "FONTX");
		recordNames.put("" + Types.IFMT, "IFMT");
		recordNames.put("" + Types.WSBOOL, "WSBOOL");
		recordNames.put("" + Types.GRIDSET, "GRIDSET");
		recordNames.put("" + Types.REFMODE, "REFMODE");
		recordNames.put("" + Types.GUTS, "GUTS");
		recordNames.put("" + Types.EXTERNNAME, "EXTERNNAME");
		recordNames.put("" + Types.FBI, "FBI");
		recordNames.put("" + Types.CRN, "CRN");
		recordNames
				.put("" + Types.HORIZONTALPAGEBREAKS, "HORIZONTALPAGEBREAKS");
		recordNames.put("" + Types.VERTICALPAGEBREAKS, "VERTICALPAGEBREAKS");
		recordNames.put("" + Types.DEFAULTROWHEIGHT, "DEFAULTROWHEIGHT");
		recordNames.put("" + Types.TEMPLATE, "TEMPLATE");
		recordNames.put("" + Types.PANE, "PANE");
		recordNames.put("" + Types.SCL, "SCL");
		recordNames.put("" + Types.PALETTE, "PALETTE");
		recordNames.put("" + Types.PLS, "PLS");
		recordNames.put("" + Types.OBJPROJ, "OBJPROJ");
		recordNames.put("" + Types.DEFCOLWIDTH, "DEFCOLWIDTH");
		recordNames.put("" + Types.ARRAY, "ARRAY");
		recordNames.put("" + Types.WEIRD1, "WEIRD1");
		recordNames.put("" + Types.BOOLERR, "BOOLERR");
		recordNames.put("" + Types.SORT, "SORT");
		recordNames.put("" + Types.BUTTONPROPERTYSET, "BUTTONPROPERTYSET");
		recordNames.put("" + Types.NOTE, "NOTE");
		recordNames.put("" + Types.TXO, "TXO");
		recordNames.put("" + Types.DV, "DV");
		recordNames.put("" + Types.DVAL, "DVAL");
		recordNames.put("" + Types.SERIES, "SERIES");
		recordNames.put("" + Types.SERIESLIST, "SERIESLIST");
		recordNames.put("" + Types.SBASEREF, "SBASEREF");
		recordNames.put("" + Types.CONDFMT, "CONDFMT");
		recordNames.put("" + Types.CF, "CF");
		recordNames.put("" + Types.FILTERMODE, "FILTERMODE");
		recordNames.put("" + Types.AUTOFILTER, "AUTOFILTER");
		recordNames.put("" + Types.AUTOFILTERINFO, "AUTOFILTERINFO");
		recordNames.put("" + Types.UNKNOWN, "???");
		recordNames.put("" + Types.DATEMODE, "DATEMODE");
	}
}
