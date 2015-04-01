package br.unisinos.tcc.tis4pe.wcf;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "TiS4PE!\n\n" );
        InputStreamHandler in = new InputStreamHandler();
        
        String pathFile = "/home/ileopoldes/tmp/ClarkNet.txt";
        
        in.readStream(pathFile);
    }
}
