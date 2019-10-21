package com.bondex.ysl.databaselibrary.plan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * date: 2019/8/19
 * Author: ysl
 * description:
 */
public class PlanDBHelper extends SQLiteOpenHelper {
    protected static final String DB_NAME = "db_plan";
    private static final int VERSION = 1;

    public PlanDBHelper(Context context) {

        this(context, DB_NAME, null, VERSION);
    }

    public PlanDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + DB_NAME + "(id varchar(20), mBillTotal varchar(6),hawbTotal varchar(6),qtyTotal int," +
                "weightTotal varchar(6),volumeTotal varchar(6),flight varchar(10),flghtDate varchar(20),battleDate varchar(20)," +
                "destination varchar(10),boardNum varchar(12),lockNum varchar(12),plateType INTEGER,protectType text," +
                "subPlateTypel text,battleMilles INTEGER,status INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
