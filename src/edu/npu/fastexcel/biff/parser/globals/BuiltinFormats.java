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
package edu.npu.fastexcel.biff.parser.globals;

import java.util.HashMap;

/**
 * 
 *From BIFF5 on, the built-in number formats will be omitted. The built-in
 * formats are dependent on the current regional settings of the operating
 * system. The following table shows which number formats are used by default in
 * a US-English environment. All indexes from 0 to 163 are reserved for built-in
 * formats. The first user-defined format starts at 164. The built-in number
 * formats, 
 * BIFF5-BIFF8: 
 * Index Type Format string 
 * 0 General General 
 * 1 Decimal 0
 * 2 Decimal 0.00 
 * 3 Decimal #,##0 
 * 4 Decimal #,##0.00 
 * 531 Currency "$"#,##0_);("$"#,##0) 
 * 631 Currency "$"#,##0_);[Red]("$"#,##0) 
 * 731 Currency "$"#,##0.00_);("$"#,##0.00) 
 * 831 Currency "$"#,##0.00_);[Red]("$"#,##0.00) 
 * 9Percent 0% 
 * 10 Percent 0.00% 
 * 11 Scientific 0.00E+00 
 * 12 Fraction # ?/? 
 * 13 Fraction # ??/?? 
 * 1432 Date M/D/YY 
 * 15 Date D-MMM-YY 
 * 16 Date D-MMM 
 * 17 Date MMM-YY 
 * 18 Time h:mm AM/PM 
 * 19 Time h:mm:ss AM/PM 
 * 20 Time h:mm 
 * 21 Time h:mm:ss
 * 2232 Date/Time M/D/YY h:mm 
 * 37 Account. _(#,##0_);(#,##0) 
 * 38 Account._(#,##0_);[Red](#,##0) 
 * 39 Account._(#,##0.00_);(#,##0.00) 
 * 40 Account._(#,##0.00_);[Red](#,##0.00) 
 * 4131 Currency _("$"* #,##0_);_("$"*(#,##0);_("$"* "-"_);_(@_) 
 * 423133 Currency _(* #,##0_);_(* (#,##0);_(*"-"_);_(@_) 
 * 4331 Currency _("$"* #,##0.00_);_("$"* (#,##0.00);_("$"*"-"??_);_(@_) 
 * 443133 Currency _(* #,##0.00_);_(* (#,##0.00);_(* "-"??_);_(@_)
 * 45 Time mm:ss 
 * 46 Time [h]:mm:ss 
 * 47 Time mm:ss.0 
 * 48 Scientific ##0.0E+0 
 * 49 Text @
 *
 
 *</pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class BuiltinFormats {
	private static HashMap formatMap;
	//the chinese character
	private static final String CHINESE_YEAR="\u5e74";
	private static final String CHINESE_MONTH="\u6708";
	private static final String CHINESE_DAY="\u65e5";
	private static final String CHINESE_HOUR="\u65f6";
	private static final String CHINESE_MINUTE="\u5206";
	private static final String CHINESE_SECOND="\u79d2";
	
	static {
		formatMap = new HashMap(64);
		// add buildin formats
		formatMap.put("0", new Format(0, Format.TYPE_GENERAL, "0"));
		formatMap.put("1", new Format(1, Format.TYPE_DECIMAL, "0"));
		formatMap.put("2", new Format(2, Format.TYPE_DECIMAL, "0.00"));
		formatMap.put("3", new Format(3, Format.TYPE_DECIMAL, "#,##0"));
		formatMap.put("4", new Format(4, Format.TYPE_DECIMAL, "#,##0.00"));
		formatMap.put("531", new Format(531, Format.TYPE_CURRENCY,
				"\"$\"#,##0_);(\"$\"#,##0)"));
		formatMap.put("631", new Format(631, Format.TYPE_CURRENCY,
				"\"$\"#,##0_);[Red](\"$\"#,##0)"));
		formatMap.put("731", new Format(731, Format.TYPE_CURRENCY,
				"\"$\"#,##0.00_);(\"$\"#,##0.00)"));
		formatMap.put("831", new Format(831, Format.TYPE_CURRENCY,
				"\"$\"#,##0.00_);[Red](\"$\"#,##0.00)"));
		formatMap.put("9", new Format(9, Format.TYPE_PERCENT, "0%"));
		formatMap.put("10", new Format(10, Format.TYPE_PERCENT, "0.00%"));
		formatMap.put("11", new Format(11, Format.TYPE_SCIENTIFIC, "0.00E+00"));
		formatMap.put("12", new Format(12, Format.TYPE_FRACTION, "# ?/?"));
		formatMap.put("13", new Format(13, Format.TYPE_FRACTION, "# ??/??"));
		formatMap.put("14", new Format(14, Format.TYPE_DATE, "yyyy-MM-dd"));
		formatMap.put("1432", new Format(1432, Format.TYPE_DATE, "M/D/YY"));
		formatMap.put("15", new Format(15, Format.TYPE_DATE, "D-MMM-YY"));
		formatMap.put("16", new Format(16, Format.TYPE_DATE, "D-MMM"));
		formatMap.put("17", new Format(17, Format.TYPE_DATE, "MMM-YY"));
		formatMap.put("18", new Format(18, Format.TYPE_TIME, "h:mm AM/PM"));
		formatMap.put("19", new Format(19, Format.TYPE_TIME, "h:mm:ss AM/PM"));
		formatMap.put("20", new Format(20, Format.TYPE_TIME, "h:mm"));
		formatMap.put("21", new Format(21, Format.TYPE_TIME, "h:mm:ss"));
		formatMap.put("22", new Format(22, Format.TYPE_DATE_TIME,"yyyy-M-d HH:mm"));
		formatMap.put("2232", new Format(2232, Format.TYPE_DATE_TIME,
				"M/D/YY h:mm"));
		formatMap.put("37", new Format(37, Format.TYPE_ACCOUNT,
				"_(#,##0_);(#,##0)"));
		formatMap.put("38", new Format(38, Format.TYPE_ACCOUNT,
				"_(#,##0_);[Red](#,##0)"));
		formatMap.put("39", new Format(39, Format.TYPE_ACCOUNT,
				"_(#,##0.00_);(#,##0.00)"));
		formatMap.put("40", new Format(40, Format.TYPE_ACCOUNT,
				"_(#,##0.00_);[Red](#,##0.00)"));
		formatMap.put("4131", new Format(4131, Format.TYPE_CURRENCY,
				"_(\"$\"* #,##0_);_(\"$\"* (#,##0);_(\"$\"* \"-\"_);_(@_)"));
		formatMap.put("423133", new Format(423133, Format.TYPE_CURRENCY,
				"_(* #,##0_);_(* (#,##0);_(* \"-\"_);_(@_)"));
		formatMap.put("4331",
						new Format(4331, Format.TYPE_CURRENCY,
								"_(\"$\"* #,##0.00_);_(\"$\"* (#,##0.00);_(\"$\"* \"-\"??_);_(@_)"));
		formatMap.put("443133", new Format(443133, Format.TYPE_CURRENCY,
				"_(* #,##0.00_);_(* (#,##0.00);_(* \"-\"??_);_(@_)"));
		formatMap.put("45", new Format(45, Format.TYPE_TIME, "mm:ss"));
		formatMap.put("46", new Format(46, Format.TYPE_TIME, "[h]:mm:ss"));
		formatMap.put("47", new Format(47, Format.TYPE_TIME, "mm:ss.0"));
		formatMap.put("48", new Format(48, Format.TYPE_SCIENTIFIC, "##0.0E+0"));
		formatMap.put("49", new Format(49, Format.TYPE_GENERAL, "@"));

		formatMap.put("27", new Format(27, Format.TYPE_DATE, "[$-0411]GE.M.d"));
		formatMap.put("28", new Format(28, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+"d"));
		formatMap.put("29", new Format(29, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("30", new Format(30, Format.TYPE_DATE, "[$-0411]M/d/yy"));
		formatMap.put("31", new Format(31, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap
				.put("32", new Format(32, Format.TYPE_TIME, "[$-0411]h"+CHINESE_HOUR+"mm"+CHINESE_MINUTE+""));
		formatMap.put("33", new Format(33, Format.TYPE_TIME,
				"[$-0411]h\""+CHINESE_HOUR+"\"mm\""+CHINESE_MINUTE+"\"ss\""+CHINESE_SECOND+"\""));
		formatMap.put("34", new Format(34, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+""));
		formatMap.put("35", new Format(35, Format.TYPE_DATE, "[$-0411]M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("36", new Format(36, Format.TYPE_DATE, "[$-0411]yyyy.M.d"));
		formatMap.put("50", new Format(50, Format.TYPE_DATE, "[$-0411]yyyy.M.d"));
		formatMap.put("51", new Format(51, Format.TYPE_DATE,
				"[$-0411]"+CHINESE_YEAR+"M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("52", new Format(52, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+""));
		formatMap.put("53", new Format(53, Format.TYPE_DATE, "[$-0411]M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("54", new Format(54, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("55", new Format(55, Format.TYPE_DATE,
				"[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH+""));
		formatMap.put("56", new Format(56, Format.TYPE_DATE, "[$-0411]m"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
		formatMap.put("57", new Format(57, Format.TYPE_DATE, "[$-0411]yyyy"+CHINESE_YEAR+"M"+CHINESE_MONTH));
		formatMap.put("58", new Format(58, Format.TYPE_DATE,
				"[$-0411]M"+CHINESE_MONTH+"d"+CHINESE_DAY+""));
	}

	/* private */
	private BuiltinFormats() {
	}

	public static Format getFormat(int index) {
		return (Format) formatMap.get(index + "");
	}
}
