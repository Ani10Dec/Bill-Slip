package com.example.challengeMarketGad;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {DataEntity.class}, version = 1)
public abstract class DataBase  extends RoomDatabase {
    public abstract DataDao dataDao();
}
