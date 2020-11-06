package com.capg.addressbook;

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
		AddressBookRestAPIService service;
		service = new AddressBookRestAPIService(Arrays.asList(contacts));
		long entries = service.countEntries();
		Assert.assertEquals(2, entries);
	}

	

}
