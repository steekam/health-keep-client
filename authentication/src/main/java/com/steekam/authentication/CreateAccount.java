package com.steekam.authentication;

import android.content.Context;

import com.steekam.network.RetrofitClient;
import com.steekam.network.models.Client;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;


public class CreateAccount {

	private final Context context;

	public CreateAccount(Context context) {
		this.context = context;
	}

	public Observable<Response<Client>> sendRequest(String email, String username, String password, String role) {
		return RetrofitClient.getInstance(this.context)
				.getClientService().registerClient(username, email, password, role)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}


}
