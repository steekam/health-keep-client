package com.example.android.myhealth.ui.patients.account;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.steekam.entities.Client;
import com.steekam.network.models.ClientDTO;
import com.steekam.repository.ClientRepository;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

class AccountViewModel extends ViewModel {
	BehaviorRelay<Boolean> editingPersonalDetailsRelay = BehaviorRelay.createDefault(false);
	BehaviorRelay<Client> currentClient = BehaviorRelay.create();
	BehaviorRelay<Boolean> cancelled_edit = BehaviorRelay.create();
	BehaviorRelay<Boolean> accountUsernameValid = BehaviorRelay.createDefault(true);
	BehaviorRelay<Boolean> accountEmailValid = BehaviorRelay.createDefault(true);
	BehaviorRelay<Boolean> oldPasswordValid = BehaviorRelay.createDefault(true);
	BehaviorRelay<Boolean> newPasswordValid = BehaviorRelay.createDefault(true);
	private BehaviorRelay<Boolean> accountValidForm = BehaviorRelay.create();
	private BehaviorRelay<Boolean> passwordChangeFormValid = BehaviorRelay.create();
	private ClientRepository clientRepository;

	AccountViewModel(Application application, Context activityContext) {
		this.clientRepository = new ClientRepository(application, activityContext);
	}

	Observable<Boolean> getEditingPersonalDetails() {
		return editingPersonalDetailsRelay.skip(1);
	}

	Observable<Boolean> editingCancelled() {
		return cancelled_edit;
	}

	void changeAccountValidForm() {
		accountValidForm.accept(accountEmailValid.getValue() && accountUsernameValid.getValue());
	}

	Observable<Boolean> validAccountEditForm() {
		return accountValidForm;
	}

	void resetValidationRelays() {
		accountUsernameValid.accept(true);
		accountEmailValid.accept(true);
		changeAccountValidForm();
	}

	Observable<Boolean> validPasswordChangeForm() {
		return passwordChangeFormValid;
	}

	void changePasswordChangeValidForm() {
		passwordChangeFormValid.accept(oldPasswordValid.getValue() && newPasswordValid.getValue());
	}

	void resetPasswordChangeFormRelays() {
		oldPasswordValid.accept(true);
		newPasswordValid.accept(true);
		changePasswordChangeValidForm();
	}

	Observable<Response<ClientDTO>> updatePersonalDetails(String username, String email) {
		return clientRepository.updatePersonalDetails(username, email);
	}

	@NonNull
	Client clientFromDTO(@NonNull ClientDTO clientDTO) {
		return Client.create(
				clientDTO.clientId(),
				clientDTO.username(),
				clientDTO.email(),
				clientDTO.rolesList().get(0).roleName(),
				true,
				true
		);
	}

	Observable<Object> updateCachedUser(Client client) {
		return clientRepository.update(client).toObservable()
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	Observable<Response<ClientDTO>> updateClientPassword(@NonNull String oldPassword, @NonNull String newPassword) {
		return clientRepository.updateClientPassword(oldPassword, newPassword)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
