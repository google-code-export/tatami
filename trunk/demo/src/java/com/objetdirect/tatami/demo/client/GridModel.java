package com.objetdirect.tatami.demo.client;

import java.util.Date;

import com.google.gwt.user.client.Random;
import com.objetdirect.tatami.client.data.AbstractDataStore;
import com.objetdirect.tatami.client.data.DefaultDataStore;
import com.objetdirect.tatami.client.data.Item;
import com.objetdirect.tatami.client.grid.GridDataStore;
import com.objetdirect.tatamix.client.hmvc.ControllerProcessor;
import com.objetdirect.tatamix.client.hmvc.Event;
import com.objetdirect.tatamix.client.hmvc.ModelEvent;
import com.objetdirect.tatamix.client.hmvc.ModelImpl;

public class GridModel extends ModelImpl implements TatamiDemoEvent,GridColumnsDefinition{

	private AbstractDataStore store;
	private int idseq = 0;
	private final int initialSize = 5;
	
	
	String[] firstNames = { "Jean" , "John" , "William" , "Edgar" , "Alex" , "Sabrina" , "Anne" , "Orson" , "Li" , "Jose" , "Ibrahim" , "Emmanuel" , "James" , "Xavier" , "Cory" , "Sarah" , "Curt"};
	String[] lastNames = { "Poe" , "Doe" , "Turing" , "Scottgard" , "Dupond" , " Dupont" , "Rada" , "Chen" , "Barros" , "Borges" , "Scott" , "Bond" , "Doctorow" , "Hitcher" , "Connor" , "Cobain" , "Russel"};
	
	private String firstNameKey;
	
	public GridModel(){
		store = new GridDataStore();
		ControllerProcessor initGridProcessor = new ControllerProcessor() {
			public void run(Event event) {
				initDataStore();
				fire(new ModelEvent(DATASTORE_FULLUPDATE,store));
			}
		};
		ControllerProcessor addRowProcessor = new ControllerProcessor() {
			public void run(Event event) {
				store.add(getRandomItem());
			}
		};
		ControllerProcessor removeRowProcessor = new ControllerProcessor() {
			public void run(Event event) {
				Item[] items = (Item[]) event.getSource();
				for (int i = 0; i < items.length; i++) {
					store.remove(items[i]);
				}
			}
		};
		register(REMOVE_ROWS, removeRowProcessor);
		register(ADD_ROW, addRowProcessor);
		register(INIT_GRID, initGridProcessor);
	}
	
	private void initDataStore(){
		store = new DefaultDataStore();
		for(int i = 0; i < initialSize ; i++){
			store.add(getRandomItem());
		}
	}
	
	private Item getRandomItem(){
		Item randomItem = new Item();
		randomItem.setId(this.idseq++);
		String firstName = firstNames[Random.nextInt(firstNames.length)];
		String lastName = lastNames[Random.nextInt(lastNames.length)];
		randomItem.setValue(FIRST_NAME_FIELD, firstName);
		randomItem.setValue(LAST_NAME_FIELD, lastName);
		String phoneNumber = "" + Random.nextInt(10)+Random.nextInt(10)+Random.nextInt(10)+Random.nextInt(10)+Random.nextInt(10)+
			Random.nextInt(10)+Random.nextInt(10)+Random.nextInt(10);
		randomItem.setValue(PHONE_NUMBER_FIELD, phoneNumber);
		Date birthdate = new Date(1950 + Random.nextInt(50),Random.nextInt(12),Random.nextInt(30));
		randomItem.setValue(BIRTHDATE_FIELD, birthdate);
		randomItem.setValue(SALARY_FIELD , Random.nextInt(2500)+1000);
		randomItem.setValue(DESCRIPTION_FIELD, "My name is " + firstName +" " + lastName);
		randomItem.setValue(MARITAL_STATUS_FIELD, Random.nextBoolean());
		randomItem.setValue(APPRECIATION_FIELD,APPRECIATIONS[Random.nextInt(APPRECIATIONS.length)]);
		return randomItem;
	}
	
}
