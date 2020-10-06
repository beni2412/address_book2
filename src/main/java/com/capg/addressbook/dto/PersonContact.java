package com.capg.addressbook.dto;

public class PersonContact {
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String state;
	private Long zip;
	private String phone;
	private String email;
	
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public void setZip(Long zip) {
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
	public Long getZip() {
		return zip;
	}
	public String getPhone() {
		return phone;
	}
	public String getEmail() {
		return email;
	}
	
	@Override
	public String toString() {
		return "First Name: "+firstName+"\nLast Name: "+lastName+"\nAddress: "+address+"\nCity: "+city+"\nState: "+state+"\nZip: "+zip+"\nPhone Number: "+phone+"\nEmail: "+email+"\n\n";
	}
	@Override
	public boolean equals(Object obj) {
		boolean result;
		if(obj==null) {
			result = false;
		}
		else {
			PersonContact person = (PersonContact)obj;
			result = this.firstName.equalsIgnoreCase(person.firstName);
		}
		
		return result;
	}
}
