package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import br.unisinos.tcc.tis4pe.wcf.outputdata.aws.AWSEC2InstanceController;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;
import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

public class ElasticHandler {

	private final int workload;
	private final int marginOfError;
	private final int limitVMs;
	private final AWSEC2InstanceController ec2Ctrl;
	private List<String> ec2InstancesMap;

	public ElasticHandler(int workloadCapacity) {
		this.workload = workloadCapacity;
		this.limitVMs = PropertieReaderUtil.getNumberOfInstances();
		this.marginOfError = PropertieReaderUtil.getMarginOfErrorForWorkload();
		this.ec2InstancesMap = new ArrayList<String>();
		this.ec2Ctrl = new AWSEC2InstanceController();		
	}

	public void executeElasticAction(DataSet observations) {
		int qtdAboveMarginOfError = 0;
		Iterator<DataPoint> it = observations.iterator();
		while (it.hasNext()) {
			DataPoint dp = it.next();
			double forecastValue = dp.getDependentValue();
			if( forecastValue >= this.workload) qtdAboveMarginOfError++;
			this.turnVMsOnOff(qtdAboveMarginOfError);			
		}
	}
	
	private void turnVMsOnOff(int qtdAboveMarginOfError){		
		if(qtdAboveMarginOfError >= this.marginOfError){
			if( this.getTotalVMsON() == this.limitVMs ){
				System.out.println("[Alert!] Limite de VMs configuradas excedido: " 
						+ PropertieReaderUtil.getNumberOfInstances());
			}else{
				System.out.println("[ElasticHandler] Iniciar VM ...");
				this.ec2InstancesMap.add( this.ec2Ctrl.startVM() );
			}
		}else{
			if( this.getTotalVMsON() > 0 ){
				System.out.println("[ElasticHandler] Desligando VM ...");
				this.ec2InstancesMap.remove( this.ec2Ctrl.stopVM(this.ec2InstancesMap.get(0))  );
			}else{
				//System.out.println("Carga de trabalho abaixo do limite");
			}
		}
	}
	
	private int getTotalVMsON(){
		return this.ec2InstancesMap.size();
	}
}
