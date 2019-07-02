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

public class Dashboard extends BaseFragment {
	public Dashboard() {
	}

	public Dashboard(int mContentLayoutId) {
		super(mContentLayoutId);
	}

	@Override
	public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		//REFERENCE
		RecyclerView rv = view.findViewById(R.id.rvdash);

		//LAYOUT MANAGER
		rv.setLayoutManager(new LinearLayoutManager((getActivity())));

		//ADAPTER
		rv.setAdapter(new MyAdapter(getActivity(), new String[]{"dashboard"}));
	}
}
