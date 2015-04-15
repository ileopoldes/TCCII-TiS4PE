package br.unisinos.tcc.tis4pe.wcf.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

	/**
	 * @param dateTimeString
	 * @return DateTime format: dd/MMM/yyyy:HH:mm:ss
	 */
	public static DateTime dateFromString(String dateTimeString){
		//04/Sep/1995:00:00:28
		//TODO - receber formato por parâmetro
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
		DateTime dt = formatter.parseDateTime(dateTimeString);
		return dt;
	}
	
	/**
	 * 
	 * @param dateTimeString
	 * @param format - string with dd/MM/yyyy:HH:mm:ss or other format
	 * @return
	 */
	public static DateTime dateFromString(String dateTimeString, String format){
		DateTimeFormatter formatter = DateTimeFormat.forPattern(format);
		DateTime dt = formatter.parseDateTime(dateTimeString);
		return dt;
	}
}
