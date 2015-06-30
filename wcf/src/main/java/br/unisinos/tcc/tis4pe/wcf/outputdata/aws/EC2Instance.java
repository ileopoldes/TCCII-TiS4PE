package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CredentialsForAWS;

import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StopInstancesRequest;

public class EC2Instance {
	private String instanceID;
	private CredentialsForAWS credentials;

	protected EC2Instance(CredentialsForAWS credentials, String id) {
		this.credentials = credentials;
		this.instanceID = id;
	}

	public String getInstanceID() {
		return this.instanceID;
	}

	public RunInstancesRequest getInstanceRunRequest() {

		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId("ami-5d80bb6d")
				.withInstanceType("t2.micro")
				.withMinCount(1)
				.withMaxCount(1)
				.withKeyName("aws");
		return runInstancesRequest;
	}

	public StopInstancesRequest getInstanceStopRequest() {
		 return new StopInstancesRequest().withInstanceIds(this.instanceID);
	}
	
	public void setInstanceID(String name){
		this.instanceID = name;
	}

}
