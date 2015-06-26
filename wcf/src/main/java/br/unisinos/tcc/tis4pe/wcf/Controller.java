package br.unisinos.tcc.tis4pe.wcf;

import java.util.Map;

import net.sourceforge.openforecast.DataSet;

import org.joda.time.DateTime;

import com.amazonaws.services.ec2.model.Storage;

import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerAWS;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFactory;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFile;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;
import br.unisinos.tcc.tis4pe.wcf.outputdata.DataExporter;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class Controller {

	private DataHandler dataHandler;
	private DataSet observations;
	private DataSet forecast;
	private Settings informedSettings;

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
		this.dataHandler = (DataHandlerAWS) DataHandlerFactory.getInstance(settings);
		ForecastEngine engine = new ForecastEngine( settings.getInputWindowSpace() );

		new Thread(new Runnable() {
			@Override
			public void run() {
				dataHandler.extractData();
			}
		}).start();
		
		try {
			while(true){
				Thread.sleep( PropertieReaderUtil.getSleepTime() );

				if( !dataHandler.getOriginalTimeSerie().isEmpty() ){

					this.forecast = 
							engine.buildProjectionWithAutoBestFit(
									dataHandler.getOriginalTimeSerie()
									);
					this.storeOriginalObservations( engine.getOriginalObservations() );
					System.out.println("... " + engine.getOriginalObservations() );
					/* 
					 * 4- Enviar resultado para tratador de elasticidade - saída 
					 * 5- Implementar tratador de elasticidade 
					 * 5.1 - Comunicação com aws para iniciar e desligar vm 
					 * 6- Armazenar retorno dos dados de leitura em séries maiores e
					 * submetê-las em uma outra thread para o analisador histórico 
					 * 7-Resultado do analisador histórico enviado para o tratador de
					 * elasticidade
					 */
					
				}
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
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
