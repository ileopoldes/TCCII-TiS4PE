package br.unisinos.tcc.tis4pe.wcf.util;

public enum CredentialKeysEnum {
	ACCESS_KEY_ID("aws_access_key_id"),
	SECRET_KEY_ID("aws_secret_access_key"),
	INSTANCE_ID("aws_instance_id"),
	ENDPOINT_ADDRESS("aws_endpoint_address"),
	ENDPOINT_EC2_ADDRESS("aws_endpoint_address_ec2"),
	FILE_CREDENTIALS_PATH("setting.default.credential.file.path");
	
	private String value;
	
	private CredentialKeysEnum(String value){
		this.value = value;
	}
	
	public String getValue(){
		return value;
	}
}
