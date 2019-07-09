package com.example.android.myhealth.ui.patients.appointment;

import android.app.Application;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientSharedViewModel;
import com.example.android.myhealth.ui.patients.PatientSharedViewModelFactory;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentsMaster extends BaseFragment {
	//views
	@BindView(R.id.appointment_tabs)
	TabLayout masterTabLayout;
	@BindView(R.id.appointments_viewPager)
	ViewPager appointmentsViewPager;

	private PatientSharedViewModel patientSharedViewModel;

	public AppointmentsMaster() {
		super(R.layout.patient_appointment_master);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		ButterKnife.bind(this, view);

		//setup
		assert getFragmentManager() != null;
		AppointmentMasterAdapter masterAdapter = new AppointmentMasterAdapter(getFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
		masterAdapter.addFragment(new Appointments(), getString(R.string.appointments));
		masterAdapter.addFragment(new AppointmentsHistory(), getString(R.string.history));
		appointmentsViewPager.setAdapter(masterAdapter);
		masterTabLayout.setupWithViewPager(appointmentsViewPager);

		//tab layout listener
		masterTabLayoutListener();
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Application application = Objects.requireNonNull(getActivity()).getApplication();
		patientSharedViewModel = ViewModelProviders
				.of(getActivity(),
						new PatientSharedViewModelFactory(application, getActivity()))
				.get(PatientSharedViewModel.class);

		patientSharedViewModel.fabVisibilityRelay.accept(View.VISIBLE);
		patientSharedViewModel.actionBarTitleRelay.accept(getString(R.string.appointments));
		patientSharedViewModel.checkedItemRelay.accept(R.id.appointments);
		patientSharedViewModel.homeAsUpRelay.accept(false);
	}

	private void masterTabLayoutListener() {
		masterTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
			@Override
			public void onTabSelected(TabLayout.Tab tab) {
				if (tab.getPosition() == 0) {
					patientSharedViewModel.fabVisibilityRelay.accept(View.VISIBLE);
				} else if (tab.getPosition() == 1) {
					patientSharedViewModel.fabVisibilityRelay.accept(View.GONE);
				}
			}

			@Override
			public void onTabUnselected(TabLayout.Tab tab) {

			}

			@Override
			public void onTabReselected(TabLayout.Tab tab) {

			}
		});
	}

}
