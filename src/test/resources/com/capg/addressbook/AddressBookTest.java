package com.capg.addressbook;

import static org.junit.Assert.*;

import java.time.LocalDate;
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
}