package br.unisinos.tcc.tis4pe.wcf.util;

import java.util.ResourceBundle;

public class PropertieReaderUtil {

	public static String getDefaultDateStringPattern() {
		return getProp().getString("setting.default.datestring.pattern");
	}
	
	private static ResourceBundle getProp() {
		ResourceBundle rb = ResourceBundle.getBundle("br.unisinos.tcc.tis4pe.wcf.util.settings");
		return rb;
	}
}
