package com.example.android.myhealth.ui.doctors.mFragments;

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
import com.example.android.myhealth.ui.doctors.mRecycler.MyAdapter;

public class Patients extends Fragment {

    private static String[] appoint={"Jane Doe","John Doe","Meridith Grey","Melvin Grey","Gerald Butler","Katniss Everdine","Orange is the new black"};
    private RecyclerView rv;

    public static Patients newInstance(){
        Patients patient = new Patients();
        return patient;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_patients,null);

        //REFERENCE
        rv = rootView.findViewById(R.id.rvpatient);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager((getActivity())));

        //ADAPTER
        rv.setAdapter(new MyAdapter(getActivity(),appoint));

        return rootView;
    }

    public String toString(){
        return "patient";
    }
}
