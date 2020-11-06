package com.capg.addressbook;

import java.util.ArrayList;
import java.util.List;

import com.capg.addressbook.dto.PersonContact;

public class AddressBookRestAPIService {
	public List<PersonContact> contactList = null;
	
	public AddressBookRestAPIService(List<PersonContact> contacts) {
		contactList = new ArrayList<>(contacts);
		//contactList = contacts;
	}
	
	public long countEntries() {
		return contactList.size();
	}
	
	public void addContactToJsonServer(PersonContact contact1) {
		contactList.add(contact1);
	}
	
}
