package fi.jamk.listviewexercise;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Vesada on 4.9.2017.
 */

public class DetailActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        //get intent
        Intent intent = getIntent();
        //get data
        Bundle bundle = intent.getExtras();
        //get phone name
        String phone = bundle.getString("phone");
        // update text&image
        TextView textView = (TextView) findViewById(R.id.phoneTextView);
        textView.setText(phone);
        ImageView imageView = (ImageView) findViewById(R.id.phoneImageView);
        //show phone image
        switch (phone){
            case"Android": imageView.setImageResource(R.drawable.android);break;
            case"iPhone": imageView.setImageResource(R.drawable.ios);break;
            case"WindowsMobile": imageView.setImageResource(R.drawable.windows);break;
            case"Blackberry": imageView.setImageResource(R.drawable.blackberry);break;
            case"WebOS": imageView.setImageResource(R.drawable.webos);break;
            case"Ubuntu": imageView.setImageResource(R.drawable.ubuntu);break;
            case"Sailfish": imageView.setImageResource(R.drawable.sailfish);break;
        }
    }

    public void backButtonPressed(View view){
        //finish & close
        finish();
    }

}
