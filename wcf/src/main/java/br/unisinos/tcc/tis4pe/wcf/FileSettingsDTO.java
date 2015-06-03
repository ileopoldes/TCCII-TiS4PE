package br.unisinos.tcc.tis4pe.wcf;

import br.unisinos.tcc.tis4pe.wcf.exceptions.DateHandlerException;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class FileSettingsDTO extends Settings{
	private final String pathFile;
	private final String fileLineDelimiter;
	private final String[] stringsToReplace;
	private final int beginUtilTextInLineINDEX;
	private final int endUtilTextInLineINDEX;
	private final String datePatternFormat;
	private final String regexPattern;
	
	
	private FileSettingsDTO(Builder builder){
		super(builder.workloadCapacity, 
				builder.inputWindowSpaceEnum,
				builder.objective,
				builder.inputSizePercentage);
		this.pathFile = builder.pathFile;
		this.fileLineDelimiter = builder.fileLineDelimiter;
		this.stringsToReplace = builder.stringsToReplace;
		this.beginUtilTextInLineINDEX = builder.beginUtilTextInLineINDEX;
		this.endUtilTextInLineINDEX = builder.endUtilTextInLineINDEX;		
		this.datePatternFormat = builder.datePatternFormat;
		this.regexPattern = builder.regexPattern;
	}

	
	public String getPathFile() {
		return pathFile;
	}
	
	public String getFileLineDelimiter() {
		return fileLineDelimiter;
	}
	
	public String[] getStringsToReplace() {
		return stringsToReplace;
	}
	
	public int getBeginUtilTextInLineINDEX() {
		return beginUtilTextInLineINDEX;
	}
	
	public int getEndUtilTextInLineINDEX() {
		return endUtilTextInLineINDEX;
	}
	
	public String getDatePatternFormat() {
		return datePatternFormat;
	}
	
	public String getRegexPattern() {
		return regexPattern;
	}
	
	public static class Builder{
		private String pathFile;
		private String fileLineDelimiter;
		private String[] stringsToReplace; 
		private int beginUtilTextInLineINDEX; 
		private int endUtilTextInLineINDEX;
		private String datePatternFormat; 
		private String regexPattern;
		//default settings
		private ObjectiveEnum objective;
		private InputWindowSpaceEnum inputWindowSpaceEnum;
		private int workloadCapacity;
		private float inputSizePercentage;
		
		public Builder(){
			this.beginUtilTextInLineINDEX = Integer.valueOf(
					PropertieReaderUtil.getDefaultBeginIndexForDateString()
					);
			this.endUtilTextInLineINDEX = Integer.valueOf(
					PropertieReaderUtil.getDefaultEndIndexForDateString()
					);	
			this.datePatternFormat = PropertieReaderUtil.getDefaultDateStringPattern();
			this.stringsToReplace = PropertieReaderUtil.getDefaultStringsToReplace();
			this.fileLineDelimiter = PropertieReaderUtil.getDefaultFileDelimiter();
			this.regexPattern = PropertieReaderUtil.getDefaultRegexPattern();
		}
		
		public Builder setInputSizePercentage(float percentage){
			this.inputSizePercentage = percentage;
			return this;
		}
		
		public Builder setInputWindowSpace(InputWindowSpaceEnum iws){
			this.inputWindowSpaceEnum = iws;
			return this;
		}
		
		public Builder setObjective(ObjectiveEnum objective){
			this.objective = objective;
			return this;
		}
		
		public Builder setWorkloadCapacity(int workload){
			this.workloadCapacity = workload;
			return this;
		}
		
		public Builder setBeginIndex(int index){
			this.beginUtilTextInLineINDEX = index;
			return this;
		}
		
		public Builder setEndIndex(int index){
			this.endUtilTextInLineINDEX = index;
			return this;
		}
		
		public Builder setDatePatternFormat(String datePatternFormat){
			this.datePatternFormat = datePatternFormat;
			return this;
		}
		
		public Builder setStrToReplace(String[] str){
			this.stringsToReplace = str;
			return this;
		}
		
		public Builder setPathFile(String path){
			this.pathFile = path;
			return this;	
		}
		
		public Builder setRegexPattern(String pattern){
			this.regexPattern = pattern;
			return this;
		}
		
		public Builder setFileLineDelimiter(String delimiter){
			this.fileLineDelimiter = delimiter;
			return this;
		}
		
		public FileSettingsDTO build(){
			return validateDataHandlerObject(
					new FileSettingsDTO(this)
					);
		}
		
		private FileSettingsDTO validateDataHandlerObject(FileSettingsDTO settings) 
				throws DateHandlerException{
			
			if( settings.getPathFile() != null && settings.isTheDataValid() ){
				return settings;
			}else{
				throw new DateHandlerException("É necessário informar ao menos "
						+ "o caminho do arquivo e as configurações básicas do modelo "
						+ "(workload, espaço da janela e objetivo)");				
			}
		}

		
		
	}
	
}
