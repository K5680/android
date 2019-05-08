package fi.jamk.omapicasso;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private String kuvapolku = "http://student.labranet.jamk.fi/~K5680/ttms0500-harkat/h03/kuvat/talo";
    private int indeksi = 1;
    // swipe down and up values
    private float x1,x2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        showImage();
    }

    public void showImage() {
        ImageView imageView = (ImageView) findViewById(R.id.imageView);
        Picasso.with(this)
                .load(kuvapolku + indeksi + ".jpg")
                .resize(800, 600)
                .into(imageView);

        TextView info = (TextView) findViewById(R.id.info);
        info.setText("nro: "+indeksi);

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(kuvapolku+indeksi+".jpg");
    }

    // method gets called when user performs any touch event on screen
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                if (x1 < x2) { // left to right -> previous
                    indeksi--;

                    if (indeksi < 1) indeksi = 5;
                } else { // right to left -> next
                    indeksi++;
                    if (indeksi > 5) indeksi = 1;
                }
                break;
        }
        showImage();
        return false;
    }
}
