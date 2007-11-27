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
	
	/**
	 * Tests the family property
	 *
	 */
	
	public void testFamily() {
		font.setFamily("Arial");
		assertEquals("The family should be Arial","Arial",font.getFamily());
	}
	
	
	/**
	 * Tests the contructor
	 *
	 */
	public void testFontContructor() {
		assertEquals("Arial",font.getFamily());
		assertEquals(10,font.getSize());
		assertEquals(Font.NORMAL,font.getStyle());
		assertEquals(Font.NORMAL,font.getVariant());
		assertEquals(Font.NORMAL,font.getWeight());
	}
	
	/**
	 * Tests the size property
	 *
	 */
	public void testSize() {
		font.setSize(15);
		assertEquals(15,font.getSize());
	}
	
	/**
	 * Tests the weight property
	 *
	 */
	public void testAllWeight() {
		String[] tab = { Font.BOLD,Font.BOLDER,Font.NORMAL,Font.LIGHTER};
		for (int i = 0 ; i<tab.length ; i++) {
			testWeight(tab[i]);
		}
	}
	
	/**
	 * Tests a weight property
	 * @param weight a value of a weight property to test
	 *
	 */
	private void testWeight(String weight) {
		font.setWeight(weight);
		assertEquals(weight,font.getWeight());
		
	}
	
	/**
	 * Tests the style property
	 *
	 */
	
	public void testStyle() {
		font.setStyle(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getStyle());
		font.setStyle(Font.ITALIC);
		assertEquals(Font.ITALIC,font.getStyle());
		font.setStyle(Font.OBLIQUE);
		assertEquals(Font.OBLIQUE,font.getStyle());
	}
	
	
	/**
	 * Tests the variant property
	 *
	 */
	public void testVariant() {
		font.setVariant(Font.NORMAL);
		assertEquals(Font.NORMAL,font.getVariant());
		font.setVariant(Font.SMALL_CAPS);
		assertEquals(Font.SMALL_CAPS,font.getVariant());
	}
	
	/**
	 * Destroys the point
	 */
	public void tearDown() {
		font = null;
	}
	
	/**
	 * Tests if the JavaScipt Font object is well
	 * created
	 *
	 */
	public void testJSFont() {
		assertNotNull(font.createFont());
	}
}
