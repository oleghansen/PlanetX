package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Spaceship;


public class GameController {
    private GameModel gameModel;

    public GameController(GameModel gameModel) {
        this.gameModel = gameModel;
    }

    private boolean isLocked = false;

    public void touch_map(View v, MotionEvent e) {
        if (isLocked) {
            return;
        }
        //TODO: Only flip current ship!);
        ArrayList<Spaceship> ships = gameModel.getShips();
        for (Spaceship s : ships) {
            s.flipTowardsTouch(v, e);
        }
    }

    public void click_fireButton(View v) {
        isLocked = false;
        gameModel.getMapView().showLockButton();
        ArrayList<Spaceship> ships = gameModel.getShips();
        gameModel.getMapView().fireTestShot(ships.get(0).fireTestShot(1));
        gameModel.getMapView().fireTestShot(ships.get(1).fireTestShot(100));
    }

    public void click_lockButton(View v) {
        isLocked = true;
        gameModel.getMapView().showFireButton();
    }
}
