package com.example.omistaja.shoppinglistactivity;

import android.app.ListActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class MainActivity extends ListActivity {
        DatabaseHelper db;
        SimpleCursorAdapter dataAdapter;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            db = new DatabaseHelper(MainActivity.this);
            displayList();
        }

        public void displayList(){
            db.InsertValues();
            Cursor cursor=db.GetAllData();
            String from [] = new String[]{db.colName};
            int to [] = new int[] {R.id.textView1};
            dataAdapter = new SimpleCursorAdapter(this, R.layout.list_item, cursor, from, to, 0);
            db.close();

            ListView lv = getListView();
            lv.setAdapter(dataAdapter);
        }
    }