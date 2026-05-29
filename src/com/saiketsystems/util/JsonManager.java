package com.saiketsystems.util;

public class JsonManager {

	public static String escapeJson(String value) {
		if (value == null) {
			return "";
		}

		StringBuilder escaped = new StringBuilder();

		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			switch (character) {
			case '\\':
				escaped.append("\\\\");
				break;
			case '"':
				escaped.append("\\\"");
				break;
			case '\n':
				escaped.append("\\n");
				break;
			case '\r':
				escaped.append("\\r");
				break;
			case '\t':
				escaped.append("\\t");
				break;
			default:
				escaped.append(character);
			}
		}

		return escaped.toString();
	}

	public static String unescapeJson(String value) {
		if (value == null) {
			return "";
		}

		StringBuilder unescaped = new StringBuilder();
		for (int i = 0; i < value.length(); i++) {
			char character = value.charAt(i);
			if (character == '\\' && i + 1 < value.length()) {
				char next = value.charAt(++i);
				switch (next) {
				case 'n':
					unescaped.append('\n');
					break;
				case 'r':
					unescaped.append('\r');
					break;
				case 't':
					unescaped.append('\t');
					break;
				case '\\':
					unescaped.append('\\');
					break;
				case '"':
					unescaped.append('"');
					break;
				default:
					unescaped.append(next);
				}
			} else {
				unescaped.append(character);
			}
		}

		return unescaped.toString();
	}

	public static String extractJsonValue(String json, String key) {
		String marker = "\"" + key + "\":\"";
		int start = json.indexOf(marker);

		if (start < 0) {
			return "";
		}

		start += marker.length();
		StringBuilder value = new StringBuilder();

		for (int i = start; i < json.length(); i++) {
			char character = json.charAt(i);
			if (character == '\\' && i + 1 < json.length()) {
				value.append(character);
				value.append(json.charAt(++i));
			} else if (character == '"') {
				break;
			} else {
				value.append(character);
			}
		}

		return unescapeJson(value.toString());
	}
}