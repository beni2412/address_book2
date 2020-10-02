package com.capg.addressbook.service;

import com.capg.addressbook.dto.PersonContact;

public interface PersonService {
	
	public PersonContact createPerson();
	
	public void updatePerson(PersonContact personContact);
	
	public void diplayPerson(PersonContact personContact);
	
}
