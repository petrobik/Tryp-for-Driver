package com.rdev.trypfordriver.data.localDb;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CachedDriver.class}, version = 1)
public abstract class DriverRoomDatabase extends RoomDatabase {

    public abstract DriverDao driverDao();

    private static volatile DriverRoomDatabase INSTANCE;

    public static DriverRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DriverRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DriverRoomDatabase.class, "tryp_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
