package com.capg.addressbook.dto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
public class AddressBook {
	
	private String name;
	
	 private List<PersonContact> personContacts;
	
	public AddressBook(String name) {
		this.name = name;
		personContacts= new ArrayList<PersonContact>();
	}
	
	
	public void addPersonContact(PersonContact personContact) {
		Predicate<PersonContact> isSameName = n -> n.getFirstName().equalsIgnoreCase(personContact.getFirstName());
		List<PersonContact> personsWithSameName = personContacts.stream().filter(isSameName).collect(Collectors.toList());
		if(personsWithSameName.isEmpty())
			personContacts.add(personContact);
		else 
			System.out.println("Person with this name already exists");
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
	@Override
	public boolean equals(Object obj) {
		boolean result;
		if(obj==null) {
			result = false;
		}
		else {
			AddressBook addressBook = (AddressBook)obj;
			result = this.name.equalsIgnoreCase(addressBook.name);
		}
		
		return result;
	}
}
