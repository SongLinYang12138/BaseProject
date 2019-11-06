package com.bondex.ysl.databaselibrary.hawb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Date;

/**
 * date: 2019/8/19
 * Author: ysl
 * description:
 */
public class HAWBDao {


    private static HAWBDao instance;
    private static String DB_NAME = HAWBDBHelper.DB_NAME;
    private Context context;
    private HAWBDBHelper db_helper;


    private HAWBDao(Context context) {
        this.context = context;
        db_helper = new HAWBDBHelper(context);

    }

    public static HAWBDao getInstance(Context context) {


        return instance = new HAWBDao(context);
    }


    public void insert(HAWBBean bean) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("boardId", bean.getBoardId());
        values.put("id", bean.getId());
        values.put("mBillCode", bean.getmBillCode());
        values.put("hawb", bean.getHawb());
        values.put("qty", bean.getQty());
        values.put("weight", bean.getWeight());
        values.put("volume", bean.getVolume());
        values.put("flight", bean.getFlight());
        values.put("detination", bean.getDetination());
        values.put("flight_date", bean.getDate());
        values.put("loadQty", bean.getLoadQty());

        db.insert(DB_NAME, null, values);
    }


    public void update(HAWBBean bean) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("boardId", bean.getBoardId());
        values.put("id", bean.getId());
        values.put("mBillCode", bean.getmBillCode());
        values.put("hawb", bean.getHawb());
        values.put("qty", bean.getQty());
        values.put("weight", bean.getWeight());
        values.put("volume", bean.getVolume());
        values.put("flight", bean.getFlight());
        values.put("detination", bean.getDetination());
        values.put("flight_date", bean.getDate());
        values.put("loadQty", bean.getLoadQty());

        db.update(DB_NAME, values, "id = ?", new String[]{bean.getId()});
    }

    public ArrayList<HAWBBean> selectByBoardId(String boadrdId) {

        SQLiteDatabase db = db_helper.getReadableDatabase();


        String sql = "select * from " + DB_NAME + " where boardId = '" + boadrdId + "' order by flight_date desc";
        Cursor cursor = db.rawQuery(sql, null);

        ArrayList<HAWBBean> list = new ArrayList<>();
        if (cursor != null && cursor.getCount() > 0) {


            while (cursor.moveToNext()) {

                HAWBBean bean = new HAWBBean();

                bean.setBoardId(cursor.getString(cursor.getColumnIndex("boardId")));
                bean.setId(cursor.getString(cursor.getColumnIndex("id")));
                bean.setmBillCode(cursor.getString(cursor.getColumnIndex("mBillCode")));
                bean.setHawb(cursor.getString(cursor.getColumnIndex("hawb")));
                bean.setQty(cursor.getInt(cursor.getColumnIndex("qty")));
                bean.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
                bean.setVolume(cursor.getString(cursor.getColumnIndex("volume")));
                bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
                bean.setDetination(cursor.getString(cursor.getColumnIndex("detination")));
                bean.setDate(cursor.getString(cursor.getColumnIndex("flight_date")));
                bean.setLoadQty(cursor.getInt(cursor.getColumnIndex("loadQty")));
                list.add(bean);
            }


        }

        if (cursor != null) cursor.close();

        return list;
    }

    public HAWBBean selectByHawb(String hawb) {

        SQLiteDatabase db = db_helper.getReadableDatabase();


        String sql = "select * from " + DB_NAME + " where hawb = '" + hawb + "' order by flight_date desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor == null || cursor.getCount() == 0) {

            return null;
        }
        HAWBBean bean = new HAWBBean();

        while (cursor.moveToNext()) {


            bean.setBoardId(cursor.getString(cursor.getColumnIndex("boardId")));
            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setmBillCode(cursor.getString(cursor.getColumnIndex("mBillCode")));
            bean.setHawb(cursor.getString(cursor.getColumnIndex("hawb")));
            bean.setQty(cursor.getInt(cursor.getColumnIndex("qty")));
            bean.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            bean.setVolume(cursor.getString(cursor.getColumnIndex("volume")));
            bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
            bean.setDetination(cursor.getString(cursor.getColumnIndex("detination")));
            bean.setDate(cursor.getString(cursor.getColumnIndex("flight_date")));
            bean.setLoadQty(cursor.getInt(cursor.getColumnIndex("loadQty")));
        }

        if (cursor != null) cursor.close();

        return bean;
    }

    public ArrayList<HAWBBean> selectByMhawb(String mhawb) {

        ArrayList<HAWBBean> list = new ArrayList<>();


        SQLiteDatabase db = db_helper.getReadableDatabase();


        String sql = "select * from " + DB_NAME + " where mBillCode = '" + mhawb + "' order by flight_date desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor == null || cursor.getCount() == 0) {

            return null;
        }

        while (cursor.moveToNext()) {

            HAWBBean bean = new HAWBBean();
            bean.setBoardId(cursor.getString(cursor.getColumnIndex("boardId")));
            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setmBillCode(cursor.getString(cursor.getColumnIndex("mBillCode")));
            bean.setHawb(cursor.getString(cursor.getColumnIndex("hawb")));
            bean.setQty(cursor.getInt(cursor.getColumnIndex("qty")));
            bean.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            bean.setVolume(cursor.getString(cursor.getColumnIndex("volume")));
            bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
            bean.setDetination(cursor.getString(cursor.getColumnIndex("detination")));
            bean.setDate(cursor.getString(cursor.getColumnIndex("flight_date")));
            bean.setLoadQty(cursor.getInt(cursor.getColumnIndex("loadQty")));
            list.add(bean);
        }

        if (cursor != null) cursor.close();

        return list;

    }

    public ArrayList<HAWBBean> getAll() {

        ArrayList<HAWBBean> list = new ArrayList<>();


        SQLiteDatabase db = db_helper.getReadableDatabase();

        String sql = "select * from " + DB_NAME + " order by flight_date desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor == null || cursor.getCount() == 0) {

            return null;
        }

        while (cursor.moveToNext()) {

            HAWBBean bean = new HAWBBean();
            bean.setBoardId(cursor.getString(cursor.getColumnIndex("boardId")));
            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setmBillCode(cursor.getString(cursor.getColumnIndex("mBillCode")));
            bean.setHawb(cursor.getString(cursor.getColumnIndex("hawb")));
            bean.setQty(cursor.getInt(cursor.getColumnIndex("qty")));
            bean.setWeight(cursor.getString(cursor.getColumnIndex("weight")));
            bean.setVolume(cursor.getString(cursor.getColumnIndex("volume")));
            bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
            bean.setDetination(cursor.getString(cursor.getColumnIndex("detination")));
            bean.setDate(cursor.getString(cursor.getColumnIndex("flight_date")));
            bean.setLoadQty(cursor.getInt(cursor.getColumnIndex("loadQty")));
            list.add(bean);
        }

        if (cursor != null) cursor.close();

        return list;

    }

    public void deleteByBoardId(String boardId) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        String sql = "delete from " + DB_NAME + " where boardId = '" + boardId + "'";

        db.execSQL(sql);
    }

    public void deleteByHawb(String hawb) {


        SQLiteDatabase db = db_helper.getWritableDatabase();
        String sql = "delete from " + DB_NAME + " where hawb = '" + hawb + "'";
        db.execSQL(sql);
    }


}
