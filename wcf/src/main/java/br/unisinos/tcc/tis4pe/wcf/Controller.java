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
	
	public DataSet getForecast() {
		return forecast;
	}
	
	public void timeSeriesForecastingFromTextFile(FileSettingsDTO settings){

		DataHandler dataHandler = this.stratctData(
				this.prepareDataHandler(settings)
				); 
		
		ForecastEngine engine = new ForecastEngine( settings.getInputWindowSpace() );
		this.forecast = engine.buildProjectionWithAutoBestFit(dataHandler
				.getOriginalTimeSerie() );
		
		this.storeOriginalObservations( engine.getOriginalObservations() );
	}
	
	
	private DataHandler stratctData(DataHandler dataHandler) {
		dataHandler.extractData();
		this.storeOriginalTimeSerie( dataHandler.getOriginalTimeSerie() );
		return dataHandler;
	}

	// Etapas do processo
	private StreamHandlerInterface prepareStreamHandler(FileSettingsDTO settings){
		return new FileInputStreamHandler(
				settings.getRegexPattern(),
				settings.getPathFile(), 
				settings.getFileLineDelimiter() 
				);
	}
	
	private DataHandler prepareDataHandler(FileSettingsDTO settings){
		return new DataHandler.Builder()
		.setInputFileHandler( this.prepareStreamHandler(settings) )
		.setIws( settings.getInputWindowSpace() )
		.build();
	}
	
	// Métodos para armazenar dados, prevendo futuras comparações
	private void storeOriginalTimeSerie(Map<DateTime, Integer> originalTimeSerie) {
		this.originalTimeSerie = originalTimeSerie;
	}
	
	private void storeOriginalObservations(DataSet originalObservations) {
		this.observations = originalObservations;
	}
	
	public Map<DateTime, Integer> getOriginalTimeSerie(){
		return this.originalTimeSerie;
	}
	
}
