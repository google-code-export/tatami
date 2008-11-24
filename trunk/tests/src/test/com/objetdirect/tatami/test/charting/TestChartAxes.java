package com.objetdirect.tatami.test.charting;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.PropertyResourceBundle;

import com.objetdirect.tatami.testpages.client.charting.TestBasicChartPage;
import com.objetdirect.tatami.testpages.client.charting.TestChartAxesPage;

public class TestChartAxes extends AbstractTestChart{

	protected String getTestPageId() {
		return TestChartAxesPage.class.getName();
	}
	
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testChart1() throws Exception{
		PropertyResourceBundle resource = (PropertyResourceBundle) PropertyResourceBundle.getBundle("com.objetdirect.tatami.test.charting.testChartsAxesSVG");
		//Properties props = new Properties();
		for (int i = 0; i < 3; i++) {
			try{
			String expected = resource.getString("svgCharts"+(i+1));
			String actual = page.getHtmlElementById("chart"+(i+1)).getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			//props.put("svgCharts"+(i+1),actual);
			}catch(Exception e){
				throw new Exception("An error occured at chart " + (i+1) , e);
			}
		}
		//props.store(new FileWriter("C:\\Documents and Settings\\Homsys\\Mes documents\\rdunklau\\workspace\\tatami-tests\\src\\test\\com\\objetdirect\\tatami\\test\\charting\\testChartsAxesSVG.properties"),null);
	}
	
	
	
	
}
