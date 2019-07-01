package com.example.android.myhealth.ui.auth;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;


public class LoginViewModelFactory implements ViewModelProvider.Factory {
	private Application application;
	private Context activityContext;

	LoginViewModelFactory(Application application, Context activityContext) {
		this.application = application;
		this.activityContext = activityContext;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new LoginViewModel(application, activityContext);
	}
}
