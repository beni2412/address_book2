package com.capg.addressbook;

public class AddressBookException extends Exception {
	enum ExceptionType {
		UNABLE_TO_CONNECT, WRONG_INFO;
	}

	ExceptionType type;

	public AddressBookException(ExceptionType type, String message) {
		super(message);
		this.type = type;
	}
}