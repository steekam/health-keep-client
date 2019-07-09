package com.example.android.myhealth.ui.patients.appointment;

import androidx.recyclerview.widget.DiffUtil;

import com.steekam.entities.Appointment;

import java.util.List;

class AppointmentsDiffCallback extends DiffUtil.Callback {
	private final List<Appointment> oldList;
	private final List<Appointment> newList;

	AppointmentsDiffCallback(List<Appointment> oldList, List<Appointment> newList) {
		this.oldList = oldList;
		this.newList = newList;
	}

	@Override
	public int getOldListSize() {
		return oldList.size();
	}

	@Override
	public int getNewListSize() {
		return newList.size();
	}

	@Override
	public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
		return oldList.get(oldItemPosition).appointmentId() == newList.get(newItemPosition).appointmentId();
	}

	@Override
	public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
		return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
	}
}
