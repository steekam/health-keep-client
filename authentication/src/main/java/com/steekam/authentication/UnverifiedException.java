package com.steekam.authentication;

public class UnverifiedException extends RuntimeException {

	public UnverifiedException() {
		super("Email verification is required");
	}

	public UnverifiedException(String message, Throwable cause) {
		super(message, cause);
	}
}
