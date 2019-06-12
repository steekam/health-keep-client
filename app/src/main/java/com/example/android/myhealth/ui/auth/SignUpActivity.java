package com.example.android.myhealth.ui.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.myhealth.R;

public class SignUpActivity extends AppCompatActivity {
	ProgressDialog ProcessDialog;
	private EditText mUsernameInput;
	private EditText mEmailInput;
	private EditText mPasswordInput;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		init();
	}

	public void init() {
		// TODO: Replace with data binding form models
		mUsernameInput = findViewById(R.id.signUpUsername);
		mEmailInput = findViewById(R.id.signUpEmail);
		mPasswordInput = findViewById(R.id.signUpPassword);
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
	}
}
