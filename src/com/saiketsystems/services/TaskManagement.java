package com.saiketsystems.services;

import java.time.LocalDate;

import com.saiketsystems.models.CreateTask;

public interface TaskManagement {
	public void createTask(String title, String description, LocalDate dueDate);

	public void displayTasks();

	public CreateTask findTaskById(String id);

	public void updateTask(CreateTask taskToUpdate, String description, int statusAsInt, LocalDate dueDate);

	public void delete(String id);
}