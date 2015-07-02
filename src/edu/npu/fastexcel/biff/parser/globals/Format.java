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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import edu.npu.fastexcel.BIFFSetting;
import edu.npu.fastexcel.biff.read.WorkBookGlobalsStream;
import edu.npu.fastexcel.common.util.DateUtil;
import java.math.RoundingMode;

/**
 * This Class represent the excel format record. Reference jxl.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class Format {
	
	private static final TimeZone GMTZONE = TimeZone.getTimeZone("GMT");
	public static final int TYPE_DATE = 1;
	public static final int TYPE_TIME = 2;
	public static final int TYPE_GENERAL = 3;
	public static final int TYPE_DECIMAL = 4;
	public static final int TYPE_CURRENCY = 5;
	public static final int TYPE_SCIENTIFIC = 6;
	public static final int TYPE_FRACTION = 7;
	public static final int TYPE_DATE_TIME = 8;
	public static final int TYPE_ACCOUNT = 9;
	public static final int TYPE_TEXT = 10;
	public static final int TYPE_PERCENT = 11;
	public static final String TIME_STRINGS[]={
		"AM","PM","M","S","H",
		"am","pm","m","s","h",
	};
	public static final String DATE_STRINGS[]={
		"M","Y","D",
		"m","y","d",
	};
	private int index;
	private int type;
	private String content;
	java.text.Format format;

	/**
	 * Default Constructor.
	 * 
	 * @param index
	 *            index of format record.
	 * @param type
	 *            format type.
	 * @param content
	 *            format content.
	 */
	public Format(int index, int type, String content) {
		this.index = index;
		this.type = type;
		this.content = content;
	}

	/**
	 * Format this value and return formatted value as string.
	 * 
	 * @param value
	 *            the value.
	 * @return formatted string.
	 */
	public String format(Object value, WorkBookGlobalsStream gloablStream,
			BIFFSetting setting) {
		String str = value.toString();
		switch (type) {
		case TYPE_GENERAL:
		case TYPE_FRACTION:
            str = floatFormat(new Double(str).doubleValue());
			if(str.endsWith(".0")){
				//it 's an integer
				str=str.substring(0,str.length()-2);
			}
			break;
		case TYPE_TIME:
		case TYPE_DATE_TIME:
		case TYPE_DATE:
			double time = ((Double) value).doubleValue();
			str = getDate(time, gloablStream, setting);
			break;
		default:
            double number = ((Double) value).doubleValue();
            str = getNumber(number, gloablStream, setting);
			break;
		}
		return str;
	}

	/* return formatted number */
	private String getNumber(double value, WorkBookGlobalsStream gloablStream,
			BIFFSetting setting) {
		if (setting.isFormatNumber()) {
			return getNumberFormat().format(value);
		}
		return value + "";
	}

	/*
	 * return formatted value
	 */
	private String getDate(double value, WorkBookGlobalsStream gloablStream,
			BIFFSetting setting) {
		long utcValue = DateUtil.getTimeMillis(value, gloablStream.getDateMode());	
		if (!setting.isFormatDate()) {
			return utcValue + "";
		}
		if (setting.isCommonDateFormat()) {
			format = setting.getDateFormat();
			((SimpleDateFormat)format).setTimeZone(GMTZONE);
		} else {
			format = getDateFormat();
			((SimpleDateFormat)format).setTimeZone(GMTZONE);
		}
		Date date = new Date(utcValue);
		return format.format(date);
	}

	/**
	 * Replaces all instances of search with replace in the input. Used for
	 * replacing microsoft number formatting characters with java equivalents
	 * 
	 * @param input
	 *            the format string
	 * @param search
	 *            the Excel character to be replaced
	 * @param replace
	 *            the java equivalent
	 * @return the input string with the specified substring replaced
	 */
	private static final String replace(String input, String search,
			String replace) {
		String fmtstr = input;
		int pos = fmtstr.indexOf(search);
		while (pos != -1) {
			StringBuffer tmp = new StringBuffer(fmtstr.substring(0, pos));
			tmp.append(replace);
			tmp.append(fmtstr.substring(pos + search.length()));
			fmtstr = tmp.toString();
			pos = fmtstr.indexOf(search);
		}
		return fmtstr;
	}

	/**
	 * Gets the java equivalent number format for the formatString
	 * 
	 * @return The java equivalent of the number format for this object
	 */
	public final NumberFormat getNumberFormat() {
		String formatString = content;
		if (format != null && format instanceof NumberFormat) {
			return (NumberFormat) format;
		}
		try {
			String fs = formatString;
			// Replace the Excel formatting characters with java equivalents
			fs = replace(fs, "E+", "E");
			fs = replace(fs, "_)", "");
			fs = replace(fs, "_", "");
			fs = replace(fs, "[Red]", "");
			fs = replace(fs, "\\", "");
			fs = replace(fs, "\"", "");
			fs=replaceBad(fs);
            DecimalFormat f = new DecimalFormat(fs);
            f.setRoundingMode(RoundingMode.HALF_UP);
            format = f;
		} catch (IllegalArgumentException e) {
			// Something went wrong with the date format - fail silently
			// and return a default value
			DecimalFormat f = new DecimalFormat("#.###");
            f.setRoundingMode(RoundingMode.HALF_UP);
            format = f;
		}
		return (NumberFormat) format;
	}

	/**
	 * Gets the java equivalent date format for the formatString
	 * 
	 * @return The java equivalent of the date format for this object
	 */
	public final DateFormat getDateFormat() {
		if (format != null && format instanceof DateFormat) {
			return (DateFormat) format;
		}
		content = replace(content, "\"", "");
		String formatString = replaceBad(content);
		String fmt = formatString;
		

		// Replace the AM/PM indicator with an a
		int pos = fmt.indexOf("AM/PM");
		while (pos != -1) {
			StringBuffer sb = new StringBuffer(fmt.substring(0, pos));
			sb.append('a');
			sb.append(fmt.substring(pos + 5));
			fmt = sb.toString();
			pos = fmt.indexOf("AM/PM");
		}

		// Replace ss.0 with ss.SSS (necessary to always specify milliseconds
		// because of NT)
		pos = fmt.indexOf("ss.0");
		while (pos != -1) {
			StringBuffer sb = new StringBuffer(fmt.substring(0, pos));
			sb.append("ss.SSS");

			// Keep going until we run out of zeros
			pos += 4;
			while (pos < fmt.length() && fmt.charAt(pos) == '0') {
				pos++;
			}

			sb.append(fmt.substring(pos));
			fmt = sb.toString();
			pos = fmt.indexOf("ss.0");
		}

		// Filter out the backslashes
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < fmt.length(); i++) {
			if (fmt.charAt(i) != '\\') {
				sb.append(fmt.charAt(i));
			}
		}

		fmt = sb.toString();

		// If the date format starts with anything inside square brackets then
		// filter tham out
		if (fmt.charAt(0) == '[') {
			int end = fmt.indexOf(']');
			if (end != -1) {
				fmt = fmt.substring(end + 1);
			}
		}

		// Get rid of some spurious characters that can creep in
		fmt = replace(fmt, ";@", "");

		// We need to convert the month indicator m, to upper case when we
		// are dealing with dates
		char[] formatBytes = fmt.toCharArray();

		for (int i = 0; i < formatBytes.length; i++) {
			if (formatBytes[i] == 'm') {
				// Firstly, see if the preceding character is also an m. If so,
				// copy that
				if (i > 0
						&& (formatBytes[i - 1] == 'm' || formatBytes[i - 1] == 'M')) {
					formatBytes[i] = formatBytes[i - 1];
				} else {
					// There is no easy way out. We have to deduce whether this
					// an
					// minute or a month? See which is closest out of the
					// letters H d s or y
					// First, h
					int minuteDist = Integer.MAX_VALUE;
					for (int j = i - 1; j > 0; j--) {
						if (formatBytes[j] == 'h') {
							minuteDist = i - j;
							break;
						}
					}

					for (int j = i + 1; j < formatBytes.length; j++) {
						if (formatBytes[j] == 'h') {
							minuteDist = Math.min(minuteDist, j - i);
							break;
						}
					}

					for (int j = i - 1; j > 0; j--) {
						if (formatBytes[j] == 'H') {
							minuteDist = i - j;
							break;
						}
					}

					for (int j = i + 1; j < formatBytes.length; j++) {
						if (formatBytes[j] == 'H') {
							minuteDist = Math.min(minuteDist, j - i);
							break;
						}
					}

					// Now repeat for s
					for (int j = i - 1; j > 0; j--) {
						if (formatBytes[j] == 's') {
							minuteDist = Math.min(minuteDist, i - j);
							break;
						}
					}
					for (int j = i + 1; j < formatBytes.length; j++) {
						if (formatBytes[j] == 's') {
							minuteDist = Math.min(minuteDist, j - i);
							break;
						}
					}
					// We now have the distance of the closest character which
					// could
					// indicate the the m refers to a minute
					// Repeat for d and y
					int monthDist = Integer.MAX_VALUE;
					for (int j = i - 1; j > 0; j--) {
						if (formatBytes[j] == 'd') {
							monthDist = i - j;
							break;
						}
					}

					for (int j = i + 1; j < formatBytes.length; j++) {
						if (formatBytes[j] == 'd') {
							monthDist = Math.min(monthDist, j - i);
							break;
						}
					}
					// Now repeat for y
					for (int j = i - 1; j > 0; j--) {
						if (formatBytes[j] == 'y') {
							monthDist = Math.min(monthDist, i - j);
							break;
						}
					}
					for (int j = i + 1; j < formatBytes.length; j++) {
						if (formatBytes[j] == 'y') {
							monthDist = Math.min(monthDist, j - i);
							break;
						}
					}

					if (monthDist < minuteDist) {
						// The month indicator is closer, so convert to a
						// capital M
						formatBytes[i] = Character.toUpperCase(formatBytes[i]);
					} else if ((monthDist == minuteDist)
							&& (monthDist != Integer.MAX_VALUE)) {
						// They are equidistant. As a tie-breaker, take the
						// formatting
						// character which precedes the m
						char ind = formatBytes[i - monthDist];
						if (ind == 'y' || ind == 'd') {
							// The preceding item indicates a month measure, so
							// convert
							formatBytes[i] = Character
									.toUpperCase(formatBytes[i]);
						}
					}
				}
			}
		}

		try {
			this.format = new SimpleDateFormat(new String(formatBytes));
		} catch (IllegalArgumentException e) {
			// There was a spurious character - fail silently
			this.format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		}
		return (DateFormat) this.format;
	}

	public String floatFormat(double value) {
		try {
			double newValue = value;
			String newValueStr = String.valueOf(newValue);
			int eindex = newValueStr.toUpperCase().indexOf("E");
			String format = "";
			if (eindex == -1) {
				return newValueStr;
			} else {
				String cardinalNumberStr = newValueStr.substring(0, eindex);
				String exponentialStr = newValueStr.substring(eindex + 1);
				if (exponentialStr.startsWith("+")) {
					exponentialStr = exponentialStr.substring(1);
				}
				int exponential = Integer.parseInt(exponentialStr);
				if (exponential > 0) {
					if ((cardinalNumberStr.length() - 2 - exponential) > 0) {
						format = "#.";
						for (int i = 0; i < (cardinalNumberStr.length() - 2 - exponential); i++) {
							format += 0;
						}
					} else {
						format = "#.0";
					}
				} else if (exponential < 0) {
					format = "0.";

					for (int i = 0; i < (cardinalNumberStr.substring(
							cardinalNumberStr.indexOf(".") + 1).length() - exponential); i++) {
						format += 0;
					}
				} else {
					format = "#.0";
				}
				if (format.length() == 2) {
					format += 0;
				}
				DecimalFormat df = new DecimalFormat(format);
                df.setRoundingMode(RoundingMode.HALF_UP);
				return df.format(newValue);
			}
		} catch (Exception e) {
			return String.valueOf(value);
		}
	}
	/* 
	 * Replace string like [Dbnum1][Dbnum2][$-xxx]
	 */
	public static String replaceBad(String str){
		StringBuffer sb=new StringBuffer();
		char ss[]=str.toCharArray();
		boolean flag=false;
		for(int i=0;i<ss.length;i++){
			if(ss[i]=='['){
				flag=true;
			}
			if(ss[i]==']'){
				flag=false;
			}
			if(!flag&&ss[i]!=']'){
				sb.append(ss[i]);
			}
		}
		return sb.toString();
	}
	public static boolean isTimeFormat(String format){
		for(int i=0;i<TIME_STRINGS.length;i++){
			if(format.indexOf(TIME_STRINGS[i])!=-1){
				return true;
			}
		}
		return false;
	}
	public static boolean isDateFormat(String format){
		for(int i=0;i<DATE_STRINGS.length;i++){
			if(format.indexOf(DATE_STRINGS[i])!=-1){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}

	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}

	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(String content) {
		this.content = content;
	}
	
	public String toString() {
		return "{"+index+","+type+","+content+"}";
	}
	
	public static void main(String[] args) {
		System.out.println(Format.replaceBad("[dbdd]111[aaa]333"));
		
		
	}
}
