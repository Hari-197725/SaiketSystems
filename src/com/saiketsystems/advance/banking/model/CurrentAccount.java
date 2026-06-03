package com.saiketsystems.advance.banking.model;

import com.saiketsystems.advance.banking.exception.InvalidAmountException;

public class CurrentAccount extends BankAccount {

	private static final double MINIMUM_BALANCE = 0.0;
	private static final double MINIMUM_INITIAL_DEPOSIT = 5000.0;

	public CurrentAccount(String accountHolderName, double initialDeposit, int accountNumber) throws InvalidAmountException {
		super(accountHolderName, initialDeposit, accountNumber);
	}

	@Override
	protected double getMinimumBalance() {
		return MINIMUM_BALANCE;
	}

	@Override
	protected double getMinimumInitialDeposit() {
		return MINIMUM_INITIAL_DEPOSIT;
	}

	@Override
	public String getAccountType() {
		return "Current";
	}
}