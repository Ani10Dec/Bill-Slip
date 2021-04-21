package com.example.challengeMarketGad;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DataDao {

    @Insert
    void insert(DataEntity dataEntity);

    @Query(value = "SELECT * FROM marketGad_data WHERE data_id = :dataID")
    DataEntity getDataByID(String dataID);

    @Query(value = "SELECT * FROM marketgad_data")
    List<DataEntity> getAllData();

    @Query(value = "DELETE FROM marketgad_data")
    int delete();

}
