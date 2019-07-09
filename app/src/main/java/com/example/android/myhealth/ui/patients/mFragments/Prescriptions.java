package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.example.android.myhealth.ui.patients.mRecycler.PrescriptionAdapter;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Objects;

public class Prescriptions extends BaseFragment {

	private ArrayList<prescription_data> prescribe = new ArrayList<>();
	public PrescriptionAdapter adapter = new PrescriptionAdapter(prescribe);

	public Prescriptions() {
		super(R.layout.patient_prescriptions);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		((PatientNav) Objects.requireNonNull(getActivity())).fab.setOnClickListener(this::fabOnClickListener);

		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rvprescriptions);

		//LAYOUT MANAGER
		rv.setLayoutManager(new LinearLayoutManager((getActivity())));

		//ADAPTER
		rv.setHasFixedSize(true);
		rv.setAdapter(adapter);

		prescribe.add(
				new prescription_data(
						"09:00", "Paracetamol", "2 tablets"
				)
		);
		prescribe.add(
				new prescription_data(
						"14:00", "ARVs", "1 tablet"
				)
		);
		prescribe.add(
				new prescription_data(
						"15:00", "Potassium supplements", "1 tablet"
				)
		);
		prescribe.add(
				new prescription_data(
						"15:00", "Insulin", "1 injection"
				)
		);
		prescribe.add(
				new prescription_data(
						"20:00", "Vitamins", "3 tablets"
				)
		);
		prescribe.add(
				new prescription_data(
						"22:00", "Heparin", "1 injection"
				)
		);

	}

	private void fabOnClickListener(View view) {
		Snackbar.make(view, "Add new medications", Snackbar.LENGTH_LONG).show();
	}
}
