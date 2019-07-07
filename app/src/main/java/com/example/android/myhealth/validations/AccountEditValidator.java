package com.example.android.myhealth.validations;

import android.content.Context;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxrelay2.BehaviorRelay;

import java.util.concurrent.Callable;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AccountEditValidator extends BaseValidator {
	public AccountEditValidator(Context context) {
		super(context);
	}

	public Disposable validateField(TextInputLayout inputLayout, EditText editText, Callable<Void> validation, BehaviorRelay<Boolean> editing) {
		return super.validateField(inputLayout, editText, validation)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.skipWhile(o -> !editing.getValue())
				.subscribe();
	}
}
