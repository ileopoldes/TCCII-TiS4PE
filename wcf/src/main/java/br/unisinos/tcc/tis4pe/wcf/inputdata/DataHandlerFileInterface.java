package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.Map;

import org.joda.time.DateTime;

public interface DataHandlerFileInterface extends DataHandler {
	public Map<DateTime, Integer> getOriginalTimeSerie();
	public Map<DateTime, Integer> getOriginalTimeSerieUsingAllData();
}