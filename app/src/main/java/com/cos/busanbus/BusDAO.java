package com.cos.busanbus;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Query;

import java.util.List;

@Dao
public interface BusDAO {
    @Query("SELECT * FROM Bus")
    List<Bus> findAll();

    @Query("SELECT busLineNum From Bus")
    void findBusNum(Bus busNum);

    @Delete
    void deleteByNum();

}
