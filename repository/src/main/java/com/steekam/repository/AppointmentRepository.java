package com.steekam.repository;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.steekam.database.HealthKeepDatabase;
import com.steekam.database.daos.AppointmentDao;
import com.steekam.database.daos.ReminderDao;
import com.steekam.entities.Appointment;
import com.steekam.entities.Reminder;
import com.steekam.network.RetrofitClient;
import com.steekam.network.models.AppointmentDTO;
import com.steekam.network.models.ReminderDTO;
import com.steekam.network.services.AppointmentService;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

public class AppointmentRepository {
	private final Context activityContext;
	private AppointmentDao appointmentDao;
	private ReminderDao reminderDao;
	private AppointmentService appointmentService;
	private SharedPreferences sharedPreferences;
	private long clientId;

	public AppointmentRepository(Application application, @NonNull Context activityContext) {
		this.activityContext = activityContext;
		HealthKeepDatabase database = HealthKeepDatabase.getInstance(application);
		appointmentDao = database.appointmentDao();
		reminderDao = database.reminderDao();
		appointmentService = RetrofitClient.getInstance(activityContext).getAppointmentService();
		sharedPreferences = activityContext.getSharedPreferences(activityContext.getString(R.string.mydefault_preferences_filename), Context.MODE_PRIVATE);
		clientId = sharedPreferences.getLong(activityContext.getString(R.string.logged_in_client_key), -1);
	}

	//get all from cache
	public Observable<List<Appointment>> getClientAppointments() {
		return appointmentDao.getClientAppointments(clientId)
				.subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}

	public Observable<List<AppointmentDTO>> fetchClientAppointments() {
		return appointmentService.getClientAppointments(clientId)
				.flatMap(listResponse -> {
					if (!listResponse.isSuccessful()) throw new HttpException(listResponse);
					assert listResponse.body() != null;
					return Observable.just(listResponse.body());
				})
				.subscribeOn(Schedulers.io())
				.observeOn(Schedulers.io());
	}

	//insert into local db
	public Completable insertAppointments(List<AppointmentDTO> appointmentDTOs) {
		List<Appointment> appointments = new ArrayList<>();
		List<Reminder> reminders = new ArrayList<>();

		for (AppointmentDTO appointmentDTO : appointmentDTOs) {
			appointments.add(
					Appointment.create(
							appointmentDTO.appointmentId(),
							appointmentDTO.title(),
							appointmentDTO.description(),
							appointmentDTO.location(),
							appointmentDTO.appointmentDate(),
							appointmentDTO.appointmentTime(),
							appointmentDTO.clientId(),
							(appointmentDTO.archived() == 1),
							appointmentDTO.createdAt(),
							appointmentDTO.updatedAt()
					)
			);
			ReminderDTO reminderDTO = appointmentDTO.reminder();
			reminders.add(
					Reminder.create(
							reminderDTO.reminderId(),
							reminderDTO.startDate(),
							reminderDTO.reminderTime(),
							reminderDTO.repeat() == 1,
							reminderDTO.frequency(),
							reminderDTO.reminderbleId(),
							reminderDTO.reminderbleType(),
							reminderDTO.active() == 1
					)
			);
		}

		//insert into db
		Completable insertAppointments = appointmentDao.insertAll(appointments);
		Completable insertReminders = reminderDao.insertAll(reminders);

		return appointmentDao.clearAppointments().andThen(reminderDao.clearAll()).andThen(insertAppointments)
				.andThen(insertReminders).subscribeOn(Schedulers.io())
				.observeOn(AndroidSchedulers.mainThread());
	}
}
