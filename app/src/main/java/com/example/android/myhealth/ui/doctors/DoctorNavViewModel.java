package com.example.android.myhealth.ui.doctors;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.steekam.repository.ClientRepository;

import io.reactivex.Completable;

class DoctorNavViewModel extends ViewModel {
	private ClientRepository clientRepository;

	DoctorNavViewModel(Application application, Context activityContext) {
		this.clientRepository = new ClientRepository(application, activityContext);
	}

	Completable logout() {
		return clientRepository.logout();
	}
}
