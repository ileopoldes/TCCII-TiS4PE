package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.util.Iterator;

import br.unisinos.tcc.tis4pe.wcf.outputdata.aws.AWSEC2InstanceController;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;
import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

public class ElasticHandler {

	private final int workload;
	private final int marginOfError;
	private final AWSEC2InstanceController ec2Ctrl;

	public ElasticHandler(int workloadCapacity) {
		this.workload = workloadCapacity;
		this.marginOfError = PropertieReaderUtil.getMarginOfErrorForWorkload();
		this.ec2Ctrl = new AWSEC2InstanceController();
	}

	public void executeElasticAction(DataSet observations) {
		int count = 0;
		Iterator it = observations.iterator();
		while (it.hasNext()) {
			DataPoint dp = (DataPoint) it.next();
			double forecastValue = dp.getDependentValue();
			
			if( forecastValue >= this.workload) count++;
			
			if(count >= this.marginOfError){
				//this.ec2Ctrl will do something;
			}
		}

	}
	// Pega a lista compara com o tamanho do workload
	// Se vai passar, então envia sinal para iniciar uma nova máquina
	// Se não passa, verifica status das máquinas, se está abaixo e com duas
	// máquinas
	// então para uma
}
