package com.objetdirect.tatami.test.charting;

import java.util.ResourceBundle;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.charting.TestChartUpdatingPage;

public class TestChartUpdating extends AbstractTestChart{

	protected String getTestPageId() {
		return TestChartUpdatingPage.class.getName();
	}
	

	
	
	
	public void testChart1() throws Exception{
		ResourceBundle resource = ResourceBundle.getBundle("com.objetdirect.tatami.test.charting.testChartsUpdatingSVG");
			try{
			String previous;
			String expected;
			String actual;
			expected = resource.getString("initialChart");
			actual = page.getHtmlElementById("chart").getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			HtmlElement button = page.getHtmlElementById("button");
			testGwt.mouseClick(button);
		//	testGwt.waitForBackgroundTasksToComplete(5000);
			previous = actual;
			expected = resource.getString("chartUpdated1time");
			actual = page.getHtmlElementById("chart").getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			assertFalse(actual.compareTo(previous) == 0);
			testGwt.mouseClick(button);
			//testGwt.waitForBackgroundTasksToComplete(5000);
			previous = actual;
			expected = resource.getString("chartUpdated2time");
			actual = page.getHtmlElementById("chart").getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			assertFalse(actual.compareTo(previous) == 0);
			testGwt.mouseClick(button);
		//	testGwt.waitForBackgroundTasksToComplete(5000);
			previous = actual;
			expected = resource.getString("chartUpdated3time");
			actual = page.getHtmlElementById("chart").getHtmlElementsByTagName("svg").get(0).asXml();
			assertEquals(expected, actual);
			assertFalse(actual.compareTo(previous) == 0);
			}catch(Exception e){
				throw new Exception("An error occured ",e);
			}
	}
	
	
	
	
}
