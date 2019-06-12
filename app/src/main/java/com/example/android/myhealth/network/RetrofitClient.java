package com.example.android.myhealth.network;

import android.content.Context;

import com.example.android.myhealth.BuildConfig;
import com.example.android.myhealth.models.AdapterFactory;
import com.example.android.myhealth.network.services.ClientService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
	private final static String BASEURL = "http://health-keep-server.herokuapp.com/api/v1/";
	private static RetrofitClient INSTANCE;
	private Retrofit client;
	private ClientService clientService;

	RetrofitClient(Context context) {
		this.client = provideRetrofitClient(context);
	}

	public static synchronized RetrofitClient getInstance(Context context) {

		if (INSTANCE == null) {
			INSTANCE = new RetrofitClient(context);
		}

		return INSTANCE;
	}

	private Gson provideGson() {
		return new GsonBuilder()
				.registerTypeAdapterFactory(AdapterFactory.create())
				.create();
	}

	private Retrofit provideRetrofitClient(Context context) {
		return new Retrofit.Builder()
				.baseUrl(BASEURL)
				.client(provideHTTPClient(context))
				.addConverterFactory(GsonConverterFactory.create(provideGson()))
				.build();
	}

	private OkHttpClient provideHTTPClient(Context context) {
		return new OkHttpClient.Builder()
				.cache(new Cache(context.getCacheDir(), (100 * 1024 * 1024))) // 100 MB cache
				.addInterceptor(provideApiKeyInterceptor())
				.build();
	}

	private Interceptor provideApiKeyInterceptor() {
		return new Interceptor() {
			@Override
			public Response intercept(Chain chain) throws IOException {
				Request newRequest = chain.request().newBuilder()
						.addHeader("Authorization", "Bearer " + BuildConfig.HealthKeepApiKey)
						.build();
				return chain.proceed(newRequest);
			}
		};
	}

	public ClientService getClientService() { return  this.clientService; }
}
