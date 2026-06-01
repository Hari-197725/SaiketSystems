package com.saiketsystems.intermediate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.models.CreateTask;
import com.saiketsystems.services.TaskManagement;

public class ToDoListApplication implements TaskManagement {

	public static List<CreateTask> taskList = new ArrayList<>();

	public void createTask(String title, String description, LocalDate dueDate) {
		TaskStatus taskStatus = TaskStatus.fromCode(0);
		CreateTask tasks = new CreateTask(title, description, taskStatus, dueDate);
		taskList.add(tasks);
	}

	public void displayTasks() {
		if (taskList.isEmpty()) {
			System.out.println("No tasks are available!");
		} else {
			for (CreateTask task : taskList) {
				System.out.println(task.toString());
				System.out.println();
			}
		}
	}

	public CreateTask findTaskById(String id) {
		for (CreateTask task : taskList) {
			if (id.equals(task.getId())) {
				return task;
			}
		}

		return null;
	}

	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, LocalDate dueDate) {
		if (taskList.isEmpty()) {
			System.out.println("No tasks are available!");
		}

		if (!description.trim().isEmpty()) {
			taskToUpdate.setDescription(description);
		}

		TaskStatus status = TaskStatus.fromCode(statusAsInt);
		taskToUpdate.setStatus(status);

		taskToUpdate.setDuedate(dueDate);
	}

	public void delete(String id) {
		if (taskList.isEmpty()) {
			System.out.println("No tasks are available!");
		}

		CreateTask taskToDelete = findTaskById(id);

		if (taskToDelete == null) {
			System.out.println("Task wiht ID " + id + " not found!");
			return;
		}

		taskList.remove(taskToDelete);
		System.out.println("Task deleted successfully!");
	}

	public void separator() {
		System.out.println("--------------------------------------");
	}

	public static void main(String[] args) {
		ToDoListApplication todo = new ToDoListApplication();

		Scanner scan = new Scanner(System.in);

		while (true) {
			try {

				System.out.println("Choose what you want to do: ");
				System.out.println("1. Create a New Task\n2. View All Tasks\n3. Update Task\n4. Delete Task\n5. Exit Task Mangement System");
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

						todo.createTask(title, description, dueDate);
						System.out.println("Task created successfully");

						todo.separator();

					} catch (IllegalArgumentException e) {
						System.out.println(e.getMessage());
					}
					break;
				}

				case 2: {
					todo.displayTasks();
					todo.separator();
					break;
				}

				case 3: {
					if (taskList.isEmpty()) {
						System.out.println("No tasks are available!");
						todo.separator();
						continue;
					}

					System.out.println("These are the current tasks: ");
					todo.displayTasks();

					System.out.println("Enter Task ID to update: ");
					String taskId = scan.nextLine();

					CreateTask taskToUpdate = todo.findTaskById(taskId);

					if (taskToUpdate == null) {
						System.out.println("Task with ID " + taskId + " not found!");
						break;
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

					todo.updateTask(taskToUpdate, description, statusAsInt, CreateTask.parseDueDate(dueDate));
					todo.separator();

					break;
				}

				case 4: {
					if (taskList.isEmpty()) {
						System.out.println("No tasks are available!");
						todo.separator();
						continue;
					}

					System.out.println("These are all the current tasks: ");
					todo.displayTasks();

					System.out.println("Enter Task ID to delete: ");
					String taskId = scan.nextLine();
					todo.delete(taskId);
					todo.separator();

					break;
				}

				case 5: {
					System.out.println("Bye! See you again");
					System.exit(0);
					break;
				}

				default:
					System.out.println("Invalid choice! Please enter 1-5");
					scan.close();
				}
			} catch (InputMismatchException e) {
				System.out.println("Error: Invalid input! Please enter a valid number.");
				scan.nextLine();
			}
		}
	}
}