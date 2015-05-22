package br.unisinos.tcc.tis4pe.wcf.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.dto.StringToDateFormatDTO;

public class DateUtil {

	/**
	 * @param dateTimeString
	 * @return DateTime -  format: dd/MMM/yyyy:HH:mm:ss
	 */
	public static DateTime dateFromString(String dateTimeString){
		//ex. dateTimeString: 04/Sep/1995:00:00:28
		String defaultPattern = "dd/MMM/yyyy:HH:mm:ss"; 
		return format(dateTimeString, defaultPattern);
	}
	
	/**
	 * 
	 * @param strDto - two strings, one date and another the pattern to convert string
	 * @return
	 */
	public static DateTime dateFromString(StringToDateFormatDTO strDto){ 
		return format( strDto.getDateStr(), strDto.getPatternStr() );
	}
	
	/**
	 * 
	 * @param dateTimeString
	 * @param pattern - format to convert
	 * @return DateTime
	 */
	private static DateTime format(String dateTimeString, String pattern){
		DateTimeFormatter formatter = DateTimeFormat.forPattern(pattern);
		DateTime dt = formatter.parseDateTime(dateTimeString);
		return dt;
	}

}
