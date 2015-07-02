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
package edu.npu.fastexcel.biff.parser.cell;

import edu.npu.fastexcel.biff.Types;
import edu.npu.fastexcel.biff.parser.ParserException;
import edu.npu.fastexcel.biff.parser.globals.Format;
import edu.npu.fastexcel.biff.parser.globals.XF;
import edu.npu.fastexcel.biff.read.RecordReader;
import edu.npu.fastexcel.biff.record.ReadOnlyRecord;
import edu.npu.fastexcel.common.util.NumUtil;
import edu.npu.fastexcel.compound.io.ReadException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;

/**
 *
 * @author <a href="hedyn@foxmail.com">HeDYn</a>
 * @version 0.1.20101203
 */
public class FormulaParser extends CellParser {
    private static DecimalFormat decimalFormat = new DecimalFormat("#.##############");

    public FormulaParser() {
        super(Types.FORMULA);
    }

    protected FormulaParser(int type) {
        super(type);
    }

    public void parse() throws ParserException {
        if (b[off + 6] == 0 && b[off + 12] == -1 && b[off + 13] == -1) {
            parseStringFormula();
        } else if (b[off + 6] == 1 && b[off + 12] == -1 && b[off + 13] == -1) {
            parseBooleanFormula();
        } else if (b[off + 6] == 2 && b[off + 12] == -1 && b[off + 13] == -1) {
            parseErrorFormula();
        } else if (b[off + 6] == 3 && b[off + 12] == -1 && b[off + 13] == -1) {
            parseEmptyStringFormula();
        } else {
            parseNumberFormula();
        }
    }

    protected void parseNumberFormula() {
        double value = NumUtil.getIEEEDouble(b, off + 6);
        String sValue = Double.toString(value);
        int i = sValue.indexOf('.') + 1;
        if (sValue.length() - i == 15) {
            value = Double.valueOf(decimalFormat.format(value)).doubleValue();
        }

        XF x = workBookGlobalsStream.getXF(xf);
        if (x != null) {
			Format f = workBookGlobalsStream.getFormat(x.getFormat());
            if (f != null) {
				String str = f.format(new Double(value), workBookGlobalsStream,
						context.getSetting());
        		sheetStream.setContent(r, c, str);
				return;
			}
		}
        sheetStream.setContent(r, c, value + "");
    }

    protected void parseBooleanFormula() {
        boolean value = b[off + 8] == 1;
        sheetStream.setContent(r, c, value + "");
    }

    protected void parseErrorFormula() {
        int errorCode = b[off + 4];
        sheetStream.setContent(r, c, "ERROR" + errorCode);
    }

    protected void parseEmptyStringFormula() {
        sheetStream.setContent(r, c, "");
    }

    protected void parseStringFormula() {
        ReadOnlyRecord rec = new ReadOnlyRecord();
        RecordReader rr = context.getStreamReader();
        try {
            int i = 1;
            while (rr.nextRecord(rec) && rec.getType() != Types.STRING && i < 4) {
                i++;
            }

            int len = rec.getContentLength();
            byte[] stringData = new byte[len];
            System.arraycopy(rec.getBytes(), off, stringData, 0, len);

            String value = buildString(stringData);
            sheetStream.setContent(r, c, value);
        } catch (ReadException e) {
            System.err.println(e.toString());
        }
    }

    private static String buildString(byte bytes[]) {
        try {
            int i = 0;
            int j = NumUtil.getInt(bytes[0], bytes[1]);
            i += 2;
            byte abyte = bytes[i];
            i++;
            boolean flag = (abyte & 4) != 0;
            boolean flag1 = (abyte & 8) != 0;
            if(flag1) i += 2;
            if(flag) i += 4;
            boolean flag2 = (abyte & 1) == 0;
            byte[] data = null;
            if(flag2) {
                data = new byte[j];
                System.arraycopy(bytes, i, data, 0, j);
                i += j;
            } else {
                data = new byte[j * 2];
                System.arraycopy(bytes, i, data, 0, j * 2);
                i += data.length;
            }
            if(flag2) {
                return new String(data);
            } else {
                return new String(data, "UTF8");
            }
        } catch(UnsupportedEncodingException e) {
            System.err.println(e.toString());
        }
        return "";
    }

}
