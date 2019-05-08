package com.lonestones.vesada.tutorialgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

/**
 * Created by Vesada on 31.10.2017.
 */

public class Player {

    // character bitmap
    private Bitmap bitmap;
    // coords
    private int x;
    private int y;
    // motion speed
    private int speed = 0;
    // boost up
    private boolean boosting;
    // gravity value
    private final int GRAVITY = -10; // final = values are not to be changed
    // screen limits for the ship
    private int maxY;
    private int minY;
    // speed limits
    private final int MIN_SPEED = 1;
    private final int MAX_SPEED = 20;

    // create Rect object for collision detection *COLLISION*
    private Rect detectCollision;


    // constructor
    public Player(Context context, int screenX, int screenY){
        x = 75;
        y = 50;
        speed = 1;

        // get bitmap from resources
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.player);


        // base  = getDocumentBase();
        // hahmo = getImage(base, "data/kuva.png");

        // anim = new Animaatio();
        // anim.addFrame(kuva, 50); // = frame + duration


        // loading bitmap from SD CARD would be:
        //        ImageView image = (ImageView) findViewById(R.id.test_image);
        //        Bitmap bMap = BitmapFactory.decodeFile("/sdcard/test2.png");
        //        image.setImageBitmap(bMap);

        //calculating maxY
        maxY = screenY - bitmap.getHeight();

        //top edge's y point is 0 so min y will always be zero
        minY = 0;

        // boost to false
        boosting = false;

        // init rect object *COLLISION*
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }


    //setting boosting true
    public void setBoosting() {
        boosting = true;
    }

    //setting boosting false
    public void stopBoosting() {
        boosting = false;
    }


    // method to update coords
    public void update(){
        if (boosting) {
            //speeding up
            speed += 2;
        } else {
            // slow down if not boosting
            speed -= 5;
        }

        // control top speed
        if (speed > MAX_SPEED){
            speed = MAX_SPEED;
        }
        if (speed < MIN_SPEED){
            speed = MIN_SPEED;
        }

        // move the ship down
        y -= speed + GRAVITY;

        // control ship to stay on screen
        if (y < minY) {
            y = minY;
        }
        if (y > maxY) {
            y = maxY;
        }

        // add outlines to rect *COLLISION*
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

    // automatically: Generate -> getters
    //one more getter for getting the rect object
    public Rect getDetectCollision() {
        return detectCollision;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSpeed() {
        return speed;
    }
}
