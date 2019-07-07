package com.example.android.myhealth.ui.patients.mRecycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.example.android.myhealth.ui.patients.mFragments.prescription_data;

import java.util.ArrayList;
import java.util.List;

public class PrescriptionAdapter extends RecyclerView.Adapter<PrescriptionAdapter.RecyclerViewHolder> {

    private Context context;
    private List<prescription_data> prescribe;

	public PrescriptionAdapter(ArrayList<prescription_data> prescribe) {
        this.context = context;
        this.prescribe = prescribe;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_prescription_model,parent,false);
        return new PrescriptionAdapter.RecyclerViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PrescriptionAdapter.RecyclerViewHolder holder, int position) {
        prescription_data data = prescribe.get(position);

        holder.mhour.setText(data.getmhour());
        holder.mmedicine.setText(data.getmmedicine());
        holder.mdosage.setText(data.getmdosage());
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
        return prescribe.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView mhour, mmedicine, mdosage;

        RecyclerViewHolder(View itemView) {
            super(itemView);

            mhour = itemView.findViewById(R.id.hour);
            mmedicine = itemView.findViewById(R.id.medicine);
            mdosage = itemView.findViewById(R.id.dosage);
        }
    }
}
