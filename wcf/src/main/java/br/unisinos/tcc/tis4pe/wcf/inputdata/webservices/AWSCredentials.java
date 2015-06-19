package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.util.PropertyResourceBundle;

import br.unisinos.tcc.tis4pe.wcf.util.CredentialKeysEnum;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class AWSCredentials {

	private final String awsAccessKey;
	private final String awsSecretKey;
	private final String instanceId;
	private final String endPoint;

    private AWSCredentials(PropertyResourceBundle prop){
		this.awsAccessKey = prop.getString(
				(CredentialKeysEnum.ACCESS_KEY_ID).getValue()
		);
        this.awsSecretKey = prop.getString(
        		(CredentialKeysEnum.SECRET_KEY_ID).getValue()
        );
        this.instanceId = prop.getString(
        		(CredentialKeysEnum.INSTANCE_ID).getValue()
        );
        this.endPoint = prop.getString(
        		(CredentialKeysEnum.ENDPOINT_ADDRESS).getValue()
        );
    }
    
    public static AWSCredentials buildCredentials(){
    	PropertyResourceBundle prop = readFileCredentials();
    	AWSCredentials cred = new AWSCredentials(prop);
    	return cred;
    }

	private static PropertyResourceBundle readFileCredentials() {
		return PropertieReaderUtil.readFileCredentials();
	}

	public String getAwsAccessKey() {
		return awsAccessKey;
	}

	public String getAwsSecretKey() {
		return awsSecretKey;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public String getEndPoint() {
		return endPoint;
	}
	
}
