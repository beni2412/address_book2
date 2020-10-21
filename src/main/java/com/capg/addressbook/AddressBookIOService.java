package com.capg.addressbook;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import com.capg.addressbook.dto.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.opencsv.CSVWriter;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.StatefulBeanToCsv;
import com.opencsv.bean.StatefulBeanToCsvBuilder;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import com.opencsv.exceptions.CsvRequiredFieldEmptyException;

public class AddressBookIOService {

	public static String CONTACT_FILE_NAME = "contactfile.txt";
	public static final String CSV_FILE_PATH = "contacts.csv";
	public static final String JSON_FILE_PATH = "contacts.json";

	public AddressBookIOService() {

	}

	public static List<PersonContact> readData() {
		List<PersonContact> contactsListRead = new ArrayList<>();
		try {
			Files.lines(new File(CONTACT_FILE_NAME).toPath()).map(line -> line.trim())
					.forEach(line -> System.out.println(line));
			Files.lines(new File(CONTACT_FILE_NAME).toPath()).map(line -> line.trim()).forEach(line -> {
				String thisLine = line.toString();
				String[] words = thisLine.split(",");
				for (int i = 0; i < words.length; i++) {
					String fname = words[i].replaceAll("First name-", "");
					i++;
					String lname = words[i].replaceAll("Last name-", "");
					i++;
					String address = words[i].replaceAll("Address-", "");
					i++;
					String city = words[i].replaceAll("City-", "");
					i++;
					String state = words[i].replaceAll("State-", "");
					i++;
					String zip = words[i].replaceAll("Zip-", "");
					i++;
					String mobile = words[i].replaceAll("Phone Number-", "");
					i++;
					String email = words[i].replaceAll("Email-", "");
					PersonContact contact = new PersonContact(fname, lname, address, city, state, zip, mobile, email);
					contactsListRead.add(contact);
				}
			});
		} catch (Exception e) {
		}
		return contactsListRead;
	}

	public static void writeData(List<PersonContact> contactList) {
		StringBuffer empBuffer = new StringBuffer();
		contactList.forEach(contact -> {
			String employeeDataString = contact.toString().concat("\n");
			empBuffer.append(employeeDataString);
		});
		try {
			Files.write(Paths.get(CONTACT_FILE_NAME), empBuffer.toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public long countEntries() {
		long entries = 0;
		try {
			entries = Files.lines(new File(CONTACT_FILE_NAME).toPath()).count();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return entries;
	}

	@SuppressWarnings("unchecked")
	public List<PersonContact> readCSV() {
		List<PersonContact> contactList = new ArrayList<PersonContact>();
		try {
			Reader reader = Files.newBufferedReader(Paths.get(CSV_FILE_PATH));
			CsvToBean<PersonContact> csvToBean = new CsvToBeanBuilder<PersonContact>(reader)
					.withType(PersonContact.class).withIgnoreLeadingWhiteSpace(true).build();
			contactList = csvToBean.parse();
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return contactList;
	}

	public boolean writeCSV(List<PersonContact> contactList) {
		try (Writer writer = Files.newBufferedWriter(Paths.get(CSV_FILE_PATH))) {
			StatefulBeanToCsv<PersonContact> beanToCsv = new StatefulBeanToCsvBuilder<PersonContact>(writer)
					.withQuotechar(CSVWriter.NO_QUOTE_CHARACTER).build();
			beanToCsv.write(contactList);

		} catch (CsvDataTypeMismatchException | CsvRequiredFieldEmptyException | IOException e1) {
			e1.printStackTrace();
			return false;
		}
		return true;
	}

	public boolean writeJson(List<PersonContact> contctList) {
		Gson gson = new Gson();
		String json = gson.toJson(contctList);
		try {
			FileWriter fileWriter = new FileWriter(JSON_FILE_PATH);
			fileWriter.write(json);
			fileWriter.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean readJson() {
		try {
			Reader reader = Files.newBufferedReader(Paths.get(JSON_FILE_PATH));
			JsonParser jsonParser = new JsonParser();
			JsonElement obj = jsonParser.parse(reader);
			JsonArray contactList = (JsonArray) obj;
			System.out.println(contactList);

			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
