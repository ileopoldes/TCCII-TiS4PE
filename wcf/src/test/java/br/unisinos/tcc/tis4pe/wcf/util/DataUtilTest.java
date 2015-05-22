package br.unisinos.tcc.tis4pe.wcf.util;

import org.joda.time.DateTime;
import org.junit.Test;

import br.unisinos.tcc.tis4pe.wcf.dto.StringToDateFormatDTO;
import br.unisinos.tcc.tis4pe.wcf.exceptions.DateFormatException;

public class DataUtilTest {

	@Test(expected=DateFormatException.class)
	public void testDateFromString(){
		StringToDateFormatDTO dto = 
				new StringToDateFormatDTO(
						"04/Sep/1995:00:00:28",
						"dd/MM/yyyy:HH:mm");
		
		@SuppressWarnings("unused")
		DateTime dt = DateUtil.dateFromString(dto);
	}
}
