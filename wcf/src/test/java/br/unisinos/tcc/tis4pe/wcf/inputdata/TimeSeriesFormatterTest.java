package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;
import junit.framework.TestCase;

public class TimeSeriesFormatterTest extends TestCase {

	public void testFormatSeconds(){
		
		List<DateTime> list = new ArrayList<DateTime>();
		
		String[] arrayDatasSTR = {"04/sep/1995:00:00:27",
				"04/sep/1995:00:00:28","04/sep/1995:00:00:28",
				"04/sep/1995:00:00:29","04/sep/1995:00:00:29","04/sep/1995:00:00:29",
				"04/sep/1995:00:01:27",
				"04/sep/1995:01:00:27",
				"04/oct/1995:00:00:27"};
		
		for(int i=0; i<9; i++){
			DateTime date = DateUtil.dateFromString(arrayDatasSTR[i]);
			list.add(date);
		}
		
		Map<DateTime, Integer> map = TimeSeriesFormatter.format(list, InputWindowSpaceEnum.SECONDS);
		
		assertTrue(
				map.keySet().size() == 6
				&&
				map.get( list.get(0) ) == 1	//"1995-09-04T00:00:27.000-03:00"
				&&
				map.get( list.get(1) ) == 2	//"1995-09-04T00:00:28.000-03:00"
				&&
				map.get( list.get(3) ) == 3	//"1995-09-04T00:00:29.000-03:00"
				&&
				map.get( list.get(6) ) == 1	//"1995-09-04T00:01:27.000-03:00"
				&&
				map.get( list.get(7) ) == 1	//"1995-09-04T01:00:27.000-03:00"
				&&
				map.get( list.get(8) ) == 1	//"1995-10-04T00:00:27.000-03:00"
				);
	}
	
	public void testFormatMinutes(){
		List<DateTime> list = new ArrayList<DateTime>();
		
		String[] arrayDatasSTR = {"04/sep/1995:00:01:00",
				"04/sep/1995:00:02:01","04/sep/1995:00:02:00",
				"04/sep/1995:00:07:00","04/sep/1995:00:07:01","04/sep/1995:00:07:02",
				"04/sep/1995:01:00:00",
				"04/sep/1996:01:00:00",
				"04/oct/1995:01:00:00"};
		
		for(int i=0; i<9; i++){
			DateTime date = DateUtil.dateFromString(arrayDatasSTR[i]);
			list.add(date);
		}
		
		// A lista será modificada para fazer com que os segundos sejam desprezados (serão setados para 0)
		Map<DateTime, Integer> map = TimeSeriesFormatter.format(list, InputWindowSpaceEnum.MINUTES);
		
		
		assertTrue(
				map.keySet().size() == 6
				&&
				map.get( list.get(0) ) == 1	//"1995-09-04T00:01:00.000-03:00"
				&&
				map.get( list.get(2) ) == 2	//"1995-09-04T00:02:00.000-03:00"
				&&
				map.get( list.get(3) ) == 3	//"1995-09-04T00:07:00.000-03:00"
				&&
				map.get( list.get(6) ) == 1	//"1995-09-04T01:00:00.000-03:00"
				&&
				map.get( list.get(7) ) == 1	//"1996-09-04T01:00:00.000-03:00"
				&&
				map.get( list.get(8) ) == 1	//"1995-10-04T01:00:00.000-03:00"
				);
	}
	
	public void testFormatHours(){
		List<DateTime> list = new ArrayList<DateTime>();
		
		String[] arrayDatasSTR = {"04/sep/1995:03:01:00",
				"04/sep/1995:04:02:01","04/sep/1995:04:02:03",
				"04/sep/1995:05:07:00","04/sep/1995:05:08:00","04/sep/1995:05:09:00",
				"05/sep/1995:05:07:00",
				"04/sep/1996:05:07:00",
				"04/oct/1995:05:07:00"};
		
		for(int i=0; i<9; i++){
			DateTime date = DateUtil.dateFromString(arrayDatasSTR[i]);
			list.add(date);
		}
		
		// A lista será modificada para fazer com que os segundos e minutos sejam desprezados (serão setados para 0)
		Map<DateTime, Integer> map = TimeSeriesFormatter.format(list, InputWindowSpaceEnum.HOURS);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
		assertTrue(
				map.keySet().size() == 6
				&&
				map.get( formatter.parseDateTime("04/sep/1995:03:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("04/sep/1995:04:00:00") ) == 2	
				&&
				map.get( formatter.parseDateTime("04/sep/1995:05:00:00") ) == 3	
				&&
				map.get( formatter.parseDateTime("05/sep/1995:05:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("04/sep/1996:05:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("04/oct/1995:05:00:00") ) == 1	
				);

	}
	
	public void testFormatDays(){
		List<DateTime> list = new ArrayList<DateTime>();
		
		String[] arrayDatasSTR = {"04/sep/1995:03:01:00",
				"05/sep/1995:04:02:01","05/sep/1995:04:02:03",
				"06/sep/1995:05:07:00","06/sep/1995:05:08:00","06/sep/1995:06:09:00",
				"07/sep/1995:05:07:00",
				"07/sep/1996:05:07:00",
				"07/oct/1995:05:07:00"};
		
		for(int i=0; i<9; i++){
			DateTime date = DateUtil.dateFromString(arrayDatasSTR[i]);
			list.add(date);
		}
		
		// A lista será modificada para fazer com que a hora seja desprezada (serão setados para 0)
		Map<DateTime, Integer> map = TimeSeriesFormatter.format(list, InputWindowSpaceEnum.DAYS);
		
		DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MMM/yyyy:HH:mm:ss");
		assertTrue(
				map.keySet().size() == 6
				&&
				map.get( formatter.parseDateTime("04/sep/1995:00:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("05/sep/1995:00:00:00") ) == 2	
				&&
				map.get( formatter.parseDateTime("06/sep/1995:00:00:00") ) == 3	
				&&
				map.get( formatter.parseDateTime("07/sep/1995:00:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("07/sep/1996:00:00:00") ) == 1	
				&&
				map.get( formatter.parseDateTime("07/oct/1995:00:00:00") ) == 1	
				);
	}
}
