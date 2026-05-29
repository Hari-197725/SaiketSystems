package com.saiketsystems.models;

import java.util.UUID;
import com.saiketsystems.enums.TaskStatus;
import com.saiketsystems.util.JsonManager;

public class CreateTask {
	private String id;
	private String title;
	private String description;
	private TaskStatus status;
	private String duedate;

	public CreateTask(String title, String description, TaskStatus status, String duedate) {
		this.id = UUID.randomUUID().toString();
		this.title = title;
		this.description = description;
		this.status = status;
		this.duedate = duedate;
	}

	public CreateTask(String id, String title, String description, TaskStatus status, String duedate) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.status = status;
		this.duedate = duedate;
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

	public String getDuedate() {
		return duedate;
	}

	public void setDuedate(String duedate) {
		this.duedate = duedate;
		System.out.println("Due Date Update Successfully");
	}

	@Override
	public String toString() {
		return "ID: " + getId() + "\n" + "Title: " + getTitle() + "\n" + "Description: " + getDescription() + "\n" + "Task Status: " + getStatus()
				+ "\n" + "Due Date: " + getDuedate() + "\n";
	}

	public String toJson() {
		return "{" + "\"id\":\"" + JsonManager.escapeJson(this.getId()) + "\"," + "\"title\":\"" + JsonManager.escapeJson(this.getTitle()) + "\","
				+ "\"description\":\"" + JsonManager.escapeJson(this.getDescription()) + "\"," + "\"status\":\"" + JsonManager.escapeJson(this
						.getStatus().name()) + "\"," + "\"duedate\":\"" + JsonManager.escapeJson(this.getDuedate()) + "\"" + "}";
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
		return new CreateTask(id, title, description, status, duedate);
	}

}