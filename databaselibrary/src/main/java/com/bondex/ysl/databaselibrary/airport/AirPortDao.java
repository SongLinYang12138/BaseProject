package com.bondex.ysl.databaselibrary.airport;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * date: 2019/8/29
 * Author: ysl
 * description:
 */
@Dao
public interface AirPortDao {


    @Insert
    void insert(AirPort airPort);

    @Update
    void update(AirPort airPort);

    @Query("SELECT * FROM AirPort WHERE code in (:code)")
    AirPort check(String code);


    @Query("SELECT * FROM AirPort WHERE city in (:city)")
    List<AirPort> selectByCity(String city);

    @Query("DELETE FROM AirPort ")
    void deleteAll();


}
