package com.example.android.myhealth.ui.patients.appointment;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.steekam.entities.Appointment;
import com.steekam.repository.AppointmentRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;

class AppointmentsViewModel extends ViewModel {
	BehaviorRelay<Boolean> loadingRelay = BehaviorRelay.createDefault(true);
	BehaviorRelay<Boolean> emptyAppointmentsRelay = BehaviorRelay.createDefault(true);
	BehaviorRelay<List<Appointment>> appointmentsRelay = BehaviorRelay.createDefault(new ArrayList<>());
	private AppointmentRepository appointmentRepository;

	AppointmentsViewModel(Application application, @NonNull Context activityContext) {
		appointmentRepository = new AppointmentRepository(application, activityContext);
	}

	Observable<List<Appointment>> getClientAppointments() {
		return appointmentsRelay;
	}

	Observable<List<Appointment>> getClientAppointmentsCached() {
		return appointmentRepository.getClientAppointments()
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io());
	}

	Observable<List<Appointment>> fetchClientAppointments() {
		return appointmentRepository.fetchClientAppointments()
				.flatMap(appointmentDTOS -> appointmentRepository.insertAppointments(appointmentDTOS)
						.andThen(appointmentRepository.getClientAppointments()));
	}

	Observable<Boolean> emptyAppointments() {
		return emptyAppointmentsRelay;
	}

	Observable<Boolean> loading() {
		return loadingRelay;
	}
}
