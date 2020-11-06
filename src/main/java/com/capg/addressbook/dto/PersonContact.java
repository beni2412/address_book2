package com.capg.addressbook.dto;

import java.time.LocalDate;

import com.opencsv.bean.CsvBindByName;

public class PersonContact {
	@CsvBindByName
	private String firstName;
	@CsvBindByName
	private String lastName;
	@CsvBindByName
	private String address;
	@CsvBindByName
	private String city;
	@CsvBindByName
	private String state;
	@CsvBindByName
	private String zip;
	@CsvBindByName
	private String phone;
	@CsvBindByName
	private String email;
	
	private String dateAdded;
	public String id;
	

	public PersonContact() {
		
	}

	public PersonContact(String firstname, String lastname, String address, String city, String state, String zip,
			String phone, String email) {
		super();
		this.firstName = firstname;
		this.lastName = lastname;
		this.address = address;
		this.city = city;
		this.state = state;
		this.zip = zip;
		this.phone = phone;
		this.email = email;
	}
	
	public PersonContact(String firstname, String lastname, String address, String city, String state, String zip,
			String phone, String email, String dateAdded) {
		this(firstname, lastname, address, city, state, zip, phone, email);
		this.dateAdded = dateAdded.toString();
	}
	public PersonContact(String firstname, String lastname, String address, String city, String state, String zip,
			String phone, String email, String dateAdded, String id) {
		this(firstname, lastname, address, city, state, zip, phone, email, dateAdded);
		this.id = id;
	}
	public void setFirstName(String firstname) {
		this.firstName = firstname;
	}

	public void setLastName(String lastname) {
		this.lastName = lastname;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setState(String state) {
		this.state = state;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getAddress() {
		return address;
	}

	public String getCity() {
		return city;
	}

	public String getState() {
		return state;
	}

	public String getZip() {
		return zip;
	}

	public String getPhone() {
		return phone;
	}

	public String getEmail() {
		return email;
	}
	
	

	public String getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(LocalDate dateAdded) {
		this.dateAdded = dateAdded.toString();
	}

	@Override
	public String toString() {
		return "First name-" + firstName + ",Last name-" + lastName + ",Address-" + address + ",City-" + city
				+ ",State-" + state + ",Zip-" + zip + ",Phone Number-" + phone + ",Email-" + email ;
	}

	@Override
	public boolean equals(Object obj) {
		boolean result;
		if (obj == null) {
			result = false;
		} else {
			PersonContact person = (PersonContact) obj;
			result = this.firstName.equalsIgnoreCase(person.firstName);
		}

		return result;
	}
}
