package com.mordansoft.healthywork;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    // Context context;
    public static final String dbName = "db_bank";
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
        if (oldVer < 1) { // first run on this devise
            try {
                deleteAllTables(db);
                createAllTables(db);
            } catch (Exception e) {
                MordanSoftLogger.addLog("updateMyDatabase deleteAllTables or createAllTables(db) error");
            }
        }
    }

    public static void deleteAllTables(SQLiteDatabase db){
        try {
                db.execSQL("DROP TABLE IF EXISTS '" + "EXERCISE" + "'");
        } catch (Exception e) {
            MordanSoftLogger.addLog("No tables was found");
        }
    }


    private static void createAllTables(SQLiteDatabase db){

        db.execSQL("CREATE TABLE EXERCISE ("
                + "_id TEXT PRIMARY KEY AUTOINCREMENT, "
                + "name TEXT,"
                + "description TEXT,"
                + "unit TEXT,"
                + "count REAL,"
                + "enable integer);");
    }

    public static void insertTestData(Context context) {

    }

    public static SQLiteDatabase getDatabase(Context context){

        SQLiteOpenHelper databaseHelper = new DatabaseHelper(context);
        return databaseHelper.getWritableDatabase();
    }

}


