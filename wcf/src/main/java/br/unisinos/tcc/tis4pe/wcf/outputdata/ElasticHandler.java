package br.unisinos.tcc.tis4pe.wcf.outputdata;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import br.unisinos.tcc.tis4pe.wcf.outputdata.aws.AWSEC2InstanceController;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;
import net.sourceforge.openforecast.DataPoint;
import net.sourceforge.openforecast.DataSet;

public class ElasticHandler {

	private final int workload;
	private final int marginOfError;
	private final AWSEC2InstanceController ec2Ctrl;
	private Map<String, Boolean> ec2InstancesMap;

	public ElasticHandler(int workloadCapacity) {
		this.workload = workloadCapacity;
		this.marginOfError = PropertieReaderUtil.getMarginOfErrorForWorkload();
		this.init();
		this.ec2Ctrl = new AWSEC2InstanceController(this.ec2InstancesMap);
		this.updateEC2Status();
		
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
			if( this.getTotalVMsON() == this.ec2InstancesMap.size() ){
				System.out.println("Alert! Não há VMs disponíveis");
			}else{
				System.out.println("Turn ON VMs ...");
				this.ec2Ctrl.startVM();
			}
		}else{
			if( this.getTotalVMsON() == this.ec2InstancesMap.size() ){
				System.out.println("Turn OFF VMs ...");
				this.ec2Ctrl.stopVM();
			}
		}
	}
	
	private void init(){
		this.ec2InstancesMap = new TreeMap<String, Boolean>();
		String[] instanceIDs =  PropertieReaderUtil.getEC2Instances();
		for(String id : instanceIDs){
			this.ec2InstancesMap.put( id, false );
		}
	}
	
	private boolean checkEC2InstanceStatus(String id){
		return this.ec2Ctrl.getInstanceStatus(id);
	}
		
	private void updateEC2Status() {
		String[] instanceIDs =  PropertieReaderUtil.getEC2Instances();
		for(String id : instanceIDs){
			this.ec2InstancesMap.put( id, checkEC2InstanceStatus(id) );
		}	
	}

	
	private int getTotalVMsON(){
		int vmsON = 0;
		for( String key : this.ec2InstancesMap.keySet() ){
			if( this.ec2InstancesMap.get(key) == true ) vmsON++;
		}
		return vmsON;
	}
}
