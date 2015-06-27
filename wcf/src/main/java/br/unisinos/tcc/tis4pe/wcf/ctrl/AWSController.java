package br.unisinos.tcc.tis4pe.wcf.ctrl;

import br.unisinos.tcc.tis4pe.wcf.Settings;
import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerAWS;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandlerFactory;

//TODO substituir controles das threds por semáforos e melhorar separação de responsabilidades
public class AWSController extends Thread {
	private DataHandlerAWS dataHandlerAWS;
	private Settings settings;
	private ForecastEngine engine;

	public AWSController(Settings settings, ForecastEngine engine) {
		this.settings = settings;
		this.engine = engine;
	}

	@Override
	public void run() {
		this.timeSeriesForcast();
	}

	private void timeSeriesForcast() {
		this.dataHandlerAWS = (DataHandlerAWS) DataHandlerFactory
				.getInstance(settings);
		this.dataHandlerAWS.setEngine(engine);
		this.dataHandlerAWS.start();
	}

}
