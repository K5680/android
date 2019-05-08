package com.lonestones.vesada.tutorialgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;

/**
 * Created by Vesada on 31.10.2017.
 */

public class GameView extends SurfaceView implements Runnable{

    // track the game on / off.     Volatile = arvoa voidaan muttaa eri säikeistä
    volatile boolean playing;    // Boolean = true/false
    // game thread
    private Thread gameThread = null;

    // add player to this class
    private Player player;

    // objects for drawing
    private Paint paint;
    private Canvas canvas;

    // Allows you to control the surface size and format, edit the pixels in the surface, and monitor changes to the surface
    private SurfaceHolder surfaceHolder;

    // add stars
    private ArrayList<Star> stars = new ArrayList<Star>();
    // add enemies object array
    private Enemy[] enemies;
    private int enemyCount = 3;

    // boom effect
    private Boom boom;


    // class constructor
    // Context is an Interface to global information about an application environment, abstract class
    //  ...for application-level operations such as launching activities, broadcasting and receiving intents, etc
    // super() calls the parent's class constructor (all the way back to Object) and it runs before the current class's constructor.
    public GameView(Context context, int screenX, int screenY){
        super(context);

        // init player object
        // screen size passed to player constructor
        player = new Player(context, screenX, screenY);

        // init drawing objects
        surfaceHolder = getHolder(); // access and control over underlying "surface"
        paint = new Paint(); // The Paint class holds the style and color information about how to draw geometries, text and bitmaps.

        // add 100 stars
        int starNums = 100;
        for (int i = 0; i < starNums; i++) {
            Star s = new Star(screenX, screenY);
            stars.add(s);
        }

        // int enemy object array
        enemies = new Enemy[enemyCount];
        for (int i = 0; i<enemyCount; i++){
            enemies[i] = new Enemy(context, screenX, screenY);
        }

        // init boom object
        boom = new Boom(context);
    }

    @Override
    public void run(){
        while (playing){
            update();   // update frame
            draw();     // Here we will draw the characters to the canvas.
            control();  //This method will control the frames per seconds drawn. Here we are calling the delay method of Thread. And this is actually making our frame rate to aroud 60fps.
            }
    }


    private void update(){
        // update player position
        player.update();

        // update stars with player speed
        for (Star s : stars) {
            s.update(player.getSpeed());
        }

        // update enemy coords
        for(int i=0;i<enemyCount;i++){
            enemies[i].update(player.getSpeed());

            // detect rect collision    *COLLISION*
            if (Rect.intersects(player.getDetectCollision(), enemies[i].getDetectCollision())) {
                // show boom blast at collision position
                boom.setX(enemies[i].getX());
                boom.setY(enemies[i].getY());

                // move enemy out the screen
                enemies[i].setX(-200);

            }
        }
    }

    private void draw(){
        // check surface is valid
        if (surfaceHolder.getSurface().isValid()) {
            // lock canvas
            canvas = surfaceHolder.lockCanvas();
            // draw background color
            canvas.drawColor(Color.BLACK);

            // star color
            paint.setColor(Color.WHITE);
            // draw stars
            for (Star s : stars){
                paint.setStrokeWidth(s.getStarWidth());
                canvas.drawPoint(s.getX(), s.getY(), paint);
            }

            // draw the player
            canvas.drawBitmap(      // bitmap, left, top, paint used to draw the bitmap
                    player.getBitmap(),
                    player.getX(),
                    player.getY(),
                    paint);

            // draw enemies
            for(int i = 0; i < enemyCount; i++){
                canvas.drawBitmap(
                    enemies[i].getBitmap(),
                    enemies[i].getX(),
                    enemies[i].getY(),
                    paint
                    );
            }

            // draw boom image
            canvas.drawBitmap(
                    boom.getBitmap(),
                    boom.getX(),
                    boom.getY(),
                    paint
            );

            // unlock canvas
            surfaceHolder.unlockCanvasAndPost(canvas);  // "flip screen?"
        }
    }

    private void control() {
        try  {
            gameThread.sleep(17);   // delay thread = framerate
            } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void pause() {
        // game paused -> variable false, stop thread
        playing = false;
        try {
            gameThread.join();
        } catch (InterruptedException e){
        }
    }

    public void resume() {
        // game resumed -> start over thread
        playing = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent){
        switch(motionEvent.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_UP:
                // user touch on the screen
                player.stopBoosting();

                break;
            case MotionEvent.ACTION_DOWN:
                // user releases touch
                player.setBoosting();

                break;

        }
        return true;
    }
}

