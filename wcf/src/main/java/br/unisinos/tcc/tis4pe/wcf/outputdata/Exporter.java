package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.io.IOException;
import java.util.Map;

import org.joda.time.DateTime;

public interface Exporter {
	public void export(Map<DateTime, Integer> timeSerie);
	public void export(Map<DateTime, Integer> timeSerieResult,
			String sufixFileName);
}
