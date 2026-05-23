package com.saiketsystems.basic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class CalculatorApplication {

	public double add(double firstNum, double secondNum) {
		return firstNum + secondNum;
	}

	public double subtract(double firstNum, double secondNum) {
		return firstNum - secondNum;
	}

	public double multiply(double firstNum, double secondNum) {
		return firstNum * secondNum;
	}

	public double divide(double firstNum, double secondNum) {
		if (secondNum == 0) {
			throw new ArithmeticException("Cannot divided by Zero!");
		}

		return firstNum / secondNum;
	}

	public double modulo(double firstNum, double secondNum) {
		if (secondNum == 0) {
			throw new ArithmeticException("Cannot perform modulo with Zero!");
		}

		return firstNum % secondNum;
	}

	public static void main(String[] args) {
		CalculatorApplication calculator = new CalculatorApplication();
		Scanner scan = new Scanner(System.in);

		while (true) {
			try {
				System.out.println("===========Simple Calculator Application==========");
				System.out.println(
						"Select what Operation you want to do: \n1. Addition\n2. Subtraction\n3. Multiplication\n4. Division\n5. Modulo\n6. Exit Calculator");
				int option = scan.nextInt();

				if (option < 1 || option > 6) {
					System.out.println("Invalid option! Please choose option between 1 to 6.");
					continue;
				}

				if (option == 6) {
					System.out.println("See you again [^_^]");
					break;
				}

				System.out.println("Enter your First Number");
				double firstNum = scan.nextDouble();

				System.out.println("Enter your Second Number");
				double secondNum = scan.nextDouble();

				switch (option) {
				case 1:
					System.out.printf("The Addition of two numbers are: %.2f", calculator.add(firstNum, secondNum));
					System.out.println();
					break;

				case 2:
					System.out.printf("The Subtraction of two numbers are: %.2f", calculator.subtract(firstNum, secondNum));
					System.out.println();
					break;

				case 3:
					System.out.printf("The Multiplication of two numbers are: %.2f", calculator.multiply(firstNum, secondNum));
					System.out.println();
					break;

				case 4:
					System.out.printf("The Division of two numbers are: %.2f", calculator.divide(firstNum, secondNum));
					System.out.println();
					break;

				case 5:
					System.out.printf("The Modulo of two numbers are: %.2f", calculator.modulo(firstNum, secondNum));
					System.out.println();
					break;

				default:
					System.out.println("You entered Invalid option, Please try again.");
				}

			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input! Please enter a valid number.");
				scan.nextLine();
			} catch (ArithmeticException e) {
				System.out.println("Error: " + e.getMessage());
			}
		}

		scan.close();

	}
}