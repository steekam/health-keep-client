package com.example.android.myhealth.network.services;

import com.example.android.myhealth.models.Client;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ClientService {

    @Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
    @POST("clients")
    @FormUrlEncoded
    Observable<Response<Client>> registerClient(
            @Field("username") String username,
            @Field("email") String email,
            @Field("password") String password,
            @Field("client_role") String clientRole
    );

    @Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
    @POST("clients_login")
    @FormUrlEncoded
    Call<Client> loginClient(
            @Field("username") String username,
            @Field("password") String password
    );

    @Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
    @POST("LoginActivity")
    @FormUrlEncoded
    Call<ResponseBody> testLogin(
            @Field("signUpEmail") String email,
            @Field("password") String password
    );
}
