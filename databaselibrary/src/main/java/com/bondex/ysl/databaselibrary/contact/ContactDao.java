package com.bondex.ysl.databaselibrary.contact;

import android.arch.persistence.room.*;

import java.util.List;

/**
 * date: 2019/1/29
 * Author: ysl
 * description:
 */
@Dao
public interface ContactDao {


    @Query("SELECT * FROM contactbean ")
    List<ContactBean> queryAllContact();

    @Query("SELECT * FROM contactbean WHERE code IN (:code)")
    ContactBean getContact(String code);

    @Update
    void updateContact(ContactBean bean);

    @Insert
    void inster(ContactBean bean);

    @Delete
    void delete(ContactBean bean);

    @Query("DELETE  FROM contactbean ")
    void deleteAll();
}
