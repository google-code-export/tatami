package com.objetdirect.tatami.test.charting;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;

import com.objetdirect.tatami.testpages.client.charting.TestBasicChartPage;
import com.objetdirect.tatami.unit.TatamiTestCase;

public class TestChartAxes extends AbstractTestChart{

	protected String getTestPageId() {
		return TestBasicChartPage.class.getName();
	}
	
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testChart1() throws Exception{
		PropertyResourceBundle resource = (PropertyResourceBundle) PropertyResourceBundle.getBundle("com.objetdirect.tatami.test.testChartsAxesSVG");
		Properties props = new Properties();
		props.load(new FileInputStream(ClassLoader.getSystemResource("com.objetdirect.tatami.test.testChartsAxesSVG.properties").getFile()));
		for (int i = 0; i < 30; i++) {
			try{
			//String expected = resource.getString("svgCharts"+i);
			String actual = page.getHtmlElementById("chart"+(i+1)).getHtmlElementsByTagName("svg").get(0).asXml();
			//assertEquals(expected, actual);
			props.put("chart"+(i+1),actual);
			}catch(Exception e){
				throw new Exception("An error occured at chart " + (i+1) , e);
			}
		}
		props.store(new FileWriter(ClassLoader.getSystemResource("com.objetdirect.tatami.test.testChartsAxesSVG.properties").getFile()),null);
	}
	
	
	
	
}
