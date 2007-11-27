package com.objetdirect.tatami.client.gfx;

public class TextTest extends TestGraphicObject {

	private final String text = "Some text"; 
	
	
	/**
	 * Creates a <code>Text</code> component
	 */
	protected GraphicObject createInstance() {
		return new Text(text);
	}
	
	/**
	 * Tests the constructor of a <code>Text</code> component
	 *
	 */
	public void testText() {
		initGraphics();
		assertEquals(text,toText().getText());
		assertEquals(Text.NONE,toText().getDecoration());
	}
	
	/**
	 * Casts the <code>GraphicObject</code> component to a 
	 * <code>Text</code> component
	 * @return a <code>Text</code>
	 */
	public Text toText() {
		return (Text)component;
	}
	
	/**
	 * Tests the getDecoration() method
	 *
	 */
	public void testDecoration() {
		initGraphics();
		Text text1 = new Text(text,Text.OVERLINE);
		Text text2 = new Text(text,Text.LINE_THROUGH);
		Text text3 = new Text(text,Text.UNDERLINE);
		
		canvas.add(text1, 50, 30);
		canvas.add(text2, 50, 50);
		canvas.add(text3, 50, 70);
		
		assertEquals(Text.OVERLINE,text1.getDecoration());
		assertEquals(Text.LINE_THROUGH,text2.getDecoration());
		assertEquals(Text.UNDERLINE,text3.getDecoration());
	}
	
	
	/**
	 * Tests the setFont() method
	 *
	 */
	public void testFont() {
		initGraphics();
		Font font = new Font("courier",12,Font.ITALIC,Font.SMALL_CAPS,Font.BOLD);
		toText().setFont(font);
		assertSame(font,toText().getFont());
		
	}

	/**
	 * Tests the getHeight() and the getLineHeight() methods
	 *
	 */
	public void testHeight() {
		initGraphics();
		assertTrue(0!= toText().getHeight());
		assertTrue(0!= toText().getLineHeight());
	}
}
