package com.example.android.myhealth.ui.auth;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseActivity;
import com.example.android.myhealth.ui.doctors.DoctorNav;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding3.view.RxView;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.steekam.authentication.Authentication;
import com.steekam.authentication.UnverifiedException;
import com.steekam.custom_stylings.CustomToast;
import com.steekam.entities.Client;
import com.steekam.network.models.ClientDTO;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {
	//ui views
	@BindView(R.id.signInUsernameLayout)
	TextInputLayout mUsernameLayout;
	@BindView(R.id.signInUsername)
	EditText mUsername;
	@BindView(R.id.signInPasswordLayout)
	TextInputLayout mPasswordLayout;
	@BindView(R.id.signInPassword)
	EditText mPassword;
	@BindView(R.id.btnSignin)
	Button mBtnSignIn;
	private ProgressDialog progressDialog;
	private LoginViewModel loginViewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		init();
	}

	void init() {
		//Butterknife
		ButterKnife.bind(this);

		//Associate view model
		loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory(getApplication(), this)).get(LoginViewModel.class);
		// password key listener
		mPassword.setOnKeyListener((v, keyCode, event) -> {
			if (keyCode == KeyEvent.KEYCODE_ENTER) {
				mPassword.clearFocus();
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				assert imm != null;
				imm.hideSoftInputFromWindow(mPassword.getWindowToken(), 0);
				return true;
			} else return false;
		});

		disposables.addAll(
				validateField(mUsernameLayout,
						mUsername,
						() -> {
							setFieldError(!loginViewModel.isUsernameValid(mUsername.getText()), mUsernameLayout);
							return null;
						}
				),
				validateField(mPasswordLayout,
						mPassword,
						() -> {
							setFieldError(!loginViewModel.isPasswordValid(mPassword.getText()), mPasswordLayout);
							return null;
						}
				),
				loginViewModel.validForm()
						.subscribe(validForm -> mBtnSignIn.setEnabled(validForm))
		);
	}

	public void launchSignUp(View view) {
		Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	void setFieldError(boolean isInvalid, TextInputLayout inputLayout) {
		inputLayout.setErrorEnabled(isInvalid);
		inputLayout.setError(isInvalid ? getResources().getString(R.string.required_field_error) : null);
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

	public void signIn(View view) {
		progressDialog = new ProgressDialog(LoginActivity.this);
		progressDialog.setMessage("Logging in ...");
		progressDialog.show();

		//credentials
		String username = mUsername.getText().toString();
		String password = mPassword.getText().toString();

		Authentication authentication = new Authentication(this);
		disposables.add(
				authentication.clientLogin(username, password)
						.map(response -> {
							progressDialog.dismiss();
							if (!response.isSuccessful()) throw new Exception(String.valueOf(response.code()));
							ClientDTO clientDTO = response.body();
							assert clientDTO != null;
							String role = clientDTO.rolesList().get(0).roleName();
							boolean verified = clientDTO.emailVerifiedAt() != null;
							if (!verified) throw new UnverifiedException();
							return Client.create(
									clientDTO.clientId(),
									clientDTO.username(),
									clientDTO.email(),
									role,
									true,
									true
							);
						})
						.flatMap(client -> loginViewModel.saveLoggedInClient(client))
						.map(Client::role)
						.subscribe(role -> authentication.redirectCient(role.equals("patient") ? PatientNav.class : DoctorNav.class),
								throwable -> {
									progressDialog.dismiss();
									CustomToast toast = new CustomToast(getApplicationContext(), this);
									if (Objects.equals(throwable.getMessage(), "422")) {
										toast.show("error", getString(R.string.login_invalid_credentials), Gravity.TOP | Gravity.CENTER_HORIZONTAL);
									} else if (throwable instanceof UnknownHostException) {
										Snackbar.make(mBtnSignIn, R.string.internet_connection_error, Snackbar.LENGTH_LONG)
												.show();
									} else if (throwable instanceof SocketTimeoutException) {
										Snackbar.make(mBtnSignIn, R.string.connection_timeout_error, Snackbar.LENGTH_LONG)
												.show();
									} else if (throwable instanceof UnverifiedException) {
										toast.show("error", throwable.getMessage(), Gravity.TOP | Gravity.CENTER_HORIZONTAL);
									} else {
										// unknown error
										Timber.e(throwable);
										toast.show("error", getString(R.string.error_occurred), Gravity.TOP | Gravity.CENTER_HORIZONTAL);
									}
								})
		);
	}

	public void launchForgotPassword(View view) {
		Intent intent = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
		startActivity(intent);
	}
}
