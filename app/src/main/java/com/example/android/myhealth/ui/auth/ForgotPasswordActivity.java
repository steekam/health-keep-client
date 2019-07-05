package com.example.android.myhealth.ui.auth;

import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.android.myhealth.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ForgotPasswordActivity extends AppCompatActivity {
	//uis
	@BindView(R.id.forgot_password_webview)
	WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_forgot_password);

		//Butterknife
		ButterKnife.bind(this);

		// web view setup
		webView.setWebViewClient(new CustomWebViewClient(this));
		webView.getSettings().setLoadsImagesAutomatically(true);
		webView.loadUrl("http://health-keep-server.herokuapp.com/client/password/reset");
	}

	@Override
	protected void onDestroy() {
		webView.destroy();
		super.onDestroy();
	}
}
