package com.bondex.ysl.databaselibrary.plan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import com.bondex.ysl.databaselibrary.hawb.HAWBBean;
import com.bondex.ysl.databaselibrary.hawb.HAWBDao;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;


/**
 * date: 2019/8/19
 * Author: ysl
 * description:
 */
public class PlanBeanDao {

    private static final String DB_NAME = PlanDBHelper.DB_NAME;
    private static PlanBeanDao instance;
    private PlanDBHelper db_helper;
    private Context context;


    public PlanBeanDao(Context context) {

        db_helper = new PlanDBHelper(context);

        this.context = context;
    }


    public static PlanBeanDao getInstance(Context context) {

        instance = new PlanBeanDao(context);

        return instance;
    }


    public long intsert(PlanBean bean) {


        SQLiteDatabase db = db_helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", bean.getId());
        values.put("mBillTotal", bean.getmBillTotal());
        values.put("hawbTotal", bean.getHawbTotal());
        values.put("qtyTotal", bean.getQtyTotal());
        values.put("weightTotal", bean.getWeightTotal());
        values.put("volumeTotal", bean.getVolumeTotal());
        values.put("flight", bean.getFlight());
        values.put("flghtDate", bean.getFlghtDate());
        values.put("battleDate", bean.getBattleDate());
        values.put("destination", bean.getDestination());
        values.put("boardNum", bean.getBoardNum());
        values.put("lockNum", bean.getLockNum());
        values.put("plateType", bean.getPlateType().getId());
        values.put("battleMilles", System.currentTimeMillis());
        values.put("status", bean.getStatus());

        Gson gson = new Gson();

        String protectJson = gson.toJson(bean.getProtectType());
        values.put("protectType", protectJson);

        String subPlateTypel = gson.toJson(bean.getSubPlateTypel());
        values.put("subPlateTypel", subPlateTypel);


        HAWBDao hawbDao = HAWBDao.getInstance(context);

        for (int i = 0; i < bean.getHawbs().size(); ++i) {

            HAWBBean hawbBean = bean.getHawbs().get(i);
            hawbBean.setBoardId(bean.getId());

            HAWBBean checkBean = hawbDao.selectByHawb(hawbBean.getHawb());

            if (checkBean != null && checkBean.getmBillCode().equals(hawbBean.getmBillCode())) {

                hawbDao.update(hawbBean);
            } else {
                hawbDao.insert(hawbBean);
            }
        }


        long row = db.insert(DB_NAME, null, values);

        return row;
    }


    public long update(PlanBean bean) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("id", bean.getId());
        values.put("mBillTotal", bean.getmBillTotal());
        values.put("hawbTotal", bean.getHawbTotal());
        values.put("qtyTotal", bean.getQtyTotal());
        values.put("weightTotal", bean.getWeightTotal());
        values.put("volumeTotal", bean.getVolumeTotal());
        values.put("flight", bean.getFlight());
        values.put("flghtDate", bean.getFlghtDate());
        values.put("battleDate", bean.getBattleDate());
        values.put("destination", bean.getDestination());
        values.put("boardNum", bean.getBoardNum());
        values.put("lockNum", bean.getLockNum());
        values.put("plateType", bean.getPlateType().getId());
        values.put("battleMilles", System.currentTimeMillis());
        values.put("status", bean.getStatus());

        Gson gson = new Gson();

        String protectJson = gson.toJson(bean.getProtectType());
        values.put("protectType", protectJson);

        String subPlateTypel = gson.toJson(bean.getSubPlateTypel());
        values.put("subPlateTypel", subPlateTypel);


        HAWBDao hawbDao = HAWBDao.getInstance(context);

        for (int i = 0; i < bean.getHawbs().size(); ++i) {

            HAWBBean hawbBean = bean.getHawbs().get(i);
            hawbBean.setBoardId(bean.getId());

            HAWBBean checkBean = hawbDao.selectByHawb(hawbBean.getHawb());

            if (checkBean != null && checkBean.getmBillCode().equals(hawbBean.getmBillCode())) {

                hawbDao.update(hawbBean);
            } else {
                hawbDao.insert(hawbBean);
            }
        }

