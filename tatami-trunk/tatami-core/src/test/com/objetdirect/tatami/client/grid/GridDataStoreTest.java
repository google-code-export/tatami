package com.objetdirect.tatami.client.grid;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.data.DataStore;
import com.objetdirect.tatami.client.data.DatumChangeListener;
import com.objetdirect.tatami.client.data.FetchEventSource;
import com.objetdirect.tatami.client.data.FetchListener;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.data.LoadItemListener;
import com.objetdirect.tatami.client.data.Request;

public class GridDataStoreTest extends DefaultTatamiTest{

	
	class MyFetchListener implements FetchListener{

		public int lastOnBeginSize;
		
		public Request lastOnBeginRequest;
		
		public List lastOnCompleteItems;
		
		public Request lastOnCompleteRequest;
		
		public boolean errorOccured = false;
		
		public Item lastOnItem;
		
		public void onBegin(FetchEventSource source , int size, Request request) {
			lastOnBeginRequest = request;
			lastOnBeginSize = size;
		}

		public void onComplete(FetchEventSource source , List items, Request request) {
			lastOnCompleteItems = items;
			lastOnCompleteRequest = request;
		}

		public void onError(FetchEventSource source) {
			errorOccured = true;
		}

		public void onItem(FetchEventSource source , Item item) {
			this.lastOnItem = item;
		}
		
	}
	
	
	
	public void testInitialization(){
		GridDataStore store = new GridDataStore();
		assertTrue(store.getDojoWidget() != null);
	}
	
	public void testAddRemoveItems(){
		GridDataStore store = new GridDataStore();
		Item item = new Item();
		item.addAttribute("id", "myId");
		store.add(item);
		Item item2 = new Item();
		item2.addAttribute("id", "myId2");
		store.add(item2);
		assertEquals(2, store.size());
		store.remove(item);
		assertEquals(1, store.size());
		store.remove(item2);
		assertEquals(0, store.size());
	}
	
	public void testDatumChangeEvents(){
		GridDataStore store = new GridDataStore();
		
		class MyDataListener implements DatumChangeListener{
			
			public Item lastAddedItem;
			
			public Item lastDeletedItem;
			
			public Item lastChangedItem;
			
			public String lastChangedAttribute;
			
			public Object lastOldValue;
			
			public Object lastNewValue;
			
			public void onDataChange(Item item, String attributeName,
				Object oldValue, Object newValue) {
				lastChangedItem = item;
				lastChangedAttribute = attributeName;
				lastOldValue = oldValue;
				lastNewValue = newValue;
			}

			public void onDelete(Item item) {
				lastDeletedItem = item;
			}

			public void onNew(Item item) {
				lastAddedItem = item;
			}
		
		}
		
		MyDataListener listener = new MyDataListener();
		
		store.addDatumChangeListener(listener);
		
		Item item = new Item();
		item.addAttribute("id", "myId");
		store.add(item);
		assertEquals(listener.lastAddedItem, item);

		store.setValue(item, "newAttribute", "newValue");
		assertEquals(item , listener.lastChangedItem);
		assertEquals("newAttribute" , listener.lastChangedAttribute);
		assertEquals(null ,listener.lastOldValue);
		assertEquals("newValue" , listener.lastNewValue);
		Integer newValue = new Integer(2);
		
		store.setValue(item, "newAttribute", newValue);
		assertEquals(item , listener.lastChangedItem);
		assertEquals("newAttribute" , listener.lastChangedAttribute);
		assertEquals("newValue" ,listener.lastOldValue);
		assertEquals(newValue , listener.lastNewValue);
		
		store.remove(item);
		assertEquals(listener.lastDeletedItem, item);
		
		store.removeDatumChangeListener(listener);
		
		Item item2 = new Item();
		item2.addAttribute("id","anotherId");
		
		store.add(item2);
		assertEquals(listener.lastAddedItem, item);
		
		store.setValue(item2, "anotherAttribute", "grougrou");
		assertEquals(item , listener.lastChangedItem);
		assertEquals("newAttribute" , listener.lastChangedAttribute);
		assertEquals("newValue" ,listener.lastOldValue);
		assertEquals(newValue , listener.lastNewValue);
		
		store.remove(item2);
		assertEquals(listener.lastDeletedItem, item);
		
	}
	
