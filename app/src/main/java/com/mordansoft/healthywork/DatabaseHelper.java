package com.mordansoft.healthywork;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Context context;
    public static final String dbName = "db_health_work";
    public static final int ver = 1;

    public DatabaseHelper(Context context) {
        super(context, dbName, null, ver);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db,0);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion);
    }

    @Override
    protected void finalize() throws Throwable {
        this.close();
        super.finalize();
    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVer){
        MordanSoftLogger.addLog("updateMyDatabase START oldVer = " + oldVer);
        if (oldVer < 1) { // first run on this devise
            try {
                deleteAllTables(db);
                createAllTables(db);
                insertTestData(db);
            } catch (Exception e) {
                MordanSoftLogger.addLog("updateMyDatabase deleteAllTables or createAllTables(db) error", 'e');
            }
        }
        MordanSoftLogger.addLog("updateMyDatabase END");
    }

    public static void deleteAllTables(SQLiteDatabase db){
        try {
                db.execSQL("DROP TABLE IF EXISTS '" + "EXERCISE" + "'");
        } catch (Exception e) {
            MordanSoftLogger.addLog("deleteAllTables: " + e, 'e');
        }
    }


    private static void createAllTables(SQLiteDatabase db){

        db.execSQL("CREATE TABLE EXERCISE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT,"
                + "description TEXT,"
                + "unit TEXT,"
                + "count REAL,"
                + "enable integer);");
    }

    public static void insertTestData(SQLiteDatabase db) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name","testExercise");
        contentValues.put("description","testExerciseDescription");
        contentValues.put("unit","test units");
        contentValues.put("count",5);
        contentValues.put("enable ",1);
        db.insert("EXERCISE",null,contentValues);
        contentValues.clear();
        contentValues.put("name","testExercise2");
        contentValues.put("description","testExerciseDescription2");
        contentValues.put("unit","test units2");
        contentValues.put("count",10);
        contentValues.put("enable",0);
        db.insert("EXERCISE",null,contentValues);
    }

    public static SQLiteDatabase getDatabase(Context context){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getWritableDatabase();
    }

}


