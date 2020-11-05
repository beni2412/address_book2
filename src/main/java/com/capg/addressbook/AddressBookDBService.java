package com.capg.addressbook;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.capg.addressbook.AddressBookException.ExceptionType;
import com.capg.addressbook.dto.PersonContact;

public class AddressBookDBService {
	private Connection getConnection() throws AddressBookException {

		String jdbcURL = "jdbc:mysql://localhost:3306/addressbook_service?allowPublicKeyRetrieval=true&&useSSL=false";
		String userName = "root";
		String password = "Harman24@";
		try {
			return DriverManager.getConnection(jdbcURL, userName, password);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNABLE_TO_CONNECT, e.getMessage());
		}
	}

	private List<PersonContact> getContactData(ResultSet result) throws AddressBookException {
		List<PersonContact> contactList = new ArrayList<PersonContact>();
		try {
			while (result.next()) {
				String firstName = result.getString("first_name");
				String lastName = result.getString("last_name");
				String address = result.getString("street");
				String city = result.getString("city");
				String state = result.getString("state");
				String zip = result.getString("zip");
				String phoneNo = result.getString("phone_number");
				String email = result.getString("email");
				contactList.add(new PersonContact(firstName, lastName, address, city, state, zip, phoneNo, email));
			}
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNABLE_TO_CONNECT, e.getMessage());
		}
		return contactList;
	}

	public List<PersonContact> readContacts() throws AddressBookException {
		String sql = "select first_name, last_name, street, city, state, zip, phone_number, email"
				+ " from addressbook " + " INNER JOIN contact_details ON addressbook.id = contact_details.id "
				+ "	INNER JOIN address ON address.id = addressbook.id;";

		List<PersonContact> contactList = new ArrayList<PersonContact>();
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery(sql);
			contactList = getContactData(result);
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNABLE_TO_CONNECT, e.getMessage());
		}
		return contactList;
	}

	public int updateContactInfoInAddressbook(String firstname, String lastname, String newContact)
			throws AddressBookException {
		String sql = String.format("update contact_details c, addressbook a  SET c.phone_number= '%s' WHERE c.id=a.id AND first_name = '%s' AND last_name='%s';",
				newContact, firstname, lastname);
		int res = 0;
		try (Connection connection = this.getConnection()) {
			Statement statement = connection.createStatement();
			res = statement.executeUpdate(sql);
		} catch (SQLException e) {
			throw new AddressBookException(ExceptionType.WRONG_INFO, e.getMessage());
		} catch (AddressBookException e1) {
			e1.printStackTrace();
		}
		return res;
	}

	public PersonContact AddressBookSyncWithDB(String firstName) throws AddressBookException {
		List<PersonContact> tempList = this.readContacts();
		return tempList.stream().filter(contact -> contact.getFirstName().contentEquals(firstName)).findFirst()
				.orElse(null);
	}
}
