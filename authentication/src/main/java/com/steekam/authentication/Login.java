package com.steekam.authentication;

import android.content.Context;

import com.steekam.network.RetrofitClient;
import com.steekam.network.models.ClientDTO;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class Login {

	private final Context context;

	public Login(Context context) {
		this.context = context;
	}

	public Observable<Response<ClientDTO>> clientLogin(String username, String password) {
		return RetrofitClient.getInstance(this.context)
				.getClientService().loginClient(username, password)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
