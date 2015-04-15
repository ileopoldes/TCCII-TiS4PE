package br.unisinos.tcc.tis4pe.wcf;

public enum InputWindowSpaceEnum {
	SECONDS(1), MINUTES(2), HOURS(3), DAYS(4);

	private final int value;
	
	private InputWindowSpaceEnum(int value){
		this.value = value;
	}
	
	int getValue(){
		return this.value;
	}
}
