package com.steekam.network.gsonAdapters;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import org.threeten.bp.LocalTime;

import java.io.IOException;

public class LocalTimeAdapter extends TypeAdapter<LocalTime> {
	@Override
	public void write(JsonWriter out, LocalTime value) throws IOException {
		if (value == null) {
			out.nullValue();
			return;
		}
		out.value(value.toString());
	}

	@Override
	public LocalTime read(JsonReader in) throws IOException {
		if (in.peek() == JsonToken.NULL) {
			in.nextNull();
			return null;
		}
		return LocalTime.parse(in.nextString());
	}
}
