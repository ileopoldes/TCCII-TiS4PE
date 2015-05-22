/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.nio.file.attribute.AclEntry.Builder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandler {

	private StreamHandlerInterface inputFileHandler;
	private InputWindowSpaceEnum iws;
	private Map<DateTime, Integer> originalTimeSerie;
	private String[] strToReplace;
	private int beginIndex;
	private int endIndex;
	// colocar formatação da data 
	
	//TODO adicionar dados para criação das séries (kpi)
	//TODO construir um builder passando todos os parâmetros com interface fluída
	// (iws, inputFileHandler, lenghOfTS, etc) 
	public DataHandler(StreamHandlerInterface inputFileHandler, InputWindowSpaceEnum iws){
		this.inputFileHandler = inputFileHandler;
		this.iws = iws;		
	}
	
	public DataHandler(StreamHandlerInterface inputFileHandler, InputWindowSpaceEnum iws,
			String[] strToReplace){
		this.inputFileHandler = inputFileHandler;
		this.iws = iws;
		this.strToReplace = strToReplace;
		this.beginIndex = Integer.valueOf(
				PropertieReaderUtil.getDefaultBeginIndexForDateString()
				);
		this.endIndex = Integer.valueOf(
				PropertieReaderUtil.getDefaultEndIndexForDateString()
				);
				
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
		if( this.strToReplace != null ){
			StringBuilder builder = new StringBuilder(str);
			for(int i=0; i<this.strToReplace.length; i++){
				builder = new StringBuilder(
						builder
						.toString()
						.replace( this.strToReplace[i],"" )
						);
			}
			return builder.toString()
					.substring(
							this.beginIndex,
							this.endIndex
							);
		}
		return str;
		//return str.replace("[", "").replace("]", "").substring(0, 20);
	}

	private DateTime makeDate(String str) {
		return DateUtil.dateFromString(str);
	}
	
	private void makeTimeSerie(List<DateTime> dateList) {
		this.originalTimeSerie = TimeSeriesFormatter.format(dateList, this.iws);
		
	}

	public Map<DateTime, Integer> getOriginalTimeSerie() {
		return originalTimeSerie;
	}
	
}
