package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

//import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.AWSCredentials;

import java.util.ArrayList;
import java.util.List;

import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CredentialsForAWS;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.InstanceStateChange;
import com.amazonaws.services.ec2.model.RunInstancesRequest;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesRequest;
import com.amazonaws.services.ec2.model.StartInstancesResult;

public class EC2Instance {
	private boolean status;
	private String instanceID;
	private CredentialsForAWS credentials;

	protected EC2Instance(CredentialsForAWS credentials, String id) {
		this.credentials = credentials;
		this.instanceID = id;
	}

	private boolean checkStatus() {
		// TODO verificar status
		return false;
	}

	public boolean isStatus() {
		return this.status;
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
		/*
		 * StartInstancesRequest startRequest = new StartInstancesRequest()
		 * .withInstanceIds(this.instanceID); StartInstancesResult startResult =
		 * this.ec2.startInstances(startRequest); List<InstanceStateChange>
		 * stateChangeList = startResult .getStartingInstances();
		 * 
		 * //debug for(InstanceStateChange inst : stateChangeList){
		 * System.out.println(">> Current State: " + inst.getCurrentState()); }
		 */

		/*
		 * StartInstancesRequest request = new StartInstancesRequest();
		 * request.setRequestCredentials(this.createAWSCredentials());
		 * List<String> id = new ArrayList<String>(); id.add(this.instanceID);
		 * request.setInstanceIds(id); this.ec2.startInstances(request);
		 */
	}

	private String startInstance(final String instanceId, AmazonEC2 ec2)
			throws AmazonServiceException, AmazonClientException,
			InterruptedException {

		StartInstancesRequest startRequest = new StartInstancesRequest()
				.withInstanceIds(instanceId);
		StartInstancesResult startResult = ec2.startInstances(startRequest);
		List<InstanceStateChange> stateChangeList = startResult
				.getStartingInstances();
		// buildLogger.addBuildLogEntry("Starting instance '" + instanceId +
		// "':");

		return "";
		// Wait for the instance to be started
		// return waitForTransitionCompletion(stateChangeList, "running", ec2,
		// instanceId, buildLogger);
	}

	public void turnOFF() {

	}

}
