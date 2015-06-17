package br.unisinos.tcc.tis4pe.wcf.util;

public enum CredentialKeysEnum {
	ACCESS_KEY_ID("aws_access_key_id"),
	SECRET_KEY_ID("aws_secret_access_key"),
	INSTANCE_ID("aws_instance_id"),
	ENDPOINT_ADDRESS("aws_endpoint_address"),
	FILE_CREDENTIALS_PATH("/home/ileopoldes/.aws/credentials");
	
	private String value;
	
	private CredentialKeysEnum(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
