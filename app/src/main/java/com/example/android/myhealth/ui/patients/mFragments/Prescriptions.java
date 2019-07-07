package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.mRecycler.PrescriptionAdapter;

import java.util.ArrayList;

public class Prescriptions extends BaseFragment {

	TextView mhour, mmedicine, mdosage;
	private ArrayList<prescription_data> prescribe = new ArrayList<>();
	public PrescriptionAdapter adapter = new PrescriptionAdapter(prescribe);

	public Prescriptions() {
		super(R.layout.patient_prescriptions);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rvprescriptions);
		mhour = view.findViewById(R.id.hour);
		mmedicine = view.findViewById(R.id.medicine);
		mdosage = view.findViewById(R.id.dosage);

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
}
