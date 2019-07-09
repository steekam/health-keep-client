package com.example.android.myhealth.ui.patients.appointment;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.example.android.myhealth.ui.patients.PatientSharedViewModel;
import com.example.android.myhealth.ui.patients.PatientSharedViewModelFactory;
import com.google.android.material.snackbar.Snackbar;

import org.jetbrains.annotations.Contract;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;
import timber.log.Timber;

public class Appointments extends BaseFragment {

	//views
	@BindView(R.id.rvappointments)
	RecyclerView appointmentsDisplay;
	@BindView(R.id.empty_appointments_display)
	View emptyAppointmentsDisplay;
	@BindView(R.id.loading_indicator)
	ProgressBar progressBar;

	private AppointmentsAdapter appointmentsAdapter;
	private AppointmentsViewModel appointmentsViewModel;
	private PatientSharedViewModel patientSharedViewModel;

	Appointments() {

	}

	@Nullable
	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.patient_appointments, container, false);
		ButterKnife.bind(this, view);
		((PatientNav) Objects.requireNonNull(getActivity())).fab.setOnClickListener(this::fabOnClickListener);

		Application application = Objects.requireNonNull(getActivity()).getApplication();

		// view models init
		appointmentsViewModel = ViewModelProviders.of(this, new AppointmentViewModelFactory(application, getActivity())).get(AppointmentsViewModel.class);
		patientSharedViewModel = ViewModelProviders.of(getActivity(), new PatientSharedViewModelFactory(application, getActivity())).get(PatientSharedViewModel.class);

		//display appointments
		appointmentsDisplay.setLayoutManager(new LinearLayoutManager((getActivity())));
		appointmentsDisplay.setItemAnimator(new DefaultItemAnimator());
		appointmentsDisplay.setAdapter(new AppointmentsAdapter());
		appointmentsAdapter = (AppointmentsAdapter) appointmentsDisplay.getAdapter();

		appointmentsViewModel.loadingRelay.accept(true);

		//add disposables
		disposables.addAll(subscriptions());
		return view;
	}

	@NonNull
	@Contract(" -> new")
	private Disposable[] subscriptions() {
		return new Disposable[]{
				appointmentsViewModel.getClientAppointments()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(appointments -> {
					appointmentsViewModel.loadingRelay.accept(false);
					appointmentsViewModel.emptyAppointmentsRelay.accept(appointments.size() == 0);
					appointmentsAdapter.setData(appointments);
				}),
				appointmentsViewModel.getClientAppointmentsCached()
						.subscribe(appointments -> appointmentsViewModel.appointmentsRelay.accept(appointments)),
				Observable.interval(0, 1, TimeUnit.MINUTES)
						.flatMap(__ -> appointmentsViewModel.fetchClientAppointments()
								.subscribeOn(Schedulers.io())
								.observeOn(Schedulers.io()))
						.subscribe(appointments -> Timber.d("Network run complete"),
						throwable -> {
							appointmentsViewModel.loadingRelay.accept(false);
							Timber.e(throwable);
							if (throwable instanceof UnknownHostException) {
								Snackbar.make(appointmentsDisplay, R.string.internet_connection_error, Snackbar.LENGTH_LONG)
										.show();
							} else if (throwable instanceof HttpException) {
								Snackbar.make(appointmentsDisplay, getString(R.string.general_error), Snackbar.LENGTH_LONG)
										.show();
							} else if (throwable instanceof SocketTimeoutException) {
								Snackbar.make(appointmentsDisplay, R.string.connection_timeout_error, Snackbar.LENGTH_LONG)
										.show();
							} else {
								Snackbar.make(appointmentsDisplay, getString(R.string.general_error), Snackbar.LENGTH_LONG)
										.show();
							}
						}),
				appointmentsViewModel.emptyAppointments()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(empty -> {
					emptyAppointmentsDisplay.setVisibility(
							empty ? View.VISIBLE : View.GONE
					);
					appointmentsDisplay.setVisibility(
							empty ? View.GONE : View.VISIBLE
					);
				}),

				appointmentsViewModel.loading()
						.subscribeOn(Schedulers.io())
						.observeOn(AndroidSchedulers.mainThread())
						.subscribe(loading -> {
					progressBar.setVisibility(
							loading ? View.VISIBLE : View.GONE
					);
				})
		};
	}

	private void fabOnClickListener(View view) {
		PatientNav patientNav = ((PatientNav) Objects.requireNonNull(getActivity()));
		patientNav.switchFragment(R.id.appointments_add);
	}
}
