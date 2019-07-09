package com.example.android.myhealth.ui.patients.appointment;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentsHistory extends BaseFragment {

	//views
	@BindView(R.id.appointment_history_display)
	RecyclerView appointmentHistoryDisplay;

	public AppointmentsHistory() {
		super(R.layout.patient_appointments_history);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		//display appointments
		appointmentHistoryDisplay.setLayoutManager(new LinearLayoutManager((getActivity())));
		appointmentHistoryDisplay.setHasFixedSize(true);
		appointmentHistoryDisplay.setAdapter(new AppointmentsAdapter());
	}
}
