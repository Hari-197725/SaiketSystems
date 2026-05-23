package com.saiketsystems.advance;

public class CreateAccount {
	String accountHolderName;
	int accountNumber;
	double balance;

	public CreateAccount(String accountHolderName, double intialDeposit) {
		this.accountHolderName = accountHolderName;
		this.accountNumber = (int) Math.random();
		this.balance = intialDeposit;
	}

	public String getAccountName() {
		return accountHolderName;
	}

	public void setAccountName(String accountName) {
		this.accountHolderName = accountName;
	}

	public int getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(int accountNumber) {
		this.accountNumber = accountNumber;
	}

	public double getIntialDeposit() {
		return balance;
	}

	public void setIntialDeposit(double intialDeposit) {
		if (intialDeposit < 500) {
			System.out.println("Intial Deposit amount Should be 500 or above than 500");
		}

		this.balance = intialDeposit;
	}

}