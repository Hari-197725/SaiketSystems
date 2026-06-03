package com.saiketsystems.advance.banking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.saiketsystems.advance.banking.exception.AccountNotFoundException;
import com.saiketsystems.advance.banking.exception.InvalidAmountException;
import com.saiketsystems.advance.banking.model.BankAccount;
import com.saiketsystems.advance.banking.model.CurrentAccount;
import com.saiketsystems.advance.banking.model.SavingsAccount;

public class Bank {

	private final Map<Integer, BankAccount> accounts = new HashMap<>();
	private int nextAccountNumber = 10001;

	public BankAccount createSavingsAccount(String accountHolderName, double initialDeposit) throws InvalidAmountException {
		BankAccount account = new SavingsAccount(accountHolderName, initialDeposit, nextAccountNumber++);
		accounts.put(account.getAccountNumber(), account);
		return account;
	}

	public BankAccount createCurrentAccount(String accountHolderName, double initialDeposit) throws InvalidAmountException {
		BankAccount account = new CurrentAccount(accountHolderName, initialDeposit, nextAccountNumber++);
		accounts.put(account.getAccountNumber(), account);
		return account;
	}

	public BankAccount findAccount(int accountNumber) throws AccountNotFoundException {
		BankAccount account = accounts.get(accountNumber);
		if (account == null) {
			throw new AccountNotFoundException("No account found with number: " + accountNumber);
		}
		return account;
	}

	public List<BankAccount> getAllAccounts() {
		return new ArrayList<>(accounts.values());
	}

	public boolean hasAccounts() {
		return !accounts.isEmpty();
	}
}
