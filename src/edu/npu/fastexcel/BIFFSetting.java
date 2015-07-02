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
package edu.npu.fastexcel;

import java.text.SimpleDateFormat;

/**
 * Some options.
 * 
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-27
 */
public class BIFFSetting {
	public static final int SST_TYPE_DEFAULT=1;
	public static final int SST_TYPE_TEMPFILE_WITH_CACHE=2;
	public static final int SST_TYPE_TEMPFILE=3;
	
	/*
	 * If format date cell using excel date format. If this filed set be
	 * false.We just convert the date filed value to a GMT time,this time is a
	 * long number which is milliseconds from 1900-1-1. You just use Date
	 * date=new Date(time) can rebuild this date,sometimes it's a good choice.
	 */

	private boolean formatDate = true;
	/*
	 * If use a common date format.
	 */

	private boolean commonDateFormat = false;
	/*
	 * The common date format
	 */

	private SimpleDateFormat dateFormat;
	/*
	 * If format number cell using excel number format.
	 */

	private boolean formatNumber = true;
	/*
	 * The type of shared string table
	 * @see SST
	 * @see ArraySST
	 * @see TempFileSST
	 * @see CacheTempFileSST
	 */
	private int sstType;

	/**
	 *Default Constructor.
	 */
	public BIFFSetting() {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		sstType=SST_TYPE_DEFAULT;
	}
	
	/**
	 * @return the sstType
	 */
	public int getSSTType() {
		return sstType;
	}

	/**
	 * @param sstType the sstType to set
	 */
	public void setSSTType(int sstType) {
		if(sstType<SST_TYPE_DEFAULT||sstType>SST_TYPE_TEMPFILE){
			throw new IllegalArgumentException(
					"Illegal shared string table type");
		}
		this.sstType = sstType;
	}

	/**
	 * @return the formatDate
	 */
	public boolean isFormatDate() {
		return formatDate;
	}

	/**
	 * @param formatDate
	 *            the formatDate to set
	 */
	public void setFormatDate(boolean formatDate) {
		this.formatDate = formatDate;
	}

	/**
	 * @return the commonDateFormat
	 */
	public boolean isCommonDateFormat() {
		return commonDateFormat;
	}

	/**
	 * @param commonDateFormat
	 *            the commonDateFormat to set
	 */
	public void setCommonDateFormat(boolean commonDateFormat) {
		this.commonDateFormat = commonDateFormat;
		if (commonDateFormat) {
			setFormatDate(true);
		}
	}

	/**
	 * @return the dateFormat
	 */
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	/**
	 * @param dateFormat
	 *            the dateFormat to set
	 */
	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	/**
	 * @return the formatNumber
	 */
	public boolean isFormatNumber() {
		return formatNumber;
	}

	/**
	 * @param formatNumber
	 *            the formatNumber to set
	 */
	public void setFormatNumber(boolean formatNumber) {
		this.formatNumber = formatNumber;
	}

}
