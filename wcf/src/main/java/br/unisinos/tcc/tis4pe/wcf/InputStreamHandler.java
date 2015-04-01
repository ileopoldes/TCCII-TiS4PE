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
			String data = "[ffff234:09 1/23/23]  [gggg]";  
		   //String regex = "([\\.\\w\\d\\s])+";
			String regex = "([.*])+";
		    Pattern p = Pattern.compile(regex);  
		    Matcher m = p.matcher(data);  
		    while (m.find()) {  
		        System.out.println(m.group());  
		    }  
		    
			Pattern po = Pattern.compile(regex);
			FileReader reader = new FileReader(fileName);
			scanner = new Scanner(reader)
					.useDelimiter(po);
			// tagoss.clark.net - - [04/Sep/1995:00:00:27 -0400] "GET / HTTP/1.0" 200 1834
			int i = 0;
			while (scanner.hasNext()) {
				String primeiraParte = scanner.next();
				String meio = scanner.next();
				String segundaParte = scanner.next();
				//System.out.println(primeiraParte);
				System.out.println(meio);
				//System.out.println(segundaParte);
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
