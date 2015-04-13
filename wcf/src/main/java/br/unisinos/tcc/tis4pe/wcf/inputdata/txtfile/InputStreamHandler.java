package br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile;

import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;

public abstract class InputStreamHandler implements StreamHandlerInterface{
	protected String regexPattern;
	protected String pathData;

	public InputStreamHandler(String regexPattern, String pathOfData) {
		this.regexPattern = regexPattern;
		this.pathData = pathOfData;
	}
}
