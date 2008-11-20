package com.objetdirect.tatami;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.objetdirect.tatami.client.ButtonTest;
import com.objetdirect.tatami.client.ClockTest;
import com.objetdirect.tatami.client.ColorChooserTest;
import com.objetdirect.tatami.client.ColorPickerTest;
import com.objetdirect.tatami.client.DatePickerTest;
import com.objetdirect.tatami.client.DojoContentPaneTest;
import com.objetdirect.tatami.client.DropdownDatePickerTest;
import com.objetdirect.tatami.client.DropdownTimePickerTest;
import com.objetdirect.tatami.client.FishEyeTest;
import com.objetdirect.tatami.client.NumberSpinnerTest;
import com.objetdirect.tatami.client.RuleLabelsTest;
import com.objetdirect.tatami.client.RuleMarkTest;
import com.objetdirect.tatami.client.SliderTest;
import com.objetdirect.tatami.client.TimePickerTest;
import com.objetdirect.tatami.client.ToasterTest;
import com.objetdirect.tatami.client.data.ItemTest;
import com.objetdirect.tatami.client.data.RequestTest;
import com.objetdirect.tatami.client.data.SortFieldTest;
import com.objetdirect.tatami.client.dnd.DnDTest;
import com.objetdirect.tatami.client.encoding.BlowFishEncryptionTest;
import com.objetdirect.tatami.client.encoding.MD5Test;
import com.objetdirect.tatami.client.gfx.CircleTest;
import com.objetdirect.tatami.client.gfx.ColorTest;
import com.objetdirect.tatami.client.gfx.EllipseTest;
import com.objetdirect.tatami.client.gfx.FontTest;
import com.objetdirect.tatami.client.gfx.GraphicCanvasTest;
import com.objetdirect.tatami.client.gfx.ImageGfxTest;
import com.objetdirect.tatami.client.gfx.LineTest;
import com.objetdirect.tatami.client.gfx.PathTest;
import com.objetdirect.tatami.client.gfx.PatternTest;
import com.objetdirect.tatami.client.gfx.PointTest;
import com.objetdirect.tatami.client.gfx.PolyLineTest;
import com.objetdirect.tatami.client.gfx.RectTest;
import com.objetdirect.tatami.client.gfx.RectangleTest;
import com.objetdirect.tatami.client.gfx.TestGraphicObject;
import com.objetdirect.tatami.client.gfx.TextPathTest;
import com.objetdirect.tatami.client.gfx.TextTest;
import com.objetdirect.tatami.client.gfx.VirtualGroupTest;
import com.objetdirect.tatami.client.grid.CellTest;
import com.objetdirect.tatami.client.grid.GridDataStoreTest;
import com.objetdirect.tatami.client.grid.GridLayoutTest;
import com.objetdirect.tatami.client.grid.GridTest;
import com.objetdirect.tatami.client.grid.GridViewTest;

public class TatamiTestSuite extends GWTTestSuite {
	public static Test suite() {
	    TestSuite suite = new TestSuite("Tatamis Tests");
	    
	    //Base package
	    suite.addTestSuite(ButtonTest.class); 
	    suite.addTestSuite(ClockTest.class);
	    suite.addTestSuite(ColorChooserTest.class);
	    suite.addTestSuite(ColorPickerTest.class);
	    suite.addTestSuite(DatePickerTest.class);
	    suite.addTestSuite(DojoContentPaneTest.class);
	    suite.addTestSuite(DropdownDatePickerTest.class);
	    suite.addTestSuite(DropdownTimePickerTest.class);
	    suite.addTestSuite(FishEyeTest.class);
	    suite.addTestSuite(NumberSpinnerTest.class);
	    suite.addTestSuite(RuleLabelsTest.class);
	    suite.addTestSuite(RuleMarkTest.class);
	    suite.addTestSuite(SliderTest.class);
	    suite.addTestSuite(TimePickerTest.class);
	    suite.addTestSuite(ToasterTest.class);
	    
	    //Data package
	    suite.addTestSuite(ItemTest.class);
	    suite.addTestSuite(RequestTest.class);
	    suite.addTestSuite(SortFieldTest.class);
	    
	    //Dnd package
	    suite.addTestSuite(DnDTest.class);
	    
	    //Encryption package
	    suite.addTestSuite(BlowFishEncryptionTest.class);
	    suite.addTestSuite(MD5Test.class);
	    
	    //GFX package
	    suite.addTestSuite(CircleTest.class);
	    suite.addTestSuite(ColorTest.class);
	    suite.addTestSuite(EllipseTest.class);
	    suite.addTestSuite(FontTest.class);
	    suite.addTestSuite(GraphicCanvasTest.class);
	    suite.addTestSuite(ImageGfxTest.class);
	    suite.addTestSuite(LineTest.class);
	    suite.addTestSuite(PathTest.class);
	    suite.addTestSuite(PatternTest.class);
	    suite.addTestSuite(PointTest.class);
	    suite.addTestSuite(PolyLineTest.class);
	    suite.addTestSuite(PointTest.class);
	    suite.addTestSuite(RectangleTest.class);
	    suite.addTestSuite(RectTest.class);
	    suite.addTestSuite(TextPathTest.class);
	    suite.addTestSuite(TextTest.class);
	    suite.addTestSuite(VirtualGroupTest.class);
	    
	    //Grid package
	    suite.addTestSuite(CellTest.class);
	    suite.addTestSuite(GridDataStoreTest.class);
	    suite.addTestSuite(GridLayoutTest.class);
	    suite.addTestSuite(GridTest.class);
	    suite.addTestSuite(GridViewTest.class);
	    
	    return suite;
	  }
}
