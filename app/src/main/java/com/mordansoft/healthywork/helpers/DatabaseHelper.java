package com.mordansoft.healthywork.helpers;


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Context context;
    private static final String dbName = "db_health_work";
    private static final int ver = 2;

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
                //insertTestData(db);
            } catch (Exception e) {
                MordanSoftLogger.addLog("updateMyDatabase error", 'e');
            }
        } else   if (oldVer == 1) { // first run on this devise
            try {
                db.execSQL("alter table EXERCISE add column timestamp NUMERIC DEFAULT 0");
            } catch (Exception e) {
                MordanSoftLogger.addLog("updateMyDatabase to ver2 error", 'e');
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
                + "enable integer,"
                + "timestamp NUMERIC);");
    }


    public static SQLiteDatabase getDatabase(Context context){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getWritableDatabase();
    }

}


