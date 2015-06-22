package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.joda.time.DateTime;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;

import net.sourceforge.openforecast.DataPoint;

/**
 * Hello world!
 *
 */
public class App {
	

	public static void main(String[] args) throws IOException {
		System.out.println("TiS4PE!\n\n");
		
		ObjectiveEnum objective = ObjectiveEnum.ANALISE_HISTORICA;

		if( objective.equals(ObjectiveEnum.ANALISE_TEMPO_EXECUCAO) ){
			System.out.println("::: ANALISE EM TEMPO DE EXECUCAO :::");
			Controller controller = new Controller();
			controller.timeSeriesForecastingFromWebservice();			
		}else{
			System.out.println("::: ANALISE HISTORICA :::");
			// Dados de entrada
			String regexPattern = "\\[.*\\]+";
			String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
			// String pathFile = "/home/ileopoldes/tmp/ClarkNetHEAD.txt";
			String fileLineDelimiter = "\n";
			InputWindowSpaceEnum inputWindowSpace = InputWindowSpaceEnum.MINUTES;
			int workload = 100;
			
			FileSettingsDTO settings = new FileSettingsDTO.Builder()
			.setRegexPattern(regexPattern).setPathFile(pathFile)
			.setFileLineDelimiter(fileLineDelimiter)
			.setWorkloadCapacity(workload)
			.setObjective(objective)
			.setInputSizePercentage(0.1f)
			.setInputWindowSpace(inputWindowSpace).build();
			
			// Motor
			System.out.println(">> TS-begin: " + DateTime.now());
			Controller controller = new Controller();
			controller.timeSeriesForecastingFromTextFile(settings);
			System.out.println(">> TS-end: " + DateTime.now());
			
			// Saída
			/*Map<DateTime, Integer> timeSerie = controller.getResultTimeSeries();
		for( DateTime key : timeSerie.keySet() ){
			System.out.println("> " + key 
					+ " - " + timeSerie.get(key) );
		}*/
			//controller.exportOriginalTimeSerie();
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
}
