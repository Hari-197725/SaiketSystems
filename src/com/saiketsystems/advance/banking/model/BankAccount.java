package com.saiketsystems.advance.banking.model;

import java.util.ArrayList;
import java.util.List;

import com.saiketsystems.advance.banking.exception.InsufficientFundsException;
import com.saiketsystems.advance.banking.exception.InvalidAmountException;

public abstract class BankAccount {

	protected final int accountNumber;
	protected String accountHolderName;
	protected double balance;
	protected final List<Transaction> transactionHistory;

	protected BankAccount(String accountHolderName, double initialDeposit, int accountNumber) throws InvalidAmountException {
		validatePositiveAmount(initialDeposit, "Initial deposit");
		if (initialDeposit < getMinimumInitialDeposit()) {
			throw new InvalidAmountException(String.format("Initial deposit must be at least $%.2f for %s accounts.", getMinimumInitialDeposit(),
					getAccountType()));
		}

		this.accountHolderName = accountHolderName;
		this.balance = initialDeposit;
		this.accountNumber = accountNumber;
		this.transactionHistory = new ArrayList<>();
		recordTransaction(TransactionType.DEPOSIT, initialDeposit);
	}

	protected abstract double getMinimumBalance();

	protected abstract double getMinimumInitialDeposit();

	public abstract String getAccountType();

	public void deposit(double amount) throws InvalidAmountException {
		validatePositiveAmount(amount, "Deposit");
		balance += amount;
		recordTransaction(TransactionType.DEPOSIT, amount);
	}

	public void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException {
		validatePositiveAmount(amount, "Withdrawal");

		if (balance - amount < getMinimumBalance()) {
			throw new InsufficientFundsException(String.format("Insufficient funds. Available: $%.2f (minimum balance $%.2f must remain).", balance
					- getMinimumBalance(), getMinimumBalance()));
		}

		balance -= amount;
		recordTransaction(TransactionType.WITHDRAWAL, amount);
	}

	public double getBalance() {
		return balance;
	}

	public List<Transaction> getTransactionHistory() {
		return new ArrayList<>(transactionHistory);
	}

	public String getAccountHolderName() {
		return accountHolderName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	protected void validatePositiveAmount(double amount, String operation) throws InvalidAmountException {
		if (amount <= 0) {
			throw new InvalidAmountException(operation + " amount must be greater than zero.");
		}
	}

	protected void recordTransaction(TransactionType type, double amount) {
		transactionHistory.add(new Transaction(type, amount, balance));
	}
}
