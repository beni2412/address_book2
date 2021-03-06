package com.capg.addressbook.dto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AddressBook {

	private String name;

	private List<PersonContact> personContacts;
	public HashMap<String, List> DictionaryOfCity = new HashMap<String, List>();
	public HashMap<String, List> DictionaryOfCit = new HashMap<String, List>();

	public List<PersonContact> getPersonContacts() {
		return personContacts;
	}

	public void setPersonContacts(List<PersonContact> personContacts) {
		this.personContacts = personContacts;
	}

	public AddressBook(String name) {
		this.name = name;
		personContacts = new ArrayList<PersonContact>();
	}

	public List<PersonContact> showAllContacts() {
		return personContacts.stream().sorted((n1, n2) -> n1.getFirstName().compareTo(n2.getFirstName()))
				.collect(Collectors.toList());
	}

	public void addPersonContact(PersonContact personContact) {
		Predicate<PersonContact> isSameName = n -> n.equals(personContact);
		List<PersonContact> personsWithSameName = personContacts.stream().filter(isSameName)
				.collect(Collectors.toList());
		if (personsWithSameName.isEmpty())
			personContacts.add(personContact);
		else
			System.out.println("Person with this name already exists");
	}

	public PersonContact containsPerson(String firstName) {
		for (PersonContact contact : personContacts) {
			if (contact.getFirstName().equalsIgnoreCase(firstName)) {
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
		for (PersonContact contact : personContacts) {
			if (contact.getFirstName().equalsIgnoreCase(firstName)) {
				personContacts.remove(contact);
				return;
			}
		}
	}

	public void sortByCity() {
		personContacts.stream().sorted((n1, n2) -> n1.getCity().compareTo(n2.getCity()))
				.forEach(n -> System.out.println(n));
	}

	public void sortByState() {
		personContacts.stream().sorted((n1, n2) -> n1.getState().compareTo(n2.getState()))
				.forEach(n -> System.out.println(n));
	}

	public void sortByZip() {
		personContacts.stream().sorted((n1, n2) -> n1.getZip().compareTo(n2.getZip()))
				.forEach(n -> System.out.println(n));
	}

	@Override
	public boolean equals(Object obj) {
		boolean result;
		if (obj == null) {
			result = false;
		} else {
			AddressBook addressBook = (AddressBook) obj;
			result = this.name.equalsIgnoreCase(addressBook.name);
		}

		return result;
	}
}
