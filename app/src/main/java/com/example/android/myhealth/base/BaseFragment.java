package com.example.android.myhealth.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.myhealth.BuildConfig;

import io.reactivex.disposables.CompositeDisposable;
import timber.log.Timber;

public abstract class BaseFragment extends Fragment {
	protected CompositeDisposable disposables = new CompositeDisposable();

	public BaseFragment(@LayoutRes int mContentLayoutId) {
		super(mContentLayoutId);
	}

	public BaseFragment() {

	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//Timbre init
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
	}

	@Override
	public void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}
}
