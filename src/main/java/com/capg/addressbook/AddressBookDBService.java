package com.capg.addressbook;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String sql = String.format(
				"update contact_details c, addressbook a  SET c.phone_number= '%s' WHERE c.id=a.id AND first_name = '%s' AND last_name='%s';",
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

	public int getContactsWithinADateRange(LocalDate startDate, LocalDate endDate) throws AddressBookException {
		String sql = String.format("SELECT * FROM addressbook WHERE date_added BETWEEN '%s' AND '%s';",
				Date.valueOf(startDate), Date.valueOf(endDate));
		int noOfContacts = 0;
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				noOfContacts++;
			}
		} catch (SQLException e) {
			throw new AddressBookException(ExceptionType.WRONG_INFO, e.getMessage());
		} catch (AddressBookException e1) {
			e1.printStackTrace();
		}
		return noOfContacts;
	}

	public int getContactsFromPlace(String field, String placeName) throws AddressBookException {
		String sql = String.format("SELECT * FROM address WHERE %s = '%s';", field, placeName);
		int numberOfContacts = 0;
		try (Connection connection = getConnection()) {
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(sql);
			while (resultSet.next()) {
				numberOfContacts++;
			}
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.WRONG_INFO, e.getMessage());
		}
		return numberOfContacts;
	}

	public void addPersonContactToDatabase(String firstName, String lastName, String street, String city, String state,
			String zip, String phoneNo, String email, LocalDate start) throws AddressBookException {
		int id = -1;
		PersonContact contact = null;
		Connection connection = null;
		try {
			connection = this.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO addressbook (book_name, first_name, last_name, date_added)"
					+ "VALUES ('book1','%s','%s','%s')", firstName, lastName, Date.valueOf(start));
			int rowAffected = statement.executeUpdate(sql, statement.RETURN_GENERATED_KEYS);
			if (rowAffected == 1) {
				ResultSet resultSet = statement.getGeneratedKeys();
				if (resultSet.next())
					id = resultSet.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format(
					"INSERT INTO address (id, street, city, state, zip) VALUES ('%s','%s','%s','%s','%s')", id, street,
					city, state, zip);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				contact = new PersonContact(firstName, lastName, street, city, state, zip, phoneNo, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}
		try (Statement statement = connection.createStatement()) {
			String sql = String.format("INSERT INTO contact_details (id, phone_number, email) VALUES ('%s','%s','%s')",
					id, phoneNo, email);
			int rowAffected = statement.executeUpdate(sql);
			if (rowAffected == 1) {
				contact = new PersonContact(firstName, lastName, street, city, state, zip, phoneNo, email);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e.printStackTrace();
			}
		}
		try {
			connection.commit();
		} catch (SQLException e) {
			throw new AddressBookException(AddressBookException.ExceptionType.UNABLE_TO_CONNECT, e.getMessage());
		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
		}
	}

	public void addMultipleContacts(List<PersonContact> contactsToAddList) throws AddressBookException {
		Map<Integer, Boolean> contactStatusMap = new HashMap<Integer, Boolean>();
		contactsToAddList.forEach(contact -> {
			contactStatusMap.put(contact.hashCode(), false);
			Runnable task = () -> {
				try {
					this.addPersonContactToDatabase(contact.getFirstName(), contact.getLastName(), contact.getAddress(),
							contact.getCity(), contact.getState(), contact.getZip(), contact.getPhone(),
							contact.getEmail(), contact.getDateAdded());
				} catch (AddressBookException e) {
					e.printStackTrace();
				}
				contactStatusMap.put(contact.hashCode(), true);
			};
			Thread thread = new Thread(task, contact.getFirstName());
			thread.start();
		});
		while (contactStatusMap.containsValue(false)) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}