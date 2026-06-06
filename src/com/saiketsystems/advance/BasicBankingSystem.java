package com.saiketsystems.advance;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.saiketsystems.advance.banking.exception.AccountNotFoundException;
import com.saiketsystems.advance.banking.exception.BankingException;
import com.saiketsystems.advance.banking.exception.InvalidAmountException;
import com.saiketsystems.advance.banking.model.BankAccount;
import com.saiketsystems.advance.banking.model.Transaction;
import com.saiketsystems.advance.banking.Bank;

public class BasicBankingSystem {

	private final Bank bank = new Bank();
	private final Scanner scan = new Scanner(System.in);

	public static void main(String[] args) {
		BasicBankingSystem app = new BasicBankingSystem();

		System.out.println("**********************");
		System.out.println("Welcome to Saiket Bank");
		System.out.println("**********************");

		while (true) {
			try {
				app.printMenu();
				int option = app.scan.nextInt();
				app.scan.nextLine();

				switch (option) {
				case 1:
					app.createAccount();
					break;
				case 2:
					app.depositMoney();
					break;
				case 3:
					app.withdrawMoney();
					break;
				case 4:
					app.checkBalance();
					break;
				case 5:
					app.viewTransactionHistory();
					break;
				case 6:
					app.listAllAccounts();
					break;
				case 7:
					System.out.println("Thank you for banking with us. Goodbye!");
					app.scan.close();
					return;
				default:
					System.out.println("Invalid option! Please choose a number between 1 and 7.");
				}

			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input! Please enter a valid number.");
				app.scan.nextLine();
			} catch (BankingException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}
	}

	private void printMenu() {
		System.out.println();
		System.out.println("========== Banking Menu ==========");
		System.out.println("1. Create Account");
		System.out.println("2. Deposit Money");
		System.out.println("3. Withdraw Money");
		System.out.println("4. Check Balance");
		System.out.println("5. Transaction History");
		System.out.println("6. List All Accounts");
		System.out.println("7. Exit");
		System.out.println("==================================");
		System.out.print("Select an option: ");
	}

	private void createAccount() throws BankingException {
		System.out.println("Select account type:\n1. Savings (min. deposit $500)\n2. Current (min. deposit $5000)");
		int type = scan.nextInt();
		scan.nextLine();

		System.out.print("Enter account holder name: ");
		String name = scan.nextLine().trim();
		if (name.isEmpty()) {
			throw new InvalidAmountException("Account holder name cannot be empty.");
		}

		System.out.print("Enter initial deposit: ₹");
		double initialDeposit = scan.nextDouble();
		scan.nextLine();

		BankAccount account;
		if (type == 1) {
			account = bank.createSavingsAccount(name, initialDeposit);
		} else if (type == 2) {
			account = bank.createCurrentAccount(name, initialDeposit);
		} else {
			throw new InvalidAmountException("Invalid account type. Choose 1 for Savings or 2 for Current.");
		}

		System.out.printf("Account created successfully!%n");
		printAccountSummary(account);
	}

	private void depositMoney() throws BankingException {
		BankAccount account = promptForAccount();
		System.out.print("Enter deposit amount: ₹");
		double amount = scan.nextDouble();
		scan.nextLine();

		account.deposit(amount);
		System.out.printf("Deposit successful. New balance: $%.2f%n", account.getBalance());
	}

	private void withdrawMoney() throws BankingException {
		BankAccount account = promptForAccount();
		System.out.print("Enter withdrawal amount: ₹");
		double amount = scan.nextDouble();
		scan.nextLine();

		account.withdraw(amount);
		System.out.printf("Withdrawal successful. New balance: $%.2f%n", account.getBalance());
	}

	private void checkBalance() throws AccountNotFoundException {
		BankAccount account = promptForAccount();
		System.out.printf("Account #%d (%s)%n", account.getAccountNumber(), account.getAccountType());
		System.out.printf("Holder: %s%n", account.getAccountHolderName());
		System.out.printf("Current balance: $%.2f%n", account.getBalance());
	}

	private void viewTransactionHistory() throws AccountNotFoundException {
		BankAccount account = promptForAccount();
		List<Transaction> history = account.getTransactionHistory();

		System.out.printf("Transaction history for account #%d (%s):%n", account.getAccountNumber(), account.getAccountHolderName());

		if (history.isEmpty()) {
			System.out.println("No transactions recorded.");
			return;
		}

		for (Transaction transaction : history) {
			System.out.println(transaction);
		}
	}

	private void listAllAccounts() {
		List<BankAccount> accounts = bank.getAllAccounts();

		if (accounts.isEmpty()) {
			System.out.println("No accounts registered yet.");
			return;
		}

		System.out.println("--- All Accounts ---");
		for (BankAccount account : accounts) {
			printAccountSummary(account);
			System.out.println();
		}
	}

	private BankAccount promptForAccount() throws AccountNotFoundException {
		if (!bank.hasAccounts()) {
			throw new AccountNotFoundException("No accounts exist. Please create an account first.");
		}

		System.out.print("Enter account number: ");
		int accountNumber = scan.nextInt();
		scan.nextLine();
		return bank.findAccount(accountNumber);
	}

	private void printAccountSummary(BankAccount account) {
		System.out.printf("Account #%d | %s | Holder: %s | Balance: $%.2f%n", account.getAccountNumber(), account.getAccountType(), account
				.getAccountHolderName(), account.getBalance());
	}
}