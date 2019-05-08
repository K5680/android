package fi.jamk.listviewexercise;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import fi.jamk.listviewexercise.R;

import static fi.jamk.listviewexercise.R.id.imageView;

/**
 * Created by Vesada on 4.9.2017.
 */

public class PhoneArrayAdapter extends ArrayAdapter<String> {
    private Context context;
    private ArrayList<String> phones;

    //get application context and phones data to adapter
    public PhoneArrayAdapter(Context context, ArrayList<String> phones){
        super(context, R.layout.rowlayout, R.id.textView, phones);
        this.context = context;
        this.phones = phones;
    }

    //populate every row in listView
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //get row
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rowlayout, parent, false);
        //show phone name
        TextView textView = (TextView) rowView.findViewById(R.id.textView);
        textView.setText(phones.get(position));
        //show icon
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView);

        switch (phones.get(position)){
            case "Android":imageView.setImageResource(R.drawable.android); break;
            case "iPhone":imageView.setImageResource(R.drawable.ios); break;
            case "WindowsMobile":imageView.setImageResource(R.drawable.windows); break;
            case "BlackBerry":imageView.setImageResource(R.drawable.blackberry); break;
            case "WebOS":imageView.setImageResource(R.drawable.webos); break;
            case "Ubuntu":imageView.setImageResource(R.drawable.ubuntu); break;
            case "Sailfish":imageView.setImageResource(R.drawable.sailfish); break;
        }

        //return row view
        return rowView;
    }

}
