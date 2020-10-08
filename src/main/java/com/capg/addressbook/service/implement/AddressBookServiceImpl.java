package com.capg.addressbook.service.implement;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import com.capg.addressbook.dto.AddressBook;
import com.capg.addressbook.dto.PersonContact;
import com.capg.addressbook.service.AddressBookService;
import com.capg.addressbook.service.PersonService;


public class AddressBookServiceImpl implements AddressBookService {
	
	private AddressBook addressBook;
	private PersonService personService;
	private Scanner sc;
	
	public AddressBookServiceImpl(Scanner sc) {
		this.sc = sc;
	}
	
	@Override
	public void showOptions(AddressBook addressBook) {
		this.addressBook = addressBook;
		personService = new PersonServiceImpl(this.sc);
		
		while(true) {
			System.out.println("Option For Address Book");

			System.out.println("1. Find a Person");
			System.out.println("2. Update a Person");
			System.out.println("3. Delete a Person");
			System.out.println("4. Add a Person");
			System.out.println("5. View all Contacts");
			System.out.println("6. Exit");
		
			int option = sc.nextInt();
			switch(option) {	
			
			case 1:
				findPerson();
				break;
			case 2:
				updatePerson();
				break;
			case 3:
				deletePerson();
				break;
			case 4:
				createPerson();
				break;
			case 5:
				showContacts();
				break;
			case 6:
				return;
			default:
				System.out.println("Invalid Entry");
				break;
			
			
			}
		}
	}
	public void showContacts() {
		 List<PersonContact> contactList = addressBook.showAllContacts();
		 contactList.stream().forEach(n -> System.out.println(n));
		
	}

	@Override
	public void findPerson() {
		System.out.println("Enter Person Name");
		String name = sc.next();
		personService.diplayPerson(addressBook.containsPerson(name));
	}

	@Override
	public void updatePerson() {
		System.out.println("Enter Person Name");
		String name = sc.next();
		PersonContact person = addressBook.containsPerson(name);
		if(Objects.nonNull(person)) {
			personService.updatePerson(person);
			return;
		}
		System.out.println("Person Not Found");
	}
		

	@Override
	public void deletePerson() {
		System.out.println("Enter Person Name");
		String name = sc.next();
		PersonContact person = addressBook.containsPerson(name);
		if(Objects.nonNull(person)) {
			addressBook.deletePerson(name);;
			return;
		}
		System.out.println("Person Not Found");
		
	}

	@Override
	public AddressBook createAddressBook(String name) {
		AddressBook addressBook = new AddressBook(name);
		return addressBook;
	}
	
	public void createPerson() {
		addressBook.addPersonContact(personService.createPerson());
	}
	

}
