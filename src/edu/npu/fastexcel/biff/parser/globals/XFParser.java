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

/**
 * <pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 0043H 0243H 0443H 00E0H 00E0H
 * This record contains formatting information for cells, rows, columns or styles.
 * Record XF, BIFF8:
 * Offset 	Size 	Contents
 * 0 	2 Index to FONT record 
 * 2	2 Index to FORMAT record 
 * 4 	2 XF type, cell protection, and parent style XF:
 * 		Bit Mask Contents
 * 		2-0 0007H XF_TYPE_PROT  XF type, cell protection (see above)
 * 		15-4 FFF0H Index to parent style XF (always FFFH in style XFs)
 * 6 	1 Alignment and text break:
 * 		Bit	Mask Contents
 * 		2-0	07H XF_HOR_ALIGN  Horizontal alignment (see above)
 * 		3	08H 1 = Text is wrapped at right border
 * 		6-4	70H XF_VERT_ALIGN  Vertical alignment (see above)
 * 		7 	80H 1 = Justify last line in justified or distibuted text
 * 7 	1 XF_ROTATION: Text rotation angle (see above)
 * 8 	1 Indentation, shrink to cell size, and text direction:
 * 		Bit Mask Contents
 * 		3-0	0FH Indent level
 * 		4	10H 1 = Shrink content to fit into cell
 * 		7-6	C0H Text direction:
 * 			0 = According to context35; 1 = Left-to-right; 2 = Right-to-left
 * 9 	1 Flags for used attribute groups:
 * 		Bit Mask Contents
 * 		7-2	FCH XF_USED_ATTRIB  Used attributes (see above)
 * 10	4 Cell border lines and background area:
 * 		Bit Mask Contents
 * 		3-0	0000000FH Left line style 
 * 		7-4	000000F0H Right line style 
 * 		11-8	00000F00H Top line style 
 * 		15-12	0000F000H Bottom line style
 * 		22-16	007F0000H Colour index  for left line colour
 * 		29-23	3F800000H Colour index  for right line colour
 * 		30	40000000H 1 = Diagonal line from top left to right bottom
 * 		31	80000000H 1 = Diagonal line from bottom left to right top
 * 14	4 Bit Mask Contents
 * 		6-0	0000007FH Colour index  for top line colour
 * 		13-7	00003F80H Colour index  for bottom line colour
 * 		20-14	001FC000H Colour index  for diagonal line colour
 * 		24-21	01E00000H Diagonal line style 
 * 		31-26	FC000000H Fill pattern 
 * 18 	2 Bit Mask Contents
 * 		6-0	007FH Colour index for pattern colour
 * 		13-7	3F80H Colour index  for pattern background
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class XFParser extends WorkBookParser {

	/**
	 * @param type
	 */
	public XFParser() {
		super(Types.XF);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.parser.globals.WorkBookParser#parse()
	 */
	public void parse() throws ParserException {
		int indexToFormat = NumUtil.getInt(b[off + 2], b[off + 3]);
		// we just care format.
		workBookGlobalsStream.addXF(new XF(indexToFormat));
		// System.err.println("[XF]:"+indexToFormat);
		// TODO implement others
	}

}
