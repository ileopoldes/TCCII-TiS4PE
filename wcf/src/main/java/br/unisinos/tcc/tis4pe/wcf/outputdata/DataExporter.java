/*
 *Classe responsável pela exportação dos dados 
 *
 */
package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.ObjectiveEnum;

public class DataExporter {

	private final Map<DateTime, Integer> timeSerieResult;
	private final Map<DateTime, Integer> originalTimeSerie;
	private final DataSet forecast;
	private final InputWindowSpaceEnum inputWindowSpace;
	
	public DataExporter(Map<DateTime, Integer> originalTimeSerie, 
			DataSet forecast,
			InputWindowSpaceEnum inputWindowSpace){
		this.originalTimeSerie = originalTimeSerie;
		this.forecast = forecast;
		this.inputWindowSpace = inputWindowSpace;
		this.timeSerieResult = this.createTimeSerieResult();
	}
	
	public void export(ObjectiveEnum objective){
		ExporterFactory.getExporterInstance(objective).export(this.timeSerieResult);
	}
	
	private Map<DateTime, Integer> createTimeSerieResult(){
		List<DateTime> dates = new ArrayList<DateTime>( this.originalTimeSerie.keySet() );
		DateTime lastDate = dates.get( dates.size()-1 );
		dates.clear();

		Map<DateTime, Integer> timeSerie = new TreeMap<DateTime, Integer>();
		timeSerie.putAll(this.originalTimeSerie);
		
		Iterator<DataPoint> iterator = this.forecast.iterator();
		while ( iterator.hasNext() ) {
			DataPoint dataPoint = iterator.next();
			int forecastValue = (int) dataPoint.getDependentValue();
			
			DateTime dt = nextDate(lastDate);
			lastDate = dt;

			timeSerie.put(lastDate, forecastValue);
		}
		return timeSerie;
	}

	private DateTime nextDate(DateTime lastDate) {
		switch (this.inputWindowSpace) {
		case SECONDS:
			return lastDate.plusSeconds(1);
		case MINUTES:
			return lastDate.plusMinutes(1);
		case HOURS:
			return lastDate.plusHours(1);
		case DAYS:
			return lastDate.plusDays(1);
		default:
			return lastDate.plusMinutes(1);
		}
	}

	public Map<DateTime, Integer> getTimeSerieResult() {
		return timeSerieResult;
	}
	

}
