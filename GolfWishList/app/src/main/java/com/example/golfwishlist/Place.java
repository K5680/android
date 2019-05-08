package com.example.golfwishlist;

import android.content.Context;

/**
 * Created by k5680 on 9.10.2017.
 */

public class Place {
    public String name;
    public String imageName;

    public int getImageResourceId(Context context) {
        return context.getResources().getIdentifier(this.imageName, "drawable", context.getPackageName());
    }
}