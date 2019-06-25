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

public class Appointments extends Fragment {

    private static String[] appoint={"Monday 24th","Tuesday 25th","Wednesday 26th","Thursday 27th","Friday 28th","Saturday 29th","Sunday 30th"};
    private RecyclerView rv;

    public static Appointments newInstance(){
        Appointments appointment = new Appointments();
        return appointment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_appointments,null);

        //REFERENCE
        rv = rootView.findViewById(R.id.rvappointment);

        //LAYOUT MANAGER
        rv.setLayoutManager(new LinearLayoutManager((getActivity())));

        //ADAPTER
        rv.setAdapter(new MyAdapter(getActivity(),appoint));

        return rootView;
    }

    public String toString(){
        return "appointment";
    }
}
