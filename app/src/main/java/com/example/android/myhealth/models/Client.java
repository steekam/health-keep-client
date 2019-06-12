package com.example.android.myhealth.models;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@AutoValue
public abstract class Client {
    @SerializedName("client_id")
    public abstract long clientId();

    public abstract  String username();

    public abstract  String email();

    @SerializedName("email_verified_at")
    public abstract String emailVerifiedAt();

    @SerializedName("created_at")
    public abstract String createdAt();

    @SerializedName("updated_at")
    public abstract String updatedAt();

    @SerializedName("roles")
    public abstract List<Role> rolesList();

    public static TypeAdapter<Client> typeAdapter(Gson gson){
        return new AutoValue_Client.GsonTypeAdapter(gson);
    }
}
