package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.doctors.mFragments.Patients;
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;

public class Prescriptions extends Fragment {

    public static final String[] appoint = {"Panadol", "Paracetamol", "Eno", "Antacid"};

    public static Prescriptions newInstance(){
        return new Prescriptions();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_prescriptions,null);
//        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();
        ((AppCompatActivity) getActivity()).setTitle("Prescriptions");

        //REFERENCE
        RecyclerView rv = rootView.findViewById(R.id.rvprescriptions);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager((getActivity())));

        //ADAPTER
        rv.setAdapter(new MyAdapter(getActivity(),appoint));

        return rootView;
    }

    @NonNull
    public String toString(){
        return "Prescriptions";
    }
}