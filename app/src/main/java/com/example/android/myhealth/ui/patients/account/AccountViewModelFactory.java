package com.example.android.myhealth.ui.patients.account;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

class AccountViewModelFactory implements ViewModelProvider.Factory {
	private final Application application;
	private final Context activityContext;

	AccountViewModelFactory(Application application, Context activityContext) {
		this.application = application;
		this.activityContext = activityContext;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new AccountViewModel(application, activityContext);
	}
}
