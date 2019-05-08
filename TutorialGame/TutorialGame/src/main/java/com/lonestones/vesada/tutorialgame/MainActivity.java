package com.lonestones.vesada.tutorialgame;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    //image button
    private ImageButton buttonPlay;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //orientation to landscape
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        // get button
        buttonPlay = (ImageButton) findViewById(R.id.buttonPlay);

        // add a click listener
        buttonPlay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        // start game activity
        startActivity(new Intent(this, GameActivity.class));
    }
}

