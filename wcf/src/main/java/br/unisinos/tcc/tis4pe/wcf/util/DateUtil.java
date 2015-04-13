package br.unisinos.tcc.tis4pe.wcf.util;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateUtil {

	public static DateTime dateFromString(String dateTimeString){
		//04/Sep/1995:00:00:28
		//TODO - receber formato por parâmetro
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
		DateTime dt = formatter.parseDateTime(dateTimeString);
		return dt;
	}
}
