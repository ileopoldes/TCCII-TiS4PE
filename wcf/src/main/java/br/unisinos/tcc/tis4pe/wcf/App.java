package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import br.unisinos.tcc.tis4pe.wcf.engine.ForecastEngine;
import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;

/**
 * Hello world!
 *
 */
public class App {
	public static void main(String[] args) throws IOException {
		System.out.println("TiS4PE!\n\n");
		String pattern = "\\[.*\\]+";
		 String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
		//String pathFile = "/home/ileopoldes/tmp/ClarkNetHEAD.txt";
		String fileDelimiter = "\n";

		StreamHandlerInterface in = new FileInputStreamHandler(pattern,
				pathFile, fileDelimiter);
		
		InputWindowSpaceEnum input = InputWindowSpaceEnum.MINUTES;
		DataHandler dataHandler = new DataHandler(in, input);
		dataHandler.extractData();
		// dataHandler.teste();

		ForecastEngine engine = new ForecastEngine(input);
		DataSet fcDataSet = engine.buildProjectionWithAutoBestFit(dataHandler
				.getOriginalTimeSerie());

		Iterator it = fcDataSet.iterator();
		while (it.hasNext()) {
			DataPoint dp = (DataPoint) it.next();
			double forecastValue = dp.getDependentValue();

			// Do something with the forecast value, e.g.
			System.out.println(" - " + dp);
		}
	}
}
