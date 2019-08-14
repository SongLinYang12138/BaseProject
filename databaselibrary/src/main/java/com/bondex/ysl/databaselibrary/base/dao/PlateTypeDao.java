package com.bondex.ysl.databaselibrary.base.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import com.bondex.ysl.databaselibrary.base.bean.PlateType;

import java.util.List;

/**
 * date: 2019/8/12
 * Author: ysl
 * description:
 */
@Dao
public interface PlateTypeDao {

    @Query("SELECT * from platetype where cityId IN (:cityId)")
    List<PlateType> getAll(int cityId);

    @Query("DELETE  FROM PlateType WHERE cityId IN (:cityId)")
    int delteAll(int cityId);

    @Insert
    void intsert(List<PlateType> list);

    @Update
    int update(List<PlateType> list);


}
