/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;

public class DataHandler {

	private StreamHandlerInterface inputFileHandler;
	private InputWindowSpaceEnum iws;
	private List<DateTime> dateList;
	
	//TODO adicionar dados para criação das séries (kpi)
	
	//TODO construir um builder passando todos os parâmetros com interface fluída

	// Pegar lista de strings de uma classe que implemente StreamHandler
	// Definir a granularidade das listas de data e hora (minuto, hora, dias) 
	public DataHandler(StreamHandlerInterface inputFileHandler, InputWindowSpaceEnum iws){
		this.inputFileHandler = inputFileHandler;
		this.iws = iws;		
		this.dateList = new ArrayList<DateTime>();
	}
	
	public void extractData(){
		try {
			for(String str : this.inputFileHandler.readStream()){
				str = this.prepareString(str);
				this.fillListOfDates(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void fillListOfDates(String str) {
		DateTime date = DateUtil.dateFromString(str);
		this.dateList.add(date);
	}

	private String prepareString(String str) {
		return str.replace("[", "").replace("]", "").substring(0, 20);
	}
	
	
	
	
	// Organizar estruturas de dados representando data, 
	//   organizadas por período de tempo (minutos, horas) contabilizando total de ocorrências
}
