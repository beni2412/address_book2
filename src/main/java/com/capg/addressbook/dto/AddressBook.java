package com.capg.addressbook.dto;

import java.util.*;
public class AddressBook {
	
	private String name;
	
	 private List<PersonContact> personContacts;
	
	public AddressBook(String name) {
		this.name = name;
		personContacts= new ArrayList<PersonContact>();
	}
	
	public void addPersonContact(PersonContact personContact) {
		personContacts.add(personContact);
	}
	
	public PersonContact containsPerson(String firstName) {
		for (PersonContact contact: personContacts) {
			if(contact.getFirstName().equalsIgnoreCase(firstName)) {
				return contact;
			}
		}
		return null;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public void deletePerson(String firstName) {
		for (PersonContact contact: personContacts) {
			if(contact.getFirstName().equalsIgnoreCase(firstName)) {
				personContacts.remove(contact);
				return;
			}
		}
	}
}
