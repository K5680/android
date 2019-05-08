package com.lonestones.vesada.tutorialgame;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;

public class GameActivity extends AppCompatActivity { // public = any class can access

    // declare gameview
    private GameView gameView; // private = only this class has access

    @Override   // protected = classes in same package have access, subclasses have access
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // get display object. Display = size and density of a logical display
        Display display = getWindowManager().getDefaultDisplay();

        // get resolution
        Point size = new Point();   // point = two integer coordinates
        display.getSize(size);      // gets display size = current _app window_ size

        // init game view object
        // pass screen size to GameView constructor = gameview size is screen size
        gameView = new GameView(this, size.x, size.y);

        setContentView(gameView);
    }

    // pause game
    @Override // method overrides superclass's method, invoke the overridden method with "super"
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }

    // resume game
    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }
}
