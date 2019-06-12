package com.example.android.myhealth.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

@AutoValue
public abstract class Role {
	@SerializedName("client_role_id")
	public abstract long clientRoleId();

	@SerializedName("role_name")
	public abstract String roleName();

	public static TypeAdapter<Role> typeAdapter(Gson gson){
		return new AutoValue_Role.GsonTypeAdapter(gson);
	}

}
