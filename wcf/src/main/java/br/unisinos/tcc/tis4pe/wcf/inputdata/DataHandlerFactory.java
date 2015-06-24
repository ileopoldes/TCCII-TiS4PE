package br.unisinos.tcc.tis4pe.wcf.inputdata;

import br.unisinos.tcc.tis4pe.wcf.FileSettingsDTO;
import br.unisinos.tcc.tis4pe.wcf.Settings;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;

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
			return new DataHandlerAWS( settings.getInputWindowSpace() );
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
