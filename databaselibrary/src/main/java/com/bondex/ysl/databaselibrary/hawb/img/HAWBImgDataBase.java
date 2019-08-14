package com.bondex.ysl.databaselibrary.hawb.img;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

/**
 * date: 2019/8/5
 * Author: ysl
 * description:
 */
@Database(entities = HAWBImgBean.class, version = 1, exportSchema = false)
public abstract class HAWBImgDataBase extends RoomDatabase {

    private static final String DB_NAME = "hawb_imgs";
    private static HAWBImgDataBase instance;

    public static HAWBImgDataBase getInstance(Context context) {

        if (instance == null) instance = create(context);

        return instance;
    }

    private static HAWBImgDataBase create(Context context) {

        return Room.databaseBuilder(context, HAWBImgDataBase.class, DB_NAME).build();
    }


    public abstract HAWBImgDao getDao();

}
