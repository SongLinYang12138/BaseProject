package com.bondex.ysl.databaselibrary.base.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.bondex.ysl.databaselibrary.base.bean.PlateType;
import com.bondex.ysl.databaselibrary.base.dao.PlateTypeDao;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Database(entities = PlateType.class,version = 1,exportSchema = false)
public abstract class PlateTypeDataBase extends RoomDatabase {

    private static final String DB_NAME = "plate_type";
    private static PlateTypeDataBase instance;

    public static PlateTypeDataBase getInstance(Context context) {

        if(instance == null) instance = create(context);

        return instance;
    }

    private final static PlateTypeDataBase create(Context context){

        return Room.databaseBuilder(context, PlateTypeDataBase.class,DB_NAME).build();
    }


    public abstract PlateTypeDao getDao();
}
