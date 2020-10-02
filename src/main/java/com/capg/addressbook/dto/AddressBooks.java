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
		for(AddressBook addressBook: addressBooks) {
			if(addressBook.getName().equals(name)) {
				return addressBook;
			}
		}
		return null;
	}
	
}
