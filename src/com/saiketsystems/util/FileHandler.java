package com.saiketsystems.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.saiketsystems.models.CreateTask;

public class FileHandler {

	public static void create(String filePath) {
		try {
			Path path = Path.of(filePath);
			if (!Files.exists(path)) {
				BufferedWriter bw = new BufferedWriter(new FileWriter(filePath));
				System.out.println("File created Successfully");
				bw.close();
			} else {
				System.out.println("File already exists!");
			}
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static List<CreateTask> read(String filePath) {
		ArrayList<CreateTask> list = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
			String data;

			while ((data = br.readLine()) != null) {
				list.add(CreateTask.fromJson(data));
			}

			return list;
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
			return Collections.emptyList();
		}
	}

	public static void update(String filePath, String updateData) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
			bw.write(updateData);
			bw.newLine();
			System.out.println("Data update Successfully");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void writeAll(String filePath, List<CreateTask> tasks) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {

			for (CreateTask task : tasks) {
				bw.write(task.toJson());
				bw.newLine();
			}

			System.out.println("File updated Successfully");

		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void delete(String filePath) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
			System.out.println("File's data deleted Successfully");
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

}