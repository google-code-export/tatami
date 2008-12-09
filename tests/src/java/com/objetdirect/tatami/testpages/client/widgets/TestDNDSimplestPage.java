/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@googlegroups.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307
 * USA
 *
 * Authors:  Ronan Dunklau
 * Initial developer(s): Ronan Dunklau
 * Contributor(s):
 */
package com.objetdirect.tatami.testpages.client.widgets;



import java.util.Collection;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.dnd.DnD;
import com.objetdirect.tatami.client.dnd.DnDDefaultWidgetBehavior;
import com.objetdirect.tatami.client.dnd.DnDGenericBehavior;
import com.objetdirect.tatami.client.dnd.DndTreeElement;
import com.objetdirect.tatami.client.dnd.IDnDController;
import com.objetdirect.tatami.client.dnd.IDnDElement;
import com.objetdirect.tatami.client.dnd.IDnDSource;
import com.objetdirect.tatami.client.dnd.IDnDTarget;
import com.objetdirect.tatami.client.dnd.WidgetDnDBehavior;
import com.objetdirect.tatami.client.dnd.DnDBehaviors.BehaviorScopeException;
import com.objetdirect.tatami.client.tree.Tree;
import com.objetdirect.tatami.testpages.client.TestPage;

public class TestDNDSimplestPage extends TestPage{

	public TestDNDSimplestPage(){
		super(TestDNDSimplestPage.class.getName(), "Test DnD Simple");
	}
	
	
	public Widget getTestPage() {
		FlowPanel container = new FlowPanel();
		VerticalPanel sourcePanel = new VerticalPanel();
		HTML widget1 = new HTML("I can be dragged");
		HTML widget2 = new HTML("I can be dragged too");
		sourcePanel.add(widget1);
		sourcePanel.add(widget2);
		
		DnD.registerSource(sourcePanel);
		DnD.registerElement(sourcePanel,widget1);
		DnD.registerElement(sourcePanel,widget2);
		
		VerticalPanel targetPanel = new VerticalPanel();
		HTML widget3 = new HTML("I cannot be dragged");
		HTML widget4 = new HTML("Me neither"); 
		targetPanel.add(widget3);
		targetPanel.add(widget4);
		DnD.registerTarget(targetPanel);
		WidgetDnDBehavior myBehavior = new DnDDefaultWidgetBehavior() {
			@Override
			public boolean onDrop(Collection<Widget> draggedWidgets,
					Panel source, Widget target, String targetNodeId,
					boolean isCopy) {
				for (Widget widget : draggedWidgets) {
					DnD.move(widget, source, (Panel)target);
				}
				return true;
			}
		};
		try {
			DnD.registerBehavior(myBehavior, sourcePanel, targetPanel);
		} catch (BehaviorScopeException e) {
			e.printStackTrace();
		}
		container.add(targetPanel);	
		container.add(sourcePanel);
		
		sourcePanel.setBorderWidth(3);
		targetPanel.setBorderWidth(3);
		return container;
	}
	
	
}
