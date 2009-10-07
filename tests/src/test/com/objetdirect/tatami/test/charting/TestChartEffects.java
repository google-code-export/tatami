package com.objetdirect.tatami.test.charting;

import java.util.ResourceBundle;

import com.objetdirect.tatami.testpages.client.charting.TestChartEffectPage;

public class TestChartEffects extends AbstractTestChart{

	protected String getTestPageId() {
		return TestChartEffectPage.class.getName();
	}
	
	

	
	public void testChart1() throws Exception{
		ResourceBundle resource = ResourceBundle.getBundle("com.objetdirect.tatami.test.charting.testChartsEffectSVG");
		for (int i = 0; i < 10; i++) {
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
