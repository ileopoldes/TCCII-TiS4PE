package br.unisinos.tcc.tis4pe.wcf.util;

import junit.framework.TestCase;

public class PropertieReaderTest extends TestCase {

	public void testGetDefaultDateStringPattern(){
		String defaultPattern = PropertieReaderUtil.getDefaultDateStringPattern();
		assertTrue("O valor da propriedade está errado",
				defaultPattern.endsWith("dd/MMM/yyyy:HH:mm:ss"));
	}
}
