package fi.jamk.dialogtext;

import android.app.Dialog;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action:
                Toast.makeText(getBaseContext(), "Action 1", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.action2:
                // Ammu ohjuksia
                DialogFragment newFragment = new FireMissilesDialogFragment();
                newFragment.show(getSupportFragmentManager(), "missiles");

                Toast.makeText(getBaseContext(), "Missiles ready...", Toast.LENGTH_LONG).show();
                return true;
            case R.id.action3:
                showCustomLayoutToast(null);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    // this method is called from layout (activity_main)
    public void showCustomPositionToast(View view) {
        Toast toast = Toast.makeText(getApplicationContext(), "Custom Position Toast", Toast.LENGTH_LONG);
        toast.setGravity(Gravity.BOTTOM|Gravity.START, 0, 0);
        toast.show();
    }

    // this method is called from layout (activity_main)
    public void showCustomLayoutToast(View view) {
        // get Activity's layout inflater
        LayoutInflater inflater = getLayoutInflater();
        // inflate layout from XML (toast.xml), LinearLayout with ImageView and TextView
        View layout = inflater.inflate(R.layout.toast, (ViewGroup) findViewById(R.id.toast));
        // get image from resource
        ImageView image = (ImageView) layout.findViewById(R.id.imageView);
        image.setImageResource(R.drawable.missile);
        // set text
        TextView text = (TextView) layout.findViewById(R.id.textView);
        text.setText("quiting...");
        // create and show toast
        Toast toast = new Toast(getApplicationContext());
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 100);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}
