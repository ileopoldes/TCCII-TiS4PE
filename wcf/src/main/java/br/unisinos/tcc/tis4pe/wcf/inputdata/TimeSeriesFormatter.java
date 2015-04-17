package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;

public class TimeSeriesFormatter {

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
			return null;	//TODO substituir por exceções adequadas
		}
	}
	
	private static Map<DateTime, Integer> groupByDays(
			List<DateTime> listDates) {
		return groupDates( createNewList(listDates, InputWindowSpaceEnum.DAYS) );
	}
	
	private static Map<DateTime, Integer> groupByHours(
			List<DateTime> listDates) {
		return groupDates( createNewList(listDates, InputWindowSpaceEnum.HOURS) );
	}

	private static Map<DateTime, Integer> groupByMinutes(
			List<DateTime> listDates) {
		return groupDates( createNewList(listDates, InputWindowSpaceEnum.MINUTES) );
	}

	private static Map<DateTime, Integer> groupBySeconds(
			List<DateTime> listDates) {
		return groupDates(listDates);		
	}
	
	private static List<DateTime> createNewList(List<DateTime> list, InputWindowSpaceEnum windowSpace){
		List<DateTime> listDatesMinutes = new ArrayList<DateTime>();
		DateTime newDate;
		
		for(DateTime dt : list) {
			
			switch (windowSpace) {
			case MINUTES:
				newDate = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 
						dt.getHourOfDay(), dt.getMinuteOfHour(), 0);				
				break;
			case HOURS:
				newDate = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 
						dt.getHourOfDay(), 0, 0);
				break;
			case DAYS:
				newDate = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 
						0, 0, 0);
				break;
			default:
				newDate = new DateTime(dt.getYear(), dt.getMonthOfYear(), dt.getDayOfMonth(), 
						dt.getHourOfDay(), dt.getMinuteOfHour(), dt.getSecondOfMinute());	
				break;
			}
					
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
