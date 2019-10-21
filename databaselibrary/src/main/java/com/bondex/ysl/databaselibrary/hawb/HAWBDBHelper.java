package com.bondex.ysl.databaselibrary.hawb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class HAWBDBHelper extends SQLiteOpenHelper {

    protected static String DB_NAME = "hawbs";
    private static final int DB_VERSION = 1;

    public HAWBDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public HAWBDBHelper(Context context) {
        this(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + DB_NAME + "(boardId varchar(20),id varchar(20),mBillCode varchar(20),hawb varchar(20)," +
                "qty int,weight varchar(6),volume varchar(6),flight varchar(10),detination varchar(10),flight_date varchar(20)," +
                "loadQty int)";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}