package fi.jamk.firsttest;

import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String androidOS = Build.VERSION.RELEASE;
        int buildVersion = Build.VERSION.SDK_INT;
        Toast.makeText(getBaseContext(), "OS version " + androidOS + " /  API " + buildVersion, Toast.LENGTH_SHORT).show();

    }
}
