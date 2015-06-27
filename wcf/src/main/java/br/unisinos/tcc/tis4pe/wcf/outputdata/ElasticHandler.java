package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.util.Iterator;

import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

public class ElasticHandler {

	private final int workload;
	private final Measurer measurer;

	public ElasticHandler(int workloadCapacity) {
		this.workload = workloadCapacity;
		this.measurer = new Measurer();
	}

	public void executeElasticAction(DataSet observations) {
		Iterator it = observations.iterator();
		while (it.hasNext()) {
			DataPoint dp = (DataPoint) it.next();
			double forecastValue = dp.getDependentValue();
			
			System.out.println(dp);
			if( forecastValue >= this.workload){
			}

		}

	}
	// Pega a lista compara com o tamanho do workload
	// Se vai passar, então envia sinal para iniciar uma nova máquina
	// Se não passa, verifica status das máquinas, se está abaixo e com duas
	// máquinas
	// então para uma
}
