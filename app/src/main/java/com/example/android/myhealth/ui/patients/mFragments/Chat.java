package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.patients.PatientNav;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class Chat extends BaseFragment {
	public Chat() {
		super(R.layout.patient_chat);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		((PatientNav) Objects.requireNonNull(getActivity())).fab.setOnClickListener(this::fabOnClickListener);
	}

	private void fabOnClickListener(View view) {
		Snackbar.make(view, "Add new chat message", Snackbar.LENGTH_LONG).show();
	}
}
