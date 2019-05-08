package com.example.vesada.playmp3;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*  playMP3-1.png  &  playMP3_Jolla.png*/

public class MainActivity extends AppCompatActivity {

    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);

        /*play(); // doesn't work, you need to check runtime permissions to read external storage
        if (isReadStoragePermissionGranted()) {
            //play();
        } else {
            textView.setText("permission is not granted");
        }*/

        listview = (ListView) findViewById(R.id.listView);
        mediaPath = Environment.getExternalStorageDirectory().getPath() + "/Music/";

        //item listener
        listview.setOnItemClickListener(new
                AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        try {
                            mediaPlayer.reset();

                            String androidOS = Build.VERSION.RELEASE;
                            int buildVersion = Build.VERSION.SDK_INT;
                            Toast.makeText(getBaseContext(), "OS version " + androidOS + " / " + buildVersion, Toast.LENGTH_SHORT).show();

                            if (isReadStoragePermissionGranted()) {
                                mediaPlayer.setDataSource(songs.get(position));
                                mediaPlayer.prepare();
                                mediaPlayer.start();
                            }
                            else {
                                textView.setText("permission is not granted");
                            }
                        } catch (IOException e) {
                            Toast.makeText(getBaseContext(), "Cannot start audio", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // mp3 files from sd card (recursively)
        task = new LoadSongsTask();
        task.execute();

    }

    public  boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //play();
        }
    }



    private ListView listview;
    private String mediaPath;
    private List<String> songs = new ArrayList<String>();
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private LoadSongsTask task;

    private class LoadSongsTask extends AsyncTask<Void, String, Void>{
        private List<String> loadedSongs = new ArrayList<String>();

        protected void onPreExecute() {
            Toast.makeText(getApplicationContext(),"Loading...", Toast.LENGTH_LONG).show();
        }

        protected Void doInBackground(Void... url){
            updateSongListRecursive(new File(mediaPath));
            return null;
        }

        public void updateSongListRecursive(File path){
            if(path.isDirectory()){
                for (int i = 0; i < path.listFiles().length; i++){
                    File file = path.listFiles()[i];
                    updateSongListRecursive(file);
                }
            } else {
                String name = path.getAbsolutePath();
                publishProgress(name);
                if (name.endsWith(".mp3")) {
                    loadedSongs.add(name);
                }
            }
        }

        protected void onPostExecute(Void args){
            ArrayAdapter<String> songList = new ArrayAdapter<String>(MainActivity.this,
                    android.R.layout.simple_list_item_1, loadedSongs);
            listview.setAdapter(songList);
            songs = loadedSongs;

            Toast.makeText(getApplicationContext(),
                    "Songs="+songs.size(),
                    Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onStop(){
        super.onStop();
        if (mediaPlayer.isPlaying()) mediaPlayer.reset();
    }
}

