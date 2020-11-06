package com.capg.addressbook;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.capg.addressbook.dto.PersonContact;
import com.google.gson.Gson;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class AddressBookRestAPITest {

	@Before
	public void Setup() {
		RestAssured.baseURI = "http://localhost";
		RestAssured.port = 3000;
	}

	private PersonContact[] getContactsList() {
		Response response = RestAssured.get("/Contacts");
		System.out.println("ENTRIES IN JSONServer:\n" + response.asString());
		PersonContact[] contacts = new Gson().fromJson(response.asString(), PersonContact[].class);
		return contacts;
	}

	@Test
	public void givenContactsInJSONServer_whenRetrieved_ShouldMatchTheCount() {
		PersonContact[] contacts = getContactsList();
		AddressBookRestAPIService addressBookRestAPIService;
		addressBookRestAPIService = new AddressBookRestAPIService(Arrays.asList(contacts));
		long entries = addressBookRestAPIService.countEntries();
		Assert.assertEquals(2, entries);
	}

	@Test
	public void givenNewContact_WhenAdded_ShouldMatch201ResponseAndCount() {
		PersonContact[] contacts = getContactsList();
		AddressBookRestAPIService addressBookRestAPIService;
		addressBookRestAPIService = new AddressBookRestAPIService(Arrays.asList(contacts));
		 PersonContact[] personContactlist = { new PersonContact("harman","bandesha","urbanestate","patiala","punjab","140076","654545456","harman@gmail.com",LocalDate.now()),
				 new PersonContact("aman","beni","sec70","mohali","punjab","160071","6121565","aman@gmail.com",LocalDate.now()),
				 new PersonContact("harshal","bedi","sec21","mohali","punjab","160095","54565654","harshal@gmail.com",LocalDate.now())
				 };
		 
		 for(PersonContact contact : personContactlist) {
			 Response response = addContactToJsonServer(contact);
			 int statusCode = response.getStatusCode();
			 Assert.assertEquals(201, statusCode);
			 
			 contact = new Gson().fromJson(response.asString(), PersonContact.class);
			 addressBookRestAPIService.addContactToJsonServer(contact);
		 }
		 long entries = addressBookRestAPIService.countEntries();
			Assert.assertEquals(5, entries);
		
	}

	private Response addContactToJsonServer(PersonContact personContact) {
		String contactJson = new Gson().toJson(personContact);
		RequestSpecification request = RestAssured.given();
		request.header("Content-Type", "application/json");
		request.body(contactJson);
		return request.post("/Contacts");
	}

}
