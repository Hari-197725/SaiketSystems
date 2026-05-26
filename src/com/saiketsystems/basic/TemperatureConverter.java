package com.saiketsystems.basic;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TemperatureConverter {

	public double celsiusToFahrenheit(double temperature) {
		return (9.0 / 5.0) * temperature + 32;
	}

	public double fahrenheitToCelsius(double temperature) {
		return (5.0 / 9.0) * (temperature - 32);
	}

	public static void main(String[] args) {
		TemperatureConverter converter = new TemperatureConverter();
		Scanner scan = new Scanner(System.in);

		while (true) {
			try {

				System.out.println("Select which conversion you want\n1. Celsius -> Fahrenheit\n2. Fahrenheit -> Celsius\n3. Exit Converter");
				int option = scan.nextInt();

				if (option == 3) {
					System.out.println("Bye! See you again");
					break;
				}

				System.out.println("Enter your Temperature");
				double temperature = scan.nextDouble();

				switch (option) {
				case 1:
					System.out.printf("Temperature in Fahrenheit: %.1f%n", converter.celsiusToFahrenheit(temperature));
					break;

				case 2:
					System.out.printf("Temperature in Celsius: %.1f%n", converter.fahrenheitToCelsius(temperature));
					break;

				default:
					System.out.println("Invalid choice! Please select 1, 2 or 3.");
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input! Please enter a valid number.");
				scan.nextLine();
			}
		}

		scan.close();
	}
}