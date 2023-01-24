package com.mordansoft.healthywork;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;

import java.util.ArrayList;

public class Exercise {

    private final int id;
    private String  name;
    private String  description;
    private String  unit;
    private int     count;
    private boolean enable;

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

    public long saveExercise(Context context){
        long id = this.id;
        MordanSoftLogger.addLog("updateExercise START id = " + id);
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", this.name);
        contentValues.put("description", this.description);
        contentValues.put("unit", this.unit);
        contentValues.put("count", this.count);
        contentValues.put("enable", this.enable);
        if (id == 0 ){
            id = db.insert("EXERCISE",null, contentValues);
        } else {
            db.update("EXERCISE", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        }
        db.close();
        MordanSoftLogger.addLog("UpdateExercise END");
        return  id;
        
    }

    public static long saveExercise(Context context, Exercise exercise){
        long id = exercise.id;
        MordanSoftLogger.addLog("updateExercise START id = " + id);
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", exercise.name);
        contentValues.put("description", exercise.description);
        contentValues.put("unit", exercise.unit);
        contentValues.put("count", exercise.count);
        contentValues.put("enable", exercise.enable);
        if (id == 0 ){
            id = db.insert("EXERCISE",null, contentValues);
        } else {
            db.update("EXERCISE", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        }
        db.close();
        MordanSoftLogger.addLog("UpdateExercise END");
        return  id;

    }

    public static void deleteExercise(Context context, Exercise exercise){
        SQLiteDatabase db = DatabaseHelper.getDatabase(context);
        db.delete("EXERCISE",
                "_id = ?",
                new String[] {String.valueOf(exercise.id)});
        db.close();
    }

    public static ArrayList<Exercise> getExercisesByQuery(Context context, String filterQuery){
        MordanSoftLogger.addLog("getExercisesByQuery START query = " + filterQuery);
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
            MordanSoftLogger.addLog("getExercisesByQuery - " + e, 'e');
        }
        MordanSoftLogger.addLog("getExercisesByQuery END");
        return listExercises;
    }

    public static Exercise getExerciseById(Context context, int id){
        if (id == 0){
            return getNewExercise(context);
        }
        MordanSoftLogger.addLog("getExerciseById START id = " + id);
        Exercise exercise = Exercise.getNewExercise(context);
        try {
            SQLiteDatabase db =  DatabaseHelper.getDatabase(context);
            Cursor cursor = db.query("EXERCISE", new String[]{"name",
                            "description",
                            "unit",
                            "count",
                            "enable"},
                    "_id = ?", new String[]{String.valueOf(id)},null,null,null);
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
            db.close();

        } catch(Exception e) {
            MordanSoftLogger.addLog("getExerciseById - " + e, 'e');
        }
        MordanSoftLogger.addLog("getExerciseById END");
        return exercise;
    }

    public static Exercise getNewExercise(Context context){

        MordanSoftLogger.addLog("getNewExercise START ");
        Exercise exercise  = new Exercise(
                        0,
                        context.getString(R.string.activity_exercise_new),
                        context.getString(R.string.activity_exercise_description),
                        context.getString(R.string.activity_exercise_units), //todo default units
                        5,                                                   //todo default counts
                        true);
        MordanSoftLogger.addLog("getNewExercise END");
        return exercise;
    }

    public static Exercise getRandomExercise(Context context){
        ArrayList<Exercise> exercises = Exercise.getExercisesByQuery(context, "enable = 1");
        int size = exercises.size();
        int num = (int) (Math.random() * size);
        MordanSoftLogger.addLog("getRandomExercise  =  " + num );
        return exercises.get(num);
    }

    public static void test(Context context, View view, Exercise exercise) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.activity_exercise_delete));
        builder.setMessage(context.getString(R.string.activity_exercise_delete_message));
        builder.setPositiveButton(context.getString(R.string.yes), (dialog, id) -> {
            try {
                Exercise.deleteExercise(view.getContext(), exercise);
                Toast toast = Toast.makeText(view.getContext(),
                        context.getString(R.string.activity_exercise_delete_complete_message),
                        Toast.LENGTH_LONG);
                Intent intent;
                intent = new Intent(view.getContext(), MainActivity.class);
                toast.show();
                context.startActivity(intent);
            } catch (Exception e){
                Toast toast = Toast.makeText(view.getContext(),
                        "deleteKnife error: " + e,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        builder.setNegativeButton(context.getString(R.string.no), (dialog, id) -> dialog.cancel());
        builder.show();
    }

    public static void insertConstantsData(Context context) {
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_1),  context.getString(R.string.demo_data_exercise_description_1), context.getString(R.string.demo_data_unit_times),   10, true));       //Push-Ups
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_2),  context.getString(R.string.demo_data_exercise_description_2), context.getString(R.string.demo_data_unit_times),   15, true));       //Squats
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_3),  context.getString(R.string.demo_data_exercise_description_3), context.getString(R.string.demo_data_unit_times),   3,  true));       //Pull-Ups
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_4),  context.getString(R.string.demo_data_exercise_description_4), context.getString(R.string.demo_data_unit_seconds), 30, true));       //Plank
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_5),  context.getString(R.string.demo_data_exercise_description_5), context.getString(R.string.demo_data_unit_seconds), 60, true));       //Eye Exercises
        saveExercise(context, new Exercise(0, context.getString(R.string.demo_data_exercise_name_6),  context.getString(R.string.demo_data_exercise_description_6), context.getString(R.string.demo_data_unit_times),    5, true));       //Scapular wall slides
    }




}
