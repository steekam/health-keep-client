package com.steekam.network.models;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.LocalTime;

@AutoValue
public abstract class AppointmentDTO {
	static TypeAdapter<AppointmentDTO> typeAdapter(Gson gson) {
		return new AutoValue_AppointmentDTO.GsonTypeAdapter(gson);
	}

	@SerializedName("appointment_id")
	public abstract long appointmentId();

	public abstract String title();

	@Nullable
	public abstract String description();

	@Nullable
	public abstract String location();

	@SerializedName("appointment_date")
	public abstract LocalDate appointmentDate();

	@SerializedName("appointment_time")
	public abstract LocalTime appointmentTime();

	@SerializedName("client_id")
	public abstract long clientId();

	public abstract int archived();

	@SerializedName("created_at")
	public abstract LocalDateTime createdAt();

	@SerializedName("updated_at")
	public abstract LocalDateTime updatedAt();

	public abstract ReminderDTO reminder();
}
