package fi.jamk.listviewexercise;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // find list view
        ListView listview = (ListView)  findViewById(R.id.listView);

        // dummy data
        String[] phones = new String[]{
                "Android", "iPhone", "WindowsMobile", "BlackBerry", "WebOS", "Ubuntu", "Sailfish", "Android", "iPhone", "WindowsMobile", "BlackBerry", "WebOS", "Ubuntu"
            };

        // add data to array list
        final ArrayList<String> list = new ArrayList<>();

        for(int i = 0; i < phones.length; ++i) {
            list.add(phones[i]);
        }

        // add data to array adapter
        // ArrayAdapter adapter = new ArrayAdapter(this, R.layout.rowlayout, R.id.textView, list);
        PhoneArrayAdapter adapter = new PhoneArrayAdapter(this, list);

        // set data to listView with adapter
        listview.setAdapter(adapter);

        //item listener
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                //get list row data
                String phone = list.get(position);
                // create an explicit intent
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                //add data to intent
                intent.putExtra("phone",phone);
                // start new activity
                startActivity(intent);
            }
        });
      }
}

