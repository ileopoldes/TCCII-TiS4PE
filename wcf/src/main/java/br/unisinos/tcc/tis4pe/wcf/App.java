package br.unisinos.tcc.tis4pe.wcf;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;
import br.unisinos.tcc.tis4pe.wcf.dto.FileSettingsDTO;
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

		// Dados de entrada
		String regexPattern = "\\[.*\\]+";
		String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
		// String pathFile = "/home/ileopoldes/tmp/ClarkNetHEAD.txt";
		String fileLineDelimiter = "\n";
		InputWindowSpaceEnum inputWindowSpace = InputWindowSpaceEnum.MINUTES;

		FileSettingsDTO settings = new FileSettingsDTO.Builder()
				.setRegexPattern(regexPattern).setPathFile(pathFile)
				.setFileLineDelimiter(fileLineDelimiter)
				.setIws(inputWindowSpace).build();

		// Motor
		Controller controller = new Controller();
		controller.timeSeriesForecastingFromTextFile(settings);

		
		// Saída
		Map<DateTime, Integer> tsOriginal = controller.getOriginalTimeSerie();
		Set<DateTime> listDates = tsOriginal.keySet();
		System.out.println("Tamanho original: " + tsOriginal.size());		//9959
		System.out.println("Forecast: " + controller.getForecast().size());	//9959
		
		Iterator itDates = listDates.iterator();
		Iterator it = controller.getForecast().iterator();
		
		while (it.hasNext() && itDates.hasNext()) {
			DataPoint dp = (DataPoint) it.next();
			int forecastValue = (int) dp.getDependentValue();
			
			DateTime dt = (DateTime) itDates.next();

			System.out.println(dp.getDependentValue() 
					+ " - " + ((int)tsOriginal.get(dt))
					+ " - " + dt
					);
			/*System.out.println("-> " + dp 
					+ " ["
					+ "vlr: " + tsOriginal.get(dt) + dt 
					+ "]"
			);
			*/
		}
		
	}
}
