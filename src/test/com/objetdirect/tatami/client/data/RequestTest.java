package com.objetdirect.tatami.client.data;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.objetdirect.tatami.client.DefaultTatamiTest;

import junit.framework.TestCase;

public class RequestTest extends DefaultTatamiTest {

	public void testBuildRequestFromJSObject(){
		Request request = new Request(buildJSRequest());
		assertEquals(5, request.getNbItemToReturn());
		assertEquals(3, request.getStartItemNumber());
		
		Map query = request.getQuery();
		assertEquals(true, query.containsKey("name"));
		assertEquals(true, query.containsKey("age"));
		assertEquals("myNameIs", query.get("name"));
		assertEquals(23, ((Number)query.get("age")).intValue());
		
		List sortFields = request.getSortFields();
		
		boolean isGrouPresentAndDescending = false;
		boolean isGroumPresentAndAscending = false;
		
		for (Iterator iterator = sortFields.iterator(); iterator.hasNext();) {
			SortField sortField = (SortField) iterator.next();
			if(sortField.getAttribute().compareTo("grou") == 0  && sortField.isDescending()){
				isGrouPresentAndDescending = true;
			}
			if(sortField.getAttribute().compareTo("groum") == 0&& !sortField.isDescending()){
				isGroumPresentAndAscending = true;
			}
		}
		assertEquals(true , isGrouPresentAndDescending);
		assertEquals(true , isGroumPresentAndAscending);
	}
	
	public void testRequestToJSObject(){
		Request request = new Request();
		request.setNbItemToReturn(2);
		request.setStartItemNumber(5);
		request.addSortParameter("grou", true);
		request.addSortParameter("groum", false);
		request.addQueryParameter("name", "myName");
		request.addQueryParameter("age", new Integer(23));
		JavaScriptObject jsRequest = request.toJSObject();
		assertEquals(true, isCountOk(jsRequest, 2));
		assertEquals(true, isStartOk(jsRequest, 5));
		assertEquals(true, areSortParametersOk(jsRequest));
		assertEquals(true, areQueryParametersOk(jsRequest));
	}
	
	private native boolean isCountOk(JavaScriptObject request , int expectedCount)/*-{
		if(request.count == expectedCount){
			return true;
		}else{
			return false;
		}
	}-*/;
	
	private native boolean isStartOk(JavaScriptObject request , int expectedStart)/*-{
		if(request.start == expectedStart){
			return true;
		}else{
			return false;
		}
	}-*/;
	
	private native boolean areSortParametersOk(JavaScriptObject request)/*-{
		var sort = request.sort;
		if(sort.length != 2){
			return false;
		}
		var isGrouPresentAndDescending = false;
		var isGroumPresentAndAscending = false;
		
		for(var i = 0 ; i < sort.length ; i++){
			var currSortField = sort[i];
			if(currSortField.attribute == 'grou' && currSortField.descending == true){
				isGrouPresentAndDescending = true;
			}
			if(currSortField.attribute == 'groum' && currSortField.descending == false){
				isGroumPresentAndAscending = true;
			}
		}
		if(!(isGrouPresentAndDescending && isGroumPresentAndAscending)){
			return false;
		}
		return true;
	}-*/;
	
	private native boolean areQueryParametersOk(JavaScriptObject request)/*-{
		var query = request.query;
		var isNameOK = false;
		var isAgeOK = false;
		if(query.name == 'myName'){
			isNameOK = true;
		}
		if(query.age == 23){
			isAgeOK = true;
		}
		if(!(isNameOK && isAgeOK)){
			return false;
		}
		return true;
		
	}-*/;
	
	
	private native JavaScriptObject buildJSRequest()/*-{
		var request = {};
		request.count = 5;
		request.start = 3;
		var sortField1 = { attribute : "grou" , descending : true};
		var sortField2 = { attribute : "groum" , descending : false};
		request.sort = [ sortField1 , sortField2 ];
		request.query = { name : "myNameIs" , age : 23 };
		return request;
	}-*/;
}
