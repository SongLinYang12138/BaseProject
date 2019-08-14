package com.bondex.ysl.databaselibrary.login;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * date: 2019/7/19
 * Author: ysl
 * description:
 */
@Database(entities = LoginBean.class, version = 1, exportSchema = false)
public abstract class LoginDataBase extends RoomDatabase {

    private static final String DB_NAME = "login";

    private static LoginDataBase instance;


    public static LoginDataBase getInstance(Context context) {

        if (instance == null)
            instance = create(context);

        return instance;
    }


    private static LoginDataBase create(Context context) {

        return Room.databaseBuilder(context, LoginDataBase.class, DB_NAME).build();
    }

    public abstract LoginDao getDao();


}
