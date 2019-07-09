package com.steekam.network.models;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalTime;

@AutoValue
public abstract class ReminderDTO {
	static TypeAdapter<ReminderDTO> typeAdapter(Gson gson) {
		return new AutoValue_ReminderDTO.GsonTypeAdapter(gson);
	}

	@SerializedName("reminder_id")
	public abstract long reminderId();

	@SerializedName("start_date")
	public abstract LocalDate startDate();

	@SerializedName("reminder_time")
	public abstract LocalTime reminderTime();

	public abstract int repeat();

	@AutoValue.CopyAnnotations
	@Nullable
	public abstract String frequency();

	@SerializedName("reminderble_id")
	public abstract long reminderbleId();

	@SerializedName("reminderble_type")
	public abstract String reminderbleType();

	public abstract int active();
}
