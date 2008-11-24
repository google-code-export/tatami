package com.objetdirect.tatami.test.charting;

import java.util.ResourceBundle;

import com.objetdirect.tatami.testpages.client.charting.TestBasicChartPage;
import com.objetdirect.tatami.unit.TatamiTestCase;

public class TestChart extends AbstractTestChart{

	protected String getTestPageId() {
		return TestBasicChartPage.class.getName();
	}
	
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testChart1() throws Exception{
		ResourceBundle resource = ResourceBundle.getBundle("com.objetdirect.tatami.test.charting.testChartsSVG");
		for (int i = 0; i < 30; i++) {
			try{
			String expected = resource.getString("svgCharts"+i);
			String actual = page.getHtmlElementById("chart"+(i+1)).getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			}catch(Exception e){
				throw new Exception("An error occured at chart " + (i+1) , e);
			}
		}
	}
	
	
	
	
}
