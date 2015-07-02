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
package edu.npu.fastexcel.biff.read;

import java.util.HashMap;

import edu.npu.fastexcel.compound.CompoundFileReader;
import edu.npu.fastexcel.compound.DirectoryEntry;
import edu.npu.fastexcel.compound.io.Reader;
import edu.npu.fastexcel.compound.stream.ReadableStream;

/**
 * The workbook document contains the Workbook Stream, which is divided into
 * Workbook Globals Substream and several Sheet Substreams.<br/> <strong>Common
 * structure of the Workbook Stream, BIFF5-BIFF8:</strong><br/> Workbook Globals
 * Substream<br/> Sheet Substreams<br/> <strong>Records in the Workbook Stream,
 * BIFF5-BIFF8:</strong><br/> BOF Type = workbook globals <br/> Global workbook
 * records<br/> EOF <br/> BOF Type = sheet, chart, macro sheet, Basic module,
 * <br/> Sheet records <br/> EOF<br/> In BIFF5-BIFF8 Workbook Streams, the
 * Workbook Globals Substream ist the leading part of the stream. It is followed
 * by all Sheet Substreams in order of the sheets that are in the
 * document.Common structure of a BIFF5-BIFF8 Workbook Stream:
 * <ul>
 * <li>Workbook Globals Substream (required)</li>
 * <li>First Sheet Substream (required)</li>
 * <li>Second Sheet Substream (optional)</li>
 * <li>Third Sheet Substream (optional)</li>
 * </ul>
 * 
 * @see WorkBookGlobalsStream
 * @see DefaultSheetStream
 * @author <a href="guooscar@gmail.com">yAma</a> 2008-11-21
 */
public class WorkBookStream {

	//
	private SubStream currentStream;
	private HashMap subStreams;// substream table
	protected ReadableStream readStream;
	protected CompoundFileReader compoundFile;
	protected Reader reader;

	/**
	 * Default Constructor
	 * 
	 * @param file
	 *            the compound file,note this file should be already opened.
	 */
	public WorkBookStream(CompoundFileReader file) {
		subStreams = new HashMap();
		this.compoundFile = file;
		readStream = file.getReadableStream(DirectoryEntry.WORKBOOK_NAME);
        if (readStream == null) {
            readStream = file.getReadableStream(DirectoryEntry.WORKBOOK_NAME_OTHER);
        }
        reader = readStream.getReader();
	}

	/**
	 * @return the currentStream
	 */
	public SubStream getCurrentStream() {
		return currentStream;
	}

	/**
	 * @param currentStream
	 *            the currentStream to set
	 */
	public void setCurrentStream(SubStream currentStream) {
		this.currentStream = currentStream;
	}

	/**
	 * Add a substream to this stream.
	 * 
	 * @param sub
	 */
	public void addSubStream(SubStream sub) {
		subStreams.put(sub.getName(), sub);
	}

	/**
	 * Get substream by name.
	 * 
	 * @param name
	 * @return
	 */
	public SubStream getSubStream(String name) {
		return (SubStream) subStreams.get(name);
	}
}
