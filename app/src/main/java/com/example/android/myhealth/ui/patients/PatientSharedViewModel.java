package com.example.android.myhealth.ui.patients;

import android.app.Application;
import android.content.Context;
import android.view.View;

import androidx.lifecycle.ViewModel;

import com.example.android.myhealth.R;
import com.jakewharton.rxrelay2.BehaviorRelay;
import com.steekam.entities.Client;
import com.steekam.repository.ClientRepository;

import io.reactivex.Completable;
import io.reactivex.Observable;

public class PatientSharedViewModel extends ViewModel {
	public BehaviorRelay<Integer> fabVisibilityRelay = BehaviorRelay.createDefault(View.VISIBLE);
	public BehaviorRelay<String> actionBarTitleRelay = BehaviorRelay.create();
	private ClientRepository clientRepository;

	PatientSharedViewModel(Application application, Context activityContext) {
		this.clientRepository = new ClientRepository(application, activityContext);
		actionBarTitleRelay.accept(activityContext.getString(R.string.dashboard));
	}

	Completable logout() {
		return clientRepository.logout();
	}

	public Observable<Client> getLoggedInUser() {
		return clientRepository.getLoggedInUser();
	}

	public Observable<Integer> fabVisibility() {
		return fabVisibilityRelay;
	}

	public Observable<String> actionBarTitle() {
		return actionBarTitleRelay;
	}
}
