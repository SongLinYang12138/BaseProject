package com.bondex.ysl.databaselibrary.base.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.bondex.ysl.databaselibrary.base.bean.ProtectType;
import com.bondex.ysl.databaselibrary.base.dao.ProtectTypeDao;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Database(entities = ProtectType.class, version = 1, exportSchema = false)
public abstract class ProtectTypeDataBase extends RoomDatabase {


    private static final String DB_NAME = "protect_type";
    private static ProtectTypeDataBase instance;

    public static ProtectTypeDataBase getInstance(Context context) {

        if (instance == null) instance = cretate(context);

        return instance;
    }

    public final static ProtectTypeDataBase cretate(Context context) {


        return Room.databaseBuilder(context, ProtectTypeDataBase.class, DB_NAME).build();
    }

    public abstract ProtectTypeDao getDao();


}
