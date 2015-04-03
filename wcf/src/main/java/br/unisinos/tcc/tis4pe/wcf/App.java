package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;

import br.unisinos.tcc.tis4pe.wcf.inputdata.FileInputStreamHandler;
import br.unisinos.tcc.tis4pe.wcf.inputdata.StreamHandlerInterface;

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
        String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
        String fileDelimiter = "\n";
        
        StreamHandlerInterface in = new FileInputStreamHandler(pattern, pathFile, fileDelimiter);
        
        
        in.readStream();
    }
}
