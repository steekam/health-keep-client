package com.example.android.myhealth.ui.patients.mFragments;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;
import com.example.android.myhealth.ui.patients.PatientSharedViewModel;
import com.example.android.myhealth.ui.patients.PatientSharedViewModelFactory;

import java.util.Objects;

public class Dashboard extends BaseFragment {
	public Dashboard() {
		super(R.layout.patient_dashboard);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		Application application = Objects.requireNonNull(getActivity()).getApplication();
		PatientSharedViewModel patientSharedViewModel = ViewModelProviders.of(getActivity(), new PatientSharedViewModelFactory(application, getActivity())).get(PatientSharedViewModel.class);
		patientSharedViewModel.fabVisibilityRelay.accept(View.INVISIBLE);
		patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.dashboard));
		patientSharedViewModel.checkedItemRelay.accept(R.id.dashboard);
		patientSharedViewModel.homeAsUpRelay.accept(false);

		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rv_dashboard_appointments);

		//LAYOUT MANAGER
		rv.setLayoutManager(new LinearLayoutManager((getActivity())));

		//ADAPTER
		rv.setAdapter(new MyAdapter(getActivity(), new String[]{"dashboard"}));
	}
}
