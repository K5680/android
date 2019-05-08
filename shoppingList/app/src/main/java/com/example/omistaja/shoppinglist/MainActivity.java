 package com.example.omistaja.shoppinglist;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;
import android.view.MenuItem;


 public class MainActivity extends AppCompatActivity {
     private final String DATABASE_TABLE = "products";
     private final int DELETE_ID = 0;
     private SQLiteDatabase db;
     private Cursor cursor;
     private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find list view
        listView = (ListView)  findViewById(R.id.listView);
        // register listView's context menu (to delete row)
        registerForContextMenu(listView);

        // get database instance
        db = (new DatabaseOpenHelper(this)).getWritableDatabase();
        // get data with using own made queryData method
        queryData();

        // calculate total points in highscores
        float points = 0f;
        if (cursor.moveToFirst()) {
            do {
                float score = cursor.getFloat(2); // columnIndex
                points += score;
            } while(cursor.moveToNext());
            Toast.makeText(getBaseContext(), "Total points: " + points, Toast.LENGTH_SHORT).show();
        }
    }

     // query data from database
     public void queryData() {
         //cursor = db.rawQuery("SELECT _id, name, score FROM highscores ORDER BY score DESC", null);
         // get data with query
         String[] resultColumns = new String[]{"_id","product","count", "price"};
         cursor = db.query(DATABASE_TABLE,resultColumns,null,null,null,null,"product DESC",null);

         // add data to adapter
         ListAdapter adapter = new SimpleCursorAdapter(this,
                 R.layout.list_item, cursor,
                 new String[] {"product", "count", "price"},      // from
                 new int[] {R.id.product, R.id.count, R.id.price}    // to
                 ,0);  // flags

         // show data in listView
         listView.setAdapter(adapter);
     }


     // Handles item selections
     public boolean onOptionsItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case R.id.action_add:
                 AddProductListFragment eDialog = new AddProductListFragment();
                 eDialog.show(getFragmentManager(), "Add a product");
         }
         return false;
     }

     @Override
     public void onDialogPositiveClick(DialogFragment dialog, String product, int count, int price) {
         ContentValues values=new ContentValues(2);
         values.put("product", product);
         values.put("count", count);
         values.put("price", price);
         db.insert("products", null, values);
         // get data again
         queryData();
     }

     @Override
     public void onDialogNegativeClick(android.app.DialogFragment dialog) {
         // TODO Auto-generated method stub

     }

     @Override
     public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
         menu.add(Menu.NONE, DELETE_ID, Menu.NONE, "Delete");
     }

     @Override
     public boolean onContextItemSelected(MenuItem item) {
         switch (item.getItemId()) {
             case DELETE_ID:
                 AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
                 String[] args = {String.valueOf(info.id)};
                 db.delete("products", "_id=?", args);
                 queryData();
                 return true;
         }
         return super.onOptionsItemSelected(item);
     }


     @Override
     public void onDestroy() {
         super.onDestroy();
         // close cursor and db connection
         cursor.close();
         db.close();
     }
}
