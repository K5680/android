package com.example.vesada.playmp4;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaSoitin = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        String osoite = getString(R.string.url_os);

        intent.setDataAndType(Uri.parse(osoite), "video/mp4");
        Toast.makeText(getApplicationContext(),osoite, Toast.LENGTH_LONG).show();

        startActivity(intent);
    }
}

