package com.example.android.myhealth.ui.patients.appointment;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientSharedViewModel;
import com.example.android.myhealth.ui.patients.PatientSharedViewModelFactory;

import java.util.Objects;

public class AddAppointment extends BaseFragment {

	private AppointmentsViewModel appointmentsViewModel;

	public AddAppointment() {
		super(R.layout.add_appointment_fragment);
	}

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		Application application = Objects.requireNonNull(getActivity()).getApplication();
		appointmentsViewModel = ViewModelProviders.of(this, new AppointmentViewModelFactory(application, getActivity())).get(AppointmentsViewModel.class);
		PatientSharedViewModel patientSharedViewModel = ViewModelProviders.of(getActivity(), new PatientSharedViewModelFactory(application, getActivity())).get(PatientSharedViewModel.class);

		patientSharedViewModel.fabVisibilityRelay.accept(View.INVISIBLE);
		patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.add_appointments));
		patientSharedViewModel.checkedItemRelay.accept(R.id.appointments);
		patientSharedViewModel.homeAsUpRelay.accept(true);
	}

}
