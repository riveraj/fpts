package edu.rit.se.fpts.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateUtil {

	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public static String format(LocalDate date) {
		return DATE_FORMATTER.format(date);
	}

	public static LocalDate parse(String value) {
		try {
			return DATE_FORMATTER.parse(value, LocalDate::from);
		} catch (DateTimeParseException e) {
			return null;
		}
	}

	public static boolean validDate(String value) {
		return DateUtil.parse(value) != null;
	}
}