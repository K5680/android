package fi.jamk.launchamap;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showMap(View view) {
        //get values
        EditText editLat = (EditText) findViewById(R.id.editLatText);
        EditText editLng = (EditText) findViewById(R.id.editLngText);
        String numberOne = editLat.getText().toString();
        String numberTwo = editLng.getText().toString();

        double lat = Double.parseDouble(numberOne);
        double lng = Double.parseDouble(numberTwo);

        //Show map
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo: "+lat+","+lng));
        startActivity(intent);
    }
}
