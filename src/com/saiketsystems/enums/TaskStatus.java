package com.saiketsystems.enums;

import java.util.ArrayList;

import java.util.List;

public enum TaskStatus {
	CREATED(0, "Created"), INPROGRESS(1, "In-progress"), COMPLETED(2, "Completed");

	private final int code;
	private final String name;

	private TaskStatus(int code, String name) {
		this.code = code;
		this.name = name;
	}

	public int getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public static TaskStatus fromCode(int code) {
		for (TaskStatus status : values()) {
			if (status.code == code) {
				return status;
			}
		}

		throw new IllegalArgumentException("Invalid code: " + code);
	}

	public static List<TaskStatus> getUpdateTaskStatus() {
		List<TaskStatus> updateTaskStatus = new ArrayList<>();
		for (TaskStatus status : values()) {
			if (status != CREATED) {
				updateTaskStatus.add(status);
			}
		}

		return updateTaskStatus;
	}
}