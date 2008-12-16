package com.objetdirect.tatami.test.charting;

import java.util.Iterator;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.objetdirect.tatami.testpages.client.charting.TestChartCustomEffectPage;
import com.objetdirect.tatami.unit.wrappers.ChartWrapper;

public class TestChartCustomEffect extends AbstractTestChart{

	protected String getTestPageId() {
		return TestChartCustomEffectPage.class.getName();
	}
	
	public void setUp() throws Exception{
		super.setUp();
		testGwt.waitForBackgroundTasksToComplete(5000);
	}
	
	public void testChart1() throws Exception{
		ChartWrapper chart1 = new ChartWrapper(page.getHtmlElementById("chart1"));
		List<HtmlElement> slices = chart1.getPiePieces();
		assertTrue(slices.size() == 6);
		HtmlElement myPiePiece;
		//We seek the white pie piece to click it
		for (Iterator<HtmlElement> iterator = slices.iterator(); iterator.hasNext();) {
			HtmlElement htmlElement =  iterator.next();
			if(htmlElement.getAttribute("fill").equals("rgb(255, 255, 255)")){
				myPiePiece = htmlElement;
				myPiePiece.mouseOver();
				testGwt.mouseClick(myPiePiece);
				myPiePiece.mouseOut();
				break;
			}
		}
		testGwt.waitForBackgroundTasksToComplete(2000);
		assertEquals("Mouse Over : Africa", page.getHtmlElementById("chart1MouseOver").getTextContent());
		assertEquals("Clicked on : Africa", page.getHtmlElementById("chart1MouseClick").getTextContent());
		assertEquals("Mouse Out : Africa", page.getHtmlElementById("chart1MouseOut").getTextContent());
	}
	
	public void testChart2() throws Exception{
		ChartWrapper chart2 = new ChartWrapper(page.getHtmlElementById("chart2"));
		List<HtmlElement> markers = chart2.getCircleMarkers(0);
		assertEquals(3,markers.size());
		HtmlElement myMarker = markers.get(1);
		myMarker.mouseOver();
		testGwt.mouseClick(myMarker);
		myMarker.mouseOut();
		testGwt.waitForBackgroundTasksToComplete(2000);
		assertEquals("Mouse Over : X : 1.5 Y : 1.5 X From Point object : 1.5 Y From Point Object : 1.5Tooltip : SecondPoint", page.getHtmlElementById("chart2MouseOver").getTextContent());
		assertEquals("Clicked on : X : 1.5 Y : 1.5 X From Point object : 1.5 Y From Point Object : 1.5Tooltip : SecondPoint", page.getHtmlElementById("chart2MouseClick").getTextContent());
		assertEquals("Mouse Out : X : 1.5 Y : 1.5 X From Point object : 1.5 Y From Point Object : 1.5Tooltip : SecondPoint", page.getHtmlElementById("chart2MouseOut").getTextContent());
	}
	
	public void testChart3() throws Exception{
		ChartWrapper chart3 = new ChartWrapper(page.getHtmlElementById("chart3"));
		List<HtmlElement> bars = chart3.getColumns(0);
		assertEquals(5,bars.size());
		HtmlElement myMarker = bars.get(2);
		myMarker.mouseOver();
		testGwt.mouseClick(myMarker);
		myMarker.mouseOut();
		testGwt.waitForBackgroundTasksToComplete(2000);
		assertEquals("Mouse Over : Value From Bar = 3", page.getHtmlElementById("chart3MouseOver").getTextContent());
		assertEquals("Clicked on : Value From Bar = 3", page.getHtmlElementById("chart3MouseClick").getTextContent());
		assertEquals("Mouse Out : Value From Bar = 3", page.getHtmlElementById("chart3MouseOut").getTextContent());
	}
	
	public void testChart4() throws Exception{
		ChartWrapper chart4 = new ChartWrapper(page.getHtmlElementById("chart4"));
		List<HtmlElement> bubbles = chart4.getBubbles(1);
		assertEquals(4,bubbles.size());
		HtmlElement myMarker = bubbles.get(1);
		myMarker.mouseOver();
		testGwt.mouseClick(myMarker);
		myMarker.mouseOut();
		testGwt.waitForBackgroundTasksToComplete(2000);
		assertEquals("Mouse Over : Size = 4.5X = 1.5Y = 1.5Tooltip = Big Bubble !!", page.getHtmlElementById("chart4MouseOver").getTextContent());
		assertEquals("Clicked on : Size = 4.5X = 1.5Y = 1.5Tooltip = Big Bubble !!", page.getHtmlElementById("chart4MouseClick").getTextContent());
		assertEquals("Mouse Out : Size = 4.5X = 1.5Y = 1.5Tooltip = Big Bubble !!", page.getHtmlElementById("chart4MouseOut").getTextContent());
	}
	
	
}
