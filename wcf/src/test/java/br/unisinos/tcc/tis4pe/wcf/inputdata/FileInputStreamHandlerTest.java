package br.unisinos.tcc.tis4pe.wcf.inputdata;

/**
 * Esta classe pressupoe a existência de um arquivo em src/test/resources/dataRequests.txt
 * com o seguinte texto:
 * 
 * tagoss.clark.net - - [04/Sep/1995:00:00:27 -0400] "GET / HTTP/1.0" 200 1834
 * ix-dc9-19.ix.netcom.com - - [04/Sep/1995:00:00:28 -0400] "GET /html/cgi.html HTTP/1.0" 200 2217
 *
 */
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class FileInputStreamHandlerTest extends TestCase {
	String pattern;
	String pathFile;
	String fileDelimiter;
	String STR_FILE_MATCH = "[04/Sep/1995:00:00:27 -0400]";
	String STR_FILE_MATCH2 = "[04/Sep/1995:00:00:28 -0400]";

	protected void setUp(){
		pattern = "\\[.*\\]+";
        pathFile = "/dataRequests.txt";
        fileDelimiter = "\n";
	}
	
	public void testStreamToString() {
	   assertNotNull("Arquivo de texto faltando em test/resources", 
	               getClass().getResource(pathFile));
	}
	
	public void testReadFile() throws IOException, URISyntaxException{
		URL resourceUrl = getClass().
				getResource(pathFile);
		Path resourcePath = Paths.get(resourceUrl.toURI());
    	
        String pattern = "\\[.*\\]+";
        String pathFile = resourcePath.toString();
        String fileDelimiter = "\n";
        
        StreamHandlerInterface in = new FileInputStreamHandler(pattern, pathFile, fileDelimiter);
        
        List<String> listPadroes = new ArrayList<String>();
        listPadroes = in.readStream();
        
        assertTrue(
        		listPadroes.get(0).equals(STR_FILE_MATCH)
        		&&
        		listPadroes.get(1).equals(STR_FILE_MATCH2)
        		);
	}
	
	
}
