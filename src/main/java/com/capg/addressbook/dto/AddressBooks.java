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

	public void searchContactsByPlace(String place) {
		addressBooks.stream().forEach(n -> {
			n.getPersonContacts().stream()
					.filter(n1 -> n1.getCity().equalsIgnoreCase(place) || n1.getState().equalsIgnoreCase(place))
					.forEach(n2 -> {
						System.out.println(n2);
					});
		});
		System.out.println("If no contact is shown above then there is no person from this place\n");

	}

}
