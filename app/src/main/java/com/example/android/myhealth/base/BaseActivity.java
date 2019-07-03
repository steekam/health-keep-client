package com.example.android.myhealth.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.android.myhealth.BuildConfig;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public abstract class BaseActivity extends AppCompatActivity {
	protected final CompositeDisposable disposables = new CompositeDisposable();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//Timbre init
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}

	}

	@Override
	protected void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}
}
