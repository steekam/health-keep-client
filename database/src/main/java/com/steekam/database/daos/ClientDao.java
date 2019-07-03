package com.steekam.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.steekam.entities.Client;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;

@Dao
public interface ClientDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
	Single<Long> insert(Client client);

	@Update
	Completable update(Client client);

	@Delete
	Completable delete(Client client);

	@Query("DELETE FROM client")
	Completable clear();

	@Query("SELECT * FROM client WHERE clientId = :clientId")
	Observable<Client> getClient(long clientId);

}
