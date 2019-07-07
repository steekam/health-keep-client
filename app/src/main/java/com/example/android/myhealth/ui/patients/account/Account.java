package com.example.android.myhealth.ui.patients.account;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.example.android.myhealth.ui.patients.PatientSharedViewModel;
import com.example.android.myhealth.ui.patients.PatientSharedViewModelFactory;
import com.example.android.myhealth.validations.AccountEditValidator;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.steekam.custom_stylings.CustomToast;
import com.steekam.entities.Client;
import com.steekam.network.models.ClientDTO;

import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.HttpException;
import timber.log.Timber;

public class Account extends BaseFragment implements View.OnClickListener {
	//uis
	@BindView(R.id.accountUsernameLayout)
	TextInputLayout accountUsernameLayout;
	@BindView(R.id.accountUsernameInput)
	EditText accountUsernameInput;
	@BindView(R.id.accountEmailLayout)
	TextInputLayout accountEmailLayout;
	@BindView(R.id.accountEmailInput)
	EditText accountEmailInput;
	@BindView(R.id.personal_details_card_option)
	TextView personalDetailsOption;
	@BindView(R.id.btn_personal_details_save)
	Button personalDetailsSave;
	@BindView(R.id.accountOldPasswordLayout)
	TextInputLayout oldPasswordLayout;
	@BindView(R.id.accountOldPasswordInput)
	EditText oldPasswordInput;
	@BindView(R.id.accountNewPasswordLayout)
	TextInputLayout newPasswordLayout;
	@BindView(R.id.accountNewPasswordInput)
	EditText newPasswordInput;
	@BindView(R.id.btn_account_password_reset)
	Button accountPasswordReset;

	//view models
	private PatientSharedViewModel patientSharedViewModel;
	private AccountViewModel accountViewModel;

