package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Spaceship;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Weapon;

public class GameModel {
    public static final double INIT_RADIUS = 0.1;
    public static final double UNIT_GRAVITY = 9.81;
    public static double UNIT_RADIUS = 0.0;

    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();
    private ArrayList<Spaceship> spaceships = new ArrayList<>();

    private GravityGod gravityGod;
    private MapView mapView;
    private Spaceship spaceship;
    private boolean isLocked = false;
    private boolean turnInProgress = false;
    private ImageView lockButton;

    public GameModel(Context context, JsonMapReader jmr) {
        for (SpaceObstacle so : jmr.getObstacles(context, this)) {
            spaceObstacles.add(so);
        }
        for (Spaceship sp : jmr.getSpaceships(context, this)) {
            spaceships.add(sp);
        }

        gravityGod = new GravityGod(spaceObstacles);
        mapView = new MapView(context, this);
        this.spaceship = this.spaceships.get(0);
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
        return this.spaceship;
    }

    public void click_fireButton(int progress) {
        if(!turnInProgress) {
            isLocked = false;
            this.turnInProgress = true;
            Spaceship s = getCurrentShip();
            mapView.fireTestShot(s.fireTestShot(progress));
        }
    }

    public void click_lockButton(View v) {
        if(!turnInProgress) {
            this.isLocked = true;
        }
    }

    public void touch_map(View v, MotionEvent e) {
        if (!isLocked && !turnInProgress) {
            Spaceship s = getCurrentShip();
            s.flipTowardsTouch(v, e);
        }
    }

    public static void spaceshipIsDead(Spaceship spaceship) {
        //TODO:Start END sequence
    }

    public void checkCollision (SpaceEntity se){
        for (SpaceObstacle soObstical: spaceObstacles) {
            if(se.collidesWith(soObstical)){
                se.collides(soObstical);
                soObstical.collides(se);
            }
        }
        for (Spaceship spaceship: spaceships) {
            if(se.collidesWith(spaceship)){
                se.collides(spaceship);
                spaceship.collides(se);

                Log.d("COLLISION", "Spaceentity hit! gameModel collide!!");
                if(!spaceship.isAlive())
                {
                    Toast.makeText(mapView.getContext(), "Game over!", Toast.LENGTH_LONG).show();
                    //TODO: FINISH activity and take to "show-winner-celebration"-screen or something like that
                }
            }
        }
    }

    public void endTurn(){
        try
        {
            if (spaceships.indexOf(this.spaceship) == 0) {
                this.spaceship = this.spaceships.get(1);
            } else {
                this.spaceship = this.spaceships.get(0);
            }
            this.turnInProgress = false;
            mapView.showLockButton();
        }
        catch (IndexOutOfBoundsException e){
            Log.d("error", "Feil ved oppretting av spaceship");
        }
    }

    public void setLockButton(ImageView lockButton) {
        this.lockButton = lockButton;
    }

    public void showLockButton() {
        this.lockButton.setVisibility(View.VISIBLE);
    }
}
