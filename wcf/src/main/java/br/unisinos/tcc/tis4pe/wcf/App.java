package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;
import net.sourceforge.openforecast.DataPoint;

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
		InputWindowSpaceEnum inputWindowSpace = InputWindowSpaceEnum.HOURS;
		int workload = 100;

		FileSettingsDTO settings = new FileSettingsDTO.Builder()
				.setRegexPattern(regexPattern).setPathFile(pathFile)
				.setFileLineDelimiter(fileLineDelimiter)
				.setWorkloadCapacity(workload)
				.setObjective(ObjectiveEnum.ANALISE_HISTORICA)
				.setInputWindowSpace(inputWindowSpace).build();

		// Motor
		System.out.println(">> TS-begin: " + DateTime.now());
		Controller controller = new Controller();
		controller.timeSeriesForecastingFromTextFile(settings);
		System.out.println(">> TS-end: " + DateTime.now());

		// Saída
		Map<DateTime, Integer> timeSerie = controller.getResultTimeSeries();
		for( DateTime key : timeSerie.keySet() ){
			System.out.println("> " + key 
					+ " - " + timeSerie.get(key) );
		}
		
		controller.exportTimeSerie();
		System.out.println(":::");
		/*
		Map<DateTime, Integer> tsOriginal = controller.getOriginalTimeSerie();
		Set<DateTime> listDates = tsOriginal.keySet();
		//System.out.println("Tamanho original: " + tsOriginal.size());		//9959
		//System.out.println("Forecast: " + controller.getForecast().size());	//9959
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
		}
		*/
		
	}
}
