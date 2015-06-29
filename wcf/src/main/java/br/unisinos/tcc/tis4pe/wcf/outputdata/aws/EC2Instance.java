package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CredentialsForAWS;

import com.amazonaws.services.ec2.model.RunInstancesRequest;

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

	public RunInstancesRequest getInstanceRequest() {

		RunInstancesRequest runInstancesRequest = new RunInstancesRequest();
		runInstancesRequest.withImageId("ami-5d80bb6d")
				.withInstanceType("t2.micro")
				.withMinCount(1)
				.withMaxCount(1)
				.withKeyName("aws");

		return runInstancesRequest;
	}

	public void turnOFF() {

	}

}
