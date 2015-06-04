package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.joda.time.DateTime;

public class FileExporter implements Exporter {

	@Override
	public void export(Map<DateTime, Integer> timeSerie) {
		FileWriter file;
		try {
			file = new FileWriter("./analisehistoricaOUT-"+ DateTime.now() + ".csv");
			//file = new FileWriter("./analisehistoricaOUT-RegressionModel.csv");
			PrintWriter writer = new PrintWriter(file);
			
			for(DateTime date : timeSerie.keySet() ){
				String line = new StringBuffer()
				.append( date.toString() )
				.append(",")
				.append( timeSerie.get(date) ).toString();
				writer.println(line);
			}
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
