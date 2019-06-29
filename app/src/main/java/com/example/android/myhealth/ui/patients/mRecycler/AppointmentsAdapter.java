package com.example.android.myhealth.ui.patients.mRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.patients.mFragments.appointments_data;

import java.util.List;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.RecyclerViewHolder> {

    private Context context;
    private List<appointments_data> appoint;

    public AppointmentsAdapter(List<appointments_data> appoint) {
        this.context = context;
        this.appoint = appoint;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointments_model,parent,false);
        return new RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        appointments_data data = appoint.get(position);

        holder.mdoctor.setText(data.getmdoctor());
        holder.mdate.setText(data.getmdate());
        holder.mtime.setText(data.getmtime());
//        holder.option.setOnClickListener(v -> {
//            PopupMenu popup = new PopupMenu(context, holder.option);
//            popup.inflate(R.menu.option_menu);
//            popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    switch (item.getGroupId()){
//                        case R.id.edit:
//                            Toast.makeText(context, "Add edit function", Toast.LENGTH_LONG).show();
//                            return true;
//
//                        case R.id.delete:
//                            Toast.makeText(context, "Add delete pop up function", Toast.LENGTH_LONG).show();
//                            return true;
//
//                        default:
//                            break;
//                    }
//                    return false;
//                }
//            });
//            popup.show();
//        });
    }

    @Override
    public int getItemCount() {
        return appoint.size();
    }


    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView mdoctor, mdate, mtime, option;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            mdoctor = itemView.findViewById(R.id.dr);
            mdate = itemView.findViewById(R.id.date);
            mtime = itemView.findViewById(R.id.time);
            option = itemView.findViewById(R.id.options);
        }
    }
}
