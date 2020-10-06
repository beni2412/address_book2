package com.capg.addressbook;

import java.util.Objects;
import java.util.Scanner;

import com.capg.addressbook.dto.AddressBook;
import com.capg.addressbook.dto.AddressBooks;
import com.capg.addressbook.service.AddressBookService;
import com.capg.addressbook.service.implement.AddressBookServiceImpl;

public class Executer {
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		AddressBooks addressBooks = new AddressBooks();
		AddressBookService addressBookService = new AddressBookServiceImpl(sc);
		System.out.println("Welcome to Address Book");
		while (true) {
			System.out.println("1. Open an existing Address book");
			System.out.println("2. Create a new Address Book");
			System.out.println("3. Search Person in City");
			System.out.println("4. Search Person in State");
			System.out.println("5. Exit");
			int options = sc.nextInt();

			switch (options) {
			case 1:
				openAddressBook(addressBookService, addressBooks, sc);
				break;
			case 2:
				createAddressBook(addressBookService, addressBooks, sc);
				break;
			case 3:
				searchByCity(addressBookService, addressBooks, sc);
				break;
			case 4:
				searchByState(addressBookService, addressBooks, sc);
				break;
			case 5:
				System.out.println("Bye\n");
				return;
			default:
				System.out.println("Invalid Input");
				break;
			}
		}
	}

	public static void openAddressBook(AddressBookService addressBookService, AddressBooks addressBooks, Scanner sc) {
		System.out.print("Enter Name: ");
		String name = sc.next();
		AddressBook addressBook = addressBooks.containAddressBook(name);
		if (Objects.nonNull(addressBook)) {
			addressBookService.showOptions(addressBook);
			return;
		}
		System.out.println("No Address Book Found");
	}

	public static void createAddressBook(AddressBookService addressBookService, AddressBooks addressBooks, Scanner sc) {
		System.out.print("Enter Name: ");
		String name = sc.next();
		AddressBook addressBook = addressBookService.createAddressBook(name);
		addressBooks.addAddressBook(addressBook);
		System.out.print("Created new address book\n");
	}
	
	
	public static void searchByCity(AddressBookService addressBookService, AddressBooks addressBooks, Scanner sc) {
		System.out.print("Enter City: ");
		String city = sc.next();
		addressBooks.searchContactsByCity(city);
	}
	
	public static void searchByState(AddressBookService addressBookService, AddressBooks addressBooks, Scanner sc) {
		System.out.print("Enter State: ");
		String state = sc.next();
		addressBooks.searchContactsByState(state);
	}
}
