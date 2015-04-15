package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;

public class TimeSeriesFormatter {
	// TODO - criar testes
	public static Map<DateTime, Integer> format(List<DateTime> listDates,
			InputWindowSpaceEnum inputWindow) {
		switch (inputWindow) {
		case SECONDS:
			return groupBySeconds(listDates);
		case MINUTES:
			return groupByMinutes(listDates);
		case HOURS:
			return groupByHours(listDates);
		case DAYS:
			return groupByDays(listDates);

		default:
			return null;
		}
	}
	
	private static Map<DateTime, Integer> groupByDays(
			List<DateTime> listDates) {
		String patternMinute = "dd/MM/yyyy";
		return groupDates( createNewList(listDates, patternMinute) );
	}
	
	private static Map<DateTime, Integer> groupByHours(
			List<DateTime> listDates) {
		String patternMinute = "dd/MM/yyyy:HH";
		return groupDates( createNewList(listDates, patternMinute) );
	}

	private static Map<DateTime, Integer> groupByMinutes(
			List<DateTime> listDates) {
		String patternMinute = "dd/MM/yyyy:HH:mm";
		return groupDates( createNewList(listDates, patternMinute) );
	}

	private static Map<DateTime, Integer> groupBySeconds(
			List<DateTime> listDates) {
		return groupDates(listDates);		
	}
	
	private static List<DateTime> createNewList(List<DateTime> list, String pattern){
		List<DateTime> listDatesMinutes = new ArrayList<DateTime>();
		for(DateTime dt : list) {		
			// "dd/MM/yyyy:HH:mm:ss"
			String str = new StringBuilder( dt.getDayOfMonth()  )
					.append("/")
					.append( dt.getMonthOfYear() )
					.append("/")
					.append( dt.getYear() )
					.append(":")
					.append( dt.getHourOfDay() )
					.append(":")
					.append( dt.getMinuteOfHour() )
					.append( dt.getSecondOfMinute() )
					.toString();
					
			DateTime newDate = DateTime.parse( str, DateTimeFormat.forPattern(pattern) );
			listDatesMinutes.add(newDate);
		}
		return listDatesMinutes;
	}
	
	private static Map<DateTime, Integer> groupDates(List<DateTime> listDates){
		Map<DateTime, Integer> timeSerie = new TreeMap<DateTime, Integer>();
		
		for (DateTime token : listDates) {
			if (timeSerie.containsKey(token)) {
				Integer previousValue = timeSerie.get(token);
				timeSerie.put(token, previousValue == null ? 1
						: previousValue + 1);
			}else{
				timeSerie.put(token, 1);
			}
		}
		return timeSerie;		
	}
}
