package com.example.android.myhealth.ui.doctors.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.sendbird.main.ChatLoginActivity;

public class Chat extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.doctor_chat, container, false);
        final Button chatBtn = rootView.findViewById(R.id.btnChat);

        chatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent redirect = new Intent(getActivity(), ChatLoginActivity.class);
                getActivity().startActivity(redirect);
            }
        });
        return rootView;
    }
}