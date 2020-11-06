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
	
	public void updateContactInfo(String fname, String lname, String updateField, String newValue) {
		PersonContact contact = getContact(fname, lname);
		if(contact!=null) {
			switch (updateField) {
			case "address": {
				contact.setAddress(newValue);
				break;
			}
			case "city": {
				contact.setCity(newValue);
				break;
			}
			case "state": {
				contact.setState(newValue);
				break;
			}
			case "zip": {
				contact.setZip(newValue);
				break;
			}
			case "phone": {
				contact.setPhone(newValue);
				break;
			}
			case "email": {
				contact.setEmail(newValue);
				break;
			}
			case "firstname": {
				contact.setFirstName(newValue);
				break;
			}
			case "lastname": {
				contact.setLastName(newValue);
				break;
			}

			}
		}
	}

	public PersonContact getContact(String fname, String lname) {
		for (PersonContact contact : contactList) {
			if (contact.getFirstName().equalsIgnoreCase(fname) && contact.getLastName().equalsIgnoreCase(lname)) {
				return contact;
			}
		}
		return null;
	}
	
}
