package br.unisinos.tcc.tis4pe.wcf.ctrl;

import java.util.Map;

import net.sourceforge.openforecast.DataSet;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.FileSettingsDTO;
import br.unisinos.tcc.tis4pe.wcf.Settings;
import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFactory;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFile;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFileInterface;
import br.unisinos.tcc.tis4pe.wcf.outputdata.DataExporter;

public class Controller {

	//TODO separar os controlers. Deixar uma interface geral mas os controles específicos de cada implementação
	// atribuir para um controller especializado
	private DataHandlerFileInterface dataHandler;
	private DataSet observations;
	private DataSet forecast;
	
	private AWSController awsCtrl;
	
	private Settings informedSettings;
	private ForecastEngine engine;

	public DataSet getForecast() {
		return forecast;
	}

	public void exportOriginalTimeSerie() {
		new DataExporter(this.dataHandler.getOriginalTimeSerieUsingAllData(),
				this.forecast, this.informedSettings.getInputWindowSpace())
				.export(this.informedSettings.getObjective(), "Original");
	}

	public void exportTimeSerie() {
		new DataExporter(this.dataHandler.getOriginalTimeSerie(),
				this.forecast, this.informedSettings.getInputWindowSpace())
				.export(this.informedSettings.getObjective());
	}

	public Map<DateTime, Integer> getResultTimeSeries() {
		return (new DataExporter(this.dataHandler.getOriginalTimeSerie(),
				this.forecast, this.informedSettings.getInputWindowSpace()))
				.getTimeSerieResult();
	}

	public void timeSeriesForecastingFromTextFile(FileSettingsDTO settings) {
		this.setInformedSettings(settings);

		this.dataHandler = (DataHandlerFile) DataHandlerFactory
				.getInstance(settings);
		this.dataHandler.extractData();

		ForecastEngine engine = new ForecastEngine(
				settings.getInputWindowSpace());
		this.forecast = engine.buildProjectionWithAutoBestFit(dataHandler
				.getOriginalTimeSerie());

		this.storeOriginalObservations(engine.getOriginalObservations());
	}

	public void timeSeriesForecastingFromWebservice(Settings settings) {
		this.setInformedSettings(settings);
		this.engine = new ForecastEngine(settings.getInputWindowSpace());
		this.awsCtrl = new AWSController(this.informedSettings, this.engine);
		this.awsCtrl.start();
	}

	
	// Métodos para armazenar dados, prevendo futuras comparações
	private void storeOriginalObservations(DataSet originalObservations) {
		this.observations = originalObservations;
	}

	private void setInformedSettings(Settings settings) {
		this.informedSettings = settings;
	}

	public Map<DateTime, Integer> getOriginalTimeSerie() {
		return this.dataHandler.getOriginalTimeSerie();
	}

}
