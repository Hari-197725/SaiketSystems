package com.saiketsystems.advance;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class BasicBankingSystem {

	List<CreateAccount> accountList = new ArrayList<>();

	public void createAccount(String accountHolderName, double intialDeposit) {
		CreateAccount accounts = new CreateAccount(accountHolderName, intialDeposit);
		accountList.add(accounts);
	}

	public static void mina(String[] args) {
		BasicBankingSystem banking = new BasicBankingSystem();
		Scanner scan = new Scanner(System.in);

		System.out.println("*******************");
		System.out.println("Welcome to xyz Bank");
		System.out.println("*******************");
		System.out.println(
				"What do you want to do: \n1.Create Account\2. Deposity Money\n3. Withdraw Money/4. Check Balance\n5. Transaction History\n6. Exit");

		int option = scan.nextInt();

		switch (option) {
		case 1:

			System.out.println("Enter your Account Holder Name");
			String accountHolderName = scan.nextLine();

			System.out.println("Enter your Intial Deposit");
			double intialDeposit = scan.nextDouble();

			banking.createAccount(accountHolderName, intialDeposit);
			System.out.println("Account created Successfully");

			break;

		case 2:

		default:
			System.out.println("Invalid option! Please select option between 1 to 6");
		}
	}
}