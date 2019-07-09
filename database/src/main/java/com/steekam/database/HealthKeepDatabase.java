package com.steekam.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.steekam.database.converters.DateConverter;
import com.steekam.database.daos.AppointmentDao;
import com.steekam.database.daos.ClientDao;
import com.steekam.database.daos.ReminderDao;
import com.steekam.entities.Appointment;
import com.steekam.entities.Client;
import com.steekam.entities.Reminder;

@Database(
		entities = {
				Client.class,
				Appointment.class,
				Reminder.class
		},
		version = 3
)
@TypeConverters(DateConverter.class)
public abstract class HealthKeepDatabase extends RoomDatabase {
	private static HealthKeepDatabase INSTANCE;

	public static synchronized HealthKeepDatabase getInstance(Context context) {
		if (INSTANCE == null) {
			INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
					HealthKeepDatabase.class,
					"healthkeep_database")
					.fallbackToDestructiveMigration()
					.build();
		}
		return INSTANCE;
	}

	public abstract ClientDao clientDao();

	public abstract AppointmentDao appointmentDao();

	public abstract ReminderDao reminderDao();
}
