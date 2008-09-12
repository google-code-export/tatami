package com.objetdirect.tatamix.client.widget;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.Command;

public class MenuTest extends GWTTestCase {

	private Menu menu;
	private static final String NEW_ITEM = "NEW_ITEM";
	private static final String NEW_TOKEN = "NEW_TOKEN";
	
	
	
	public String getModuleName() {
			return "com.francetelecom.diamants.presentation.Diamants";
	}

	
     public void testAdd() {
    	 setMenu();
    	 assertEquals(20,menu.countItems());
     }

     public void testRemove() {
    	 setMenu();
    	 for (int i=0; i< 10; i=i+2) {
    		 menu.remove(i);
    	 }
    	 assertEquals(15,menu.countItems());
     }

     public void testClear() {
    	 setMenu();
    	 menu.clear();
    	 assertEquals(0,menu.countItems());
     }
     
     
     public void testCountItems() {
    	 setMenu();
 	    assertEquals(20,menu.countItems());
 	    menu.clear();
 	    assertEquals(0,menu.countItems());
 	 }
     
     public void testTokenHistory() {
    	 setMenu();
    	 String token = menu.getTokenHistoryAt(5);
    	 assertEquals(token,"token5");
    	 menu.setTokenHistoryAt(NEW_TOKEN, 7);
    	 assertEquals(menu.getTokenHistoryAt(7),NEW_TOKEN);
     }
     
     public void testText() {
    	 setMenu();
    	 String token = menu.getTextAt(5);
    	 assertEquals(token,"item5");
    	 menu.setTokenHistoryAt(NEW_ITEM, 7);
    	 assertEquals(menu.getTokenHistoryAt(7),NEW_ITEM);
     }
     
     public void testCommand() {
    	 setMenu();
    	 Command cmd = new Command() {
    		 public void execute() {
    			 System.out.println("Nouvelle commmande");
    		 }
    	 };
    	 menu.setCommandAt(cmd,8);
    	 assertEquals(menu.getCommandAt(8),cmd);
    	 
     }
     
     
     
     
     public void setMenu() {
    	
    	 menu =new Menu();
  		for (int i=0; i < 20; i++) {
  			  
  			menu.add("item"+i, "token"+i, null);
  		}
     }
     
     public void tearDown() {
    	 menu = null;
     }
}//
