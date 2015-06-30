package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesResult;
import com.amazonaws.services.ec2.model.StartInstancesResult;
import com.amazonaws.services.ec2.model.StopInstancesResult;

import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.CredentialsForAWS;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class AWSEC2InstanceController {
	private CredentialsForAWS credentials;
	private AmazonEC2 ec2;
	private Map<String, EC2Instance> mapEC2Instances;
	private String imgAWSVM;

	public AWSEC2InstanceController() {
		this.credentials = CredentialsForAWS.buildCredentials();
		this.imgAWSVM = PropertieReaderUtil.getImgNameInstancesEC2();
		this.mapEC2Instances = new TreeMap<String, EC2Instance>();
		this.initEC2();
	}

	private void initEC2() {
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

	private EC2Instance startInstance() {
		EC2Instance instance = null;
		instance = new EC2Instance(this.credentials, this.imgAWSVM);
		RunInstancesResult runInstancesResult = this.ec2.runInstances(instance
				.getInstanceRunRequest());
		System.out.println(runInstancesResult.toString());
		instance.setInstanceID(
				runInstancesResult.getReservation().getInstances().get(0).getInstanceId());
		return instance;
	}

	private String stopInstance(String instanceID) {
		if(this.mapEC2Instances.size() > 0){
			EC2Instance ec2Instance = this.mapEC2Instances.get(instanceID);
			instanceID = ec2Instance.getInstanceID();
			StopInstancesResult stopResult = 
					ec2.stopInstances(ec2Instance.getInstanceStopRequest());
			this.mapEC2Instances.remove(instanceID);
		}
		return instanceID;
	}
	
	public String startVM() {
		EC2Instance instance = this.startInstance();
		this.mapEC2Instances.put(instance.getInstanceID(), instance);
		System.out.println("::: Iniciando instância AWS EC2: " + instance.toString());

		return instance.getInstanceID();
	}

	public String stopVM(String instanceID) {
		this.stopInstance(instanceID);
		this.mapEC2Instances.remove(instanceID);
		System.out.println("::: Desligando instância AWS EC2: " + instanceID);
		return instanceID;
	}


}
