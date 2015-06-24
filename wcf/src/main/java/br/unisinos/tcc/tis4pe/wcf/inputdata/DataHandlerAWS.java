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

public class DataHandlerAWS implements DataHandler{

	private final InputWindowSpaceEnum iws;
	private Map<DateTime, Integer> originalTimeSerie;
	private Map<DateTime, Integer> originalTimeSerieUsingAllData;

	//TODO adicionar dados para criação das séries (kpi)
	
	protected DataHandlerAWS(InputWindowSpaceEnum iws){
		this.iws = iws;
	}
	
	@Override
	public void extractData(){		
	}

	
	private DateTime makeDate(String str) {
		return DateUtil.dateFromString(str);
	}
	
	private void makeTimeSerie(List<DateTime> dateList) {
		this.originalTimeSerieUsingAllData = TimeSeriesFormatter.format(dateList, this.iws);
		this.originalTimeSerie = this.originalTimeSerieUsingAllData;			
	}

	@Override
	public Map<DateTime, Integer> getOriginalTimeSerie() {
		return originalTimeSerie;
	}

	@Override
	public Map<DateTime, Integer> getOriginalTimeSerieUsingAllData() {
		return originalTimeSerieUsingAllData;
	}

	public InputWindowSpaceEnum getIws() {
		return iws;
	}	
}
