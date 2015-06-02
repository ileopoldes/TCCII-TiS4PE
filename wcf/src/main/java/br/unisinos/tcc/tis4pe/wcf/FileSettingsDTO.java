package br.unisinos.tcc.tis4pe.wcf;

import br.unisinos.tcc.tis4pe.wcf.exceptions.DateHandlerException;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler.Builder;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class FileSettingsDTO {
	private final String pathFile;
	private final String fileLineDelimiter;
	private final String[] stringsToReplace;
	private final int beginUtilTextInLineINDEX;
	private final int endUtilTextInLineINDEX;
	private final String datePatternFormat;
	private final String regexPattern;
	private final InputWindowSpaceEnum inputWindowSpaceEnum;
	
	private FileSettingsDTO(Builder builder){
		this.pathFile = builder.pathFile;
		this.fileLineDelimiter = builder.fileLineDelimiter;
		this.stringsToReplace = builder.stringsToReplace;
		this.beginUtilTextInLineINDEX = builder.beginUtilTextInLineINDEX;
		this.endUtilTextInLineINDEX = builder.endUtilTextInLineINDEX;		
		this.datePatternFormat = builder.datePatternFormat;
		this.regexPattern = builder.regexPattern;
		this.inputWindowSpaceEnum = builder.inputWindowSpaceEnum;
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
	
	public InputWindowSpaceEnum getInputWindowSpace() {
		return inputWindowSpaceEnum;
	}
	
	public static class Builder{
		private String pathFile;
		private String fileLineDelimiter;
		private String[] stringsToReplace; 
		private int beginUtilTextInLineINDEX; 
		private int endUtilTextInLineINDEX;
		private String datePatternFormat; 
		private String regexPattern; 
		private InputWindowSpaceEnum inputWindowSpaceEnum;
		
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
		
		public Builder setBeginIndex(int index){
			this.beginUtilTextInLineINDEX = index;
			return this;
		}
		
		public Builder setEndIndex(int index){
			this.endUtilTextInLineINDEX = index;
			return this;
		}
		
		public Builder setIws(InputWindowSpaceEnum iws){
			this.inputWindowSpaceEnum = iws;
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
			
			if( settings.getInputWindowSpace() != null
					&& settings.getPathFile() != null){
				return settings;
			}else{
				throw new DateHandlerException("É necessário informar ao menos "
						+ "o caminho do arquivo e o tamanho da janela");				
			}
		}

		
		
	}
	
}
