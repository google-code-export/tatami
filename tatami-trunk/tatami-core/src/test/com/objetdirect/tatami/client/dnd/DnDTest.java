package com.objetdirect.tatami.client.dnd;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.DefaultTatamiTest;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;

import junit.framework.TestCase;

public class DnDTest extends DefaultTatamiTest {

	public void testInternalWidgetDndRegister(){
		Panel panel = new FlowPanel();
		
		//Test Register Source as Source
		WidgetSource source = new WidgetSource(panel);
		WidgetDnDController.getInstance().registerSource(source);
		assertTrue(WidgetDnDController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertFalse(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		//Test Register Source as Target
		WidgetDnDController.getInstance().registerTarget(source);
		assertTrue(WidgetDnDController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertTrue(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		
		//Test Register Target
		Panel panel2 = new FlowPanel();
		WidgetTarget target = new WidgetTarget(panel2);
		WidgetDnDController.getInstance().registerTarget(target);
		assertTrue(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(target) != null);
		
		//Test unregister Source 
		WidgetDnDController.getInstance().unRegisterSource(source);
		assertFalse(WidgetDnDController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertTrue(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		//Test unregister Target(Source)
		WidgetDnDController.getInstance().unRegisterTarget(source);
		assertFalse(WidgetDnDController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertFalse(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		//Test unregister Target(Target)
		WidgetDnDController.getInstance().unRegisterTarget(target);
		assertFalse(WidgetDnDController.getInstance().getJSTargetFromGWTTarget(target) != null);
	}
	
	public void testInternalTreeDndRegister(){
		Tree tree = new Tree();
		RootPanel.get().add(tree);
		//Test Register Source as Source
		TreeSource source = new TreeSource(tree);
		DnDTreeController.getInstance().registerSource(source);
		assertTrue(DnDTreeController.getInstance().getJSSourceFromGWTSource(source)!=null);
		assertFalse(DnDTreeController.getInstance().getJSTargetFromGWTTarget(source)!=null);
		
		//Test Register Source as Target
		DnDTreeController.getInstance().registerTarget(source);
		assertTrue(DnDTreeController.getInstance().getJSSourceFromGWTSource(source)!=null);
		assertTrue(DnDTreeController.getInstance().getJSTargetFromGWTTarget(source)!=null);
		
		//Test Register Target
		TreeTarget target = new TreeTarget(tree);
		assertFalse(DnDTreeController.getInstance().getJSTargetFromGWTTarget(target)!=null);
		DnDTreeController.getInstance().registerTarget(target);
		assertTrue(DnDTreeController.getInstance().getJSTargetFromGWTTarget(target)!=null);
		
		//Test unregister Source 
		DnDTreeController.getInstance().unRegisterSource(source);
		assertFalse(DnDTreeController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertTrue(DnDTreeController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		//Test unregister Target(Source)
		DnDTreeController.getInstance().unRegisterTarget(source);
		assertFalse(DnDTreeController.getInstance().getJSSourceFromGWTSource(source) != null);
		assertFalse(DnDTreeController.getInstance().getJSTargetFromGWTTarget(source) != null);
		
		//Test unregister Target(Target)
		DnDTreeController.getInstance().unRegisterTarget(target);
		assertFalse(DnDTreeController.getInstance().getJSTargetFromGWTTarget(target) != null);
	}
	
	public void testBehaviorScopes(){
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		Panel panel3 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetSource source3 = new WidgetSource(panel3);	
		DnDGenericBehavior<IDnDElement> panel1ToPanel2 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel1ToPanel2, source1, source2);
		} catch (BehaviorScopeException e) {
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source1, source2) == panel1ToPanel2);
		assertTrue(DnDBehaviors.getBehaviorFor(source2, source1) == null);
		DnDGenericBehavior<IDnDElement> panel2ToPanel1 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel2ToPanel1, source2, source1);
		} catch (BehaviorScopeException e) {
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source2, source1) == panel2ToPanel1);
		assertTrue(DnDBehaviors.getBehaviorFor(source1, source2) == panel1ToPanel2);
	}
	
	public void testBehaviorAnyToOne(){
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		Panel panel3 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetSource source3 = new WidgetSource(panel3);	
		DnDGenericBehavior<IDnDElement> anyToPanel3 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(anyToPanel3, null, source3);
		} catch (BehaviorScopeException e) {
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source1, source3) == anyToPanel3);
		assertTrue(DnDBehaviors.getBehaviorFor(source2, source3) == anyToPanel3);
		assertTrue(DnDBehaviors.getBehaviorFor(null, source3) == anyToPanel3);
		assertTrue(DnDBehaviors.getBehaviorFor(source3, source2) == null);
		assertTrue(DnDBehaviors.getBehaviorFor(source3, source1) == null);
	}
	
	public void testBehaviorOneToAny(){
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		Panel panel3 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetSource source3 = new WidgetSource(panel3);	
		DnDGenericBehavior<IDnDElement> panel3ToAny = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel3ToAny, source3 , null);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source1) == panel3ToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source3, source2) == panel3ToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , null) == panel3ToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source2,source3) == null);
		assertTrue(DnDBehaviors.getBehaviorFor(source2, source3) == null);
	}
	
	public void testGlobalBehavior(){
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		Panel panel3 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetSource source3 = new WidgetSource(panel3);	
		DnDGenericBehavior<IDnDElement> anyToAny = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(anyToAny, null , null);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source1) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source3, source2) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source2,source3) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source2, source3) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source1 , source2) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source2 , source1) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source1 , null) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source2 , null) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , null) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(null ,source1 ) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(null ,source2 ) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(null ,source3 ) == anyToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(null , null) == anyToAny);
	}
	
	public void testBehaviorPriority(){
		DnDBehaviors.removeAllBehaviors();
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		Panel panel3 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetSource source3 = new WidgetSource(panel3);	
		
		//Test the source --- > target priority over source --> any
		DnDGenericBehavior<IDnDElement> panel3ToAny = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel3ToAny, source3 , null);
		} catch (BehaviorScopeException e) {
			fail();
		}
		DnDGenericBehavior<IDnDElement> panel3ToPanel1 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel3ToPanel1, source3 , source1);
		} catch (BehaviorScopeException e) {
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source1) == panel3ToPanel1);
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source2) == panel3ToAny);
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source3) == panel3ToAny);
		
		//Test the source-->null , null-->target ambiguity detection
		DnDGenericBehavior<IDnDElement> anyToPanel2 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(anyToPanel2, null , source2);
			fail();
		} catch (BehaviorScopeException e) {
		}
		
		//Test to remove the ambiguity by adding a more precise scope
		try{
			DnDBehaviors.addScopeToBehavior(anyToPanel2, source3, source2);
		}catch (BehaviorScopeException e){
			fail();
		}
		try {
			DnDBehaviors.addScopeToBehavior(anyToPanel2, null , source2);
		} catch (BehaviorScopeException e) {
			fail();
		}
		
		DnDGenericBehavior<IDnDElement> panel1ToPanel2 = new DnDGenericBehavior<IDnDElement>(){};
		try {
			DnDBehaviors.addScopeToBehavior(panel1ToPanel2, source1 , source2);
		} catch (BehaviorScopeException e) {
			fail();
		}
		assertTrue(DnDBehaviors.getBehaviorFor(source1 , source2) == panel1ToPanel2);
		assertEquals(anyToPanel2 , DnDBehaviors.getBehaviorFor(source3 , source2));
		assertTrue(DnDBehaviors.getBehaviorFor(source3 , source3) == panel3ToAny);
	}
	
	public void testBehaviorCall(){
		Panel panel1 = new FlowPanel();
		Panel panel2 = new FlowPanel();
		WidgetSource source1 = new WidgetSource(panel1);
		WidgetSource source2 = new WidgetSource(panel2);
		WidgetDnDController.getInstance().registerSource(source1);
		WidgetDnDController.getInstance().registerTarget(source2);
		final JavaScriptObject jssource1 = WidgetDnDController.getInstance().getJSSourceFromGWTSource(source1);
		final JavaScriptObject jssource2 = WidgetDnDController.getInstance().getJSTargetFromGWTTarget(source2);
		
		class MyDnDBehavior extends WidgetDnDBehavior{
			public boolean checkSourceCalled = false;
			public boolean checkTargetCalled = false;
			public boolean elementsAcceptedCalled = false;
			public boolean onDropCalled = false;
			
			@Override
			public boolean checkSourceItemAcceptance(Panel target,
					Panel source, Collection<Widget> draggedWidgets) {
				checkSourceCalled = true;
				return true;
			}

			@Override
			public boolean checkTargetItemAcceptance(Panel target,
					Panel source, Collection<Widget> draggedWidgets) {
				checkTargetCalled = true;
				return true;
			}

			@Override
			public void elementsAccepted(Panel source, Panel target,
					Collection<Widget> draggedWidgets, boolean copied) {
				elementsAcceptedCalled = true;
			}

			@Override
			public boolean onDrop(Collection<Widget> draggedWidgets,
					Panel source, Panel target, String targetNodeId,
					boolean isCopy) {
				onDropCalled = true;
				return true;
			}
		}
		
		final MyDnDBehavior behavior = new MyDnDBehavior();
		try {
			DnDBehaviors.addScopeToBehavior(behavior, source1, source2);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
			fail();
		}
		DnDMainController.getInstance().onDndDrop(jssource1, jssource2, null, "id", false);
		DnDMainController.getInstance().checkAcceptance(jssource1, jssource2, null);
		Timer timer = new Timer() {
			@Override
			public void run() {
				assertTrue(behavior.checkSourceCalled);
				assertTrue(behavior.checkTargetCalled);
				assertTrue(behavior.elementsAcceptedCalled);
				assertTrue(behavior.onDropCalled);
				finishTest();
			}
		};
		this.delayTestFinish(700);
		timer.schedule(100);
		
	}
	
	@Override
	public void gwtTearDown(){
		DnDMainController.getInstance().destroyDnDEngine();
		DnDBehaviors.removeAllBehaviors();
		try {
			super.gwtTearDown();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void gwtSetUp(){
		try {
			super.gwtSetUp();
		} catch (Exception e) {
			e.printStackTrace();
		}
		DnDBehaviors.removeAllBehaviors();
	}
	
}
