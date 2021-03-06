package com.capg.addressbook.dto;

import java.util.*;

public class AddressBooks {

	private List<AddressBook> addressBooks;

	public AddressBooks() {
		addressBooks = new ArrayList<AddressBook>();
	}

	public void addAddressBook(AddressBook addressBook) {
		addressBooks.add(addressBook);
	}

	public AddressBook containAddressBook(String name) {
		for (AddressBook addressBook : addressBooks) {
			if (addressBook.getName().equals(name)) {
				return addressBook;
			}
		}
		return null;
	}

	public void searchContactsByCity(String city) {
		System.out.println("The contacts in " + city + " are: \n");
		addressBooks.stream().forEach(n -> {
			n.getPersonContacts().stream().filter(n1 -> n1.getCity().equalsIgnoreCase(city)).forEach(n2 -> {

				System.out.println(n2);
			});
		});
		System.out.println("If no contact is shown above then there is no person from this city\n");

	}

	public void searchContactsByState(String state) {
		System.out.println("The contacts in " + state + " are: \n");
		addressBooks.stream().forEach(n -> {
			n.getPersonContacts().stream().filter(n1 -> n1.getState().equalsIgnoreCase(state)).forEach(n2 -> {

				System.out.println(n2);
			});
		});
		System.out.println("If no contact is shown above then there is no person from this state\n");

	}

	public void countContactsByCity(String city) {
		System.out.println("The number of contacts in " + city + " are: ");
		List<PersonContact> peopleFromCity = new ArrayList<PersonContact>();
		addressBooks.stream().forEach(n -> {
			n.getPersonContacts().stream().filter(n1 -> n1.getCity().equalsIgnoreCase(city)).forEach(n2 -> {
				peopleFromCity.add(n2);

			});
		});

		System.out.println(peopleFromCity.size()+"\n");
	}

	public void countContactsByState(String state) {
		System.out.println("The number of contacts in " + state + " are: ");
		List<PersonContact> peopleFromState = new ArrayList<PersonContact>();
		addressBooks.stream().forEach(n -> {
			n.getPersonContacts().stream().filter(n1 -> n1.getState().equalsIgnoreCase(state)).forEach(n2 -> {
				peopleFromState.add(n2);

			});
		});

		System.out.println(peopleFromState.size()+"\n");
	}

}
