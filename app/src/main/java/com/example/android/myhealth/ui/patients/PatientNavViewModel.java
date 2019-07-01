package com.example.android.myhealth.ui.patients;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.steekam.repository.ClientRepository;

import io.reactivex.Completable;

class PatientNavViewModel extends ViewModel {
	private ClientRepository clientRepository;

	PatientNavViewModel(Application application, Context activityContext) {
		this.clientRepository = new ClientRepository(application, activityContext);
	}

	Completable logout() {
		return clientRepository.logout();
	}
}
