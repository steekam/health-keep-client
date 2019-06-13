package com.example.android.myhealth.ui.auth;

import androidx.lifecycle.ViewModel;

import com.example.android.myhealth.R;
import com.jakewharton.rxrelay2.BehaviorRelay;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import timber.log.Timber;

public class SignUpViewModel extends ViewModel {

	public Boolean isEmailValid(CharSequence value) {
		return value.toString().matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])");
	}

	public Boolean isPasswordValid(CharSequence value) {
		// at least 8 characters and at least one number
		return value.toString().matches("^(?=.*\\d).{8,}$");
	}

	public Boolean isUsernameValid (CharSequence value) {
		return !value.toString().isEmpty();
	}

	private final BehaviorRelay<Integer> errorRelay = BehaviorRelay.create();
	private final BehaviorRelay<Boolean> validformRelay = BehaviorRelay.create();

	Observable<Integer> error() {
		return errorRelay;
	}

	Consumer<Throwable> onError() {
		return throwable -> {
			Timber.e(throwable, "Error creating account");
			errorRelay.accept(R.string.signup_error);
		};
	}


}
