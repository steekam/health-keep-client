package com.steekam.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.steekam.database.daos.ClientDao;
import com.steekam.entities.Client;

@Database(
		entities = {
				Client.class
		},
		version = 1
)
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
}
