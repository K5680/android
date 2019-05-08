package com.example.omistaja.shopping;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;
    private Cursor cursor;

    public final static String OMA_TABLE="Ostokset"; // name of table

    public final static String OMA_ID="_id";
    public final static String OMA_NAME="name";
    public final static String OMA_COUNT="count";
    public final static String OMA_PRICE="price";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dbHelper = new DatabaseHelper(this);
        database = dbHelper.getReadableDatabase();

        createRecords("1", "tomaatti", 5, 10);

        String[] columns = new String[]{"_id","name","count", "price"};


        /*
        String table = "table2";
        String[] columns = {"column1", "column3"};
        String selection = "column3 =?";
        String[] selectionArgs = {"apple"};
        String groupBy = null;
        String having = null;
        String orderBy = "column3 DESC";
        String limit = "10";
        */

        cursor = database.query(OMA_TABLE, columns, null, null, null, null, "name DESC", "3");

    }



    public long createRecords(String id, String name, int count, int price){
        ContentValues values = new ContentValues();
        values.put(OMA_ID, id);
        values.put(OMA_NAME, name);
        values.put(OMA_COUNT, count);
        values.put(OMA_PRICE, price);
        return database.insert(OMA_TABLE, null, values);
    }

    public Cursor selectRecords() {
        String[] cols = new String[] {OMA_ID, OMA_NAME, OMA_COUNT, OMA_PRICE};
        Cursor mCursor = database.query(true, OMA_TABLE,cols,null
                , null, null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor; // iterate to get each value.
    }
}
