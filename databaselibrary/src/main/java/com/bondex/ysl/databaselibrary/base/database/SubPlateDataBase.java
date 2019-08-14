package com.bondex.ysl.databaselibrary.base.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType;
import com.bondex.ysl.databaselibrary.base.dao.SubplateTypedDao;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Database(entities = SubPlateType.class, version = 1, exportSchema = false)
public abstract class SubPlateDataBase extends RoomDatabase {

    private static final String DB_NAME = "subplate_type";
    private static SubPlateDataBase instance;

    public static SubPlateDataBase getInstance(Context context) {

        return instance;
    }

    private static final SubPlateDataBase create(Context context) {

        return Room.databaseBuilder(context, SubPlateDataBase.class, DB_NAME).build();
    }

    public abstract SubplateTypedDao getDao();
}
