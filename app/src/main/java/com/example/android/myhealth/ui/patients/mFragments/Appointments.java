package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.doctors.mFragments.Patients;
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;

public class Appointments extends Fragment {
    private static final String[] appoint = {"Dr. A.N. Other", "Dr. John Doe", "Dr. Jane Doe", "Dr. K. L. Mao"};

    public static Patients newInstance(){
        return new Patients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_appointments,null);

        //REFERENCE
        RecyclerView rv = rootView.findViewById(R.id.rvappointments);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager((getActivity())));

        //ADAPTER
        rv.setAdapter(new MyAdapter(getActivity(),appoint));

        return rootView;
    }

    @NonNull
    public String toString(){
        return "patient";
    }
}
