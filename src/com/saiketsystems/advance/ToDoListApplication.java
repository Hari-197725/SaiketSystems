package com.saiketsystems.advance;

import java.util.Scanner;

public class ToDoListApplication {

	public void createTask() {

	}

	public static void main(String[] args) {
		ToDoListApplication todo = new ToDoListApplication();
		Scanner scan = new Scanner(System.in);

		System.out.println("What do you want to do: \n1. Create Task\n2. View Tasks\n3. Update Task\n4. Delete Task");
		int option = scan.nextInt();

		switch (option) {
		case 1:

		default:
			throw new IllegalArgumentException("Unexpected value: ");
		}

	}

}
