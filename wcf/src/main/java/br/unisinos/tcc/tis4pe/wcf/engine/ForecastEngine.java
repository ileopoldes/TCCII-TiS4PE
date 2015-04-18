/*
 * Motor de forecast, recebe a série temporal e executa o forecast
 */
package br.unisinos.tcc.tis4pe.wcf.engine;

import java.util.Map;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;

public class ForecastEngine {
	
	private InputWindowSpaceEnum inputWindowSpace;
	private int windowSpaceSize;
	
	public ForecastEngine(InputWindowSpaceEnum iws, int windowSpaceSize ){
		this.inputWindowSpace = iws;
		this.windowSpaceSize = windowSpaceSize;
	}
	
	public ForecastEngine(InputWindowSpaceEnum iws){
		this.inputWindowSpace = iws;
	}
	
	public DataSet buildProjectionWithAutoBestFit(Map<DateTime, Integer> mapDates, int projectionSize ){
		DataSet observations = this.makeDataSet(mapDates);
		this.windowSpaceSize = this.windowSpaceSize > 0 ? this.windowSpaceSize : observations.size();
		
		ForecastingModel model = this.getBestFit(observations); //TODO armazenar nome do modelo empregado
		model.init(observations);
		return model.forecast(observations);
	}

	private DataSet makeDataSet(Map<DateTime, Integer> mapDates) {
		DataSet dataSet = new DataSet();
		int sequence = 0;
		for( DateTime key : mapDates.keySet() ){
			 Observation observation = new Observation( mapDates.get(key) );
			 observation.setIndependentValue(inputWindowSpace.name(), ++sequence);
			 dataSet.add(observation);
			 
			 if(this.windowSpaceSize == sequence) break;
		}
		return dataSet;
	}
	
	//TODO criar opção para informar diretamente o modelo a ser usado e informando critérios de avaliação
	private ForecastingModel getBestFit(DataSet observations) {
		return Forecaster.getBestForecast(observations);
	}
}
