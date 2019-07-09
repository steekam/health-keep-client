package com.steekam.network.services;

import com.steekam.network.models.AppointmentDTO;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AppointmentService {

	@Headers({"Accept: application/json"})
	@GET("clients/{clientId}/appointments")
	Observable<Response<List<AppointmentDTO>>> getClientAppointments(@Path("clientId") long clientId);

	@Headers({"Accept: application/json", "Content-Type: application/x-www-form-urlencoded"})
	@POST("clients/{clientId}/appointments")
	@FormUrlEncoded
	Observable<Response<AppointmentDTO>> addAppointment(
			@Path("clientId") long clientId,
			@FieldMap Map<String, String> appointment,
			@FieldMap Map<String, String> reminder
	);
}