	private Item[] initItems(){
		Item[] items = new Item[5];
		Item item1 = new Item();
		item1.addAttribute("id", new Integer(0));
		item1.addAttribute("name", "James");
		item1.addAttribute("age", new Integer(42));
		items[0] = item1;
		
		Item item2 = new Item();
		item2.addAttribute("id", new Integer(1));
		item2.addAttribute("name", "John");
		item2.addAttribute("age", new Integer(38));
		items[1] = item2;
		
		Item item3 = new Item();
		item3.addAttribute("id", new Integer(2));
		item3.addAttribute("age", new Integer(52));
		item3.addAttribute("name", "John");
		items[2] = item3;
		
		Item item4 = new Item();
		item4.addAttribute("id", new Integer(3));
		item4.addAttribute("age", new Integer(23));
		item4.addAttribute("name", "James");
		items[3] = item4;
		
		Item item5 = new Item();
		item5.addAttribute("id", new Integer(4));
		items[4] = item5;
		
		return items;
	}
	
	public void testFetchEvents(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		for (int i = 0; i < items.length; i++) {
			store.add(items[i]);
		}
		MyFetchListener listener = new MyFetchListener();
		store.addFetchListener(listener);
		
		Request request = new Request();
		request.setNbItemToReturn(2);
		request.setStartItemNumber(2);
		request.addSortParameter("id", false);
		store.fetch(request);
		assertEquals(5, listener.lastOnBeginSize);
		assertEquals(request, listener.lastOnBeginRequest);
		assertEquals(request, listener.lastOnCompleteRequest);
		assertEquals(items[3], listener.lastOnItem);
		assertEquals(request, store.getLastRequest());
		
		store.removeFetchListener(listener);
		request.setNbItemToReturn(1);
		request.setStartItemNumber(3);
		request.addSortParameter("name", false);
		store.fetch(request);
		assertEquals(5, listener.lastOnBeginSize);
		assertEquals(request, listener.lastOnBeginRequest);
		assertEquals(request, listener.lastOnCompleteRequest);
		assertEquals(items[3], listener.lastOnItem);
		
	}
	
	
	public void testFetchSorting(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		for (int i = 0; i < items.length; i++) {
			store.add(items[i]);
		}
		MyFetchListener listener = new MyFetchListener();
		store.addFetchListener(listener);
		
		Request request = new Request();
		request.setNbItemToReturn(2);
		request.setStartItemNumber(2);
		request.addSortParameter("id", false);
		store.fetch(request);
		List fetcheditems = listener.lastOnCompleteItems;

		List expectedItems = new ArrayList();
		expectedItems.add(items[2]);
		expectedItems.add(items[3]);
		Iterator iterator2 = expectedItems.iterator();
		assertEquals(expectedItems.size(), fetcheditems.size());
		for (Iterator iterator = fetcheditems.iterator() ; iterator.hasNext() && iterator2.hasNext();) {
			Item item = (Item) iterator.next();
			Item expecteditem = (Item) iterator2.next();
			assertEquals(expecteditem, item);
		}
		request.clearQueryParameters();
		request.clearSortOptions();
		request.addSortParameter("id", true);
		request.setStartItemNumber(1);
		request.setNbItemToReturn(3);
		
		store.fetch(request);
		assertEquals(5, listener.lastOnBeginSize);
		assertEquals(request, listener.lastOnBeginRequest);
		assertEquals(request, listener.lastOnCompleteRequest);
		assertEquals(items[1], listener.lastOnItem);
		
		fetcheditems = listener.lastOnCompleteItems;

		expectedItems.clear();
		expectedItems.add(items[3]);
		expectedItems.add(items[2]);
		expectedItems.add(items[1]);
		iterator2 = expectedItems.iterator();
		assertEquals(expectedItems.size(), fetcheditems.size());
		for (Iterator iterator = fetcheditems.iterator() ; iterator.hasNext() && iterator2.hasNext();) {
			Item item = (Item) iterator.next();
			Item expecteditem = (Item) iterator2.next();
			assertEquals(expecteditem, item);
		}
		request.clearQueryParameters();
		request.clearSortOptions();
		request.addSortParameter("name", false);
		request.addSortParameter("age", true);
		request.setStartItemNumber(0);
		request.setNbItemToReturn(5);
		store.fetch(request);
		
		fetcheditems = listener.lastOnCompleteItems;

		expectedItems.clear();
		expectedItems.add(items[4]);
		expectedItems.add(items[0]);
		expectedItems.add(items[3]);
		expectedItems.add(items[2]);
		expectedItems.add(items[1]);
		iterator2 = expectedItems.iterator();
		assertEquals(expectedItems.size(), fetcheditems.size());
		for (Iterator iterator = fetcheditems.iterator() ; iterator.hasNext() && iterator2.hasNext();) {
			Item item = (Item) iterator.next();
			Item expecteditem = (Item) iterator2.next();
			assertEquals(expecteditem, item);
		}
	}
	
