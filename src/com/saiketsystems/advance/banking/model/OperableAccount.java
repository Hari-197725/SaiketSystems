package com.saiketsystems.advance.banking.model;

import java.util.List;

import com.saiketsystems.advance.banking.exception.InsufficientFundsException;
import com.saiketsystems.advance.banking.exception.InvalidAmountException;

public interface OperableAccount {

	void deposit(double amount) throws InvalidAmountException;

	void withdraw(double amount) throws InsufficientFundsException, InvalidAmountException;

	double getBalance();

	List<Transaction> getTransactionHistory();

	String getAccountHolderName();

	int getAccountNumber();

	String getAccountType();
}
