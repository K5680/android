package com.example.omistaja.shoppinglistactivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    static String DatabaseName = "NeroDB";
    static String colName = "_id";
    static String colAge = "Age";

    public DatabaseHelper(Context context) {
        super(context, DatabaseName, null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS NeroTable(_id VARCHAR, Age INT(3));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE NeroTable;");
        db.execSQL("CREATE TABLE IF NOT EXISTS NeroTable(_id VARCHAR, Age INT(3));");
    }

    public void InsertValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO NeroTable VALUES('Vergil', 20);");
        db.execSQL("INSERT INTO NeroTable VALUES('Dante', 20);");
        db.execSQL("INSERT INTO NeroTable VALUES('Nero', 18);");
        db.execSQL("INSERT INTO NeroTable VALUES('Sparda', 40);");
        db.execSQL("INSERT INTO NeroTable VALUES('Arkham', 38);");
        db.execSQL("INSERT INTO NeroTable VALUES('Agni', 35);");
        db.execSQL("INSERT INTO NeroTable VALUES('Rudra', 35);");
        db.execSQL("INSERT INTO NeroTable VALUES('Beowulf', 60);");
        db.execSQL("INSERT INTO NeroTable VALUES('Nevan', 26);");
        db.close();
    }

    public Cursor GetAllData() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM NeroTable;", null);
        return c;
    }

    public void DeleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE NeroTable;");
    }
}