package br.unisinos.tcc.tis4pe.wcf.inputdata;

import br.unisinos.tcc.tis4pe.wcf.AWSSettingsDTO;
import br.unisinos.tcc.tis4pe.wcf.FileSettingsDTO;
import br.unisinos.tcc.tis4pe.wcf.Settings;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.webservices.BufferCloudWatch;

public abstract class DataHandlerFactory {

	public static DataHandler getInstance(Settings settings) {
		if (settings instanceof FileSettingsDTO) {
			return new DataHandlerFile.Builder()
					.setInputFileHandler(
							prepareStreamHandler((FileSettingsDTO) settings))
					.setIws(settings.getInputWindowSpace())
					.setInputSizePercentage(settings.getInputSizePercentage())
					.build();
		} else {
			return new DataHandlerAWS( (AWSSettingsDTO) settings );
		}
	}
	
	private static StreamHandlerInterface prepareStreamHandler(FileSettingsDTO settings){
		return new FileInputStreamHandler(
				settings.getRegexPattern(),
				settings.getPathFile(), 
				settings.getFileLineDelimiter() 
				);
	}
}
