package com.mordansoft.healthywork.domain.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mordansoft.healthywork.domain.MordanSoftLogger;

import java.util.ArrayList;

public class Exercise {

    private final int id;
    private String name;
    private String description;
    private String unit;
    private int     count;
    private boolean enable;
    private long timestamp;

    public Exercise(int id, String name, String description, String unit, int count, boolean enable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.count = count;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getUnit() {
        return unit;
    }

    public int getCount() {
        return count;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }



}
