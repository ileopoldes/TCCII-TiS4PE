package br.unisinos.tcc.tis4pe.wcf.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import org.apache.commons.lang3.StringUtils;

public class PropertieReaderUtil {
	
	public static PropertyResourceBundle readFileCredentials() {
		String credentials = getFileCredentialsPath();
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
	
	private static String getFileCredentialsPath(){
		return getProp().getString(
				(CredentialKeysEnum.FILE_CREDENTIALS_PATH).getValue()
		);
	}
	
	public static int getWindowSpaceSize(){
		return Integer.parseInt( getProp().getString("setting.default.windowSpaceSize") );
	}
	
	public static int getProducerID(){
		return Integer.parseInt( getProp().getString("setting.default.producerID") );
	}
	
	public static int getConsumerID(){
		return Integer.parseInt( getProp().getString("setting.default.consumerID") );
	}
	
	public static long getSleepTime(){
		return Long.parseLong(
				getProp().getString("setting.default.sleepTimeThread")
		);
	}
	
	public static String getWorkloadSize(){
		return getProp().getString("setting.default.cloudwatch.workloadSize");
	}
	
	public static String getAmountOfSecondsAgoForCloudWatch(){
		return getProp().getString("setting.default.cloudwatch.amountOfSecondsAgo");
	}
	
	public static String getAmountOfHoursAgoForCloudWatch(){
		return getProp().getString("setting.default.cloudwatch.amountOfHoursAgo");
	}
	
	public static String getAmountOfHoursOfThePeriod(){
		return getProp().getString("setting.default.cloudwatch.amountOfHoursOfThePeriod");
	}
	
	public static String getAmountOfSecondsOfThePeriod(){
		return getProp().getString("setting.default.cloudwatch.amountOfSecondsOfThePeriod");
	}
	
	public static String getCloudWatchNameSpace(){
		return getProp().getString("setting.default.cloudwatch.NameSpace");
	}
	
	public static String getCloudWatchDimension(){
		return getProp().getString("setting.default.cloudwatch.Dimension");
	}
	
	public static String getCloudWatchMetric(){
		return getProp().getString("setting.default.cloudwatch.Metric");
	}
	
	public static String[] getCloudWatchStatistics(){
		return (getProp().getString("setting.default.cloudwatch.Statistic")).split(",");
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
