package com.example.android.myhealth.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.BuildConfig;
import com.example.android.myhealth.R;
import com.google.android.material.textfield.TextInputLayout;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.jakewharton.rxbinding3.widget.RxTextView;
import com.steekam.authentication.CreateAccount;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class SignUpActivity extends AppCompatActivity {
	private final CompositeDisposable disposables = new CompositeDisposable();

	private ProgressDialog ProcessDialog;

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
	@BindView(R.id.btnSignup)
	Button mBtnSignup;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
	}

	private void init() {
		//Timbre init
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		//Butterknife
		ButterKnife.bind(this);

		// associate viewmodel
		SignUpViewModel signupViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

		//Observables
		Observable<Boolean> emailObservable = RxTextView.textChanges(mEmailInput).skip(1).map(signupViewModel::isEmailValid);
		Observable<Boolean> passwordObservable = RxTextView.textChanges(mPasswordInput).skip(1).map(signupViewModel::isPasswordValid);
		Observable<Boolean> usernameObservable = RxTextView.textChanges(mUsernameInput).skip(1).map(signupViewModel::isUsernameValid);
		Observable<Integer> clientTypeObservable = RxRadioGroup.checkedChanges(mClientType);

		Disposable combinedObservable = Observable.combineLatest(emailObservable, passwordObservable,
				usernameObservable, clientTypeObservable,
				(emailisValid, passwordisValid, usernameIsValid, clientType) ->
						validateEmail(emailisValid) && validatePassword(passwordisValid)
								&& validateUsername(usernameIsValid) && validateClientType(clientType))
				.subscribe(aBoolean -> mBtnSignup.setEnabled(aBoolean));
		disposables.add(combinedObservable);
	}

	private Boolean validateEmail(Boolean isValid) {
		mEmailLayout.setError(isValid ? null : "Invalid email");
		mEmailLayout.setErrorEnabled(!isValid);
		return isValid;
	}

	private Boolean validatePassword(Boolean isValid) {
		mPasswordLayout.setError(isValid ? null : "At least 8 characters and one digit");
		mPasswordLayout.setErrorEnabled(!isValid);
		return isValid;
	}

	private Boolean validateUsername(Boolean isValid) {
		mUsernameLayout.setError(isValid ? null : "Username is required");
		mUsernameLayout.setErrorEnabled(!isValid);
		return isValid;
	}

	private Boolean validateClientType(Integer integer) {
		int lastChild = mClientType.getChildCount() - 1;
		((RadioButton) mClientType.getChildAt(lastChild)).setError("Choose one");
		if (integer == -1) {
			//none clicked
			((RadioButton) mClientType.getChildAt(lastChild)).setError("Choose one");
			return false;
		} else {
			((RadioButton) mClientType.getChildAt(lastChild)).setError(null);
			return true;
		}
	}

	@Override
	protected void onDestroy() {
		disposables.clear();
		super.onDestroy();
	}

	public void launchlogin(View view) {
		Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
		startActivity(intent);
	}

	public void createAccount(View view) {
		ProcessDialog = new ProgressDialog(SignUpActivity.this);
		ProcessDialog.setMessage("Creating account...");
		ProcessDialog.show();
		String clientEmail = mEmailInput.getText().toString();
		String clientUsername = mUsernameInput.getText().toString();
		String clientPassword = mPasswordInput.getText().toString();
		RadioButton clientRadioButton = findViewById(mClientType.getCheckedRadioButtonId());
		String clientRole = clientRadioButton.getText().toString().toLowerCase();

		CreateAccount createAccount = new CreateAccount(this);

		disposables.add(createAccount
				.sendRequest(clientEmail, clientUsername, clientPassword, clientRole)
				.subscribe(clientResponse -> {
					ProcessDialog.dismiss();
					if (clientResponse.isSuccessful()) {
						//TODO: Handle success
						Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
					}
				}, throwable -> {
					ProcessDialog.dismiss();
					//TODO: Finish errors
					Toast.makeText(this, "An error occurred", Toast.LENGTH_SHORT).show();
					if (throwable instanceof HttpException) {
						ResponseBody responseBody = Objects.requireNonNull(((HttpException) throwable).response()).errorBody();
						assert responseBody != null;
						String errString = responseBody.string();
						Toast.makeText(this, errString, Toast.LENGTH_LONG).show();
					}
				}));
	}
}
