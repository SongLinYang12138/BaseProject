package com.bondex.ysl.databaselibrary.base.dao;

import android.arch.persistence.room.*;
import com.bondex.ysl.databaselibrary.base.bean.SubPlateType;

import java.util.List;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Dao
public interface SubplateTypedDao {

    @Insert
    void insert(List<SubPlateType> list);

    @Update
    void update(List<SubPlateType> list);

    @Query("DELETE FROM SUBPLATETYPE WHERE cityId IN (:cityId)")
    int delete(int cityId);


    @Query("SELECT * FROM SubPlateType WHERE cityId IN (:cityId)")
    List<SubPlateType> getAll(int cityId);
}
