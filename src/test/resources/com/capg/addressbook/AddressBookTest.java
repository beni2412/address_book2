package com.capg.addressbook;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capg.addressbook.AddressBookDBService;
import com.capg.addressbook.AddressBookException;
import com.capg.addressbook.dto.PersonContact;

public class AddressBookTest {

	private AddressBookDBService addressBookDBService;

	@Before
	public void initialize() {
		addressBookDBService = new AddressBookDBService();
	}

	@Test
	public void givenAddressBookDB_ShouldMatchCount() throws AddressBookException {
		List<PersonContact> contactList = addressBookDBService.readContacts();
		Assert.assertEquals(6, contactList.size());
	}

	@Test
	public void givenAddressBookDB_WhenContactUpdatedShouldSyncWithDB() throws AddressBookException {
		addressBookDBService.updateContactInfoInAddressbook("Daman", "Benipal", "95989");
		PersonContact contact = addressBookDBService.AddressBookSyncWithDB("Daman");
		Assert.assertEquals("95989", contact.getPhone());
	}

	@Test
	public void givenAddressBookInDataBase_RetrieveContactsWithinADateRange() throws AddressBookException {
		LocalDate startDate = LocalDate.of(2020, 01, 01);
		LocalDate endDate = LocalDate.of(2020, 06, 06);
		int contacts = addressBookDBService.getContactsWithinADateRange(startDate, endDate);
		Assert.assertEquals(3, contacts);

	}
	
	@Test
	public void givenAddressBookInDataBase_RetrieveContactsfromCity_ShouldReturnCorrectNumber() throws AddressBookException {
		int contacts = addressBookDBService.getContactsFromPlace("city" ,"b");
		Assert.assertEquals(2, contacts);

	}
	
	@Test
	public void givenAddressBookInDataBase_RetrieveContactsfromState_ShouldReturnCorrectNumber() throws AddressBookException {
		int contacts = addressBookDBService.getContactsFromPlace("state" ,"c");
		Assert.assertEquals(2, contacts);

	}
	
	@Test
	public void whenContactAddedToDataBase_ShouldMatchCount() throws AddressBookException {
		addressBookDBService.addPersonContactToDatabase("Guri", "Sidhu", "sector 70", "Mohali", "Punjab", "160071", "9056618", "guri@gmail", LocalDate.now());
		List<PersonContact> contactList = addressBookDBService.readContacts();
		Assert.assertEquals(2, contactList.size());
	}
	
	@Test
	public void whenAddedMultipleContactsUsingThreads_ShouldRetunCount() throws AddressBookException {
		PersonContact[] arrOfContacts = {
		new PersonContact("daman","beni","sec70","mohali","punjab","160071","9056618815","beni@gmail.com",LocalDate.now()),	
		new PersonContact("harry","singh","sector1","chd","haryana","680041","445456666","harry@gmail.com",LocalDate.now()),
		new PersonContact("harman","bandesha","urbanestate","patiala","punjab","140076","654545456","harman@gmail.com",LocalDate.now())
		};
		addressBookDBService.addMultipleContacts(Arrays.asList(arrOfContacts));
		List<PersonContact> contactList = addressBookDBService.readContacts();
		Assert.assertEquals(5, contactList.size());
	}
}