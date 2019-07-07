package com.steekam.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.steekam.database.HealthKeepDatabase;
import com.steekam.database.daos.ClientDao;
import com.steekam.entities.Client;
import com.steekam.network.RetrofitClient;
import com.steekam.network.models.ClientDTO;
import com.steekam.network.services.ClientService;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

public class ClientRepository {
	private final Context activityContext;
	private ClientDao clientDao;
	private SharedPreferences sharedPreferences;
	private ClientService clientService;

	public ClientRepository(Application application, Context activityContext) {
		this.activityContext = activityContext;
		HealthKeepDatabase database = HealthKeepDatabase.getInstance(application);
		clientDao = database.clientDao();
		clientService = RetrofitClient.getInstance(activityContext).getClientService();
		sharedPreferences = activityContext.getSharedPreferences(activityContext.getString(R.string.mydefault_preferences_filename), Context.MODE_PRIVATE);
	}

	private Single<Long> insert(Client client) {
		return clientDao.insert(client)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Completable update(Client client) {
		return clientDao.update(client)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Completable delete(Client client) {
		return clientDao.delete(client)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	private Completable clear() {
		return clientDao.clear()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	private Observable<Client> getUser(long clientId) {
		return clientDao.getClient(clientId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<Client> saveLoggedInClient(Client client) {
		return clear()
				.andThen(
						insert(client)
								.doOnSuccess(clientId -> {
									SharedPreferences.Editor editor = sharedPreferences.edit();
									editor.putLong(activityContext.getString(R.string.logged_in_client_key), clientId);
									editor.apply();
								})).flatMapObservable(this::getUser);
	}

	public Observable<String> isClientLoggedIn() {
		long clientId = sharedPreferences.getLong(activityContext.getString(R.string.logged_in_client_key), -1);
		return (clientId != -1) ? getUser(clientId)
				.map(Client::role) : Observable.just("");
	}

	public Completable logout() {
		SharedPreferences.Editor editor = sharedPreferences.edit();
		editor.clear();
		editor.apply();
		return clear();
	}

	private long getClientId() {
		return sharedPreferences.getLong(activityContext.getString(R.string.logged_in_client_key), -1);
	}

	public Observable<Client> getLoggedInUser() {
		long clientId = sharedPreferences.getLong(activityContext.getString(R.string.logged_in_client_key), -1);
		return getUser(clientId);
	}

	public Observable<Response<ClientDTO>> updatePersonalDetails(String username, String email) {
		return clientService.updateClient(getClientId(), username, email)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<Response<ClientDTO>> updateClientPassword(@NonNull String oldPassword, @NonNull String newPassword) {
		return clientService.updateClientPassword(getClientId(), oldPassword, newPassword)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
