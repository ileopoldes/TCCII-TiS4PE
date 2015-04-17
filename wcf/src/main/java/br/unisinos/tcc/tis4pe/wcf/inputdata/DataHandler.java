/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;

public class DataHandler {

	private StreamHandlerInterface inputFileHandler;
	private InputWindowSpaceEnum iws;
	private Map<DateTime, Integer> originalTimeSerie;
	
	//TODO adicionar dados para criação das séries (kpi)
	//TODO construir um builder passando todos os parâmetros com interface fluída
	// (iws, inputFileHandler, lenghOfTS, etc) 
	public DataHandler(StreamHandlerInterface inputFileHandler, InputWindowSpaceEnum iws){
		this.inputFileHandler = inputFileHandler;
		this.iws = iws;		
	}
	
	public void extractData(){
		List<DateTime> dateList = new ArrayList<DateTime>();		
		try {
			for(String str : this.inputFileHandler.readStream()){
				str = this.prepareString(str);
				dateList.add( makeDate(str) );
			}
			this.makeTimeSerie(dateList);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			dateList = null;
		}
	}

	private String prepareString(String str) {
		return str.replace("[", "").replace("]", "").substring(0, 20);
	}

	private DateTime makeDate(String str) {
		return DateUtil.dateFromString(str); //TODO - adicionar formatação como parâmetro
	}
	
	private void makeTimeSerie(List<DateTime> dateList) {
		this.originalTimeSerie = TimeSeriesFormatter.format(dateList, this.iws);
		
	}

	public void teste(){
		System.out.println(this.originalTimeSerie);
	}
	
	
	
	
	// Organizar estruturas de dados representando data, 
	//   organizadas por período de tempo (minutos, horas) contabilizando total de ocorrências
}
