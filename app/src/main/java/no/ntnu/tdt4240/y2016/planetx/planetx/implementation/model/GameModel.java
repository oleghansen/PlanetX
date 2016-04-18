package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Spaceship;

public class GameModel {
    public static final double INIT_RADIUS = 0.1;
    public static final double UNIT_GRAVITY = 9.81;
    public static double UNIT_RADIUS = 0.0;

    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();
    private ArrayList<Spaceship> spaceships = new ArrayList<>();

    private GravityGod gravityGod;
    private MapView mapView;

    public GameModel(Context context, JsonMapReader jmr) {
        for (SpaceObstacle so : jmr.getObstacles(context, this)) {
            spaceObstacles.add(so);
        }
        for (Spaceship sp : jmr.getSpaceships(context, this)) {
            spaceships.add(sp);
        }

        gravityGod = new GravityGod(spaceObstacles);
        mapView = new MapView(context, this);
    }

    public GravityGod getGravityGod() {
        return gravityGod;
    }

    public ArrayList<SpaceEntity> getEntities() {
        ArrayList<SpaceEntity> entities = new ArrayList<>();
        entities.addAll(spaceObstacles);
        entities.addAll(spaceships);
        return entities;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void initializeMap() {
        mapView.initializeMap(getEntities());
    }

    public Spaceship getCurrentShip() {
        //TODO: Implement turn logic and return correct ship
        return spaceships.get(0);
    }

    public void click_fireButton(View v) {
        isLocked = false;
        Spaceship s = getCurrentShip();
        mapView.fireTestShot(s.fireTestShot(100));

        mapView.showLockButton();
    }

    public void click_lockButton(View v) {
        isLocked = true;
        mapView.showFireButton();
    }

    private boolean isLocked = false;

    public void touch_map(View v, MotionEvent e) {
        if (isLocked) return;
        Spaceship s = getCurrentShip();
        s.flipTowardsTouch(v, e);
    }
    public static void spaceshipIsDead(Spaceship spaceship) {
        //TODO:Start END sequence
    }
}
