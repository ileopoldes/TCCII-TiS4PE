package br.unisinos.tcc.tis4pe.wcf.outputdata.aws;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.ec2.AmazonEC2;
import com.amazonaws.services.ec2.AmazonEC2Client;
import com.amazonaws.services.ec2.model.RunInstancesResult;

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
				.getInstanceRequest());
		System.out.println(runInstancesResult.toString());
		return instance;
	}

	public String startVM() {
		EC2Instance instance = this.startInstance();
		this.mapEC2Instances.put(instance.getInstanceID(), instance);
		System.out.println(instance.toString());

		return instance.getInstanceID();
	}

	public String stopVM() {
		// TODO Auto-generated method stub
		return "nome da isntancia a ser parada";
	}

}
