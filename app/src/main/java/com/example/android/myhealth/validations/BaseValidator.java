package com.example.android.myhealth.validations;

import android.content.Context;
import android.widget.EditText;

import androidx.annotation.StringRes;

import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class BaseValidator {
	private Context context;

	BaseValidator(Context context) {
		this.context = context;
	}

	public void setFieldError(boolean isInvalid, TextInputLayout inputLayout, @StringRes int errorStringId) {
		inputLayout.setErrorEnabled(isInvalid);
		inputLayout.setError(isInvalid ? context.getResources().getString(errorStringId) : null);
	}

	public void setFieldError(boolean isInvalid, TextInputLayout inputLayout, String errorString) {
		inputLayout.setErrorEnabled(isInvalid);
		inputLayout.setError(isInvalid ? errorString : null);
	}

	public Observable<Object> validateField(TextInputLayout inputLayout, EditText editText, Callable<Void> validation) {
		return RxView.focusChanges(editText)
				.skipInitialValue()
				.map(hasFocus -> {
					if (!hasFocus) validation.call();
					return hasFocus;
				})
				.flatMap(hasFocus -> RxTextView.textChanges(editText)
						.skipInitialValue()
						.map(sequence -> {
							if (hasFocus && inputLayout.isErrorEnabled())
								inputLayout.setErrorEnabled(false);
							return hasFocus;
						})
						.skipWhile(__ -> hasFocus)
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.doOnEach(__ -> validation.call())
				);
	}
}
