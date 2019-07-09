package com.example.android.myhealth.ui.sendbird.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.android.myhealth.R;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SelectDistinctFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SelectDistinctFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SelectDistinctFragment extends Fragment {
    private CheckBox mCheckBox;
    private DistinctSelectedListener mListener;

    static SelectDistinctFragment newInstance() {
        return new SelectDistinctFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_select_distinct, container, false);

        ((CreateChatActivity) getActivity()).setState(CreateChatActivity.STATE_SELECT_DISTINCT);

        mListener = (CreateChatActivity) getActivity();

        mCheckBox = rootView.findViewById(R.id.checkbox_select_distinct);
        mCheckBox.setChecked(true);
        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListener.onDistinctSelected(isChecked);
            }
        });

        return rootView;
    }

    interface DistinctSelectedListener {
        void onDistinctSelected(boolean distinct);
    }
}