/*
 * Tatami: 
 * Copyright (C) 2007 Objet Direct
 * Copyright (C) 2007 France Telecom
 * Contact: tatami@objectweb.org
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
 * Authors: Henri Darmet
 * Initial developer(s):
 * Contributor(s):
 */
package com.objetdirect.tatami.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import com.google.gwt.core.client.JavaScriptObject;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbsolutePanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * <p>
 * Panel allowing the operations of Drag and Drop on the child widgets. 
 * This panel positions the widgets with an absolute way(by specifying their position in X, Y). 
 * The mechanism of Drag and Drop used here defines two capacities : 
 * <ul>
 * 		<li> a widget can be "Draggable". In that case, an user can drag it with the mouse 
 *           to deposit it on a target target. If the widget is not deposited on a target, 
 *           it comes back to its previous place. 
 * 
 * 		<li> a widget can be target. In that case, it is possible to deposit widgets on them.
 *            A target can accept an indeterminate number of draggable widgets. 
 * </ul>
 * <p> All combinations are possible. A widget can  be:
 * <ul>
 * 		<li> no draggable,  no t a target
 * 		<li> draggable
 * 		<li> target
 * 		<li> target and draggable
 * </ul> 
 * <p> 
 *    In every faculty  is linked one (at least) or several affordance .
 *    For that a draggable widget can be deposited on a target, 
 *     it has to  share at least an affordance with it. An affordance
      is defined by a String which names it. 
 
 * <p> It is possible dynamically to add or to remove affordances in one of the faculties of a given widget 
 * 
 * <ul>
 * 		<li> if at least an affordance is defined for a faculty, the widget acquires faculty 
 * 		<li> if all affordances are removed from a faculty, the widget loses this faculty. 
 *		<li> a widget which is removed from the panel loses all these faculties (with affordances!)
 * 		<li> the widgets which are at the same time draggable and target can have a list of affordances for the "draggable" faculty different 
 *           from the list of affordances for "target" faculty. 
 * 
 * </ul>
 * <p> The mechanism of Drag and Drop can also be controlled of a programmatic way by recording to the panel 
 *     some objects in the listening of the event of drop. These objects have to implement interface DragAndDropListener. 
 *     The treatment of an event of drop is done in two points 
 * 
 * <ul>
 * 		<li> Objects in listening are solicited a first time to point out if they accept the drop. 
 *          If at least an object in listening refuses the drop, the registered widget returns to its initial place. 
 * 		<li> 
 *           If all objects in listening accepted the drop, they are solicited a second time to "execute" 
 *           the drop. It allows for them to accomplish specific treatments in application, to change position or characteristics 
 *           of a widget (including the registered widget).
 * </ul>
 * <p> This component uses the library YUI (Yahoo UI). Scripts yahoo.js, dom.js, event.js, dragdraop.js are requiered. 
 *     It also uses a version altered by the script of example DDPlayer.js. Attention: this script was changed 
 *     for the good of this component. Do not replace it in with a more recent version of YUI 
 *     without having brought necessary adjustments.

 * <p> Some comments on the implemtation:
 * <ul>
 * 		<li> So that a draggable widget can be visible, a base with a "div" for it has to be created
 *          (YUI takes the contents of the element DOM to be moved to fabricate the ghost, 
 *          and not the element DOM itself !).  
 *           So any addition of a widget within the panel gives rise to hidden creation of a  SimplePanel 
 *           which acts as intermediary (even if the widget is not - still? - draggable)
 * 		<li> The "draggable" faculty of a widget is always defined "before " target faculty 
 *             (it is therefore necessary to create DDPlayer always before of DDTarget).
 * 		<li> YUI does not know how to remove an object of management of Drag and Drop in a selective way: 
 *           it removes ALL DDProxies systematically (DDTarget or DDPlayer) of a widget 
 *           when they call dd.unreg. It is therefore necessary to "rebuild " 
 *           possibly the faculty eradicated in a untimely way. 
 * 
 * </ul>
 * @author Henry
 *
 */
public class DragAndDropPanel extends AbsolutePanel {