	public Account() {
		super(R.layout.patient_account);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);
		onClickListeners();
		disposables.addAll(
				patientSharedViewModel.getLoggedInUser()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(client -> {
							// set behavior relay for current client
							accountViewModel.currentClient.accept(client);
							accountDetailsSetup(client);
						}, Timber::e),
				accountViewModel.getEditingPersonalDetails()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(this::personalDetailsStateToggle, Timber::e),
				accountViewModel.editingCancelled()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(cancelled -> {
							if (cancelled) {
								accountDetailsSetup(accountViewModel.currentClient.getValue());
							}
						}, Timber::e)
		);
		personalDetailsEditValidators();
		passwordResetValidators();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application application = Objects.requireNonNull(getActivity()).getApplication();
		patientSharedViewModel = ViewModelProviders
				.of(getActivity(),
						new PatientSharedViewModelFactory(application, getActivity()))
				.get(PatientSharedViewModel.class);
		accountViewModel = ViewModelProviders.of(this,
				new AccountViewModelFactory(application, getActivity()))
				.get(AccountViewModel.class);
	}

	private void accountDetailsSetup(@NonNull Client client) {
		accountUsernameInput.setText(client.username());
		accountEmailInput.setText(client.email());
	}

	private void personalDetailsStateToggle(boolean editing) {
		personalDetailsOption.setText(
				editing ? getString(R.string.cancel_option) : getString(R.string.edit_option)
		);
		personalDetailsOption.setTextColor(
				editing ? getResources().getColor(R.color.design_default_color_error) : getResources().getColor(R.color.colorPrimaryDark)
		);
		personalDetailsSave.setVisibility(
				editing ? View.VISIBLE : View.GONE
		);
		accountUsernameInput.setEnabled(editing);
		accountEmailInput.setEnabled(editing);

		if (!editing) {
			accountEmailInput.clearFocus();
			accountUsernameInput.clearFocus();
		}
	}

	private void onClickListeners() {
		personalDetailsOption.setOnClickListener(this);
		personalDetailsSave.setOnClickListener(this);
		accountPasswordReset.setOnClickListener(this);
	}

	private void toggleEditState() {
		boolean currentState = accountViewModel.editingPersonalDetailsRelay.getValue();
		accountViewModel.editingPersonalDetailsRelay.accept(!currentState);
		accountViewModel.resetValidationRelays();
	}

	private void clearFocusOnDone(@NonNull EditText editText) {
		editText.setOnEditorActionListener((v, actionId, event) -> {
			if (actionId == EditorInfo.IME_ACTION_DONE) {
				//Clear focus here from edit text
				InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
				assert imm != null;
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				editText.clearFocus();
			}
			return false;
		});
	}

	private void personalDetailsEditValidators() {
		//on done lose focus
		clearFocusOnDone(accountUsernameInput);
		clearFocusOnDone(accountEmailInput);

		//validators
		AccountEditValidator validator = new AccountEditValidator(getContext());
		disposables.addAll(
				validator.validateField(accountEmailLayout, accountEmailInput,
						() -> {
							String emailValue = accountEmailInput.getText().toString();
							boolean emailIsInvalid = !emailValue.matches("(?:[a-z0-9!#$%'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#$%'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)])");
							boolean emailIsEmpty = emailValue.isEmpty();
							boolean isInvalid = emailIsInvalid || emailIsEmpty;
							accountViewModel.accountEmailValid.accept(!isInvalid);
							accountViewModel.changeAccountValidForm();

							int errorStringId = 0;
							if (emailIsEmpty) {
								errorStringId = R.string.required_field_error;
							} else if (emailIsInvalid) {
								errorStringId = R.string.signup_email_error;
							}
							validator.setFieldError(isInvalid, accountEmailLayout, errorStringId);
							return null;
						}, accountViewModel.editingPersonalDetailsRelay),
				validator.validateField(accountUsernameLayout, accountUsernameInput,
						() -> {
							boolean isInvalid = accountUsernameInput.getText().toString().isEmpty();
							accountViewModel.accountUsernameValid.accept(!isInvalid);
							accountViewModel.changeAccountValidForm();
							validator.setFieldError(isInvalid, accountUsernameLayout, R.string.required_field_error);
							return null;
						}, accountViewModel.editingPersonalDetailsRelay),
				accountViewModel.validAccountEditForm()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(valid -> personalDetailsSave.setEnabled(valid))
		);
	}

	private void savePersonalDetails() {
		Client currentClient = accountViewModel.currentClient.getValue();
		Client updatedClient = Client.create(
				currentClient.clientId(),
				accountUsernameInput.getText().toString(),
				accountEmailInput.getText().toString(),
				currentClient.role(),
				currentClient.verified(),
				currentClient.loggedIn()
		);
		boolean changed = !updatedClient.equals(currentClient);
		if (changed) {
			ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
					Objects.requireNonNull(getActivity()).getString(R.string.account_update_progress_title),
					getActivity().getString(R.string.please_wait_msg), true);
			disposables.add(
					accountViewModel.updatePersonalDetails(updatedClient.username(), updatedClient.email())
							.flatMap(response -> {
								if (!response.isSuccessful()) throw new HttpException(response);
								ClientDTO clientDTO = response.body();
								Timber.d(String.valueOf(response));
								assert clientDTO != null;
								return accountViewModel.updateCachedUser(
										accountViewModel.clientFromDTO(clientDTO)
								);
							})
							.doOnComplete(() -> {
								progressDialog.dismiss();
								toggleEditState();
								Snackbar.make(personalDetailsSave, R.string.account_updated, Snackbar.LENGTH_LONG)
										.show();
							})
							.subscribe(o -> {
							}, throwable -> {
								progressDialog.dismiss();
								Timber.e(throwable);
								CustomToast toast = new CustomToast(Objects.requireNonNull(getActivity()).getApplicationContext(), getActivity());
								if (throwable instanceof UnknownHostException) {
									Snackbar.make(personalDetailsSave, R.string.internet_connection_error, Snackbar.LENGTH_LONG)
											.show();
								} else if (throwable instanceof HttpException) {
									ResponseBody responseBody = Objects.requireNonNull(((HttpException) throwable).response()).errorBody();
									assert responseBody != null;
									String errString = responseBody.string();
									JsonParser parser = new JsonParser();
									JsonObject jsonObject = parser.parse(errString).getAsJsonObject();
									Snackbar.make(personalDetailsSave, jsonObject.get("error").toString(), Snackbar.LENGTH_LONG)
											.show();
								} else if (throwable instanceof SocketTimeoutException) {
									Snackbar.make(personalDetailsSave, R.string.connection_timeout_error, Snackbar.LENGTH_LONG)
											.show();
								} else {
									toast.show("error", getString(R.string.general_error), Gravity.TOP | Gravity.CENTER_HORIZONTAL);
								}
							})
			);
		} else toggleEditState();
	}

	private void passwordResetValidators() {
		//clear focus on done
		clearFocusOnDone(newPasswordInput);

		//validators
		AccountEditValidator validator = new AccountEditValidator(getContext());

		disposables.addAll(
				validator.validateField(oldPasswordLayout, oldPasswordInput, () -> {
					String oldPassword = oldPasswordInput.getText().toString();
					boolean isEmpty = oldPassword.isEmpty();
					accountViewModel.oldPasswordValid.accept(!isEmpty);
					accountViewModel.changePasswordChangeValidForm();
					validator.setFieldError(isEmpty, oldPasswordLayout, R.string.required_field_error);
					return null;
				}).subscribe(),
				validator.validateField(newPasswordLayout, newPasswordInput, () -> {
					String newPassword = newPasswordInput.getText().toString();
					boolean isEmpty = newPassword.isEmpty();
					boolean isInvalidPassword = !newPassword.matches("^(?=.*\\d).{8,}$");
					boolean isInvalid = isEmpty || isInvalidPassword;
					int errorStringId = 0;
					if (isEmpty) {
						errorStringId = R.string.required_field_error;
					} else if (isInvalidPassword) {
						errorStringId = R.string.signup_password_error;
					}
					accountViewModel.newPasswordValid.accept(!isInvalid);
					accountViewModel.changePasswordChangeValidForm();
					validator.setFieldError(isInvalid, newPasswordLayout, errorStringId);
					return null;
				}).subscribe(),
				accountViewModel.validPasswordChangeForm()
						.subscribe(valid -> accountPasswordReset.setVisibility(
								valid ? View.VISIBLE : View.GONE
						))
		);
	}

	private void resetPassword() {
		ProgressDialog progressDialog = ProgressDialog.show(getActivity(),
				Objects.requireNonNull(getActivity()).getString(R.string.account_update_password_progress_title),
				getActivity().getString(R.string.please_wait_msg), true);
		String oldPassword = oldPasswordInput.getText().toString();
		String newPassword = newPasswordInput.getText().toString();
		disposables.add(
				accountViewModel.updateClientPassword(oldPassword, newPassword)
						.doOnComplete(() -> {
							progressDialog.dismiss();
							((PatientNav) getActivity()).switchFragment(R.id.account);
						})
						.subscribe(response -> {
							if (!response.isSuccessful()) throw new HttpException(response);
							Snackbar.make(accountPasswordReset, R.string.password_updated_success, Snackbar.LENGTH_LONG)
									.show();
						}, throwable -> {
							progressDialog.dismiss();
							Timber.e(throwable);
							CustomToast toast = new CustomToast(Objects.requireNonNull(getActivity()).getApplicationContext(), getActivity());
							if (throwable instanceof UnknownHostException) {
								Snackbar.make(accountPasswordReset, R.string.internet_connection_error, Snackbar.LENGTH_LONG)
										.show();
							} else if (throwable instanceof HttpException) {
								ResponseBody responseBody = Objects.requireNonNull(((HttpException) throwable).response()).errorBody();
								assert responseBody != null;
								String errString = responseBody.string();
								JsonParser parser = new JsonParser();
								JsonObject jsonObject = parser.parse(errString).getAsJsonObject();
								//Create hash map
								Gson gson = new Gson();
								Type errorMapType = new TypeToken<Map<String, ArrayList<String>>>() {
								}.getType();
								String errorString = gson.toJson(jsonObject.get("errors"));
								Map<String, ArrayList<String>> formErrors = gson.fromJson(errorString, errorMapType);
								if (formErrors.containsKey("old_password")) {
									AccountEditValidator validator = new AccountEditValidator(getContext());
									validator.setFieldError(true, oldPasswordLayout, Objects.requireNonNull(formErrors.get("old_password")).get(0));
								}
							} else if (throwable instanceof SocketTimeoutException) {
								Snackbar.make(accountPasswordReset, R.string.connection_timeout_error, Snackbar.LENGTH_LONG)
										.show();
							} else {
								toast.show("error", getString(R.string.general_error), Gravity.TOP | Gravity.CENTER_HORIZONTAL);
							}
						})
		);
	}

	@Override
	public void onClick(View v) {
		if (personalDetailsOption.equals(v)) {
			// Restore values if cancelled
			String currentOption = personalDetailsOption.getText().toString();
			accountViewModel.cancelled_edit.accept(currentOption.equals(getString(R.string.cancel_option)));
			toggleEditState();
		} else if (personalDetailsSave.equals(v)) {
			savePersonalDetails();
		} else if (accountPasswordReset.equals(v)) {
			resetPassword();
		}
	}
}
