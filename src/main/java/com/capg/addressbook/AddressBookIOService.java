package com.capg.addressbook;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.capg.addressbook.dto.*;

public class AddressBookIOService {
		
	private List<PersonContact> contactList;
	public static String CONTACT_FILE_NAME = "contactfile.txt";
	
	public AddressBookIOService() {

	}

	public AddressBookIOService(List<PersonContact> contactList) {

		this.contactList = contactList;
	}


	public static List<PersonContact> readData() {
		List<PersonContact> contactsListRead = new ArrayList<>();
		try {
			 Files.lines(new File(CONTACT_FILE_NAME).toPath()).map(line -> line.trim())
				.forEach(line -> System.out.println(line));
			Files.lines(new File(CONTACT_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> {
				String thisLine = line.toString();
				String[] words = thisLine.split(",");
				for (int i = 0; i < words.length; i++) {
					String fname = words[i].replaceAll("First name-", "");
					i++;
					String lname = words[i].replaceAll("Last Name-", "");
					i++;
					String address = words[i].replaceAll("Address-", "");
					i++;
					String city = words[i].replaceAll("City-", "");
					i++;
					String state = words[i].replaceAll("State-", "");
					i++;
					String zip = words[i].replaceAll("Zip Code-", "");
					i++;
					String mobile = words[i].replaceAll("Phone No.-", "");
					i++;
					String email = words[i].replaceAll("Email-", "");
					PersonContact contact = new PersonContact(fname, lname, address, city, state, zip, mobile,
							email);
					contactsListRead.add(contact);
				}
			});
		} catch (Exception e) {
			}
		return contactsListRead;
	}

	public static void writeData(List<PersonContact> contactList) {
		StringBuffer empBuffer = new StringBuffer();
		contactList.forEach(contact -> {
			String employeeDataString = contact.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(CONTACT_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(CONTACT_FILE_NAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}
} 

