package com.cos.busanbus;

import android.text.Editable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Time;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
public class Bus {
    @PrimaryKey(autoGenerate = true)
    public String busLineNum;

    public String busType;
    public String companyId;
    public String endTime;
    public String firstTime;
    public String headway;
    public String headwayHoli;
    public String headwayNorm;
    public String headwayPeak;
    public Long lineId;
    public String startPoint;
    public String endPoint;


    public Bus(String toString) {
    }
}
