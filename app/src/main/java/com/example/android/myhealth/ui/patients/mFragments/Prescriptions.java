package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;

public class Prescriptions extends BaseFragment {
	private static final String[] appoint = {"Panadol", "Paracetamol", "Eno", "Antacid"};

	public Prescriptions() {
		super(R.layout.patient_prescriptions);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rvprescriptions);

		//LAYOUT MANAGER
		rv.setLayoutManager(new LinearLayoutManager((getActivity())));

		//ADAPTER
		rv.setAdapter(new MyAdapter(getActivity(), appoint));
	}
}
