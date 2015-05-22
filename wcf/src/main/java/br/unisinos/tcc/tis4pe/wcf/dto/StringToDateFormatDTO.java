package br.unisinos.tcc.tis4pe.wcf.dto;

public class StringToDateFormatDTO {

	private String dateStr;
	private String patternStr;
	
	public StringToDateFormatDTO(String date, String pattern){
		this.dateStr = date;
		this.patternStr = pattern;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public String getPatternStr() {
		return patternStr;
	}

	public void setPatternStr(String patternStr) {
		this.patternStr = patternStr;
	}
}
