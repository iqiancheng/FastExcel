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
package edu.npu.fastexcel.biff.parser.sheet;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.record.Record;
import edu.npu.fastexcel.common.util.NumUtil;

/**
 *<pre>
 * BIFF2 BIFF3 BIFF4 BIFF5 BIFF8
 * 000BH 020BH 020BH 020BH 020BH
 *</pre>
 * 
 * This record stores the range of used rows and stream positions of several
 * records of the current sheet. In particular the position of the first
 * DEFINEDNAME record and XF record is stored (BIFF2-BIFF4) and the position of
 * a specific record in each Row Block (the first ROW record in BIFF2-BIFF4, and
 * the DBCELL record in BIFF5-BIFF8). This stream position array also contains
 * stream offsets to empty Row Blocks, they will point to the next extant Row
 * Block.The number of entries nm in this array can be calculated from the row
 * range given in this record (rf is the index to the first used row, rl is the
 * index to the first row of unused tail of sheet): nm = (rl - rf + 1) / 32 + 1
 * (using integer division).
 * 
 * <pre>
 * offset Size Contents
 *  	4 	Not used
 *  	4 	Index to first used row (rf, 0-based)
 *  	4 	Index to first row of unused tail of sheet (rl, last used row + 1, 0-based)
 * 2 	4 	Absolute stream position of the DEFCOLWIDTH record  of the current sheet. If
 * 	this record does not exist, the offset points to the record at the position where the
 * 	DEFCOLWIDTH record would occur.
 * 6 	4+6*nm 	Array of nm absolute stream positions to the DBCELL record  of each Row Block
 * </pre>
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-20
 */
public class IndexParser extends SheetParser {

	public IndexParser() {
		super(Types.INDEX);
	}

	public void parse() throws ParserException {
		//
		final int off = Record.OFFSET + 4;
		//
		int rf = NumUtil.getInt(b[off], b[off + 1], b[off + 2], b[off + 3]);
		// Index to first used row 0 based
		int rl = NumUtil.getInt(b[off + 4], b[off + 5], b[off + 6], b[off + 7]);
		// Index to first row of unused tail of sheet.last used row + 1, 0-based
		// int
		// defColWidthPos=NumUtil.getInt(b[off+8],b[off+9],b[off+10],b[off+11]);
		// Absolute stream position of the DEFCOLWIDTH record of the current
		// sheet. If
		// this record does not exist, the offset points to the record at the
		// position where the
		// DEFCOLWIDTH record would occur
		int nm = (rl - rf - 1) / 32 + 1;
		if (rl == rf) {
			nm = 0;
		}// TODO is this aright???
		int dbCellPos[] = new int[nm];
		int now = off + 12;
		for (int i = 0; i < nm; i++) {
			dbCellPos[i] = NumUtil.getInt(b[now + (i << 2)], b[now + (i << 2)
					+ 1], b[now + (i << 2) + 2], b[now + (i << 2) + 3]);
		}

	}

}
