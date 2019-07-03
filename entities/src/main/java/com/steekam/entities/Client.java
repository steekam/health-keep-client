package com.steekam.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.auto.value.AutoValue;

@AutoValue
@Entity(tableName = "client")
public abstract class Client {
	public static Client create(long clientId, String username, String email, String role, boolean verified, boolean loggedIn) {
		return new AutoValue_Client(clientId, username, email, role, verified, loggedIn);
	}

	@AutoValue.CopyAnnotations
	@PrimaryKey
	public abstract long clientId();

	public abstract String username();

	public abstract String email();

	public abstract String role();

	public abstract boolean verified();

	public abstract boolean loggedIn();
}