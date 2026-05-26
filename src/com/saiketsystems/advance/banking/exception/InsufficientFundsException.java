package com.saiketsystems.advance.banking.exception;

public class InsufficientFundsException extends BankingException {

	private static final long serialVersionUID = 1L;

	public InsufficientFundsException(String message) {
		super(message);
	}
}
