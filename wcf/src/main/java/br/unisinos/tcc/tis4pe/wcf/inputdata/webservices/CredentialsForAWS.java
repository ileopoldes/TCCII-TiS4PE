package br.unisinos.tcc.tis4pe.wcf.inputdata.webservices;

import java.util.PropertyResourceBundle;

import br.unisinos.tcc.tis4pe.wcf.util.CredentialKeysEnum;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class CredentialsForAWS {

	private final String awsAccessKey;
	private final String awsSecretKey;
	private final String instanceId;
	private final String endPoint;
	private final String endPointEC2;

    private CredentialsForAWS(PropertyResourceBundle prop){
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
        this.endPointEC2 = prop.getString(
        		(CredentialKeysEnum.ENDPOINT_EC2_ADDRESS).getValue()
        );
    }
    
    public static CredentialsForAWS buildCredentials(){
    	PropertyResourceBundle prop = readFileCredentials();
    	CredentialsForAWS cred = new CredentialsForAWS(prop);
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
	
	public String getEndPointEC2(){
		return endPointEC2;
	}
	
}
