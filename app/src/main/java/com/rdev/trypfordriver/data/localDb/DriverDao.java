package com.rdev.trypfordriver.data.localDb;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface DriverDao {

    @Insert
    void insert(CachedDriver word);

    @Query("DELETE FROM login_driver")
    void deleteAll();

    @Query("SELECT * from login_driver ")
    LiveData<CachedDriver> getCurrentDriver();

    @Update
    void updateDriver(CachedDriver cachedDriver);
}