package com.bondex.ysl.databaselibrary.airport;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * date: 2019/8/29
 * Author: ysl
 * description:
 */
@Database(entities = AirPort.class, version = 1, exportSchema = false)
public abstract class AirPortDatabase extends RoomDatabase {

    private static final String DB_NAME = "airport";
    private static AirPortDatabase instance;

    public static AirPortDatabase getInstance(Context context) {

        if (instance == null) {
            instance = creat(context);
        }

        return instance;

    }

    private static AirPortDatabase creat(Context context) {

        return Room.databaseBuilder(context, AirPortDatabase.class, DB_NAME).build();
    }

    public abstract AirPortDao getDao();

}
