package com.steekam.network;

import android.content.Context;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.steekam.network.models.AdapterFactory;
import com.steekam.network.services.ClientService;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class RetrofitClient {
	//	private final static String BASEURL = "http://health-keep-server.herokuapp.com/api/v1/";
	private final static String BASEURL = "http://93fe9d0c.ngrok.io/api/v1/";
	private static RetrofitClient INSTANCE;
	private final ClientService clientService;

	private RetrofitClient(Context context) {
		Retrofit client = provideRetrofitClient(context);
		clientService = client.create(ClientService.class);
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
				.addCallAdapterFactory(RxJava2CallAdapterFactory.create())
				.addConverterFactory(GsonConverterFactory.create(provideGson()))
				.build();
	}

	private OkHttpClient provideHTTPClient(Context context) {
		return new OkHttpClient.Builder()
				.connectTimeout(10, TimeUnit.SECONDS)
				.readTimeout(15, TimeUnit.SECONDS)
				.writeTimeout(15, TimeUnit.SECONDS)
				.cache(new Cache(context.getCacheDir(), (100 * 1024 * 1024))) // 100 MB cache
				.addInterceptor(provideApiKeyInterceptor())
				.addNetworkInterceptor(new StethoInterceptor())
				.build();
	}

	private Interceptor provideApiKeyInterceptor() {
		return chain -> {
			Request newRequest = chain.request().newBuilder()
					.addHeader("Authorization", "Bearer " + Constants.HealthKeepApiKey)
					.build();
			return chain.proceed(newRequest);
		};
	}

	public ClientService getClientService() {
		return this.clientService;
	}
}
