package com.steekam.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

@AutoValue
@Entity(tableName = "reminders")
public abstract class Reminder {

	public static Reminder create(long reminderId, LocalDate startDate, LocalTime reminderTime, boolean repeat, String frequency, long reminderbleId,
	                              String reminderbleType, boolean active) {
		return new AutoValue_Reminder(reminderId, startDate, reminderTime,
				repeat, frequency, reminderbleId, reminderbleType, active);
	}

	@AutoValue.CopyAnnotations
	@PrimaryKey
	public abstract long reminderId();

	public abstract LocalDate startDate();

	public abstract LocalTime reminderTime();

	public abstract boolean repeat();

	@Nullable
	public abstract String frequency();

	public abstract long reminderbleId();

	public abstract String reminderbleType();

	public abstract boolean active();
}
