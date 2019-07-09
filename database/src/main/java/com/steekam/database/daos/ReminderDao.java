package com.steekam.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.steekam.entities.Reminder;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@SuppressWarnings("AndroidUnresolvedRoomSqlReference")
@Dao
public interface ReminderDao {

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(Reminder reminder);

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Completable insertAll(List<Reminder> reminderList);

	@Update
	Completable update(Reminder reminder);

	@Delete
	Completable delete(Reminder reminder);

	@Query("DELETE FROM reminders")
	Completable clearAll();

	@Query("SELECT * FROM reminders WHERE reminderbleId = :reminderbleId AND reminderbleType = :reminderbleType")
	Observable<Reminder> getReminder(long reminderbleId, String reminderbleType);
}
