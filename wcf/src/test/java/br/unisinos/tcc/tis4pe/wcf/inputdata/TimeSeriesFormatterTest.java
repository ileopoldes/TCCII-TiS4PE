package br.unisinos.tcc.tis4pe.wcf.inputdata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;

import br.unisinos.tcc.tis4pe.wcf.InputWindowSpaceEnum;
import br.unisinos.tcc.tis4pe.wcf.util.DateUtil;
import junit.framework.TestCase;

public class TimeSeriesFormatterTest extends TestCase {

	public void testFormatSeconds(){
		
		List<DateTime> list = new ArrayList<DateTime>();
		
		String[] arrayDatasSTR = {"04/sep/1995:00:00:27",
				"04/sep/1995:00:00:28","04/sep/1995:00:00:28",
				"04/sep/1995:00:00:29","04/sep/1995:00:00:29","04/sep/1995:00:00:29",
				"04/sep/1995:00:01:27",
				"04/sep/1995:01:00:27",
				"04/oct/1995:00:00:27"};
		
		for(int i=0; i<9; i++){
			DateTime date = DateUtil.dateFromString(arrayDatasSTR[i]);
			list.add(date);
		}
		
		Map<DateTime, Integer> map = TimeSeriesFormatter.format(list, InputWindowSpaceEnum.SECONDS);
		
		assertTrue(
				map.keySet().size() == 6
				&&
				map.get( list.get(0) ) == 1	//"1995-09-04T00:00:27.000-03:00"
				&&
				map.get( list.get(1) ) == 2	//"1995-09-04T00:00:28.000-03:00"
				&&
				map.get( list.get(3) ) == 3	//"1995-09-04T00:00:29.000-03:00"
				&&
				map.get( list.get(6) ) == 1	//"1995-09-04T00:01:27.000-03:00"
				&&
				map.get( list.get(7) ) == 1	//"1995-09-04T01:00:27.000-03:00"
				&&
				map.get( list.get(8) ) == 1	//"1995-10-04T00:00:27.000-03:00"
				);
	}
}
