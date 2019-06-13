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
import com.example.android.myhealth.models.Client;
import com.example.android.myhealth.network.RetrofitClient;
import com.jakewharton.rxbinding3.widget.RxRadioGroup;
import com.jakewharton.rxbinding3.widget.RxTextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;
import timber.log.Timber;

public class SignUpActivity extends AppCompatActivity {
	private final CompositeDisposable disposables = new CompositeDisposable();

	ProgressDialog ProcessDialog;

	@BindView(R.id.signUpUsername)
	EditText mUsernameInput;
	@BindView(R.id.signUpEmail)
	EditText mEmailInput;
	@BindView(R.id.signUpPassword)
	EditText mPasswordInput;
	@BindView(R.id.radioClientType)
	RadioGroup mClientType;
	@BindView(R.id.btnSignup)
	Button mBtnSignup;

	private SignUpViewModel signupViewModel;
	private Disposable combinedObservable;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
	}

	public void init() {
		//Timbre init
		if (BuildConfig.DEBUG) {
			Timber.plant(new Timber.DebugTree());
		}
		//Butterknife
		ButterKnife.bind(this);

		// associate viewmodel
		signupViewModel = ViewModelProviders.of(this).get(SignUpViewModel.class);

		//Observables
		Observable<Boolean> emailObservable = RxTextView.textChanges(mEmailInput).skip(1).map(signupViewModel::isEmailValid);
		Observable<Boolean> passwordObservable = RxTextView.textChanges(mPasswordInput).skip(1).map(signupViewModel::isPasswordValid);
		Observable<Boolean> usernameObservable = RxTextView.textChanges(mUsernameInput).skip(1).map(signupViewModel::isUsernameValid);
		Observable<Integer> clientTypeObservable = RxRadioGroup.checkedChanges(mClientType);

		combinedObservable = Observable.combineLatest(emailObservable, passwordObservable,
				usernameObservable, clientTypeObservable,
				(emailisValid, passwordisValid, usernameIsValid, clientType) ->
						validateEmail(emailisValid) && validatePassword(passwordisValid)
								&& validateUsername(usernameIsValid) && validateClientType(clientType))
				.subscribe(aBoolean -> {
					mBtnSignup.setEnabled(aBoolean);
				});
		disposables.add(combinedObservable);
	}

	Boolean validateEmail(Boolean isValid) {
		if (!isValid) {
			mEmailInput.setError("Invalid email");
		} else {
			mEmailInput.setError(null);
		}
		return isValid;
	}

	Boolean validatePassword(Boolean isValid) {
		if (!isValid) {
			mPasswordInput.setError("At least 8 characters and one digit");
		} else {
			mPasswordInput.setError(null);
		}
		return isValid;
	}

	Boolean validateUsername(Boolean isValid) {
		if (!isValid) {
			mUsernameInput.setError("Required");
		} else {
			mUsernameInput.setError(null);
		}
		return isValid;
	}

	Boolean validateClientType(Integer integer) {
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

		RetrofitClient.getInstance(this)
				.getClientService().registerClient(clientUsername, clientEmail, clientPassword, clientRole)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread())
				.subscribe(new Observer<Response<Client>>() {
					@Override
					public void onSubscribe(Disposable d) {
//						disposables.add(d);
					}

					@Override
					public void onNext(Response<Client> clientResponse) {
						Toast.makeText(SignUpActivity.this, clientResponse.code(), Toast.LENGTH_SHORT).show();
						if (clientResponse.isSuccessful()) {
							assert clientResponse.body() != null;
							Timber.d(clientResponse.body().toString());
							Timber.d(clientResponse.message());
							Toast.makeText(SignUpActivity.this, clientResponse.body().username(), Toast.LENGTH_LONG).show();
						} else {
							assert clientResponse.errorBody() != null;
							Timber.d(clientResponse.errorBody().toString());
							Toast.makeText(SignUpActivity.this, clientResponse.code(), Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onError(Throwable e) {
						Timber.e(e);
						Toast.makeText(SignUpActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
					}

					@Override
					public void onComplete() {
						ProcessDialog.dismiss();
					}
				});
	}
}
