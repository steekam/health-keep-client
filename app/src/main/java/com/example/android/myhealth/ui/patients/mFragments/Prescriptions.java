package com.example.android.myhealth.ui.patients.mFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.doctors.mFragments.Patients;
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;
import com.example.android.myhealth.ui.patients.mRecycler.AppointmentsAdapter;
import com.example.android.myhealth.ui.patients.mRecycler.PrescriptionAdapter;

import java.util.ArrayList;

public class Prescriptions extends Fragment {

    private ArrayList<prescription_data> prescribe = new ArrayList<>();
    public PrescriptionAdapter adapter = new PrescriptionAdapter(prescribe);
    TextView mhour, mmedicine, mdosage;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_prescriptions, container, false);
        ((AppCompatActivity) getActivity()).setTitle("Prescriptions");
        return rootView;
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
