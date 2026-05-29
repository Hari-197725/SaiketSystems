package com.saiketsystems.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.models.CreateTask;
import com.saiketsystems.services.TaskManagement;
import com.saiketsystems.util.FileHandler;

public class TextFileAnalyzer implements TaskManagement {

	private static final String FILE_PATH = "files/Task.txt";

	public TextFileAnalyzer() {
		FileHandler.create(FILE_PATH);
	}

	@Override
	public void createTask(String title, String description, String dueDate) {
		TaskStatus taskStatus = TaskStatus.fromCode(0);
		CreateTask task = new CreateTask(title, description, taskStatus, dueDate);
		FileHandler.update(FILE_PATH, task.toJson());
	}

	@Override
	public void displayTasks() {
		List<CreateTask> tasks = FileHandler.read(FILE_PATH);
		for (CreateTask task : tasks) {
			System.out.println(task.toString());
		}
	}

	@Override
	public CreateTask findTaskById(String id) {
		List<CreateTask> tasks = FileHandler.read(FILE_PATH);
		Optional<CreateTask> task = tasks.stream().filter(t -> t.getId().equals(id)).findFirst();
		if (task.isEmpty()) {
			return null;
		}

		return task.get();
	}

	@Override
	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, String dueDate) {
		if (!description.trim().isEmpty()) {
			taskToUpdate.setDescription(description);
		}

		TaskStatus status = TaskStatus.fromCode(statusAsInt);
		taskToUpdate.setStatus(status);

		if (!dueDate.trim().isEmpty()) {
			taskToUpdate.setDuedate(dueDate);
		}

		List<CreateTask> tasks = FileHandler.read(FILE_PATH);
		List<CreateTask> updatedTasks = new ArrayList<>();
		boolean found = false;

		for (CreateTask task : tasks) {
			if (task.getId().equals(taskToUpdate.getId())) {
				updatedTasks.add(taskToUpdate);
				found = true;
			} else {
				updatedTasks.add(task);
			}
		}

		if (found) {
			FileHandler.writeAll(FILE_PATH, updatedTasks);
			System.out.println("Task updated successfully");
		} else {
			System.out.println("Task not found in file");
		}
	}

	@Override
	public void delete(String id) {
		List<CreateTask> tasks = FileHandler.read(FILE_PATH);
		List<CreateTask> remainingTasks = new ArrayList<>();
		boolean found = false;

		for (CreateTask task : tasks) {
			if (task.getId().equals(id)) {
				found = true;
			} else {
				remainingTasks.add(task);
			}
		}

		if (found) {
			FileHandler.writeAll(FILE_PATH, remainingTasks);
			System.out.println("Task deleted successfully");
		} else {
			System.out.println("Task with ID " + id + " not found!");
		}
	}

	public static void main(String[] args) {
		TextFileAnalyzer taskManagementSystem = new TextFileAnalyzer();
		Scanner scan = new Scanner(System.in);

		while (true) {
			System.out.println("Choose what you want to do: ");
			System.out.println("1. Create a New Task\n2. View All Tasks\n3. Update Task\n4. Delete Task\n5. Exit Application");
			int choice = scan.nextInt();

			scan.nextLine();

			if (choice < 1 || choice > 5) {
				System.out.println("You have selected out of range choice. Please choose from 1 to 5:");
				continue;
			}

			switch (choice) {
			case 1: {
				System.out.println("Enter task's title: ");
				String title = scan.nextLine();

				System.out.println("Enter task's Description: ");
				String description = scan.nextLine();

				System.out.println("Enter task's Due Date: ");
				String duedate = scan.nextLine();

				taskManagementSystem.createTask(title, description, duedate);
				System.out.println("Task created successfully");

				break;
			}

			case 2: {
				taskManagementSystem.displayTasks();
				break;
			}

			case 3: {
				while (true) {
					System.out.println("These are all the current tasks: ");
					taskManagementSystem.displayTasks();

					System.out.println("Enter Task ID to update: ");
					String taskId = scan.nextLine();

					CreateTask taskToUpdate = taskManagementSystem.findTaskById(taskId);
					if (taskToUpdate == null) {
						System.out.println("Invalid ID provided, Please try again");
						continue;
					}

					System.out.println("Note to update:Use enter to separate fields \n(eg:- Description\nStatus\nDue Date)");
					String description = scan.nextLine();

					System.out.println("Enter your new Status:");
					for (TaskStatus ele : TaskStatus.getUpdateTaskStatus()) {
						System.out.println(ele.getCode() + "-" + ele.getName());
					}
					int statusAsInt = scan.nextInt();

					scan.nextLine();
					System.out.println("Enter Due Date to Update");
					String dueDate = scan.nextLine();

					taskManagementSystem.updateTask(taskToUpdate, description, statusAsInt, dueDate);
					break;
				}

				break;
			}

			case 4: {
				System.out.println("These are all the current tasks: ");
				taskManagementSystem.displayTasks();

				System.out.println("Enter Task ID to delete: ");
				String taskId = scan.nextLine();

				if (taskManagementSystem.findTaskById(taskId) == null) {
					System.out.println("Task wiht ID " + taskId + " not found!");
					break;
				}

				taskManagementSystem.delete(taskId);
				break;
			}

			case 5: {
				System.out.println("Bye! See you again");
				System.exit(0);
				break;
			}

			default:
				System.out.println("Invalid choice! Please enter 1-7");

				scan.close();
			}
		}
	}

}