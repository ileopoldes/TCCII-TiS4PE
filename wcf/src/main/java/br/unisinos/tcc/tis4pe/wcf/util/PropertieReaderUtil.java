package br.unisinos.tcc.tis4pe.wcf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class PropertieReaderUtil {
	
	public static PropertyResourceBundle readFileCredentials() {
		String credentials = (CredentialKeysEnum.FILE_CREDENTIALS_PATH).getValue();
		FileInputStream fis;
		PropertyResourceBundle prop = null;
		try {
			fis = new FileInputStream(credentials);
			prop = new PropertyResourceBundle(fis);
		} catch (FileNotFoundException e) {
			System.out.println("::: O arquivo com as credenciais não foi encontrado :::");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("::: Erro ao ler arquivo de credenciais :::");
			e.printStackTrace();
		}
		return prop;
	}

	public static String getDefaultDateStringPattern() {
		return getProp().getString("setting.default.datestring.pattern");
	}
	
	public static String getDefaultBeginIndexForDateString(){
		return getProp().getString("setting.default.beginIndex.DateString");
	}
	
	public static String getDefaultEndIndexForDateString(){
		return getProp().getString("setting.default.endIndex.DateString");
	}
	
	public static String[] getDefaultStringsToReplace(){
		String str = getProp().getString("setting.default.stringsToReplace");
		String[] strToReplace = StringUtils.split(str, ",");
		return strToReplace; 
	}
	
	public static String getDefaultFileDelimiter(){
		return getProp().getString("setting.default.fileDelimiter");
	}
	
	public static String getDefaultRegexPattern(){
		return getProp().getString("setting.default.regexPattern");
	}
	
	private static ResourceBundle getProp() {
		ResourceBundle rb = ResourceBundle.getBundle("br.unisinos.tcc.tis4pe.wcf.util.settings");
		return rb;
	}
}
