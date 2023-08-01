package com.mordansoft.healthywork.data.storage;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.data.R;
import com.mordansoft.healthywork.data.DatabaseHelper;
import com.mordansoft.healthywork.data.MordanSoftLogger;
import com.mordansoft.healthywork.data.model.ExerciseD;

import java.util.ArrayList;


public class ExerciseStorageImplSqlite implements ExerciseStorage {

    Context context;

    public ExerciseStorageImplSqlite(Context context) {
        this.context = context;
    }

    public boolean  deleteExercise (ExerciseD exercise){
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        db.delete("EXERCISE",
                "_id = ?",
                new String[] {String.valueOf(exercise.getId())});
        db.close();
        return true;
    }

    public boolean  updateExercise (ExerciseD exercise){
        long id = exercise.getId();
        MordanSoftLogger.addLog("updateExercise START id = " + id);
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", exercise.getName());
        contentValues.put("description", exercise.getDescription());
        contentValues.put("unit", exercise.getUnit());
        contentValues.put("count", exercise.getCount());
        contentValues.put("enable", exercise.isEnable());
        contentValues.put("timestamp", System.currentTimeMillis());
        if (id == 0) {
            id = db.insert("EXERCISE", null, contentValues);
        } else {
            db.update("EXERCISE", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        }
        db.close();
        MordanSoftLogger.addLog("UpdateExercise END");
        return true;
    }
    public ExerciseD getExerciseById(int exerciseId){
        MordanSoftLogger.addLog("getExerciseById START id = " + exerciseId);
        ExerciseD exercise = null;
        /* ExerciseD exercise = new ExerciseD(
                0,
                context.getString(R.string.activity_exercise_new),
                context.getString(R.string.activity_exercise_description),
                context.getString(R.string.activity_exercise_unit_times), //todo default units
                5,        //todo default counts
                true);
        if (exerciseId == 0){
            return exercise;
        }*/ // todo move this logic to UC
        try {
            SQLiteDatabase db =  DatabaseHelper.getDatabase(context);
            Cursor cursor = db.query("EXERCISE", new String[]{"name",
                            "description",
                            "unit",
                            "count",
                            "enable"},
                    "_id = ?", new String[]{String.valueOf(exerciseId)},null,null,null);
            if (cursor.moveToFirst()){
                exercise = new ExerciseD(
                        exerciseId,
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getInt(3),
                        cursor.getInt(4) == 1);
            }
            cursor.close();
            db.close();

        } catch(Exception e) {
            MordanSoftLogger.addLog("getExerciseById - " + e, 'e');
        }
        MordanSoftLogger.addLog("getExerciseById END");
        return exercise;
    }


    public ArrayList<ExerciseD> getExercisesByQuery(String filterQuery){
        com.mordansoft.healthywork.domain.MordanSoftLogger.addLog("getExercisesByQuery START query = " + filterQuery);
        ArrayList<ExerciseD> listExercises= new ArrayList<>();
        try {
            SQLiteDatabase db =  DatabaseHelper.getDatabase(context);
            Cursor cursor = db.query("EXERCISE", new String[]{
                            "_id",
                            "name",
                            "description",
                            "unit",
                            "count",
                            "enable"},
                    filterQuery, null,null,null,"timestamp");
            while (cursor.moveToNext()){
                ExerciseD exercise = new ExerciseD(
                        cursor.getInt(0),
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
            com.mordansoft.healthywork.domain.MordanSoftLogger.addLog("getExercisesByQuery - " + e, 'e');
        }
        com.mordansoft.healthywork.domain.MordanSoftLogger.addLog("getExercisesByQuery END");
        return listExercises;
    }

    @Override
    public ExerciseD getDefaultExercise() {
        return new ExerciseD(0,"New exercise", "Your description","",15,true); // todo
    }

    /* private void insertConstantsData(Context context) {
        updateExercise(context, new ExerciseD(0, context.getString(R),  context.getString(R.string.demo_data_exercise_description_1), context.getString(R.string.demo_data_unit_times),   10, true));       //Push-Ups
        updateExercise(context, new ExerciseD(0, context.getString(R.string.demo_data_exercise_name_2),  context.getString(R.string.demo_data_exercise_description_2), context.getString(R.string.demo_data_unit_times),   15, true));       //Squats
        updateExercise(context, new ExerciseD(0, context.getString(R.string.demo_data_exercise_name_3),  context.getString(R.string.demo_data_exercise_description_3), context.getString(R.string.demo_data_unit_times),   3,  true));       //Pull-Ups
        updateExercise(context, new ExerciseD(0, context.getString(R.string.demo_data_exercise_name_4),  context.getString(R.string.demo_data_exercise_description_4), context.getString(R.string.demo_data_unit_seconds), 30, true));       //Plank
        updateExercise(context, new ExerciseD(0, context.getString(R.string.demo_data_exercise_name_5),  context.getString(R.string.demo_data_exercise_description_5), context.getString(R.string.demo_data_unit_seconds), 60, true));       //Eye Exercises
        updateExercise(context, new ExerciseD(0, context.getString(R.string.demo_data_exercise_name_6),  context.getString(R.string.demo_data_exercise_description_6), context.getString(R.string.demo_data_unit_times),    5, true));       //Scapular wall slides
    }*/

}
