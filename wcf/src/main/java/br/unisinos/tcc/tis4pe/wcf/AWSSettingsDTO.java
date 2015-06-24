package br.unisinos.tcc.tis4pe.wcf;

import br.unisinos.tcc.tis4pe.wcf.exceptions.DateHandlerException;
import br.unisinos.tcc.tis4pe.wcf.util.PropertieReaderUtil;

public class AWSSettingsDTO extends Settings{
	
	private AWSSettingsDTO(Builder builder){
		super(builder.workloadCapacity, 
				builder.inputWindowSpaceEnum,
				builder.objective);
	}

	
	public static class Builder{
		//default settings
		private ObjectiveEnum objective;
		private InputWindowSpaceEnum inputWindowSpaceEnum;
		private int workloadCapacity;
		
		public Builder(){
			this.workloadCapacity = Integer.valueOf(
					PropertieReaderUtil.getWorkloadSize()
					);
		}
		
		public Builder setInputWindowSpace(InputWindowSpaceEnum iws){
			this.inputWindowSpaceEnum = iws;
			return this;
		}
		
		public Builder setObjective(ObjectiveEnum objective){
			this.objective = objective;
			return this;
		}
		
		public Builder setWorkloadCapacity(int workload){
			this.workloadCapacity = workload;
			return this;
		}
		
		public AWSSettingsDTO build(){
			return validateDataHandlerObject(
					new AWSSettingsDTO(this)
					);
		}
		
		private AWSSettingsDTO validateDataHandlerObject(AWSSettingsDTO settings) 
				throws DateHandlerException{
			
			if( settings.isTheDataValid() ){
				return settings;
			}else{
				throw new DateHandlerException("É necessário informar ao menos "
						+ "as configurações básicas do modelo "
						+ "(workload, espaço da janela e objetivo)");				
			}
		}

		
		
	}
	
}
