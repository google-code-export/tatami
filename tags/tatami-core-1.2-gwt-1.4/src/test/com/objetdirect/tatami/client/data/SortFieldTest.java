package com.objetdirect.tatami.client.data;

import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DefaultTatamiTest;

public class SortFieldTest extends DefaultTatamiTest{

	public void testToJSObject(){
		SortField field1 = new SortField("grou" , true);
		SortField field2 = new SortField("groum" , false); 
		assertEquals(true, isDescending(field1.toJSObject()));
		assertEquals(false, isDescending(field2.toJSObject()));
		assertEquals("grou", getFieldName(field1.toJSObject()));
	}
	
	private native boolean isDescending(JavaScriptObject sortField)/*-{
		return sortField.descending;
	}-*/;

	private native String getFieldName(JavaScriptObject sortField)/*-{
		return sortField.attribute;
	}-*/;
	
}
