/*
 * Classe que trata o fluxo de entrada ainda cru
 */
package br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileInputStreamHandler extends InputStreamHandler {
	
	private Pattern pattern;
	private String fileDelimiter;
	private FileReader file;
	private Scanner scanner;

	public FileInputStreamHandler(String regexPattern, String pathFile, String fileDelimiter) {
		super(regexPattern, pathFile);
		this.regexPattern = regexPattern;
		this.pathData = pathFile;
		this.fileDelimiter = fileDelimiter;
	}
	
	@Override
	public List<String> readStream(){
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
			this.file = new FileReader(this.pathData);
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
			//System.out.println(dataMatcher.group(0)); //debug
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
