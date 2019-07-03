package com.example.android.myhealth.ui.auth;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.steekam.entities.Client;
import com.steekam.repository.ClientRepository;

import io.reactivex.Observable;

class LoginViewModel extends ViewModel {
	private ClientRepository clientRepository;

	private BehaviorRelay<Boolean> validFormRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> usernameRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> passwordRelay = BehaviorRelay.createDefault(false);

	LoginViewModel(Application application, Context activityContext) {
		clientRepository = new ClientRepository(application, activityContext);
	}

	Observable<Boolean> validForm() {
		return validFormRelay;
	}

	Boolean isUsernameValid(CharSequence value) {
		boolean isValid = !value.toString().isEmpty();
		usernameRelay.accept(isValid);
		changeValidFormRelay();
		return isValid;
	}

	Boolean isPasswordValid(CharSequence value) {
		boolean isValid = !value.toString().isEmpty();
		passwordRelay.accept(isValid);
		changeValidFormRelay();
		return isValid;
	}

	private void changeValidFormRelay() {
		validFormRelay.accept(usernameRelay.getValue() && passwordRelay.getValue());
	}

	Observable<Client> saveLoggedInClient(Client client) {
		return clientRepository.saveLoggedInClient(client);
	}
}
