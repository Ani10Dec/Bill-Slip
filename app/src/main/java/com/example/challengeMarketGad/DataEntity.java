package com.example.challengeMarketGad;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "MarketGad_data")
public class DataEntity {
    @PrimaryKey
    int data_id;
    @ColumnInfo(name = "imageUri")
    String bitmap;
    @ColumnInfo(name = "amount")
    String amount;

    public DataEntity(int data_id, String bitmap, String amount) {
        this.data_id = data_id;
        this.bitmap = bitmap;
        this.amount = amount;
    }

    public int getData_id() {
        return data_id;
    }

    public void setData_id(int data_id) {
        this.data_id = data_id;
    }

    public String getBitmap() {
        return bitmap;
    }

    public void setBitmap(String bitmap) {
        this.bitmap = bitmap;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
