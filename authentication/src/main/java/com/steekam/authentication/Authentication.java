package com.steekam.authentication;

import android.content.Context;
import android.content.Intent;

import com.steekam.network.RetrofitClient;
import com.steekam.network.models.ClientDTO;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Authentication {

	private final Context context;

	public Authentication(Context context) {
		this.context = context;
	}

	public Observable<Response<ClientDTO>> createAccount(String email, String username, String password, String role) {
		return RetrofitClient.getInstance(this.context)
				.getClientService().registerClient(username, email, password, role)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<Response<ClientDTO>> clientLogin(String username, String password) {
		return RetrofitClient.getInstance(this.context)
				.getClientService().loginClient(username, password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public void redirectCient(Class redirectTo) {
		Intent intent = new Intent(context, redirectTo);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
		context.startActivity(intent);
	}
}
