/*
 * Esta classe é o tratador dos dados. Ela é responsável por encapsular
 * o fluxo recebido na estrutura de dados necessária para o motor
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.exceptions.DateHandlerException;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class DataHandlerFile implements DataHandler{

	private final StreamHandlerInterface inputFileHandler;
	private final InputWindowSpaceEnum iws;
	private final float inputSizePercentage;
	private int beginIndex;
	private int endIndex;
	private String[] strToReplace;
	private String datePatternFormat;
	private Map<DateTime, Integer> originalTimeSerie;
	private Map<DateTime, Integer> originalTimeSerieUsingAllData;

	//TODO adicionar dados para criação das séries (kpi)
	
	private DataHandlerFile(Builder builder){
		this.datePatternFormat = builder.datePatternFormat;
		this.inputFileHandler = builder.inputFileHandler;
		this.iws = builder.iws;
		this.inputSizePercentage = builder.inputSizePercentage;
		this.strToReplace = builder.strToReplace;
		this.beginIndex = builder.beginIndex;
		this.endIndex = builder.endIndex;				
	}
	
	@Override
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
		this.originalTimeSerieUsingAllData = TimeSeriesFormatter.format(dateList, this.iws);
		if(this.inputSizePercentage > 0.0f){
			this.originalTimeSerie = this.cutList(this.originalTimeSerieUsingAllData);						
		}else{
			this.originalTimeSerie = this.originalTimeSerieUsingAllData;			
		}
	}

	private Map<DateTime, Integer> cutList(
			Map<DateTime, Integer> originalTimeSerieUsingAllData) {
		
		Map<DateTime, Integer> newList = new TreeMap<DateTime, Integer>();
		int newListSize = (int) (originalTimeSerieUsingAllData.size() * this.inputSizePercentage);
		
		int count = 0;
		for( DateTime dt : originalTimeSerieUsingAllData.keySet() ){
			if(newListSize == count++) break;
			newList.put( dt, originalTimeSerieUsingAllData.get(dt) );
		}
		return newList;
	}


	@Override
	public Map<DateTime, Integer> getOriginalTimeSerie() {
		return originalTimeSerie;
	}
	
	@Override
	public Map<DateTime, Integer> getOriginalTimeSerieUsingAllData() {
		return originalTimeSerieUsingAllData;
	}
	
	
	// getters	
	public int getBeginIndex() {
		return beginIndex;
	}
	
	public int getEndIndex(){
		return endIndex;
	}

	public StreamHandlerInterface getInputFileHandler() {
		return inputFileHandler;
	}

	public InputWindowSpaceEnum getIws() {
		return iws;
	}

	public String getDatePatternFormat() {
		return datePatternFormat;
	}

	public String[] getStrToReplace() {
		return strToReplace;
	}
	//
	


	
// ***********************************************BUILDER**************************************************//

	protected static class Builder{

		private StreamHandlerInterface inputFileHandler;
		private InputWindowSpaceEnum iws;
		private float inputSizePercentage;
		private String datePatternFormat;
		private String[] strToReplace;
		private int beginIndex;
		private int endIndex;
		
		protected Builder(){
			this.beginIndex = Integer.valueOf(
					PropertieReaderUtil.getDefaultBeginIndexForDateString()
					);
			this.endIndex = Integer.valueOf(
					PropertieReaderUtil.getDefaultEndIndexForDateString()
					);	
			this.datePatternFormat = PropertieReaderUtil.getDefaultDateStringPattern();
			this.strToReplace = PropertieReaderUtil.getDefaultStringsToReplace();
		}
		
		protected Builder setInputSizePercentage(float size){
			this.inputSizePercentage = size;
			return this;
		}
		protected Builder setBeginIndex(int index){
			this.beginIndex = index;
			return this;
		}
		
		protected Builder setEndIndex(int index){
			this.endIndex = index;
			return this;
		}
		
		protected Builder setInputFileHandler(StreamHandlerInterface inputFileHandler){
			this.inputFileHandler = inputFileHandler;
			return this;
		}
		
		protected Builder setIws(InputWindowSpaceEnum iws){
			this.iws = iws;
			return this;
		}
		
		protected Builder setDatePatternFormat(String datePatternFormat){
			this.datePatternFormat = datePatternFormat;
			return this;
		}
		
		protected Builder setStrToReplace(String[] str){
			this.strToReplace = str;
			return this;
		}
		
		protected DataHandlerFile build(){
			return validateDataHandlerObject(
					new DataHandlerFile(this)
					);
		}
		
		private DataHandlerFile validateDataHandlerObject(DataHandlerFile dataHandler) {
			if( dataHandler.getInputFileHandler() != null
					&& dataHandler.getIws() != null){
				return dataHandler;
			}else{
				throw new DateHandlerException("É necessário informar ao menos "
						+ "o caminho do arquivo e o tamanho da janela");				
			}
		}
	}	
}
