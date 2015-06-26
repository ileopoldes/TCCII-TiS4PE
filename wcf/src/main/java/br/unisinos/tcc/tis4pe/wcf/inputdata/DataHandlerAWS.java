/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CloudWatchMetricsListener;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandlerAWS implements DataHandler{

	private final InputWindowSpaceEnum iws;
	private Map<DateTime, Integer> originalTimeSerie;
	private final long sleepTime; 
	
	protected DataHandlerAWS(InputWindowSpaceEnum iws){
		this.iws = iws; // TODO lançar exceção caso iws seja diferente de segundos
		this.sleepTime = PropertieReaderUtil.getSleepTime();
		this.originalTimeSerie = new TreeMap<DateTime, Integer>();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void extractData(){
		Map<DateTime, Integer> averagesList = new TreeMap<DateTime, Integer>();
		CloudWatchMetricsListener awsListener = new CloudWatchMetricsListener();
		int qtdeAnterior = 0;
		
		awsListener.start();
		while(true){
				
			if( !awsListener.getAveragesList().isEmpty() 
					&& awsListener.getAveragesList().size() > qtdeAnterior){
				
				qtdeAnterior = awsListener.getAveragesList().size();
						
				averagesList = awsListener.getAveragesList();				
				this.makeTimeSerie(averagesList);
				System.out.println("handler " + averagesList.toString());
			}
			
		}

	}
	
	private void makeTimeSerie(Map<DateTime, Integer> averagesList) {
		this.originalTimeSerie = averagesList;			
	}

	@Override
	public synchronized Map<DateTime, Integer> getOriginalTimeSerie() {
		return originalTimeSerie;
	}

	@Override
	public Map<DateTime, Integer> getOriginalTimeSerieUsingAllData() {
		return this.getOriginalTimeSerie();
	}
	
	public InputWindowSpaceEnum getIws() {
		return iws;
	}

}
