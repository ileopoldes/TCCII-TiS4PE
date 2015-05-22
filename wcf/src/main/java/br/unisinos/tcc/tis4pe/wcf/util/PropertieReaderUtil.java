package br.unisinos.tcc.tis4pe.wcf.util;

import java.util.ResourceBundle;

public class PropertieReaderUtil {

	public static String getDefaultDateStringPattern() {
		return getProp().getString("setting.default.datestring.pattern");
	}
	
	public static String getDefaultBeginIndexForDateString(){
		return getProp().getString("setting.default.beginIndex.DateString");
	}
	
	public static String getDefaultEndIndexForDateString(){
		return getProp().getString("setting.default.endIndex.DateString");
	}
	
	private static ResourceBundle getProp() {
		ResourceBundle rb = ResourceBundle.getBundle("br.unisinos.tcc.tis4pe.wcf.util.settings");
		return rb;
	}
}