        long row = db.update(DB_NAME, values, "id = ?", new String[]{bean.getId()});

        return row;
    }


    public PlanBean getPlanBean(String id) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        PlanBean bean = new PlanBean();

        String sql = "SELECT * FROM " + DB_NAME + " WHERE id = '" + id + "'";

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor == null || cursor.getCount() == 0) {

            return null;
        }
        while (cursor.moveToNext()) {


            bean.setId(cursor.getString(cursor.getColumnIndex("id")));
            bean.setmBillTotal(cursor.getString(cursor.getColumnIndex("mBillTotal")));
            bean.setHawbTotal(cursor.getString(cursor.getColumnIndex("hawbTotal")));
            bean.setQtyTotal(cursor.getInt(cursor.getColumnIndex("qtyTotal")));
            bean.setWeightTotal(cursor.getString(cursor.getColumnIndex("weightTotal")));
            bean.setVolumeTotal(cursor.getString(cursor.getColumnIndex("volumeTotal")));
            bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
            bean.setFlghtDate(cursor.getString(cursor.getColumnIndex("flghtDate")));
            bean.setBattleDate(cursor.getString(cursor.getColumnIndex("battleDate")));
            bean.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
            bean.setBoardNum(cursor.getString(cursor.getColumnIndex("boardNum")));
            bean.setLockNum(cursor.getString(cursor.getColumnIndex("lockNum")));
            bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));


            WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
            plateType.setId(cursor.getInt(cursor.getColumnIndex("plateType")));
            bean.setPlateType(plateType);
            Gson gson = new Gson();

            ArrayList<WorkBentchChoiceBean> protects = gson.fromJson(cursor.getString(cursor.getColumnIndex("protectType")),
                    new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                    }.getType());
            ArrayList<WorkBentchChoiceBean> subplates = gson.fromJson(cursor.getString(cursor.getColumnIndex("subPlateTypel")),
                    new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                    }.getType());

            bean.setProtectType(protects);
            bean.setSubPlateTypel(subplates);
            bean.setBattleMilles(cursor.getInt(cursor.getColumnIndex("battleMilles")));


            HAWBDao hawbDao = HAWBDao.getInstance(context);
            bean.setHawbs(hawbDao.selectByBoardId(bean.getId()));


        }

        if (cursor != null) cursor.close();
        return bean;

    }

    public ArrayList<PlanBean> getAll() {

        ArrayList<PlanBean> list = new ArrayList<>();
        SQLiteDatabase db = db_helper.getWritableDatabase();


        String sql = "select * from " + DB_NAME + " order by battleMilles desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                PlanBean bean = new PlanBean();

                bean.setId(cursor.getString(cursor.getColumnIndex("id")));
                bean.setmBillTotal(cursor.getString(cursor.getColumnIndex("mBillTotal")));
                bean.setHawbTotal(cursor.getString(cursor.getColumnIndex("hawbTotal")));
                bean.setQtyTotal(cursor.getInt(cursor.getColumnIndex("qtyTotal")));
                bean.setWeightTotal(cursor.getString(cursor.getColumnIndex("weightTotal")));
                bean.setVolumeTotal(cursor.getString(cursor.getColumnIndex("volumeTotal")));
                bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
                bean.setFlghtDate(cursor.getString(cursor.getColumnIndex("flghtDate")));
                bean.setBattleDate(cursor.getString(cursor.getColumnIndex("battleDate")));
                bean.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
                bean.setBoardNum(cursor.getString(cursor.getColumnIndex("boardNum")));
                bean.setLockNum(cursor.getString(cursor.getColumnIndex("lockNum")));
                bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
                plateType.setId(cursor.getInt(cursor.getColumnIndex("plateType")));
                bean.setPlateType(plateType);
                Gson gson = new Gson();

                ArrayList<WorkBentchChoiceBean> protects = gson.fromJson(cursor.getString(cursor.getColumnIndex("protectType")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());
                ArrayList<WorkBentchChoiceBean> subplates = gson.fromJson(cursor.getString(cursor.getColumnIndex("subPlateTypel")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());

                bean.setProtectType(protects);
                bean.setSubPlateTypel(subplates);
                bean.setBattleMilles(cursor.getInt(cursor.getColumnIndex("battleMilles")));

                HAWBDao hawbDao = HAWBDao.getInstance(context);
                bean.setHawbs(hawbDao.selectByBoardId(bean.getId()));

                list.add(bean);
            }

            if (cursor != null) cursor.close();

        }
        return list;
    }


    public ArrayList<PlanBean> getNotSuccess() {

        ArrayList<PlanBean> list = new ArrayList<>();
        SQLiteDatabase db = db_helper.getWritableDatabase();


        String sql = "select * from " + DB_NAME + " where status != 1 and status != 5 order by battleMilles desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                PlanBean bean = new PlanBean();

                bean.setId(cursor.getString(cursor.getColumnIndex("id")));
                bean.setmBillTotal(cursor.getString(cursor.getColumnIndex("mBillTotal")));
                bean.setHawbTotal(cursor.getString(cursor.getColumnIndex("hawbTotal")));
                bean.setQtyTotal(cursor.getInt(cursor.getColumnIndex("qtyTotal")));
                bean.setWeightTotal(cursor.getString(cursor.getColumnIndex("weightTotal")));
                bean.setVolumeTotal(cursor.getString(cursor.getColumnIndex("volumeTotal")));
                bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
                bean.setFlghtDate(cursor.getString(cursor.getColumnIndex("flghtDate")));
                bean.setBattleDate(cursor.getString(cursor.getColumnIndex("battleDate")));
                bean.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
                bean.setBoardNum(cursor.getString(cursor.getColumnIndex("boardNum")));
                bean.setLockNum(cursor.getString(cursor.getColumnIndex("lockNum")));
                bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
                plateType.setId(cursor.getInt(cursor.getColumnIndex("plateType")));
                bean.setPlateType(plateType);
                Gson gson = new Gson();

                ArrayList<WorkBentchChoiceBean> protects = gson.fromJson(cursor.getString(cursor.getColumnIndex("protectType")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());
                ArrayList<WorkBentchChoiceBean> subplates = gson.fromJson(cursor.getString(cursor.getColumnIndex("subPlateTypel")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());

                bean.setProtectType(protects);
                bean.setSubPlateTypel(subplates);
                bean.setBattleMilles(cursor.getInt(cursor.getColumnIndex("battleMilles")));

                HAWBDao hawbDao = HAWBDao.getInstance(context);
                bean.setHawbs(hawbDao.selectByBoardId(bean.getId()));

                list.add(bean);
            }


            cursor.close();
        }
        return list;
    }

    public ArrayList<PlanBean> getSuccess() {

        ArrayList<PlanBean> list = new ArrayList<>();
        SQLiteDatabase db = db_helper.getWritableDatabase();


        String sql = "select * from " + DB_NAME + " where status = 1 order by battleMilles desc";
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                PlanBean bean = new PlanBean();

                bean.setId(cursor.getString(cursor.getColumnIndex("id")));
                bean.setmBillTotal(cursor.getString(cursor.getColumnIndex("mBillTotal")));
                bean.setHawbTotal(cursor.getString(cursor.getColumnIndex("hawbTotal")));
                bean.setQtyTotal(cursor.getInt(cursor.getColumnIndex("qtyTotal")));
                bean.setWeightTotal(cursor.getString(cursor.getColumnIndex("weightTotal")));
                bean.setVolumeTotal(cursor.getString(cursor.getColumnIndex("volumeTotal")));
                bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
                bean.setFlghtDate(cursor.getString(cursor.getColumnIndex("flghtDate")));
                bean.setBattleDate(cursor.getString(cursor.getColumnIndex("battleDate")));
                bean.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
                bean.setBoardNum(cursor.getString(cursor.getColumnIndex("boardNum")));
                bean.setLockNum(cursor.getString(cursor.getColumnIndex("lockNum")));
                bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
                plateType.setId(cursor.getInt(cursor.getColumnIndex("plateType")));
                bean.setPlateType(plateType);
                Gson gson = new Gson();

                ArrayList<WorkBentchChoiceBean> protects = gson.fromJson(cursor.getString(cursor.getColumnIndex("protectType")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());
                ArrayList<WorkBentchChoiceBean> subplates = gson.fromJson(cursor.getString(cursor.getColumnIndex("subPlateTypel")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());

                bean.setProtectType(protects);
                bean.setSubPlateTypel(subplates);
                bean.setBattleMilles(cursor.getInt(cursor.getColumnIndex("battleMilles")));

                HAWBDao hawbDao = HAWBDao.getInstance(context);
                bean.setHawbs(hawbDao.selectByBoardId(bean.getId()));

                list.add(bean);
            }

            cursor.close();

        }
        return list;
    }

    public ArrayList<PlanBean> getPlans(int size, int start) {

        ArrayList<PlanBean> list = new ArrayList<>();
        SQLiteDatabase db = db_helper.getWritableDatabase();


//        String sql = "select * from " + DB_NAME + "limit " + size + " offset " + start + " where status = 5  order by battleMilles desc";

        String sql = "select * from " + DB_NAME + " where status = 5 order by battleMilles desc  limit " + size + " offset " + start;

        Cursor cursor = db.rawQuery(sql, null);

        if (cursor != null && cursor.getCount() > 0) {

            while (cursor.moveToNext()) {

                PlanBean bean = new PlanBean();

                bean.setId(cursor.getString(cursor.getColumnIndex("id")));
                bean.setmBillTotal(cursor.getString(cursor.getColumnIndex("mBillTotal")));
                bean.setHawbTotal(cursor.getString(cursor.getColumnIndex("hawbTotal")));
                bean.setQtyTotal(cursor.getInt(cursor.getColumnIndex("qtyTotal")));
                bean.setWeightTotal(cursor.getString(cursor.getColumnIndex("weightTotal")));
                bean.setVolumeTotal(cursor.getString(cursor.getColumnIndex("volumeTotal")));
                bean.setFlight(cursor.getString(cursor.getColumnIndex("flight")));
                bean.setFlghtDate(cursor.getString(cursor.getColumnIndex("flghtDate")));
                bean.setBattleDate(cursor.getString(cursor.getColumnIndex("battleDate")));
                bean.setDestination(cursor.getString(cursor.getColumnIndex("destination")));
                bean.setBoardNum(cursor.getString(cursor.getColumnIndex("boardNum")));
                bean.setLockNum(cursor.getString(cursor.getColumnIndex("lockNum")));
                bean.setStatus(cursor.getInt(cursor.getColumnIndex("status")));

                WorkBentchChoiceBean plateType = new WorkBentchChoiceBean();
                plateType.setId(cursor.getInt(cursor.getColumnIndex("plateType")));
                bean.setPlateType(plateType);
                Gson gson = new Gson();

                ArrayList<WorkBentchChoiceBean> protects = gson.fromJson(cursor.getString(cursor.getColumnIndex("protectType")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());
                ArrayList<WorkBentchChoiceBean> subplates = gson.fromJson(cursor.getString(cursor.getColumnIndex("subPlateTypel")),
                        new TypeToken<ArrayList<WorkBentchChoiceBean>>() {
                        }.getType());

                bean.setProtectType(protects);
                bean.setSubPlateTypel(subplates);
                bean.setBattleMilles(cursor.getInt(cursor.getColumnIndex("battleMilles")));

                HAWBDao hawbDao = HAWBDao.getInstance(context);
                bean.setHawbs(hawbDao.selectByBoardId(bean.getId()));

                list.add(bean);
            }

            cursor.close();
        }
        return list;
    }


    public void delete(String id) {

        SQLiteDatabase db = db_helper.getWritableDatabase();

        String sql = "DELETE FROM " + PlanDBHelper.DB_NAME + " WHERE id = '" + id + "'";

//        db.delete(DB_NAME, "id=?", new String[]{id});

        db.execSQL(sql);
    }


}
