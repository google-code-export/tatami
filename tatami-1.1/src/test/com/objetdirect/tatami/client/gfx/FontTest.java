package com.objetdirect.tatami.client.gfx;

import com.objetdirect.tatami.client.DefaultTatamiTest;

public class FontTest extends DefaultTatamiTest {

	private Font font;
	

	/**
	 * Creates a point
	 */
	public void setUp() {
		font = Font.DEFAULT_FONT;
	}
	
	public void testFamily() {
		font.setFamily("Arial");
		assertEquals("The family should be Arial","Arial",font.getFamily());
	}
	
	
	
	public void testFontContructor() {
		assertEquals("Arial",font.getFamily());
		assertEquals(10,font.getSize());
		assertEquals(Font.NORMAL,font.getStyle());
		assertEquals(Font.NORMAL,font.getVariant());
		assertEquals(Font.NORMAL,font.getWeight());
	}
	
	public void testSize() {
		font.setSize(15);
		assertEquals(15,font.getSize());
	}
	
	public void testAllWeight() {
		String[] tab = { Font.BOLD,Font.BOLDER,Font.NORMAL,Font.LIGHTER};
		for (int i = 0 ; i<tab.length ; i++) {
			testWeight(tab[i]);
		}
	}
	
	private void testWeight(String weight) {
		font.setWeight(weight);
		assertEquals(weight,font.getWeight());
		
	}
	
	public void testStyle() {
		font.setStyle(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getStyle());
		font.setStyle(Font.ITALIC);
		assertEquals(Font.ITALIC,font.getStyle());
		font.setStyle(Font.OBLIQUE);
		assertEquals(Font.OBLIQUE,font.getStyle());
	}
	
	
	public void testVariant() {
		font.setVariant(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getVariant());
		font.setStyle(Font.SMALL_CAPS);
		assertEquals(Font.SMALL_CAPS,font.getVariant());
	}
	
	/**
	 * Destroys the point
	 */
	public void tearDown() {
		font = null;
	}
	
	public void testJSFont() {
		assertNotNull(font.createFont());
	}
}
