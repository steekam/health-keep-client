package com.example.android.myhealth.ui.patients.appointment;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class AppointmentViewModelFactory implements ViewModelProvider.Factory {
	private Application application;
	private Context activityContext;

	public AppointmentViewModelFactory(Application application, @NonNull Context activityContext) {
		this.application = application;
		this.activityContext = activityContext;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new AppointmentsViewModel(application, activityContext);
	}
}
