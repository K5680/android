package fi.jamk.intentexample;

import android.content.Intent;
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

    public void login(View view) {
        EditText firstnameEditText = (EditText) findViewById(R.id.firstnameEditText);
        EditText lastnameEditText = (EditText) findViewById(R.id.lastnameEditText);

        String firstname = firstnameEditText.getText().toString();
        String lastname = lastnameEditText.getText().toString();

        // start another activity
        Intent intent = new Intent(this,SecondActivity.class);
        intent.putExtra("firstname", firstname);
        intent.putExtra("lastname", lastname);
        startActivity(intent);
    }
}
