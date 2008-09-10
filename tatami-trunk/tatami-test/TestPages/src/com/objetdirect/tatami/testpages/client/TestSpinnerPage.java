package com.objetdirect.tatami.testpages.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.objetdirect.tatami.client.Button;
import com.objetdirect.tatami.client.NumberSpinner;

public class TestSpinnerPage extends TestPage{

	public TestSpinnerPage(){
		super("com.objetdirect.tatami.testpages.client.TestSpinnerPage" , "Test Spinner");
	}
	
	public Widget getTestPage() {
		VerticalPanel panel = new VerticalPanel();
		Map constraints = new HashMap();
		constraints.put("min",new Integer(-10));
		constraints.put("max",new Integer(10));
		final Boolean intermediateChanges = Boolean.FALSE;
		final NumberSpinner spinner = new NumberSpinner(300 , "Grou" , intermediateChanges.booleanValue() , 1 , "" , "" , 0.90f , true , 0 , constraints);
		final HTML value = new HTML("" + spinner.getValue());
		spinner.addChangeListener(new ChangeListener(){
			public void onChange(Widget sender) {
				value.setText("" + ((NumberSpinner) sender).getValue().intValue());
			}
		});
		
		final Button toggleIntermediateChangeButton = new Button("Toggle Intermediate Changes");
		toggleIntermediateChangeButton.addClickListener(new ClickListener(){
			
			boolean iChanges = intermediateChanges.booleanValue();
			
			public void onClick(Widget sender) {
				iChanges = !iChanges;
				GWT.log("INTERMEDIATE CHANGES SET TO " + iChanges, null);
				System.out.println("INTERMEDIATE CHANGES SET TO " + iChanges);
				spinner.setIntermediateChanges(iChanges);
			}
		});
		
		final TextBox setSpinnerMaxValueBox = new TextBox();
		setSpinnerMaxValueBox.setText("10");
		final TextBox setSpinnerMinValueBox = new TextBox();
		setSpinnerMinValueBox.setText("-10");
		final TextBox setSpinnerPatternBox = new TextBox();
		setSpinnerPatternBox.setText("#,##0.##");
		final TextBox setSpinnerTypeBox = new TextBox();
		final TextBox setSpinnerCurrencyBox = new TextBox();
		final TextBox setSpinnerDeltaBox = new TextBox();
		setSpinnerDeltaBox.setText("1");
		panel.add(setSpinnerMinValueBox);
		panel.add(setSpinnerMaxValueBox);
		panel.add(setSpinnerPatternBox);
		panel.add(setSpinnerTypeBox);
		panel.add(setSpinnerCurrencyBox);
		panel.add(setSpinnerDeltaBox);
		
		
		final Button applyConstraintsChange = new Button("Apply" , new ClickListener() {
		
			public void onClick(Widget sender) {
				spinner.addConstraint(NumberSpinner.CONSTRAINT_MIN, setSpinnerMinValueBox.getText());
				spinner.addConstraint(NumberSpinner.CONSTRAINT_MAX, setSpinnerMaxValueBox.getText());
				spinner.addConstraint(NumberSpinner.CONSTRAINT_PATTERN, setSpinnerPatternBox.getText());
				spinner.addConstraint(NumberSpinner.CONSTRAINT_TYPE, setSpinnerTypeBox.getText());
				spinner.addConstraint(NumberSpinner.CONSTRAINT_CURRENCY, setSpinnerCurrencyBox.getText());
				spinner.setDelta(Float.parseFloat(setSpinnerDeltaBox.getText()));
			}
		});
		panel.add(applyConstraintsChange);
		DOM.setElementAttribute(value.getElement(), "id", "SpinnerValueBOX" );
		DOM.setElementAttribute(spinner.getElement(),"id", "TheSpinner");
		DOM.setElementAttribute(toggleIntermediateChangeButton.getElement(),"id", "toggleIntermediateChangeButton");
		panel.add(spinner);
		panel.add(value);
		panel.add(toggleIntermediateChangeButton);
		return panel;
	}

}
