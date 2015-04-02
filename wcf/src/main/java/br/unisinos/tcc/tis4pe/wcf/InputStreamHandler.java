/*
 * Classe que trata o fluxo de entrada ainda cru
 */
package br.unisinos.tcc.tis4pe.wcf;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputStreamHandler {

	/*
	 * lê arquivo
	 * http://ita.ee.lbl.gov/html/contrib/ClarkNet-HTTP.html
	 */
	@SuppressWarnings("resource")
	public void readStream(String fileName) throws IOException {
		Scanner scanner;
		try {
			//String data = "goss.clark.net - - [04/Sep/1995:00:00:27 -0400] \"GET / HTTP/1.0\" 200 1834";  
		    //String regex = "\\[.*\\]+";
		    //Pattern p = Pattern.compile(regex);  
		    //Matcher m = p.matcher(data);  
		    //while (m.find()) {  
		    //    System.out.println(m.group());  
		    //}  
			String regex = "\\[.*\\]+";
			Pattern pattern = Pattern.compile(regex);
			FileReader reader = new FileReader(fileName);
			scanner = new Scanner(reader).useDelimiter("\n");
			// tagoss.clark.net - - [04/Sep/1995:00:00:27 -0400] "GET / HTTP/1.0" 200 1834
			int i = 0;
			while (scanner.hasNext()) {
				String line = scanner.next();
				//System.out.println("LINHA: "+ i + " - " + line + "\n\n");
				Matcher dataMatcher = pattern.matcher(line);
				
				if(dataMatcher.find()) System.out.println(dataMatcher.group(0));
				
				i++;
				if(i==5) break;
			}
			reader.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
