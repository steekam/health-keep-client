package com.example.android.myhealth.ui.patients.mFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.android.myhealth.R;
import com.example.android.myhealth.base.BaseFragment;
import com.example.android.myhealth.ui.sendbird.main.ChatLoginActivity;

public class Chat extends BaseFragment {
    public Chat() {
        super(R.layout.patient_chat);
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.patient_chat, container, false);
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

