package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.joda.time.DateTime;
import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;

public class TimeSeriesFormatter {
	// TODO - criar testes
	public static Map<DateTime, Integer> format(List<DateTime> listDates,
			InputWindowSpaceEnum inputWindow) {
		switch (inputWindow) {
		case SECONDS:
			return groupBySeconds(listDates);
		case MINUTES:
			break;
		case HOURS:
			break;
		case DAYS:
			break;
		case MONTHS:
			break;

		default:
			return null;
		}
		return null;
	}

	private static Map<DateTime, Integer> groupBySeconds(List<DateTime> listDates) {
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
