package com.objetdirect.tatami.test.charting;

import com.objetdirect.tatami.unit.TatamiTestCase;

public abstract class AbstractTestChart extends TatamiTestCase{


	protected String getPageName() {
		return "http://localhost:7777/com.objetdirect.tatami.testpages.TestCharting/";
	}
	
	public void setUp() throws Exception{
		super.setUp();
		//testGwt.waitForBackgroundTasksToComplete(5000);
	}
}
