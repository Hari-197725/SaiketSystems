package com.saiketsystems.intermediate;

import java.time.LocalDate;

import java.util.ArrayList;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.models.CreateTask;
import com.saiketsystems.services.TaskManagement;
import com.saiketsystems.util.FileHandler;

public class TextFileAnlayzer implements TaskManagement {

	private static final String FILE_PATH = "files/Task.txt";

	public TextFileAnlayzer() {
		FileHandler.create(FILE_PATH);
	}

	@Override
	public void createTask(String title, String description, LocalDate dueDate) {
		TaskStatus taskStatus = TaskStatus.fromCode(0);
		CreateTask task = new CreateTask(title, description, taskStatus, dueDate);
		FileHandler.update(FILE_PATH, task.toJson());
	}

	@Override
	public void displayTasks() {
		List<CreateTask> tasks = FileHandler.read(FILE_PATH);

		if (tasks.isEmpty()) {
			System.out.println("No tasks are available!");
		}

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
	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, LocalDate dueDate) {
		if (!description.trim().isEmpty()) {
			taskToUpdate.setDescription(description);
		}

		TaskStatus status = TaskStatus.fromCode(statusAsInt);
		taskToUpdate.setStatus(status);

		taskToUpdate.setDuedate(dueDate);

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

	public boolean hasNoTasks() {
		return FileHandler.read(FILE_PATH).isEmpty();
	}

	public void separator() {
		System.out.println("--------------------------------------");
	}

	public static void main(String[] args) {
		TextFileAnlayzer advToDo = new TextFileAnlayzer();
		Scanner scan = new Scanner(System.in);

		while (true) {
			try {
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
					try {

						System.out.println("Enter task's title: ");
						String title = scan.nextLine();

						System.out.println("Enter task's Description: ");
						String description = scan.nextLine();

						System.out.println("Enter task's Due Date: ");
						String duedate = scan.nextLine();
						LocalDate dueDate = CreateTask.parseDueDate(duedate);

						advToDo.createTask(title, description, dueDate);
						System.out.println("Task created successfully");
						advToDo.separator();

					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}

					break;
				}

				case 2: {
					advToDo.displayTasks();
					advToDo.separator();
					break;
				}

				case 3: {
					while (true) {
						System.out.println("These are all the current tasks: ");

						if (advToDo.hasNoTasks()) {
							System.out.println("No tasks are available!");
							break;
						}

						advToDo.displayTasks();

						System.out.println("Enter Task ID to update: ");
						String taskId = scan.nextLine();

						CreateTask taskToUpdate = advToDo.findTaskById(taskId);
						if (taskToUpdate == null) {
							System.out.println("Invalid ID provided, Please try again");
							continue;
						}

						System.out.println("Note to update:Use enter to separate fields eg:- \nDescription\nStatus\nDue Date");
						String description = scan.nextLine();

						System.out.println("Enter your new Status:");
						for (TaskStatus ele : TaskStatus.getUpdateTaskStatus()) {
							System.out.println(ele.getCode() + "-" + ele.getName());
						}
						int statusAsInt = scan.nextInt();

						scan.nextLine();
						System.out.println("Enter Due Date to Update");
						String dueDate = scan.nextLine();

						advToDo.updateTask(taskToUpdate, description, statusAsInt, CreateTask.parseDueDate(dueDate));
						advToDo.separator();
						break;
					}

					break;
				}

				case 4: {
					System.out.println("These are all the current tasks: ");

					if (advToDo.hasNoTasks()) {
						System.out.println("No tasks are available!");
						break;
					}

					advToDo.displayTasks();

					System.out.println("Enter Task ID to delete: ");
					String taskId = scan.nextLine();

					if (advToDo.findTaskById(taskId) == null) {
						System.out.println("Task wiht ID " + taskId + " not found!");
						break;
					}

					advToDo.delete(taskId);
					advToDo.separator();
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
			} catch (Exception e) {
				System.out.println("Error: Invalid input! Please enter a valid number.");
				scan.nextLine();
			}
		}
	}
}