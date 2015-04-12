/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;

public class DataHandler {

	private StreamHandlerInterface inputFileHandler;
	private InputWindowSpaceEnum iws;
	private Map<Date,String> dateMap;
	

	// Pegar lista de strings de uma classe que implemente StreamHandler
	// Definir a granularidade das listas de data e hora (minuto, hora, dias) 
	public DataHandler(StreamHandlerInterface inputFileHandler, InputWindowSpaceEnum iws){
		this.inputFileHandler = inputFileHandler;
		this.iws = iws;
		this.dateMap = new Hashtable<Date, String>();
		
	}
	
	// Quebrar Stream em data e hora
	public void extractData(){
		int i = 0;
		try {
			//"[04/Sep/1995:00:00:28 -0400]"
			for(String str : this.inputFileHandler.readStream()){
				str = this.prepareString(str);
				Date date = this.formatDate(str);
				//this.dateMap.put(new Date(str.substring(0, 11)),
					//	str);
				
				System.out.println("::>> " + str.substring(0, 11) );
				System.out.println("::>>> " + str.substring( 12, 20 ));
				if( i > 10 ) return;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Date formatDate(String str) {
		// TODO Auto-generated method stub
		return null;
	}

	private String prepareString(String str) {
		return str.replace("[", "").replace("]", "");
	}
	
	
	
	
	// Organizar estruturas de dados representando data, 
	//   organizadas por período de tempo (minutos, horas) contabilizando total de ocorrências
}
