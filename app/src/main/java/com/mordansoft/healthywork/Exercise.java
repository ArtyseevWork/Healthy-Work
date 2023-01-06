package com.mordansoft.healthywork;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class Exercise {

    private String  id;
    private String  name;
    private String  description;
    private String  unit;
    private int     count;
    private boolean enable;

    public Exercise(String id, String name, String description, String unit, int count, boolean enable) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.unit = unit;
        this.count = count;
        this.enable = enable;
    }

    public String getId() {
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

    public static long insertExercise(Context context, Exercise exercise){
        SQLiteDatabase db =  DatabaseHelper.getDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",exercise.name);
        contentValues.put("description",exercise.description);
        contentValues.put("unit",exercise.unit);
        contentValues.put("count",exercise.count);
        contentValues.put("enable",exercise.enable);
        long result = db.insert("EXERCISE",null, contentValues);
        db.close();
        return (result);
    }

    public static void updateExercise(Context context, Exercise exercise){
        SQLiteDatabase db =  DatabaseHelper.getDatabase(context);

        ContentValues contentValues = new ContentValues();
        contentValues.put("name",exercise.name);
        contentValues.put("description",exercise.description);
        contentValues.put("unit",exercise.unit);
        contentValues.put("count",exercise.count);
        contentValues.put("enable",exercise.enable);
        db.update("EXERCISE",contentValues, "_id = ?",new String[] {String.valueOf(exercise.id)});
        db.close();
    }

    public static void deleteExercise(Context context, Exercise exercise){
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        db.delete("EXERCISE",
                "_id = ?",
                new String[] {String.valueOf(exercise.id)});
        db.close();
    }

    public static ArrayList<Exercise> getExercisesByQuery(Context context, String filterQuery){
        MordanSoftLogger.addLog("getExercisesByQuery Start query = " + filterQuery);
        ArrayList<Exercise> listExercises= new ArrayList<>();
        try {
            SQLiteDatabase db =  DatabaseHelper.getDatabase(context);
            Cursor cursor = db.query("EXERCISE", new String[]{
                            "_id",
                            "name",
                            "description",
                            "unit",
                            "count",
                            "enable"},
                    filterQuery, null,null,null,null);
            while (cursor.moveToNext()){
                Exercise exercise = new Exercise(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getInt(5) == 1);
                listExercises.add(exercise);
            }
            cursor.close();
            db.close();
        } catch(Exception e) {
            MordanSoftLogger.addLog("getExercisesByQuery - " + e, 'e');
        }
        MordanSoftLogger.addLog("getExercisesByQuery END");
        return listExercises;
    }

    public static Exercise getExerciseById(Context context, String id){
        MordanSoftLogger.addLog("getExerciseById Start");
        Exercise exercise = null;
        try {
            SQLiteDatabase db =  DatabaseHelper.getDatabase(context);
            Cursor cursor = db.query("EXERCISE", new String[]{"name",
                            "description",
                            "unit",
                            "count",
                            "enable"},
                    "_id = ?", new String[]{id},null,null,null);
            db.close();
            if (cursor.moveToFirst()){
                exercise = new Exercise(
                        id,
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4) == 1);
            }
            cursor.close();

        } catch(Exception e) {
            MordanSoftLogger.addLog("getExerciseById - " + e, 'e');
        }
        MordanSoftLogger.addLog("getExerciseById End");
        return exercise;
    }




}