	/**
	 * 
	 * Map linking the widgets of the panel, in the objects of management of the slippery faculty of 
	 * the drag and drop (DDPlayer of YUI). Widgets is the keys of the map, value being objects
	 *  DDRecord which besides the suitable objects DDPlayer, contain the list of linked affinity.
	 */
	Map draggables = new HashMap();
	/**
	 *
	 * Map linking the widgets of the panel, in the objects of management of the target faculty of the drag and drop 
	 * (DDTarget of YUI). Widgets is the keys of the map, values being objects DDRecord which besides the suitable objects DDTarget, 
	 * contain the list of linked affinity.
	 */
	Map targets = new HashMap();
	/**
	 * Map linking affinity with the widgets which use them in their faculty " draggable". 
	 * Keys are affinity (String ) and values  are set of widgets.
	 */
	Map draggableAffordances = new HashMap();
	
	/**
	 * Map linking affinity with the widgets which use them in their faculty " of target ". 
	 * Keys are affinity (String ) and values  are set of widgets.
	 */
	Map targetAffordances = new HashMap();
	
	/**
	 * Map linking the widgets with their base (instance of SimplePanel)The keys are widgets, values are the bases
	 * 
	 */
	Map bases = new HashMap();
	
	/**
	 * Map linking the DOM element of the bases with the widget that they contain. This DOM elements of the bases  
	 * are attached to the YUI object for the management of the drag and drop. These are the elements which are recognized and 
	 * and returned by YUI 
	 * 
	 */
	Map widgets = new HashMap();

    private static final String ERROR_NOT_BELONG  = "The widget does not belong to the panel";
    
    private static final String ERROR_ALREADY_REGISTERED  = "Widget already registered in panel";
    /**
     * Message error when a affordance is null
     */
    private static final String ERROR_AFFORDANCE = "Affordance can't be null";
	
	/**
	 * Structure which links an object of management of Drag and Drop (DDTarget or DDPlayer) 
	 * for a widget and the list of affinity which this object of management uses.
	 */
	static class DDRecord {
		/**
		 * Object to manage the drag and drop for a widget.
		 */
		JavaScriptObject dd;
		/**
		 * Liste of affordance used by dd
		 */
		List affordances = new ArrayList();
	}
	
	/**
	 * Creates the panel. The initialization of the mechanism of Drag and drop 
	 * is done if it's necessary
	 */
	public DragAndDropPanel() {
		super();
		initDragAndDrop();
	}
	
