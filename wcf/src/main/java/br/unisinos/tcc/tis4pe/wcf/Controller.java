package br.unisinos.tcc.tis4pe.wcf;

import java.util.Map;

import net.sourceforge.openforecast.DataSet;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;

public class Controller {

	private Map<DateTime, Integer> originalTimeSerie;
	private DataSet observations;
	private DataSet forecast;
	
	public void timeSeriesForecastingFromTextFile(String pathFile, String fileLineDelimiter,
			String regexPattern, InputWindowSpaceEnum inputWindowSpace){
		
		StreamHandlerInterface in = new FileInputStreamHandler(regexPattern,
				pathFile, fileLineDelimiter);

		//DataHandler dataHandler = new DataHandler(in, inputWindowSpace);
		String[] str = {"[", "]"};
		DataHandler dataHandler = new DataHandler(in, inputWindowSpace, str);
		dataHandler.extractData();
		this.storeOriginalTimeSerie(dataHandler.getOriginalTimeSerie());
		
		ForecastEngine engine = new ForecastEngine(inputWindowSpace);
		this.forecast = engine.buildProjectionWithAutoBestFit(dataHandler
				.getOriginalTimeSerie());
		this.storeOriginalObservations( engine.getOriginalObservations() );
		
		/*
		 * 		

		Iterator it = fcDataSet.iterator();
		while (it.hasNext()) {
			DataPoint dp = (DataPoint) it.next();
			double forecastValue = dp.getDependentValue();

			// Do something with the forecast value, e.g.
			System.out.println(" - " + dp);
		}
		 */
	}


	// Métodos para armazenar dados, prevendo futuras comparações
	private void storeOriginalTimeSerie(Map<DateTime, Integer> originalTimeSerie) {
		this.originalTimeSerie = originalTimeSerie;
	}
	
	private void storeOriginalObservations(DataSet originalObservations) {
		this.observations = originalObservations;
	}

	public DataSet getForecast() {
		return forecast;
	}	
	
}
