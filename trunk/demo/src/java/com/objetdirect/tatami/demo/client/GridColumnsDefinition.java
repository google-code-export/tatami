package com.objetdirect.tatami.demo.client;

public interface GridColumnsDefinition {

	public final String FIRST_NAME_FIELD = "firstName";
	public final String FIRST_NAME_COLUMN_NAME = TatamiDemo.getMessages().column_name_first_name();
	public final String LAST_NAME_FIELD = "lastName";
	public final String LAST_NAME_COLUMN_NAME = TatamiDemo.getMessages().column_name_last_name();
	public final String PHONE_NUMBER_FIELD = "phone";
	public final String PHONE_NUMBER__COLUMN_NAME = TatamiDemo.getMessages().column_name_phone_number();
	public final String SALARY_FIELD = "salary";
	public final String SALARY_COLUMN_NAME = TatamiDemo.getMessages().column_name_salary();
	public final String BIRTHDATE_FIELD = "birthdate";
	public final String BIRTHDATE_COLUMN_NAME = TatamiDemo.getMessages().column_name_birthdate();
	public final String DESCRIPTION_FIELD = "description";
	public final String DESCRIPTION_COLUMN_NAME = TatamiDemo.getMessages().column_name_description();
	public final String MARITAL_STATUS_FIELD = "maritalstatus";
	public final String MARITAL_STATUS_COLUMN_NAME =  TatamiDemo.getMessages().column_name_ismarried();
	public final String APPRECIATION_FIELD = "rating";
	public final String APPRECIATION_COLUMN_NAME = TatamiDemo.getMessages().column_name_rating();
	
	public final String[] APPRECIATIONS = {"Good","Very Good","Awesome"};
	
}
