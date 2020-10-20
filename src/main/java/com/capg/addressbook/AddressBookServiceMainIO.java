package com.capg.addressbook;

import java.util.ArrayList;
import java.util.List;

import com.capg.addressbook.dto.*;

public class AddressBookServiceMainIO {

	public static void main(String[] args) {
		AddressBookIOService addressBookFileIOService = new AddressBookIOService();
		List<PersonContact> contactList = new ArrayList<>();
		PersonContact contact1 = new PersonContact("Hardaman", "Benipal", "Mohali", "Mohali", "Punjab", "160071",
				"91 9999999999", "abc@google.com");
		PersonContact contact2 = new PersonContact("Harman", "Singh", "patiala", "patiala", "punjab", "140071",
				"91 8888888888", "def@gmail.com");
		PersonContact contact3 = new PersonContact("Lincoln", "Verma", "mumbai", "mumbai", "maharashtra", "987345",
				"91 8888888880", "ghi@gmail.com");
		contactList.add(contact1);
		contactList.add(contact2);
		contactList.add(contact3);
		addressBookFileIOService.writeData(contactList);
		List<PersonContact> list =addressBookFileIOService.readData();
		for(PersonContact contact : list) {
			System.out.println(contact);
		}
		System.out.println(list.size());

	}

}
