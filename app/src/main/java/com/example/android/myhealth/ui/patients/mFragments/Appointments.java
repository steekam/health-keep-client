package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.mRecycler.AppointmentsAdapter;

import java.util.ArrayList;

public class Appointments extends BaseFragment {

	private ArrayList<appointments_data> appoint = new ArrayList<>();
	private AppointmentsAdapter adapter = new AppointmentsAdapter(appoint);

	public Appointments() {
		super(R.layout.patient_appointments);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rvappointments);

		//LAYOUT MANAGER
		rv.setLayoutManager(new LinearLayoutManager((getActivity())));

		//ADAPTER
		rv.setHasFixedSize(true);
		rv.setAdapter(adapter);

		appoint.add(
				new appointments_data(
						"Dr. John Doe", "25th May", "18:00"
				)
		);
		appoint.add(
				new appointments_data(
						"Dr. Jane Doe", "30th May", "18:00"
				)
		);
		appoint.add(
				new appointments_data(
						"Dr. Janet Doe", "5th June", "10:00"
				)
		);
		appoint.add(
				new appointments_data(
						"Dr. Grey", "15th June", "12:00"
				)
		);
		appoint.add(
				new appointments_data(
						"Dr. Black", "5th July", "09:00"
				)
		);
		appoint.add(
				new appointments_data(
						"Dr. Blue", "15th July", "07:00"
				)
		);

	}
}