	/**
	 * Adds a widget to the panel. The faculty of being "draggable" is added also to the widget.
	 * This doesn't be call for a widget which is already added to the panel.
	 * @param widget widget widget to add
	 * @param left initial left position of the widget.
	 * @param top initial top position of the widget.
	 * @param affordance first affordance 
	 */
	public void addDraggableWidget(Widget widget, int left, int top, String affordance) {
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE); 
		}
		if (bases.get(widget)!=null) {
			throw new IllegalStateException(ERROR_ALREADY_REGISTERED);
		} else {
			add(widget, left, top);
			setDraggable(widget, affordance);
		}
	}

	/**
	 * Adds a widget to the panel. The faculty of being "target" is added also to the widget.
	 * This doesn't be call for a widget which is already added to the panel.
	 * @param widget widget widget to add
	 * @param left initial left position of the widget.
	 * @param top initial top position of the widget.
	 * @param affordance first affordance 
	 */
	public void addTargetWidget(Widget widget, int left, int top, String affordance) {
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		} 
		if (bases.get(widget)!=null) {
			throw new IllegalStateException(ERROR_ALREADY_REGISTERED); 
   	    } else {
			add(widget, left, top);
			setTarget(widget, affordance);
		}
	}
	
	/**
	 * Updates the internal structure of the panel to take into account the addition of an affinity
	 * in a faculty (who can be "draggable " or "target") of a widget.
	 * @param widget widget receiving the new affordance
	 * @param ddr DDRecord modelling the faculty
	 * @param affordances Map linking affordance and set of widgets
	 * @param affordance new affordance
	 * @return <code>true</code> if a new affordance is added, <code>false</code> otherwise (It's means 
	 * the affordance already existed for the faculty
	 */
	protected boolean addAffordance(Widget widget, DDRecord ddr, Map affordances, String affordance) {
		boolean result = false;
		Set widgetSet = (Set)affordances.get(affordance);
		if (widgetSet==null) {
			widgetSet = new HashSet();
			affordances.put(affordance, widgetSet);
		}
		if (widgetSet.contains(widget)) {
			result =  false;
		}else {
			widgetSet.add(widget);
			ddr.affordances.add(affordance);
			result= true;
		}
		return result;
	}

	/**
	 * Updates the internal structure of the panel to take into account the addition of an affinity
	 * in a faculty (who can be "draggable " or "target") of a widget.
	 * @param widget widget removing the  affordance
	 * @param ddr DDRecord modelling the faculty
	 * @param affordances Map linking affordance and set of widgets
	 * @param affordance  affordance to remove
	 * @return <code>true</code> if a new affordance is removed, <code>false</code> otherwise (It's means 
	 * the affordance didn't  exist for the faculty)
	 */
	protected boolean removeAffordance(Widget widget, DDRecord ddr, Map affordances, String affordance) {
		boolean result = false;
		Set widgetSet = (Set)affordances.get(affordance);
		if (widgetSet!=null) {
			widgetSet.remove(widget);
			if (widgetSet.isEmpty()) {
				affordances.remove(affordance);
			}
			ddr.affordances.remove(affordance);
			result =  true;
		}
		return result;
	}
	
	/**
	 * Sets a widget draggable. If the widget was already draggable, this method add a new affordance
	 * @param widget widget which becomes draggable or receives a new affordance. This widget has to be added to the panel first 
	 * @param affordance new affordance
	 */
	public void setDraggable(Widget widget, String affordance) {
		if (bases.get(widget)==null) {
			throw new IllegalStateException(ERROR_NOT_BELONG);
		} if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		DDRecord ddr = (DDRecord)draggables.get(widget);
		if (ddr!=null) {
			if (addAffordance(widget, ddr, draggableAffordances, affordance)) {
				if (isAttached()) {
					addAffordance(ddr.dd, affordance); 
								} 
				}
		} 	else {
			ddr = new DDRecord();
			if (addAffordance(widget, ddr, draggableAffordances, affordance)) {
				draggables.put(widget, ddr);
				DDRecord ddt = (DDRecord)targets.get(widget);
				if (ddt!=null) {// cas particulier pour contourner un bug de YUI : la faculté 
							   // glissante doit toujours être définie "avant" la faculté cible.
					rebuild(widget, ddt.dd);
				}else {
					if (isAttached()) {
						JavaScriptObject dd = addDraggableSlot(widget, affordance);
						setGWTWidget(dd, this);
						ddr.dd = dd;
					}
				}			
			}
		}
	}

	/**
	 * Sets a widget as target for drop. If the widget was already a target, this method add a new affordance
	 * @param widget widget which becomes a target or receives a new affordance. This widget has to be added to the panel first 
	 * @param affordance new affordance
	 */
	public void setTarget(Widget widget, String affordance) {
		if (bases.get(widget)==null) {
			throw new IllegalStateException(ERROR_NOT_BELONG);
		} 
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
	    }
		DDRecord ddr = (DDRecord)targets.get(widget);
		if (ddr!=null) {
			if (addAffordance(widget, ddr, targetAffordances, affordance)) {
				if (isAttached()) {
					addAffordance(ddr.dd, affordance);
					}
			}
		} 	else {
			ddr = new DDRecord();
			if (addAffordance(widget, ddr, targetAffordances, affordance)) {
				if (isAttached()) {
					JavaScriptObject dd = addTargetSlot(widget, affordance);
					ddr.dd = dd;
				}
				targets.put(widget, ddr);
			}
		}
	}

	/**
	 * Removes an affordance of the draggable faculty of a widget. If this is the  last affordance draggable of the widget, this last one lost  
	 * the draggable faculty.
	 * @param widget the widget who loses an affordance. This widget has to be added to the panel first.
	 * @param affordance affordance to remove.
	 */
	public void unsetDraggable(Widget widget, String affordance) {
		if (bases.get(widget)==null) { 
			throw new IllegalStateException(ERROR_NOT_BELONG);
		} if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		DDRecord ddr = (DDRecord)draggables.get(widget);
		if (ddr!=null) {
			if (!removeAffordance(widget, ddr, draggableAffordances, affordance)) {
				throw new NoSuchElementException("Unknown affordance : "+affordance);
			} else {
				if (ddr.affordances.isEmpty()) {
					unsetDraggable(widget);
				} else { 
					if (isAttached()) {
						removeAffordance(ddr.dd, affordance);
					}
				}
			}
		}
	}
	
	/**
	 * Removes an affordance of the target faculty of a widget. If this is the  last affordance target of the widget, this last one lost  
	 * the draggable faculty.
	 * @param widget the widget who loses an affordance. This widget has to be added to the panel first.
	 * @param affordance affordance to remove.
	 */
	public void unsetTarget(Widget widget, String affordance) {
		if (bases.get(widget)==null) {
			throw new IllegalStateException(ERROR_NOT_BELONG);
		} if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		DDRecord ddr = (DDRecord)targets.get(widget);
		if (ddr!=null) {
			if (!removeAffordance(widget, ddr, targetAffordances, affordance)) {
				throw new NoSuchElementException("Unknown affordance : "+affordance);
			} 	else {
				if (ddr.affordances.isEmpty()) {
					unsetTarget(widget);
				} else {
 					if (isAttached()) {
						removeAffordance(ddr.dd, affordance);
 					}
				}
			}
		}
	}

	/**
	 * Removes the draggable faculty of a widget.
	 * @param widget widget who loses the faculty
	 */
	public void unsetDraggable(Widget widget) {
		if (bases.get(widget)==null) {
			throw new IllegalStateException(ERROR_NOT_BELONG);
		}
		DDRecord ddr = removeWidgetFromDragOrDrop(widget, draggables, draggableAffordances);
		if (ddr!=null && isAttached()){
			rebuild(widget, ddr.dd);
		}
	}
	
	/**
	 * Removes the target faculty of a widget.
	 * @param widget widget who loses the faculty
	 */
	public void unsetTarget(Widget widget) {
		if (bases.get(widget)==null) {
			throw new IllegalStateException(ERROR_NOT_BELONG);
		}
		DDRecord ddr = 	removeWidgetFromDragOrDrop(widget, targets, targetAffordances);
		if (ddr!=null && isAttached()) {
			rebuild(widget, ddr.dd);
		}
	}

	/**
	 * Updates the DDrecord structure of the panel when a widget loses  a complete faculty (It means all  
	 * the affordances of the faculty).
	 * @param widget widget who loses the  faculty
	 * @param widgetMap Map linking the DOM element of the widgets with the objects of management  
	 *        for the YUI Drag and Drop.
	 * @param affordances Map linking affordances and set of widgets for a faculty
	 * @return the updated DDrecord
	 */
	protected DDRecord removeWidgetFromDragOrDrop(Widget widget, Map widgetMap, Map affordances) {
		DDRecord ddr = (DDRecord)widgetMap.remove(widget);
		int length = ddr.affordances.size();
		for (int i=0; i<length; i++) {
			String affordance = (String)ddr.affordances.get(i);
			Set widgetSet = (Set)affordances.get(affordance);
			widgetSet.remove(widget);
			if (widgetSet.isEmpty()) {
				affordances.remove(affordance);
			}
		}
		return ddr;
	}
		
	/**
	 * Reconstruit complètement les objets YUI de gestion du Drag and Drop, pour une widget donnée. Cette
	 * méthode est nécessaire pour contourner deux bugs/limitations de YUI :
	 * <ul>
	 * 		<li> YUI ne sait pas retirer isolément une faculté à une widget
	 * 		<li> La faculté glissante doit être créée avant la faculté cible
	 * <ul>
	 * @param widget widget dont les objets de gestion du Drag and Drop doivent être reconstruit.
	 * @param ddt un des objets de gestion du Drag and Drop associé à la widget (c'est à partir de lui que
	 * la fonction unreg de YUI sera appelée).
	 */
	protected void rebuild(Widget widget, JavaScriptObject ddt) {
		if (ddt!=null) {
			removeSlot(ddt);
		}
		{
			DDRecord ddr = (DDRecord)draggables.get(widget);
			if (ddr!=null) {
				ddr.dd = addDraggableSlot(widget, (String)ddr.affordances.get(0));
				setGWTWidget(ddr.dd, this);
				int length=ddr.affordances.size();
				for (int i=1; i<length; i++) {
					addAffordance(ddr.dd, (String)ddr.affordances.get(i));
				}
			}
		}
		{
			DDRecord ddr = (DDRecord)targets.get(widget);
			if (ddr!=null) {
				ddr.dd = addTargetSlot(widget, (String)ddr.affordances.get(0));
				int length=ddr.affordances.size();
				for (int i=1; i<length; i++) {
					addAffordance(ddr.dd, (String)ddr.affordances.get(i));
				}
			}
		}
	}
	
	/**
	 * This method rebuilds the objects DDPlayer and DDProxies when the GWT is attached on the browser.
	 */
	protected void onAttach() {
		rebuildAll();
		super.onAttach();
	}
	
	/**
	 * When the GWT widget is detached from the browser, the DDProxies and DDPlayer are destroyed.
	 */
	protected void onDetach() {
		removeAll();
		super.onDetach();
	}

	/**
	 * Rebuilds all the DDPlayer and DDProxies those are necessaries for DragAndDropPanel in order that the 
	 * widget they contain can be targets or sources.

	 */
    protected void rebuildAll() {
		Iterator it = draggables.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			Widget widget = (Widget)entry.getKey();
			DDRecord ddr = (DDRecord)entry.getValue();
			rebuild(widget, ddr.dd);
			setGWTWidget(ddr.dd, this);
		}
		it = targets.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry entry = (Map.Entry)it.next();
			Widget widget = (Widget)entry.getKey();
			DDRecord ddr = (DDRecord)entry.getValue();
			rebuild(widget, ddr.dd);
		}
    }
	
	/**
	 * Destroys all the DDPlayer and DDProxies those are necessaries for DragAndDropPanel in order that the 
	 * widget they contain can be targets or sources.
	 */
    protected void removeAll() {
		Iterator it = draggables.values().iterator();
		while (it.hasNext()) {
			DDRecord ddr = (DDRecord)it.next();
			setGWTWidget(ddr.dd, null);
			removeSlot(ddr.dd);
		}
		it = targets.values().iterator();
		while (it.hasNext()) {
			DDRecord ddr = (DDRecord)it.next();
			removeSlot(ddr.dd);
		}
    }

    /**
	 * Indicates if a widget has the faculty  "draggable"
	 * @param widget widget questioned
	 * @return <code>true</code> if the widget is draggable, <code>false</code> otherwise.
	 */
	public boolean isDraggable(Widget widget) {
		return draggables.get(widget)!=null;
	}

	/**
	 * Indicates if a widget has an affordance given in the faculty "draggable".
	 * @param widget widget questioned
	 * @param affordance affordance to search
	 * @return <code>true</code> if the widget is draggable and contains the affordance searched for the faculty.
	 *  <code>false</code> otherwise.
	 */
	public boolean hasDraggableAffordance(Widget widget, String affordance) {
		boolean result = false;
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		DDRecord ddr = (DDRecord)draggables.get(widget); 
		if (ddr!=null) {
			result =  ddr.affordances.contains(affordance);
		}
	    return result;
	}
	
	/**
	 * Returns the list  of the affordances linked to the faculty draggable of a widget.
	 * @param widget  widget questioned
	 * @return the list in a array of String linked to the faculty draggable of the widget.
	 *         If the widget is not draggable, returns <code>null</code>.
	 */
	public String[] getDraggableAffordances(Widget widget) {
		DDRecord ddr = (DDRecord)draggables.get(widget);
		String[] affordances = null;
		if (ddr != null) {
			affordances = new String[ddr.affordances.size()];
			for (int i=0; i<ddr.affordances.size(); i++) {
				affordances[i] = (String)ddr.affordances.get(i);
			}
			
		}
		return affordances;
	}
	
	/**
	 * Returns the list of widgets draggable which contains a specific affordance for this faculty.
	 * @param affordance affordance to search.
	 * @return the list of the widgets in an array of <code> Widget</code> containing this affordance in their draggable faculty.
	 * If no widgets contain this affordance in their draggable faculty, this method returns 
	 * <code>null</code>.
	 */
	public Widget[] getDraggableAffordanceSet(String affordance) {
		Widget[] widgetArray = null;
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		Set widgetSet = (Set)draggableAffordances.get(affordance);
		if (widgetSet != null) { 
		   widgetArray = new Widget[widgetSet.size()];
		   int i=0;
		   Iterator it = widgetSet.iterator();
		   while (it.hasNext()) {
		      widgetArray[i++] = (Widget)it.next();
		   }
		   
		}
		return widgetArray;
	}  

	/**
	 * Indicates if a widget has the faculty "target".
	 * @param widget widget questioned.
	 * @return <code>true</code> if the widget has the faculty target, <code>false</code> otherwise.
	 */
	public boolean isTarget(Widget widget) {
		return targets.get(widget)!=null;
	}

	/**
	 * Indicates if a widget contains the given affordance in its faculty "target".
	 * @param widget widget questioned
	 * @param affordance affordance to search
	 * @return <code>true</code> if the widget can be a target for drop and contains the affordance to search
	 *  <code>false</code> otherwise.
	 */
	public boolean hasTargetAffordance(Widget widget, String affordance) {
		boolean result = false;
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		DDRecord ddr = (DDRecord)targets.get(widget);
		if (ddr != null) {
			result =  ddr.affordances.contains(affordance);
		}
		return result;
	}

	/**
	 * Returns the list of affordances linked to the faculty "target" of a widget.
	 * @param widget widget questioned 
	 * @return the list of affordances in a array os String. If the widget  
	 * widget can't be a target for a drop, this method returns <code>null</code>.
	 */
	public String[] getTargetAffordances(Widget widget) {
		String[] affordances = null;
		DDRecord ddr = (DDRecord)targets.get(widget);
		if (ddr != null) {
			affordances = new String[ddr.affordances.size()];
			for (int i=0; i<ddr.affordances.size(); i++) {
				affordances[i] = (String)ddr.affordances.get(i);
			}
			
		}
		return affordances; 
	}
	
	/**
	 * Returns the list of widgets which can be target for a drop and contains a specific affordance. 
	 * @param affordance affordance to search.
	 * @return the list of the widgets in an arrao <code>Widget</code> containing this affordance.If no widgets 
	 * contain this affordance, this method returns <code>null</code>.
	 */
	public Widget[] getTargetAffordanceSet(String affordance) {
		Widget[] widgetArray = null;
		if (affordance==null) {
			throw new IllegalArgumentException(ERROR_AFFORDANCE);
		}
		Set widgetSet = (Set)targetAffordances.get(affordance);
		if (widgetSet!=null) { 
		   widgetArray = new Widget[widgetSet.size()];
		   int i=0;
		   Iterator it = widgetSet.iterator(); 
		   while (it.hasNext()) {
			 widgetArray[i++] = (Widget)it.next();
		   }
		}
		return widgetArray;
	}

	/**
	 * Asks to YUI to remove all the objects of management for  Drag and Drop for a given widget.
	 * @param dd one of the objects of management for  Drag and Drop that we need to remove
	 */
	protected static native void removeSlot(JavaScriptObject dd)	
	/*-{
		dd.unreg();
	}-*/;

	/**
	 * Returns the widget which corresponds to a given DOM element. (This element has to be the DOM element linked 
	 * to the SimplePanel which serves of base for the panel). The widget has to be added to the panel first. 
	 * @param e DOM element that we search thje GWT widget 
	 * @return the widget linked to the DOM element given in parameter.
	 */
	protected Widget getWidget(Element e) {
		return (Widget)widgets.get(e);
	}
		
	/**
	 * Allocates an ID to the DOM object given in parameter.
	 * @param e DOM element to whom it is necessary to allocate an ID.
	 * @param counter the ID to allocate
	 */
	protected static native void initId(Element e,int counter)
	/*-{
		e.id = 'd_'+counter;
	}-*/;

	/**
	 * Creates DDPlayer object and allocates it to a widget (to the DOM element of the base of the widget, 
	 * to be more definite)
	 * @param widget
	 * @param affordance the first affordance which has to be linked to the DDPlayer
	 * @return the DDPlayer created
	 */
	protected JavaScriptObject addDraggableSlot(Widget widget, String affordance) {
		SimplePanel base = (SimplePanel)bases.get(widget);
		return addDraggableSlotToElement(base.getElement(), affordance);
	}
	
	/**
	 * Creates DDPlayer object and allocates it to a DOM element 
	 * @param e DOM element which is going to become draggable
	 * @param affordance the first affordance which has to be linked to the DDPlayer.
	 * @return the DDPlayer created
	 */
	protected static native JavaScriptObject addDraggableSlotToElement(Element e,String affordance) 
	/*-{
		var slot = new $wnd.YAHOO.tatami.DDDraggable(e.id, affordance);
		slot.gwtWidget = null;
		return slot;
	}-*/;

	/**
	 * Creates a DDTarget object and allocates it to a widget (to the DOM element of the base of the widget, 
	 * to be more definite)
	 * @param widget
	 * @param affordance first affordance which has to be associated to the DDTarget object.
	 * @return an instance of  DDTarget object.
	 */
	protected JavaScriptObject addTargetSlot(Widget widget, String affordance) {
		SimplePanel base = (SimplePanel)bases.get(widget);
		return addTargetSlotToElement(base.getElement(), affordance);
	}

	/**
	 * Creates a DDTarget obejct and allocate it to a DOM element. 
	 * @param e DOM element which is going to become "dragging"
	 * @param affordance first affordance which has to be associated to the DDTarget object.
	 * @return an instance of  DDTarget object.
	 */
	protected static native JavaScriptObject addTargetSlotToElement(Element e,String affordance) 
	/*-{
		var slot = new $wnd.YAHOO.util.DDTarget(e.id, affordance);
		return slot;
	}-*/;

	/**
	 * Adds affordance of DDPlayer or DDTarget object 
	 * @param dd DDPlayer or DDTarget object which an affordance will be added.
	 * @param affordance affordance to add
	 */
	protected static native void addAffordance(JavaScriptObject dd, String affordance) 
	/*-{
		dd.addToGroup(affordance);
	}-*/;
	
	/**
	 * Removes affordance of DDPlayer or DDTarget object 
	 * @param dd DDPlayer or DDTarget object which an affordance will be removed.
	 * @param affordance affordance to remove
	 */
	protected static native void removeAffordance(JavaScriptObject dd, String affordance) 
	/*-{
		dd.removeFromGroup(affordance);
	}-*/;

	/**
	 * Counter used to create id for DOM element associated to the widgets of the panel 
	 **/
	int counter;
	
	/**
	 * List of the objects listening the drop events
	 * This objet have to implement the interface <code>DragAndDropListener</code>.
	 */
	List listeners = new ArrayList();

	
	/**
	 * Adds a listener interface to receive drag and drop events.
	 * @param listener the listener interface to add
	 */
	public void addDragDropListener(DragAndDropListener listener) {
		listeners.add(listener);
	}
	
	
	 /**
	 * Removes a previously added listener interface.
	 * @param listener the listener interface to remove
	 */
    public void removeDragDropListener(DragAndDropListener listener) {
		listeners.remove(listener);
	}

	/**
	 * Calls all the objects listening the drop event and asks them if they accept the drop 
	 * (in invoking their method <code>DragAndDropListener.acceptDrop</code>).
	 * @param e DOM element dragged and droped
	 * @param t DOM element target of the drop
	 * @return <code>true</code> if all the object listening accepted the drop <code>false</code> otherwise.
	 */
	protected boolean acceptDrop(Element e, Element t) {
		Widget draggable = getWidget(e);
		Widget target = getWidget(t);
		boolean accept = true;
		Object[] lst = listeners.toArray();
		for (int i=0; i<lst.length; i++) {
			accept &= ((DragAndDropListener)lst[i]).acceptDrop(draggable, target);
		}
		return accept;
	}

	/**
	 * Calls all the objects listening the drop event and asks them to take in count 
	 * the drop (in invoking their method  <code>DragAndDropListener.onDrop</code>).
	 * @param e DOM element dragged and droped
	 * @param t DOM element target of the drop
	 */
	protected void dispatchDrop(Element e, Element t) {
		Widget draggable = getWidget(e);
		Widget target = getWidget(t);
		Object[] lst = listeners.toArray();
		for (int i=0; i<lst.length; i++) {
			((DragAndDropListener)lst[i]).onDrop(draggable, target);
		}
	}

	/**
	 * Adds a widget to the panel. If this method is called, the widget is inert : it can't neither be dragged
	 * neither be droped (but it will able to get this faculties later). This method create an invisible  base
	 * will act as intermediary between the panel and the widget.
	 * @param widget widget to add in the panel.
	 * @param left initial left position of the widget on the panel
	 * @param top  initial top position of the widget on the panel
	 */
	public void add(Widget widget, int left, int top) {
		SimplePanel base = (SimplePanel)bases.get(widget);
		if (base!=null) {
			throw new IllegalStateException(ERROR_ALREADY_REGISTERED);
		}else {
			base = new SimplePanel();
			bases.put(widget, base);
			base.add(widget);
			super.add(base, left, top);
			initId(base.getElement(), counter++);
			widgets.put(base.getElement(), widget);	
		}
	}
	
	/**
	 * Removes a widget from the panel. The widget losts automatically all its faculties of Drag and Drop.
	 * The base of the widget is destroy.
	 * @param widget widget to remove from the panel
	 */
	public boolean remove(Widget widget) {
		SimplePanel base = (SimplePanel)bases.get(widget);
		if (base==null) {
			throw new IllegalStateException(ERROR_ALREADY_REGISTERED);
		} else {
			unsetDraggable(widget);
			unsetTarget(widget);
			widgets.remove(base.getElement());
			SimplePanel panel = (SimplePanel)bases.get(widget);
			return super.remove(panel);
		}
	}

	/**
	 * Returns the left position of the widget given in parameter. This method is overriden in order that this position 
	 * position is asked to the base (which the "super-implmentation" of the panel knows) and not to widget itself
	 * (which the "super-implementation" of the panel doesn't know). 
	 * @param widget widget to get the position
	 */
	public int getWidgetLeft(Widget widget) {
		return super.getWidgetLeft((Widget)bases.get(widget));
	}
	
	/**
	 * Returns the top position of the widget given in parameter. This method is overriden in order that this position 
	 * position is asked to the base (which the "super-implmentation" of the panel knows) and not to widget itself
	 * (which the "super-implementation" of the panel doesn't know). 
	 * @param widget widget to get the position
	 */
	public int getWidgetTop(Widget widget) {
		return super.getWidgetTop((Widget)bases.get(widget));
	}

	/**
	 * Establishes or breaks the link that is going from a JavaScript slot and arriving on the GWT widget. 
	 * @param slot DDProxy attached to a widget of the panel
	 * @param gwtWidget the panel
	 */
	protected static native void setGWTWidget(JavaScriptObject slot,Widget gwtWidget)
	/*-{
		slot.gwtWidget = gwtWidget;
	}-*/;
	
	/**
	 * Initializes the mechanism of the drag and dop YUI
	 */
	protected static void initDragAndDrop() {
		if (!dragAndDropLoaded) {
			loadDragAndDrop();
			dragAndDropLoaded = true;
		}
	}
	
	/**
	 * Indicates if the mechanisme of drag and drop is used
	 */
	static boolean dragAndDropLoaded = false;

	/**
	 * Initialization of the mechanism of drag and drop. This initialization aims to create a class derived 
	 * from DDPlayer which arms also the necessaries callback methods for in case of drop of the element DOM, le 
	 * the panel is woken up. So, it's in this method that it's developped the calls of the methods  
	 * <code>acceptDrop</code> and <code>dispatchDrop</code>.
	 */
	protected static native void loadDragAndDrop() 
	/*-{
		$wnd.YAHOO.namespace("tatami");

		$wnd.YAHOO.tatami.DDDraggable = function(id, sGroup, config) {
    		if (!id) { return; }
    		this.initPlayer(id, sGroup, config);
    		var fctAccept = function(el, et) {
				var r = this.gwtWidget.@com.objetdirect.tatami.client.DragAndDropPanel::acceptDrop(Lcom/google/gwt/user/client/Element;Lcom/google/gwt/user/client/Element;)(el,et);
				return r;
			};
			var fctDispatch = function(el, et) {
				this.gwtWidget.@com.objetdirect.tatami.client.DragAndDropPanel::dispatchDrop(Lcom/google/gwt/user/client/Element;Lcom/google/gwt/user/client/Element;)(el,et);
				return 1;
			};
			this.accept(fctAccept);
			this.subscribe(fctDispatch);		
		};

		$wnd.YAHOO.extend($wnd.YAHOO.tatami.DDDraggable, $wnd.YAHOO.example.DDPlayer);
	}-*/;

}//end of class
