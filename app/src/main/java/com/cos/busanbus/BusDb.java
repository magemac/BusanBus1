package com.cos.busanbus;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Bus.class}, version = 1)
public abstract class BusDb extends RoomDatabase {
    public abstract BusDAO busDao();
}
