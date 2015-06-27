/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import net.sourceforge.openforecast.DataSet;
import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.BufferCloudWatch;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CloudWatchMetricsListener;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandlerAWS extends Thread implements DataHandler {

	private final InputWindowSpaceEnum iws;
	private BufferCloudWatch buffer;
	private int idConsumerCloudWatch;
	
	private ForecastEngine engine;
	Map<DateTime, Integer> timeSerie;
	private DataSet observations;

	protected DataHandlerAWS(InputWindowSpaceEnum iws) {
		this.iws = iws; // TODO lançar exceção caso iws seja diferente de
						// segundos
		this.buffer = new BufferCloudWatch();
		this.timeSerie = new TreeMap<DateTime, Integer>();
		this.idConsumerCloudWatch = PropertieReaderUtil.getConsumerID();
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
		while (true) {
			System.out.println("...");
			System.out.println("...");
			this.observations = this.engine
					.buildProjectionWithAutoBestFit(this.buffer.get(this.idConsumerCloudWatch));
			System.out.println(">>>");
			if(this.observations != null){
				System.out.println(">>> " + engine.getOriginalObservations());				
			}else{
				System.out.println("OBSERVATION EMPTY");
			}
			System.out.println(">>>");
		}
		/*
		 * 4- Enviar resultado para tratador de elasticidade - saída 
		 * 5- *
		 * Implementar tratador de elasticidade 5.1 - Comunicação com aws para
		 * iniciar e desligar vm 6- Armazenar retorno dos dados de leitura em
		 * séries maiores e submetê-las em uma outra thread para o analisador
		 * histórico 7-Resultado do analisador histórico enviado para o tratador
		 * de elasticidade
		 */

		// }

	}

	// getters
	public InputWindowSpaceEnum getIws() {
		return iws;
	}

	public void setEngine(ForecastEngine engine) {
		this.engine = engine;
	}
	
	
}
