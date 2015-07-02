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
package edu.npu.fastexcel.biff.record.globals;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.globals.XFParser;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 * * <pre>
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
 * @see XFParser
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-12-2
 */
public class XFRecord extends Record {

	private int font;/* 0 2 Index to FONT record */
	private int format;/* 2 2 Index to FORMAT record */
	private int type;/* 4 2 XF type, cell protection, and parent style XF */
	private byte alignment;/* 6 1 Alignment and text break */
	private byte textRotation;/* 7 1 XF_ROTATION: Text rotation angle */
	private byte indentation;/*
							 * 8 1 Indentation, shrink to cell size, and text
							 * direction
							 */
	private byte attributeFlag;/* 9 1 Flags for used attribute groups */
	private int borderAndBackground;/*
									 * 10 4 Cell border lines and background
									 * area
									 */
	private int mask1;/* 14 4 Bit Mask Contents */
	private int mask2;/* 18 2 Bit Mask Contents */

	/**
	 *
	 */
	public XFRecord() {
		super(new byte[20 + RECORD_HEADER_LENGTH]);
		setType(Types.XF);
		setContentLength(bytes.length - RECORD_HEADER_LENGTH);
	}

	/**
	 * Set index to FONT record.
	 * 
	 * @param font
	 *            FONT record 's index.
	 */
	public void setFont(int font) {
		this.font = font;
	}

	/**
	 * Set index to FORMAT record.
	 * 
	 * @param format
	 *            FORMAT record's index.
	 */
	public void setFormat(int format) {
		this.format = format;
	}

	/*
	 * Set bytes using specified value. NOTE:We did not implement all XF
	 * attributes.some of them are default value.
	 */
	private final void getBytes0() {
		// set default value
		/* a sample XF record like this */
		/*
		 * e0 00 14 00 00 00 00 00 f5 ff 20 00 00 00 00 00 00 00 00 00 00 00 c0
		 * 20
		 * 
		 * type=0xfff5,alignment=0x20,textRotation=0x00
		 * indentation=0x00,attributeFlag=0x00,borderAndBackground=0x00000000
		 * mask1=0x00000000,mask2=0x20c0.
		 */
		type = 0xfff5;
		alignment = 0x20;
		textRotation = 0x00;
		indentation = 0x00;
		attributeFlag = 0x00;
		borderAndBackground = 0x00000000;
		mask1 = 0x00000000;
		mask2 = 0x20c0;
		//
		NumUtil.getTwoBytes(font, bytes, 0 + RECORD_HEADER_LENGTH);
		NumUtil.getTwoBytes(format, bytes, 2 + RECORD_HEADER_LENGTH);
		NumUtil.getTwoBytes(type, bytes, 4 + RECORD_HEADER_LENGTH);
		bytes[6 + RECORD_HEADER_LENGTH] = alignment;
		bytes[7 + RECORD_HEADER_LENGTH] = textRotation;
		bytes[8 + RECORD_HEADER_LENGTH] = indentation;
		bytes[9 + RECORD_HEADER_LENGTH] = attributeFlag;
		NumUtil.getFourBytes(borderAndBackground, bytes,
				10 + RECORD_HEADER_LENGTH);
		NumUtil.getFourBytes(mask1, bytes, 14 + RECORD_HEADER_LENGTH);
		NumUtil.getTwoBytes(mask2, bytes, 18 + RECORD_HEADER_LENGTH);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.npu.fastexcel.biff.record.Record#getBytes()
	 */
	public byte[] getBytes() {
		getBytes0();
		return bytes;
	}

}
