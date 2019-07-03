package com.steekam.network.models;

import androidx.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class ClientDTO {
	static TypeAdapter<ClientDTO> typeAdapter(Gson gson) {
		return new AutoValue_ClientDTO.GsonTypeAdapter(gson);
	}

	@SerializedName("client_id")
	public abstract long clientId();

	@Nullable
	public abstract String username();

	public abstract String email();

	@Nullable
	public abstract String password();

	@Nullable
	@SerializedName("email_verified_at")
	public abstract String emailVerifiedAt();

	@SerializedName("created_at")
	public abstract String createdAt();

	@SerializedName("updated_at")
	public abstract String updatedAt();

	@SerializedName("roles")
	public abstract List<Role> rolesList();
}
