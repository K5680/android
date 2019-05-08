package com.lonestones.vesada.tutorialgame;

import java.util.Random;

/**
 * Created by Omistaja on 1.11.2017.
 */

public class Star {

    private int x;
    private int y;
    private int speed;

    private int maxX;
    private int maxY;
    private int minY;
    private int minX;

    public Star(int screenX, int screenY){
        maxX = screenX;
        maxY = screenY;
        minX = 0;
        minY = 0;
        Random generator = new Random();
        speed = generator.nextInt(10);  // star speed randomized 0-10

        // random coordinates inside screen
        x = generator.nextInt(maxX);
        y = generator.nextInt(maxY);
    }

    public void update(int playerSpeed) {
        // animate star by player speed
        x -= playerSpeed;
        x -= speed;

        // star left the screen -> back to right
        if (x < 0) {
            x = maxX;
            Random generator = new Random();
            y = generator.nextInt(maxY);
        }
    }

    public float getStarWidth() {
        // make star width random
        float minX = 1.0f;
        float maxX = 4.0f;
        Random rand = new Random();
        float finalX = rand.nextFloat() * (maxX - minX) + minX;
        return finalX;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}


