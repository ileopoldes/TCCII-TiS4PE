/*
 * Classe que trata o fluxo de entrada ainda cru
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InputStreamHandler {
	
	private String regexPattern;
	private String pathFile;
	private Pattern pattern;
	private String fileDelimiter;
	private FileReader file;
	private Scanner scanner;

	public InputStreamHandler(String regexPattern, String pathFile, String fileDelimiter) {
		this.regexPattern = regexPattern;
		this.pathFile = pathFile;
		this.fileDelimiter = fileDelimiter;
	}
	
	/*
	 * lê arquivo
	 * http://ita.ee.lbl.gov/html/contrib/ClarkNet-HTTP.html
	 */
	public List<String> readStream() throws IOException {
		String line;
		String strDataFound;
		List<String> listDataFound = new ArrayList<String>();
		
		this.prepareRegex();
		this.loadFile();
		while (scanner.hasNext()) {
			line = scanner.next();
			strDataFound = this.searchPattern(line);
			if(strDataFound != null &&
					strDataFound != ""){
				listDataFound.add(strDataFound);
			}
		}
		this.closeResources();
		return listDataFound;
	}
	
	private void prepareRegex(){
		this.pattern = Pattern.compile(this.regexPattern);
	}
	
	private void loadFile(){
		try {
			this.file = new FileReader(this.pathFile);
			this.scanner = new Scanner(this.file).useDelimiter(this.fileDelimiter);
		}catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro - Arquivo informado não encontrado");
			e.printStackTrace();
		}
	}
	
	private String searchPattern(String line) {
		Matcher dataMatcher = this.pattern.matcher(line);			
		if( dataMatcher.find() ){
			System.out.println(dataMatcher.group(0)); //debug
			return dataMatcher.group(0);
		}
		return null;
	}
	
	private void closeResources(){
		try {
			this.file.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro - Erro ao tentar fechar o arquivo");
			e.printStackTrace();
		}
		this.scanner.close();
	}
}
