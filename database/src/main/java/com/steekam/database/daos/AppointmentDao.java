package com.steekam.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.steekam.entities.Appointment;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@SuppressWarnings("AndroidUnresolvedRoomSqlReference")
@Dao
public interface AppointmentDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(Appointment appointment);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insertAll(List<Appointment> appointments);

	@Update
	Completable update(Appointment appointment);

	@Delete
	Completable delete(Appointment appointment);

	@Query("DELETE FROM appointments")
	Completable clearAppointments();

	@Query("SELECT * FROM appointments WHERE appointmentId = :appointmentId")
	Observable<Appointment> getAppointment(long appointmentId);

	@Query("SELECT * FROM appointments WHERE clientId = :clientId")
	Observable<List<Appointment>> getClientAppointments(long clientId);

}
