package com.capg.addressbook.service;

import com.capg.addressbook.dto.AddressBook;

public interface AddressBookService {

	public void findPerson();

	public void updatePerson();

	public void deletePerson();

	public void showOptions(AddressBook addressBook);

	public AddressBook createAddressBook(String name);

}
