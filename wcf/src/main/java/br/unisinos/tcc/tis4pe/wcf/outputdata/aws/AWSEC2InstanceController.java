package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesResult;

import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CredentialsForAWS;

public class AWSEC2InstanceController {
	private CredentialsForAWS credentials;
	private AmazonEC2 ec2;
	private Map<String, EC2Instance> mapEC2Instances;

	public AWSEC2InstanceController(Map<String, Boolean> ec2InstancesMap) {
		this.credentials = CredentialsForAWS.buildCredentials();
		this.initEC2();
		this.initEC2Instances(ec2InstancesMap.keySet());
	}
	
	private void initEC2(){
		this.ec2 = new AmazonEC2Client(this.createAWSCredentials());
		this.ec2.setEndpoint(credentials.getEndPointEC2());		
	}

	private AWSCredentials createAWSCredentials() {
		return new AWSCredentials() {
			@Override
			public String getAWSSecretKey() {
				return credentials.getAwsSecretKey();
			}

			@Override
			public String getAWSAccessKeyId() {
				return credentials.getAwsAccessKey();
			}
		};

	}

	private void initEC2Instances(Set<String> keySet) {
		this.mapEC2Instances = new TreeMap<String, EC2Instance>();
		for (String id : keySet) {
			this.mapEC2Instances.put(id, this.startInstance(id) );
		}
	}
	
	private EC2Instance startInstance(String id){
		EC2Instance instance = null;
		if(this.mapEC2Instances.containsKey(id)){
			instance = this.mapEC2Instances.get(id);
		}else{
			instance = new EC2Instance(this.credentials, id);
			RunInstancesResult runInstancesResult = 
					this.ec2.runInstances( instance.getInstanceRequest());
			System.out.println(runInstancesResult.toString());
		}
		return instance;
	}

	public boolean getInstanceStatus(String id) {
		// TODO Auto-generated method stub
		return false;
	}

	public void startVM() {
		//TODO escolher vm que está inativa e fazer iniciar
		EC2Instance instance = this.startInstance("i-4da188ba");
		System.out.println(instance.toString());
	}

	public void stopVM() {
		// TODO Auto-generated method stub

	}

}
