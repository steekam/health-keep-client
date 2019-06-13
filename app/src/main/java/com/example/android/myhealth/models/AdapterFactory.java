package com.example.android.myhealth.models;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class AdapterFactory implements TypeAdapterFactory {
	public  static TypeAdapterFactory create() {
		return new AutoValueGson_AdapterFactory();
	}
}
