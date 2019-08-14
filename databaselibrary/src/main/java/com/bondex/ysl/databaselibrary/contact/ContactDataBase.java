package com.bondex.ysl.databaselibrary.contact;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * date: 2019/1/29
 * Author: ysl
 * description:
 */
@Database(entities = ContactBean.class, version = 1, exportSchema = false)
public abstract class ContactDataBase extends RoomDatabase {

    private final static String DATA_BASE_NAME = "contact.db";
    private static ContactDataBase instance;
    private static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
//
//            database.execSQL("ALTER TABLE users "
//                    + " ADD COLUMN last_update INTEGER");
            // Since we didn't alter the table, there's nothing else to do here.
        }
    };


    public static ContactDataBase getInstance(Context context) {

        if (instance == null) {

            instance = create(context);
        }

        return instance;
    }

    private static ContactDataBase create(Context context) {

        return Room.databaseBuilder(context, ContactDataBase.class, DATA_BASE_NAME).build();
    }

    public abstract ContactDao getContactDao();




}
