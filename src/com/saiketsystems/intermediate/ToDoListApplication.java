package com.saiketsystems.intermediate;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.models.CreateTask;

public class ToDoListApplication {

	public static List<CreateTask> taskList = new ArrayList<>();

	public void createTask(String title, String description, String dueDate) {
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

	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, String dueDate) {
		if (!description.trim().isEmpty()) {
			taskToUpdate.setDescription(description);
		}

		TaskStatus status = TaskStatus.fromCode(statusAsInt);
		taskToUpdate.setStatus(status);

		if (!dueDate.trim().isEmpty()) {
			taskToUpdate.setDuedate(dueDate);
		}
	}

	public void delete(String id) {
		CreateTask taskToDelete = findTaskById(id);

		if (taskToDelete == null) {
			System.out.println("Task wiht ID " + id + " not found!");
			return;
		}

		taskList.remove(taskToDelete);
		System.out.println("Task deleted successfully!");
	}

	public static void main(String[] args) {
		ToDoListApplication taskManagementSystem = new ToDoListApplication();
		Scanner scan = new Scanner(System.in);

		while (true) {
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
						System.out.println("Task with ID " + taskId + " not found!");
						break;
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
			}

			case 4: {
				System.out.println("These are all the current tasks: ");
				taskManagementSystem.displayTasks();

				System.out.println("Enter Task ID to delete: ");
				String taskId = scan.nextLine();
				taskManagementSystem.delete(taskId);
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
		}
	}

}