	public void testFiltering(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		for (int i = 0; i < items.length; i++) {
			store.add(items[i]);
		}
		MyFetchListener listener = new MyFetchListener();
		store.addFetchListener(listener);
		Request request = new Request();
		request.setNbItemToReturn(5);
		request.setStartItemNumber(0);
		request.addQueryParameter("name", "James");
		store.fetch(request);
		
		List fetcheditems = listener.lastOnCompleteItems;

		List expectedItems = new ArrayList();
		expectedItems.add(items[0]);
		expectedItems.add(items[3]);
		assertEquals(expectedItems.size(), fetcheditems.size());
		assertEquals(true, expectedItems.containsAll(fetcheditems));
		assertEquals(true, fetcheditems.containsAll(expectedItems));
	}
	
	public void testGetAttributes(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		assertEquals(new Integer(0) , store.getIdentity(items[0]));
		List expectedAttributes = new ArrayList();
		expectedAttributes.add("id");
		expectedAttributes.add("name");
		expectedAttributes.add("age");
		
		
		String [] attrs = store.getAttributes(items[0]);
		List attributesFromStore = new ArrayList();
		for (int i = 0; i < attrs.length; i++) {
			attributesFromStore.add(attrs[i]);
		}
		assertTrue(expectedAttributes.containsAll(attributesFromStore));
		assertTrue(attributesFromStore.containsAll(expectedAttributes));
	}
	
	public void testGetIdentity(){
		Item[] items = initItems();
		DataStore store = new GridDataStore("name");
		store.add(items[0]);
		assertEquals("James" , store.getIdentity(items[0]));
		assertEquals("name" , store.getIdentityAttribute());
		DataStore store2 = new GridDataStore();
		store2.add(items[0]);
		assertEquals(new Integer(0), store2.getIdentity(items[0]));
	}
	
	public void testGetValue(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		store.add(items[4]);
		assertEquals("James" , store.getValue(items[0], "name", "notSpecified"));
		assertEquals("notSpecified" , store.getValue(items[4], "name", "notSpecified"));
		assertEquals("notSpecified" , store.getValue(items[2], "name", "notSpecified"));
		
		assertTrue(store.containsValue(items[0], "name", "James"));
	}
	
	public void testSetValue(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		store.setValue(items[0], "newAttr", "Groum");
		assertEquals("Groum", store.getValue(items[0], "newAttr", null));
	}
	
	public void testGetByIdentity(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		store.add(items[1]);
		assertEquals(items[0], store.getItemByIdentity(new Integer(0)));
		assertEquals(null, store.getItemByIdentity("FAKE ID"));
	}
	
	public void testLoadItem(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		store.loadItem(items[0]);
		assertTrue(store.isItemLoaded(items[0]));
		assertFalse(store.isItemLoaded(items[1]));
	}
	
	public void testLoadItemEvents(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		class MyLoadListener implements LoadItemListener{
			
			boolean hasBeenCalled = false;
			
			public void onLoad(Item item) {
				hasBeenCalled = true;
			}
		}
		MyLoadListener listener = new MyLoadListener();
		store.addLoadItemListener(listener);
		boolean itemHasBeenLoaded = store.loadItem(items[0]);
		
		if(itemHasBeenLoaded){
			assertTrue(listener.hasBeenCalled);
		}else{
			assertFalse(listener.hasBeenCalled);
		}
		assertFalse(store.loadItem(items[0]));
	}
	
	public void testLabelMethods(){
		Item[] items = initItems();
		DataStore store = new GridDataStore();
		store.add(items[0]);
		
		String[] labelAttr = store.getLabelAttributes(items[0]);
		assertEquals(1, labelAttr.length);
		assertEquals(labelAttr[0], "label");
		
		items[0].addAttribute("label", "MyLabel");
		assertEquals("MyLabel", store.getLabel(items[0]));
		
	}
	
	
}
