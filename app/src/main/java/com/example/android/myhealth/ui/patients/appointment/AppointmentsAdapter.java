package com.example.android.myhealth.ui.patients.appointment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.myhealth.R;
import com.steekam.entities.Appointment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AppointmentsAdapter extends RecyclerView.Adapter<AppointmentsAdapter.RecyclerViewHolder> {

	private List<Appointment> appointments = new ArrayList<>();

	AppointmentsAdapter() {
		setHasStableIds(true);
	}

	@NonNull
	@Override
	public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.patient_appointments_model, parent, false);
		return new RecyclerViewHolder(v);
	}

	@Override
	public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
		holder.bind(appointments.get(position));
	}

	@Override
	public long getItemId(int position) {
		return appointments.get(position).appointmentId();
	}

	@Override
	public int getItemCount() {
		return appointments.size();
	}

	void setData(List<Appointment> appointments) {
		if (appointments != null) {
			DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new AppointmentsDiffCallback(this.appointments, appointments));
			this.appointments.clear();
			this.appointments.addAll(appointments);
			diffResult.dispatchUpdatesTo(this);
		} else {
			this.appointments.clear();
			notifyDataSetChanged();
		}
	}

	class RecyclerViewHolder extends RecyclerView.ViewHolder {
		@BindView(R.id.dr)
		TextView mdoctor;
		@BindView(R.id.date)
		TextView mdate;
		@BindView(R.id.time)
		TextView mtime;

		private Appointment appointment;

		RecyclerViewHolder(View itemView) {
			super(itemView);
			ButterKnife.bind(this, itemView);

			//TODO: maybe attach onClick listener here
		}

		void bind(Appointment currentAppointment) {
			appointment = currentAppointment;
			mdoctor.setText(appointment.title());
			mdate.setText(appointment.appointmentDate().toString());
			mtime.setText(appointment.appointmentTime().toString());
		}
	}
}
