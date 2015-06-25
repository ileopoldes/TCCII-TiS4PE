/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CloudWatchMetricsListener;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandlerAWS implements DataHandler{

	private final InputWindowSpaceEnum iws;
	private Map<DateTime, Integer> originalTimeSerie;
	private final long sleepTime; 
	
	protected DataHandlerAWS(InputWindowSpaceEnum iws){
		this.iws = iws; // TODO lançar exceção caso iws seja diferente de segundos
		this.sleepTime = PropertieReaderUtil.getSleepTime();
	}
	
	@SuppressWarnings("static-access")
	@Override
	public void extractData(){
		Map<DateTime, Integer> averagesList = new TreeMap<DateTime, Integer>();
		CloudWatchMetricsListener awsListener = new CloudWatchMetricsListener();
		
		awsListener.start();
		while(true){
			try {
				awsListener.sleep(sleepTime);
				averagesList = awsListener.getAveragesList();				
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
			
			this.makeTimeSerie(averagesList);	
			System.out.println(">>> " + averagesList.toString()); //TODO apagar - debug
		}

	}
	
	private void makeTimeSerie(Map<DateTime, Integer> averagesList) {
		this.originalTimeSerie = averagesList;			
	}

	@Override
	public Map<DateTime, Integer> getOriginalTimeSerie() {
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
