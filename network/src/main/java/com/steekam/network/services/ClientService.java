package com.steekam.network.services;

import com.steekam.network.models.ClientDTO;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ClientService {

	@Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
	@POST("clients")
	@FormUrlEncoded
	Observable<Response<ClientDTO>> registerClient(
			@Field("username") String username,
			@Field("email") String email,
			@Field("password") String password,
			@Field("client_role") String clientRole
	);

	@Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
	@POST("client_login")
	@FormUrlEncoded
	Observable<Response<ClientDTO>> loginClient(
			@Field("username") String username,
			@Field("password") String password
	);

	@Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
	@PUT("clients/{clientId}")
	@FormUrlEncoded
	Observable<Response<ClientDTO>> updateClient(
			@Path("clientId") long client,
			@Field("username") String username,
			@Field("email") String email
	);

	@Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
	@PUT("clients/password_change/{clientId}")
	@FormUrlEncoded
	Observable<Response<ClientDTO>> updateClientPassword(
			@Path("clientId") long client,
			@Field("old_password") String oldPassword,
			@Field("new_password") String newPassword
	);
}
