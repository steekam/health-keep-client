package com.steekam.network.models;

import com.google.gson.TypeAdapterFactory;
import com.ryanharter.auto.value.gson.GsonTypeAdapterFactory;

@GsonTypeAdapterFactory
public abstract class AdapterFactory implements TypeAdapterFactory {
	public static TypeAdapterFactory create() {
		return new AutoValueGson_AdapterFactory();
	}
}
