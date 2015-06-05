package br.unisinos.tcc.tis4pe.wcf;

import java.util.Map;

import net.sourceforge.openforecast.DataSet;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;
import br.unisinos.tcc.tis4pe.wcf.outputdata.DataExporter;

public class Controller {

	private DataHandler dataHandler;
	private DataSet observations;
	private DataSet forecast;
	private Settings informedSettings;
	
	public DataSet getForecast() {
		return forecast;
	}
	public void exportOriginalTimeSerie(){
		new DataExporter( this.dataHandler.getOriginalTimeSerieUsingAllData(), 
				this.forecast,
				this.informedSettings.getInputWindowSpace() )
		.export(this.informedSettings.getObjective(), "Original" );
	}
	
	public void exportTimeSerie(){
		new DataExporter( this.dataHandler.getOriginalTimeSerie(), 
				this.forecast,
				this.informedSettings.getInputWindowSpace() )
		.export(this.informedSettings.getObjective() );
	}
	
	public Map<DateTime, Integer> getResultTimeSeries(){
		return (new DataExporter(
						this.dataHandler.getOriginalTimeSerie(),
						this.forecast,
						this.informedSettings.getInputWindowSpace() ) 
				).getTimeSerieResult();
	}
	
	public void timeSeriesForecastingFromTextFile(FileSettingsDTO settings){
		this.setInformedSettings(settings);

		this.dataHandler = 	this.prepareDataHandler(settings);
		this.dataHandler.extractData();
		
		ForecastEngine engine = new ForecastEngine( settings.getInputWindowSpace() );
		this.forecast = engine.buildProjectionWithAutoBestFit(dataHandler
				.getOriginalTimeSerie() );
		
		this.storeOriginalObservations( engine.getOriginalObservations() );
	}
	
	// Etapas do processo
	private DataHandler prepareDataHandler(FileSettingsDTO settings){
		return new DataHandler.Builder()
		.setInputFileHandler( this.prepareStreamHandler(settings) )
		.setIws( settings.getInputWindowSpace() )
		.setInputSizePercentage( settings.getInputSizePercentage() )
		.build();
	}
	
	private StreamHandlerInterface prepareStreamHandler(FileSettingsDTO settings){
		return new FileInputStreamHandler(
				settings.getRegexPattern(),
				settings.getPathFile(), 
				settings.getFileLineDelimiter() 
				);
	}
	
	// Métodos para armazenar dados, prevendo futuras comparações
	
	private void storeOriginalObservations(DataSet originalObservations) {
		this.observations = originalObservations;
	}
	
	private void setInformedSettings(Settings settings){
		this.informedSettings = settings;
	}
	
	public Map<DateTime, Integer> getOriginalTimeSerie(){
		return this.dataHandler.getOriginalTimeSerie();
	}
	
}
