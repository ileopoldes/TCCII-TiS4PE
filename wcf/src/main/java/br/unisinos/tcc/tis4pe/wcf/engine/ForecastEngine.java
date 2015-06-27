/*
 * Motor de forecast, recebe a série temporal e executa o forecast
 */
package br.unisinos.tcc.tis4pe.wcf.engine;

import java.util.Map;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;
import net.sourceforge.openforecast.DataSet;
import net.sourceforge.openforecast.EvaluationCriteria;
import net.sourceforge.openforecast.Forecaster;
import net.sourceforge.openforecast.ForecastingModel;
import net.sourceforge.openforecast.Observation;
import net.sourceforge.openforecast.models.DoubleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.MovingAverageModel;
import net.sourceforge.openforecast.models.SimpleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.TripleExponentialSmoothingModel;
import net.sourceforge.openforecast.models.WeightedMovingAverageModel;


public class ForecastEngine {
	
	private InputWindowSpaceEnum inputWindowSpace;
	private DataSet originalObservations;
	private int windowSpaceSize;
	
	public ForecastEngine(InputWindowSpaceEnum iws, int windowSpaceSize ){
		this.inputWindowSpace = iws;
		this.windowSpaceSize = windowSpaceSize;
	}
	
	public ForecastEngine(InputWindowSpaceEnum iws){
		this.inputWindowSpace = iws;
		this.windowSpaceSize = PropertieReaderUtil.getWindowSpaceSize();
	}
	
	public DataSet buildProjectionWithAutoBestFit(Map<DateTime, Integer> mapDates ) {
		//TODO encontrar uma forma de especificar o tamanho da projeção
		
		//TODO alterar a forma como isso funciona para arquivos.
		//this.windowSpaceSize = this.windowSpaceSize > 0 ? this.windowSpaceSize : mapDates.size();
		this.windowSpaceSize = mapDates.size();
		
		DataSet observations = this.makeDataSet(mapDates);
		this.originalObservations = new DataSet(observations); 
		
		System.out.println("observations: " + observations.size());

		if(mapDates.size() >= this.windowSpaceSize){
			ForecastingModel model = this.getBestFit(observations); 

			//Especificar modelo
			//observations.setPeriodsPerYear(windowSpaceSize/2);
			//ForecastingModel model = net.sourceforge.openforecast.models.TripleExponentialSmoothingModel.getBestFitModel(observations);
			//ForecastingModel model = new net.sourceforge.openforecast.models.NaiveForecastingModel();
			
			model.init(observations);								

			System.out.println("Tamanho: " + observations.size());
			System.out.println("Modelo: " + model.toString());
			//System.out.println("AIC: " + model.getAIC());
			//System.out.println("Bias: " + model.getBias());
			//System.out.println("MAD: " + model.getMAD());
			//System.out.println("MSE: " + model.getMSE());
			//System.out.println("MAPE: " + model.getMAPE());
			//System.out.println("SAE: " + model.getSAE());
			//System.out.println("Nº : " + model.getNumberOfPredictors());
			
			
			return model.forecast(observations);			
		}
		return null;
	}

	//TODO mover para o DataHandler
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
		observations.setPeriodsPerYear(windowSpaceSize);
		return Forecaster.getBestForecast(observations, EvaluationCriteria.MAPE);
	}
	
	public DataSet getOriginalObservations(){
		return this.originalObservations;
	}
}

