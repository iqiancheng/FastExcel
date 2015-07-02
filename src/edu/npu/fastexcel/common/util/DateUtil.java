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
package edu.npu.fastexcel.common.util;

import java.util.Date;

import edu.npu.fastexcel.biff.parser.globals.DateModeParser;

/**
 * Utility for excel date format cell. 
 * @author <a href="guooscar@gmail.com">yAma</a>
 * 2009-2-19
 */
public class DateUtil {
	// The number of days between 1 Jan 1900 and 1 March 1900. Excel thinks
	// the day before this was 29th Feb 1900, but it was 28th Feb 1900.
	// I guess the programmers thought nobody would notice that they
	// couldn't be bothered to program this dating anomaly properly
	private static final int NON_LEAP_DAY = 61;
	// The number of days between 01 Jan 1900 and 01 Jan 1970 - this gives
	// the UTC offset
	private static final int UTC_OFFSET_DAYS = 25569;
	// The number of days between 01 Jan 1904 and 01 Jan 1970 - this gives
	// the UTC offset using the 1904 date system
	private static final int UTC_OFFSET_DAYS_1904 = 24107;
	// The number of milliseconds in a day
	private static final long SECONDS_IN_A_DAY = 24 * 60 * 60;
	private static final long MS_IN_A_DAY = 1000;
	
	
	/**
	 * Return a Date object by given cell value and excel date mode
	 * @see DateModeParser
	 * @param value the date cell's value
	 * @param dateMode the workbook 's date mode
	 * @return the date
	 */
	public static Date getJavaDate(double value,int dateMode){
		return new Date(getTimeMillis(value, dateMode));
	}
	/**
	 * Return a long number by given cell value and excel date mode
	 * @see DateModeParser
	 * @param value the date cell's value
	 * @param dateMode the workbook 's date mode
	 * @return the long number which represent the date.
	 */
	public static long getTimeMillis(double value,int dateMode){
		boolean time = (Math.abs(value) < 1);
		boolean is1900 = (dateMode == DateModeParser.MODE_BASE_1899);
		if (!time && is1900 && value < NON_LEAP_DAY) {
			value++;
		}
		int offsetDays = is1900 ? UTC_OFFSET_DAYS : UTC_OFFSET_DAYS_1904;
		double utcDays = value - offsetDays;
		long utcValue = Math.round(utcDays * SECONDS_IN_A_DAY) * MS_IN_A_DAY;
		return utcValue;
	}

}
