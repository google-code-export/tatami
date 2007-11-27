package com.objetdirect.tatami.client.gfx;

public class TextPathTest extends PathTest {

	private final String text = "TestGFX"; 
	
	/**
	 * Returns a new instance of a <code>TextPath</code> with 
	 * non decoration
	 * @return a new instance of a <code>TextPath</code>
	 */
	protected GraphicObject createInstance() {
		return new TextPath(text);
	}
	
	/**
	 * Tests the getDecoration() method
	 *
	 */
	public void testDecoration() {
		initGraphics();
		TextPath text1 = new TextPath(text,Text.OVERLINE);
		applyCommands(text1);
		TextPath text2 = new TextPath(text,Text.LINE_THROUGH);
		applyCommands(text2);
		TextPath text3 = new TextPath(text,Text.UNDERLINE);
		
		applyCommands(text3);
		
		canvas.add(text1, 50, 30);
		canvas.add(text2, 50, 50);
		canvas.add(text3, 50, 70);
		
		assertEquals(Text.OVERLINE,text1.getDecoration());
		assertEquals(Text.LINE_THROUGH,text2.getDecoration());
		assertEquals(Text.UNDERLINE,text3.getDecoration());
	}
	
	/**
	 * Applies some commands to <code>TextPath</code> 
	 * @param path the <code>TextPath</code> which will execute the commands
	 */
	private void applyCommands(TextPath path) {
		  path.moveTo(20,20);
		  path.lineTo(95,20);
		  path.lineTo(102,56);
		  path.lineTo(13,56);
		  path.lineTo(20,20);
	}
	
	/**
	 * Casts the <code>GraphicObject</code> component 
	 * to a <code>TextPath</code>
	 * @return an instance of <code>TextPath</code>
	 */
	public TextPath toTextPath() {
		return (TextPath)component;
	}
	
	
	/**
	 * Tests the setFont() method
	 *
	 */
	public void testFont() {
		initGraphics();
		Font font = new Font("courier",12,Font.ITALIC,Font.SMALL_CAPS,Font.BOLD);
		toTextPath().setFont(font);
		assertSame(font,toTextPath().getFont());
		
	}



}// end of class
