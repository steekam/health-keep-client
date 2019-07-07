package com.example.android.myhealth.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.steekam.authentication.Authentication;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class SignUpActivity extends BaseActivity {
	// Views
	@BindView(R.id.signUpUsername)
	EditText mUsernameInput;
	@BindView(R.id.signUpUsernameLayout)
	TextInputLayout mUsernameLayout;
	@BindView(R.id.signUpEmail)
	EditText mEmailInput;
	@BindView(R.id.signUpEmailLayout)
	TextInputLayout mEmailLayout;
	@BindView(R.id.signUpPassword)
	EditText mPasswordInput;
	@BindView(R.id.signUpPasswordLayout)
	TextInputLayout mPasswordLayout;
	@BindView(R.id.radioClientType)
	RadioGroup mClientType;
	@BindView(R.id.patientOption)
	RadioButton mLastClientType;
	@BindView(R.id.btnSignup)
	Button mBtnSignup;
	private ProgressDialog progressDialog;

	private SignUpViewModel signupViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
	}

	private void init() {
		//Butterknife
		ButterKnife.bind(this);

		// associate view model
		signupViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

		// password key listener
		mPasswordInput.setOnKeyListener((v, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				mPasswordInput.clearFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				assert imm != null;
				imm.hideSoftInputFromWindow(mPasswordInput.getWindowToken(), 0);
				return true;
			} else return false;
		});

		disposables.addAll(
				validateField(mEmailLayout, mEmailInput, () -> {
					setFieldError(!signupViewModel.isEmailValid(mEmailInput.getText()), mEmailLayout, R.string.signup_email_error);
					return null;
				}),
				validateField(mPasswordLayout, mPasswordInput, () -> {
					setFieldError(!signupViewModel.isPasswordValid(mPasswordInput.getText()), mPasswordLayout, R.string.signup_password_error);
					return null;
				}),
				validateField(mUsernameLayout, mUsernameInput, () -> {
					setFieldError(!signupViewModel.isUsernameValid(mUsernameInput.getText()), mUsernameLayout, R.string.required_field_error);
					return null;
				}),
				RxRadioGroup.checkedChanges(mClientType)
						.skipInitialValue().subscribe(this::validateClientType),
				signupViewModel.validForm()
						.subscribe(validForm -> mBtnSignup.setEnabled(validForm))
		);
	}

	private void validateClientType(Integer integer) {
		if (integer == -1) {
			//none checked
			mLastClientType.setError(getResources().getString(R.string.signup_client_type_error));
			signupViewModel.clientTypeRelay.accept(false);
		} else {
			mLastClientType.setError(null);
			signupViewModel.clientTypeRelay.accept(true);
		}
		signupViewModel.changeValidFormRelay();
	}

	public void launchlogin(View view) {
		Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	void setFormErrors(Map<String, ArrayList<String>> formErrors) {
		//loop through keys
		for (String key : formErrors.keySet()) {
			switch (key) {
				case "email": {
					mEmailLayout.setErrorEnabled(true);
					mEmailLayout.setError(Objects.requireNonNull(formErrors.get(key)).get(0));
					break;
				}
				case "username": {
					mUsernameLayout.setErrorEnabled(true);
					mUsernameLayout.setError(Objects.requireNonNull(formErrors.get(key)).get(0));
					break;
				}
			}
		}
	}

	public void clearForm() {
		clearField(mEmailLayout, mEmailInput);
		clearField(mUsernameLayout, mUsernameInput);
		clearField(mPasswordLayout, mPasswordInput);
		mClientType.clearCheck();
		mLastClientType.setError(null);
	}

	public void clearField(TextInputLayout inputLayout, EditText editText) {
		editText.setText(null);
		editText.clearFocus();
		inputLayout.setError(null);
		inputLayout.setErrorEnabled(false);
	}

	void setFieldError(boolean isInvalid, TextInputLayout inputLayout, int stringId) {
		inputLayout.setErrorEnabled(isInvalid);
		inputLayout.setError(isInvalid ? getResources().getString(stringId) : null);
	}

	Disposable validateField(TextInputLayout inputLayout, EditText editText, Callable<Void> validation) {
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
						.doOnEach(__ -> validation.call())
				).subscribe();
	}

	public void createAccount(View view) {
		progressDialog = new ProgressDialog(SignUpActivity.this);
		progressDialog.setMessage("Creating account...");
		progressDialog.show();
		String clientEmail = mEmailInput.getText().toString();
		String clientUsername = mUsernameInput.getText().toString();
		String clientPassword = mPasswordInput.getText().toString();
		RadioButton clientRadioButton = findViewById(mClientType.getCheckedRadioButtonId());
		String clientRole = clientRadioButton.getText().toString().toLowerCase();

		Authentication authentication = new Authentication(this);

		disposables.add(authentication.createAccount(clientEmail, clientUsername, clientPassword, clientRole)
				.subscribe(clientResponse -> {
					progressDialog.dismiss();
					if (clientResponse.isSuccessful()) {
						clearForm();
						Snackbar.make(mBtnSignup, R.string.signup_success, 5000)
								.show();
					} else {
						assert clientResponse.errorBody() != null;
						String errorContent = clientResponse.errorBody().source().buffer().clone().readUtf8();
						JsonParser parser = new JsonParser();
						JsonObject jsonObject = parser.parse(errorContent).getAsJsonObject();

						//Create hash map
						Gson gson = new Gson();
						Type errorMapType = new TypeToken<Map<String, ArrayList<String>>>() {
						}.getType();
						String errorString = gson.toJson(jsonObject.get("errors"));
						Map<String, ArrayList<String>> formErrors = gson.fromJson(errorString, errorMapType);
						setFormErrors(formErrors);
					}
				}, throwable -> {
					progressDialog.dismiss();
					Timber.e(throwable);
					if (throwable instanceof UnknownHostException) {
						Snackbar.make(mBtnSignup, R.string.internet_connection_error, Snackbar.LENGTH_LONG)
								.show();
					} else if (throwable instanceof HttpException) {
						ResponseBody responseBody = Objects.requireNonNull(((HttpException) throwable).response()).errorBody();
						assert responseBody != null;
						String errString = responseBody.string();
						JsonParser parser = new JsonParser();
						JsonObject jsonObject = parser.parse(errString).getAsJsonObject();

						//Create hash map
						Gson gson = new Gson();
						Type errorMapType = new TypeToken<Map<String, ArrayList<String>>>() {
						}.getType();
						String errorString = gson.toJson(jsonObject.get("errors"));
						Map<String, ArrayList<String>> formErrors = gson.fromJson(errorString, errorMapType);
						setFormErrors(formErrors);
					} else {
						Snackbar.make(mBtnSignup, R.string.general_error, Snackbar.LENGTH_LONG)
								.show();
					}
				}));
	}
}
