package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.io.IOException;
import java.util.List;

public interface StreamHandlerInterface {

	public List<String> readStream() throws IOException;
}
