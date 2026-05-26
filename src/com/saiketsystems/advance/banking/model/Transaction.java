package com.saiketsystems.advance.banking.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transaction {

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	private final TransactionType type;
	private final double amount;
	private final double balanceAfter;
	private final LocalDateTime timestamp;

	public Transaction(TransactionType type, double amount, double balanceAfter) {
		this.type = type;
		this.amount = amount;
		this.balanceAfter = balanceAfter;
		this.timestamp = LocalDateTime.now();
	}

	public TransactionType getType() {
		return type;
	}

	public double getAmount() {
		return amount;
	}

	public double getBalanceAfter() {
		return balanceAfter;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	@Override
	public String toString() {
		return String.format("[%s] %s: $%.2f | Balance after: $%.2f",
				timestamp.format(FORMATTER), type, amount, balanceAfter);
	}
}
