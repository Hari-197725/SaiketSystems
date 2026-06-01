package com.saiketsystems.models;

import java.time.LocalDate;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.UUID;

import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.util.JsonManager;

public class CreateTask {

	private static final DateTimeFormatter DUE_DATE_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	private String id;
	private String title;
	private String description;
	private TaskStatus status;
	private LocalDate duedate;

	public CreateTask(String title, String description, TaskStatus status, LocalDate duedate) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.description = description;
		this.status = status;
		ensureValidDueDate(duedate);
		this.duedate = duedate;
	}

	public CreateTask(String id, String title, String description, TaskStatus status, LocalDate duedate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.duedate = duedate;
	}

	public static LocalDate parseDueDate(String dueDateText) {
		if (dueDateText == null || dueDateText.isBlank()) {
			throw new IllegalArgumentException("Due date cannot be empty");
		}

		try {
			LocalDate parsed = LocalDate.parse(dueDateText.trim(), DUE_DATE_FORMAT);
			if (parsed.isBefore(LocalDate.now())) {
				throw new IllegalArgumentException("Due date must be today or a future date");
			}

			return parsed;
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid due date. Use dd/MM/yyyy (e.g. 16/07/2027)");
		}
	}

	private static LocalDate parseDueDateFromStorage(String dueDateText) {
		if (dueDateText == null || dueDateText.isBlank()) {
			throw new IllegalArgumentException("Due date in stored task data cannot be empty");
		}

		try {
			return LocalDate.parse(dueDateText.trim(), DUE_DATE_FORMAT);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Invalid due date in stored task data. Use dd/MM/yyyy");
		}
	}

	public String getId() {
		return id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
		System.out.println("Description Update Successfully");
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
		System.out.println("Status Updated Successfully");
	}

	public LocalDate getDuedate() {
		return duedate;
	}

	public void setDuedate(LocalDate duedate) {
		ensureValidDueDate(duedate);
		this.duedate = duedate;
		System.out.println("Due Date Update Successfully");
	}

	private static void ensureValidDueDate(LocalDate duedate) {
		if (duedate == null) {
			throw new IllegalArgumentException("Due date cannot be null");
		}
		if (duedate.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Due date must be today or a future date");
		}
	}

	@Override
	public String toString() {
		return "ID: " + getId() + "\n" + "Title: " + getTitle() + "\n" + "Description: " + getDescription() + "\n" + "Task Status: " + getStatus()
				+ "\n" + "Due Date: " + getDuedate() + "\n";
	}

	public String toJson() {
		return "{" + "\"id\":\"" + JsonManager.escapeJson(this.getId()) + "\"," + "\"title\":\"" + JsonManager.escapeJson(this.getTitle()) + "\","
				+ "\"description\":\"" + JsonManager.escapeJson(this.getDescription()) + "\"," + "\"status\":\"" + JsonManager.escapeJson(this
						.getStatus().name()) + "\"," + "\"duedate\":\"" + JsonManager.escapeJson(this.getDuedate().format(DUE_DATE_FORMAT)) + "\""
				+ "}";
	}

	public static CreateTask fromJson(String jsonString) {
		if (jsonString == null || jsonString.isBlank()) {
			throw new IllegalArgumentException("JSON string cannot be null or blank");
		}

		String id = JsonManager.extractJsonValue(jsonString, "id");
		String title = JsonManager.extractJsonValue(jsonString, "title");
		String description = JsonManager.extractJsonValue(jsonString, "description");
		String statusName = JsonManager.extractJsonValue(jsonString, "status");
		String duedate = JsonManager.extractJsonValue(jsonString, "duedate");

		TaskStatus status = TaskStatus.valueOf(statusName);
		LocalDate parsedDueDate = parseDueDateFromStorage(duedate);
		return new CreateTask(id, title, description, status, parsedDueDate);
	}
}