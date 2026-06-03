package com.saiketsystems.basic;

import java.util.Random;

import java.util.Scanner;

public class NumberGuessingGame {

	public void hints(int guess, int secretNum) {

		if (guess > secretNum) {

			if ((guess - secretNum) <= 5) {
				System.out.println("Almost there, reduce the number a little.");
			} else {
				System.out.println("Try a smaller number!");
			}

		} else if (guess < secretNum) {

			if ((secretNum - guess) <= 5) {
				System.out.println("Increase your guess a little.");
			} else {
				System.out.println("Try a bigger number!");
			}

		} else {
			System.out.println("Excellent! You found the correct number.");
		}
	}

	public static void main(String[] args) {
		NumberGuessingGame guessing = new NumberGuessingGame();
		Scanner scan = new Scanner(System.in);
		Random random = new Random();
		int secretNum = random.nextInt(100) + 1;

		System.out.println("Guess the number \"1-100\" ");
		System.out.println("You have 3 attempts");
		int attempt = 3;

		while (attempt > 0) {

			int guess = scan.nextInt();
			if (guess < 0 || guess > 100) {
				System.out.println("You entered out of range please guess a number between 1 to 100");
				continue;
			}
			guessing.hints(guess, secretNum);
			attempt--;
			System.out.println("Attempts left: " + attempt);
		}

		System.out.println("Game Over! The number was: " + secretNum);

		scan.close();
	}
}