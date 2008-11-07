package com.objetdirect.tatami.client.data;

import junit.framework.TestCase;

public class ItemTest extends TestCase {

	public void testGetAttributes(){
		Item item = new Item();
		String[] attributes = {"attr1" , "attr2" , "attr3" , "attr4"};
		item.addAttribute(attributes[0], "grou");
		item.addAttribute(attributes[1], "grou");
		item.addAttribute(attributes[2], "grou");
		item.addAttribute(attributes[3], "grou");
		String[] gottenAttributes = item.getAttributes();
		assertEquals(attributes.length, gottenAttributes.length);
		for (int i = 0; i < attributes.length; i++) {
			boolean exists = false;
			for (int j = 0; j < gottenAttributes.length; j++) {
				if(gottenAttributes[j].equals(attributes[i])){
					exists = true;
					break;
				}
			}
			assertEquals(true, exists);
		}
	}
	
	public void testHasAttribute(){
		Item item = new Item();
		item.addAttribute("myAttr", "grou");
		assertEquals(true, item.hasAttribute("myAttr"));
		item.removeAttribute("myAttr");
		assertEquals(false, item.hasAttribute("myAttr"));
		assertEquals(false, item.hasAttribute("grougrou"));
	}
	
	public void testGetValue(){
		Item item = new Item();
		item.addAttribute("myAttr", "grou");
		assertEquals("grou", item.getValue("myAttr", null));
		assertEquals("groum", item.getValue("mahah", "groum"));
		assertEquals("grou", item.getValues("myAttr"));
		assertEquals(null, item.getValues("mahah"));
	}
	
	public void testContainsValue(){
		Item item = new Item();
		item.addAttribute("myAttr", "grou");
		assertEquals(true , item.containsValue("myAttr", "grou"));
		assertEquals(false , item.containsValue("myAttr", new Integer(3)) );
	}
	
	
}
