/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import net.sourceforge.openforecast.DataSet;
import br.unisinos.tcc.tis4pe.wcf.AWSSettingsDTO;
import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.BufferCloudWatch;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CloudWatchMetricsListener;
import br.unisinos.tcc.tis4pe.wcf.outputdata.ElasticHandler;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandlerAWS extends Thread implements DataHandler {

	private final int windowSpaceSize;
	private final AWSSettingsDTO settings;
	private BufferCloudWatch buffer;
	private int idConsumerCloudWatch;
	
	private ForecastEngine engine;
	Map<DateTime, Integer> timeSerie;
	private DataSet observations;

	protected DataHandlerAWS(AWSSettingsDTO settings) {
		// TODO lançar exceção caso iws seja diferente de segundos
		this.settings = settings;
		this.buffer = new BufferCloudWatch();
		this.timeSerie = new TreeMap<DateTime, Integer>();
		this.idConsumerCloudWatch = PropertieReaderUtil.getConsumerID();
		this.windowSpaceSize = PropertieReaderUtil.getSizeOfBlocksFromWindowSpace();
	}

	@Override
	public void run() {
		this.extractData();
	}

	@Override
	public void extractData() {
		CloudWatchMetricsListener awsListener = new CloudWatchMetricsListener(
				this.buffer);
		awsListener.start();
		
		ElasticHandler elastic = new ElasticHandler(this.settings.getWorkloadCapacity());		
		while (true) {
			//TODO mover esta lógica para AWSController
			this.timeSerie = this.buffer.get(this.idConsumerCloudWatch);
			if( timeSerie.size() % this.windowSpaceSize == 0){
				
				this.observations = this.engine
						.buildProjectionWithAutoBestFit(this.timeSerie);
				if(this.observations != null){
					elastic.executeElasticAction(observations);
					/* 
					 * 5.1- Implementar tratador de elasticidade 
					 * 5.2 - Comunicação com aws para iniciar e desligar vm 
					 * 6- Armazenar retorno dos dados de leitura em séries maiores e
					 *  submetê-las em uma outra thread para o analisador histórico 
					 *  7-Resultado do analisador histórico enviado para o tratador
					 * de elasticidade
					 */
				}				
			}
		}

	}

	// getters
	public void setEngine(ForecastEngine engine) {
		this.engine = engine;
	}
	
	
}
