package com.steekam.entities;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

@AutoValue
@Entity(tableName = "appointments")
public abstract class Appointment {

	public static Appointment create(long appointmentId, String title, String description, String location, LocalDate appointmentDate,
	                                 LocalTime appointmentTime, long clientId, boolean archived, LocalDateTime createdAt, LocalDateTime updatedAt) {
		return new AutoValue_Appointment(appointmentId, title, description, location, appointmentDate, appointmentTime, clientId, archived, createdAt, updatedAt);
	}

	@AutoValue.CopyAnnotations
	@PrimaryKey
	public abstract long appointmentId();

	public abstract String title();

	@Nullable
	public abstract String description();

	@Nullable
	public abstract String location();

	public abstract LocalDate appointmentDate();

	public abstract LocalTime appointmentTime();

	@AutoValue.CopyAnnotations
	@ForeignKey(entity = Client.class,
			parentColumns = "clientId",
			childColumns = "clientId",
			onUpdate = ForeignKey.CASCADE,
			onDelete = ForeignKey.CASCADE)
	public abstract long clientId();

	public abstract boolean archived();

	public abstract LocalDateTime createdAt();

	public abstract LocalDateTime updatedAt();

}