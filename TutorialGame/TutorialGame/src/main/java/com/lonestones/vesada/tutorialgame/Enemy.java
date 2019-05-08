package com.lonestones.vesada.tutorialgame;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Omistaja on 1.11.2017.
 */

public class Enemy {
    // enemy bitmap
    private Bitmap bitmap;
    // coords, speed, min max coords
    private int x;
    private int y;
    private int speed = 1;
    private int maxX;
    private int minX;
    private int maxY;
    private int minY;

    // create Rect object for collision detection *COLLISION*
    private Rect detectCollision;

    public Enemy(Context context, int screenX, int screenY){
        //bitmap from resources
        bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.enemy);

        // init coords;
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;

        // random coords to enemy
        Random generator = new Random();
        speed = generator.nextInt(6) +10;
        x = screenX;
        y = generator.nextInt(maxY) - bitmap.getHeight(); // coord y - bitmap height

        // init rect object *COLLISION*
        detectCollision = new Rect(x, y, bitmap.getWidth(), bitmap.getHeight());
    }

    public void update(int playerSpeed) {
        // decrease x -> moves right to left
        x -= playerSpeed;
        x -= speed;

        // left edge action
        if (x < minX - bitmap.getWidth()){
            // again to beginning (rght)
            Random generator = new Random();
            speed = generator.nextInt(10) + 10;
            x = maxX;
            y = generator.nextInt(maxY) - bitmap.getHeight(); // coord y - bitmap height
        }

        // add outlines to rect *COLLISION*
        detectCollision.left = x;
        detectCollision.top = y;
        detectCollision.right = x + bitmap.getWidth();
        detectCollision.bottom = y + bitmap.getHeight();
    }

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

    public void setX(int x) {
        this.x = x;
    }
}
