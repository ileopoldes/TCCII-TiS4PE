package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;
import java.sql.Date;

import br.unisinos.tcc.tis4pe.wcf.inputdata.DataHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;
import br.unisinos.tcc.tis4pe.wcf.inputdata.txtfile.FileInputStreamHandler;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "TiS4PE!\n\n" );
        String pattern = "\\[.*\\]+";
        //String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
        String pathFile = "/home/ileopoldes/tmp/ClarkNetHEAD.txt";
        String fileDelimiter = "\n";
        
        StreamHandlerInterface in = new FileInputStreamHandler(pattern, pathFile, fileDelimiter);
        DataHandler dataHandler = new DataHandler(in, InputWindowSpaceEnum.DAYS);
        dataHandler.extractData();
        dataHandler.teste();
        
    }
}
