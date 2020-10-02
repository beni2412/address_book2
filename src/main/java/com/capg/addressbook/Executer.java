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
				System.out.println("3. Exit");
				int options = sc.nextInt();

				switch (options) {
				case 1:
				openAddressBook(addressBookService, addressBooks,sc);
					break;
				case 2:
					createAddressBook(addressBookService, addressBooks, sc);
					break;
				case 3:
					System.out.println("Bye\n");
					return;
				default:
					break;
				}
		}
	}
	public static void openAddressBook(AddressBookService addressBookService,AddressBooks addressBooks,Scanner sc) {
			System.out.print("Enter Name");
			String name = sc.next();
			AddressBook addressBook = addressBooks.containAddressBook(name);
			if(Objects.nonNull(addressBook)) {
				addressBookService.showOptions(addressBook);
				return;
			}
			System.out.println("Not Address Book Found");
	}
	
	public static void createAddressBook(AddressBookService addressBookService,AddressBooks addressBooks ,Scanner sc) {
		System.out.print("Enter Name");
		String name = sc.next();
		AddressBook addressBook = addressBookService.createAddressBook(name);
		addressBooks.addAddressBook(addressBook);
		System.out.print("Created new address book\n");
	}
}
