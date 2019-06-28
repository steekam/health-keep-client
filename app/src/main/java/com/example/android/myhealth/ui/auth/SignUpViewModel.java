package com.example.android.myhealth.ui.auth;

import androidx.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;


class SignUpViewModel extends ViewModel {

	BehaviorRelay<Boolean> clienTypeRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> emailRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> usernameRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> passwordRelay = BehaviorRelay.createDefault(false);
	private BehaviorRelay<Boolean> validFormRelay = BehaviorRelay.createDefault(false);

	Boolean isEmailValid(CharSequence value) {
		boolean isValid = value.toString().matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
		emailRelay.accept(isValid);
		changeValidFormRelay();
		return isValid;
	}

	Boolean isPasswordValid(CharSequence value) {
		// at least 8 characters and at least one number
		boolean isValid = value.toString().matches("^(?=.*\\d).{8,}$");
		passwordRelay.accept(isValid);
		changeValidFormRelay();
		return isValid;
	}

	Boolean isUsernameValid(CharSequence value) {
		boolean isValid = !value.toString().isEmpty();
		usernameRelay.accept(isValid);
		changeValidFormRelay();
		return isValid;
	}

	Observable<Boolean> validForm() {
		return validFormRelay;
	}

	void changeValidFormRelay() {
		validFormRelay.accept(emailRelay.getValue() && usernameRelay.getValue() && passwordRelay.getValue() && clienTypeRelay.getValue());
	}
}
