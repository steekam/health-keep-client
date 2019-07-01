package com.example.android.myhealth.ui.auth;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

class CustomWebViewClient extends WebViewClient {

	private final Context context;

	CustomWebViewClient(Context context) {
		this.context = context;
	}

	@Override
	public boolean shouldOverrideUrlLoading(WebView view, String url) {
		if (Objects.equals(Uri.parse(url).getHost(), "health-keep-server.herokuapp.com")) {
			// This is my website, so do not override; let my WebView load the page
			return false;
		}
		// Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
		context.startActivity(intent);
		return true;
	}
}
