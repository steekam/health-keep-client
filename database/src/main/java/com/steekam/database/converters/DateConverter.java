package com.steekam.database.converters;

import androidx.annotation.NonNull;
import androidx.room.TypeConverter;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

public class DateConverter {

	@TypeConverter
	public static LocalDate toLocalDate(String dateString) {
		return LocalDate.parse(dateString);
	}

	@TypeConverter
	public static String fromLocalDate(@NonNull LocalDate localDate) {
		return localDate.toString();
	}

	@TypeConverter
	public static LocalTime toLocalTime(String timeString) {
		return LocalTime.parse(timeString);
	}

	@TypeConverter
	public static String fromLocalTime(@NonNull LocalTime localTime) {
		return localTime.toString();
	}

	@TypeConverter
	public static LocalDateTime toLocalDateTime(String dateTimeString) {
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
		return LocalDateTime.parse(dateTimeString);
	}

	@TypeConverter
	public static String fromLocalDateTime(@NonNull LocalDateTime localDateTime) {
		return localDateTime.toString();
	}
}
