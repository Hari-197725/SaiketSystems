package com.saiketsystems.services;

import com.saiketsystems.models.CreateTask;

public interface TaskManagement {
	public void createTask(String title, String description, String dueDate);

	public void displayTasks();

	public CreateTask findTaskById(String id);

	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, String dueDate);

	public void delete(String id);
}