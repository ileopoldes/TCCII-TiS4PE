package br.unisinos.tcc.tis4pe.wcf;

public abstract class Settings {
	private final int workloadCapacity;
	private final InputWindowSpaceEnum inputWindowSpace;
	private final ObjectiveEnum objective;
	private final float inputSizePercentage;
	
	
	public Settings(int workloadCapacity, 
			InputWindowSpaceEnum inputWindowSpaceEnum,
			ObjectiveEnum objective,
			float inputSizePercentage){
		this.workloadCapacity = workloadCapacity;
		this.inputWindowSpace = inputWindowSpaceEnum;
		this.objective = objective;
		this.inputSizePercentage = (inputSizePercentage > 0.0f ?
						1.0f : inputSizePercentage);
	}
	
	protected boolean isTheDataValid(){
		if(this.workloadCapacity > 0
				&& this.inputWindowSpace != null
				&& this.objective != null){
			if(this.objective == ObjectiveEnum.ANALISE_HISTORICA){
				return true;							
			}else{
				if(this.workloadCapacity > 0) return true;
			}
		}
		return false;
	}

	public int getWorkloadCapacity() {
		return workloadCapacity;
	}

	public InputWindowSpaceEnum getInputWindowSpace() {
		return inputWindowSpace;
	}

	public ObjectiveEnum getObjective() {
		return objective;
	}

	public float getInputSizePercentage() {
		return inputSizePercentage;
	}	
	

}
