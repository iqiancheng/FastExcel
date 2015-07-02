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
package edu.npu.fastexcel.biff.parser.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.common.util.StringUtil;

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * -- 0293H 0293H 0293H 0293H
 * This record stores the name of a user-defined cell style or specific options 
 * for a built-in cell style. All STYLE records occur together behind the XF 
 * record list. Each STYLE record refers to a style XF record, which contains the
 * formatting attributes for the cell style.
 * STYLE record for user-defined cell styles, BIFF3-BIFF8:
 * Offset Size Contents
 * 0 	2 	Bit Mask Contents
 * 		11-0	0FFFH Index to style XF record 
 * 		15	8000H Always 0 for user-defined styles
 * 2 	var. 
 * 		BIFF2-BIFF5: Non-empty byte string, 8-bit string length 
 * 		BIFF8: Non-empty Unicode string, 16-bit string length
 * &lt;hr/&gt;
 * STYLE record for built-in cell styles, BIFF3-BIFF8:
 * Offset 	Size 	Contents
 * 0 	2 	Bit Mask Contents
 * 		11-0 0FFFH Index to style XF record 
 * 		15 8000H Always 1 for built-in styles
 * 2 	1 	Identifier of the built-in cell style:
 * 		00H = Normal
 * 		01H = RowLevel_lv (see next field)
 * 		02H = ColLevel_lv (see next field)
 * 		03H = Comma
 * 		04H = Currency
 * 		05H = Percent
 * 		06H = Comma [0] (BIFF4-BIFF8)
 * 		07H = Currency [0] (BIFF4-BIFF8)
 * 		08H = Hyperlink (BIFF8)
 * 		09H = Followed Hyperlink (BIFF8)
 * 3 	1 	Level for RowLevel or ColLevel style (zero-based, lv), FFH otherwise
 * The RowLevel and ColLevel styles specify the formatting of subtotal cells in 
 * a specific outline level. The level is specified by the last field in the STYLE
 * record. Valid values are 0-6 for the outline levels 1-7.
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class StyleParser extends WorkBookParser {

	public static final int MASK_INDEX = 0x0fff;
	public static final int MASK_FLAG = 0x8000;

	public static final int STYLE_NORMAL = 0x00;
	public static final int STYLE_ROWLEVEL = 0x01;
	public static final int STYLE_COLLEVEL = 0x02;
	public static final int STYLE_COMMA = 0x03;
	public static final int STYLE_CURRENCY = 0x04;
	public static final int STYLE_PERCENT = 0x05;
	public static final int STYLE_COMMA_BIFF8 = 0x06;
	public static final int STYLE_CURRENCY_BIFF8 = 0x07;
	public static final int STYLE_HYPERLINK = 0x08;
	public static final int STYLE_FOLLOWED_HYPERLINK = 0x09;

	/**
	 * 
	 */
	public StyleParser() {
		super(Types.STYLE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int h = NumUtil.getInt(b[off], b[off + 1]);
		int index = h & MASK_INDEX;
		int flag = (h & MASK_FLAG) >> 15;
		String str = "";
		int id = 0;
		if (flag == 0) {
			// user defined style.
			StringBuffer sb = new StringBuffer();
			StringUtil.getBIFFUnicodeString(b, off + 2, sb);
			str = sb.toString();
		} else {
			// built-in style.
			id = b[2];
			int lv = b[3];

		}
		// TODO implement this
	}

}
