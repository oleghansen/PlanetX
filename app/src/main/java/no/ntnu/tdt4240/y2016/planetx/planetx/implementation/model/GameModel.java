package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.util.LayoutDirection;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.GameActivity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Spaceship;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Weapon;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.WeaponLayout;

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
    private GameActivity gameActivity;
    private ArrayList<Weapon> currentWeapon = new ArrayList<>();

    public GameModel(Context context, JsonMapReader jmr, GameActivity ga) {
        for (SpaceObstacle so : jmr.getObstacles(context, this)) {
            spaceObstacles.add(so);
        }
        gameActivity = ga;

        Spaceship sp1 = jmr.getSpaceship1(context, this);
        sp1.setHelthBar((SeekBar) gameActivity.findViewById(R.id.bar_healthbar1));
        spaceships.add(sp1);

        Spaceship sp2 = jmr.getSpaceship2(context, this);
        sp2.setHelthBar((SeekBar) gameActivity.findViewById(R.id.bar_healthbar2));
        spaceships.add(sp2);

        gravityGod = new GravityGod(spaceObstacles);
        mapView = new MapView(context, this);
        spaceship = spaceships.get(0);
    }

    //Handlers
    public void initializeMap() {
        mapView.initializeMap(getEntities());
        showLockButton();
    }

    public Spaceship getCurrentShip() {
        return this.spaceship;
    }

    public ArrayList<Spaceship> getSpaceships() {
        return spaceships;
    }

    public void click_fireButton(int power) {
        if (!turnInProgress) {
            isLocked = false;
            turnInProgress = true;
            Spaceship s = getCurrentShip();
            mapView.fireShot(s.fireShot(power));
        }
    }

    public void click_lockButton(View v) {
        removeWeaponList();
        if (!turnInProgress) {
            getCurrentShip().stopRotate();
            isLocked = true;
        }
    }

    public void touch_map(View v, MotionEvent e) {
        if (!isLocked && !turnInProgress) {
            Spaceship s = getCurrentShip();
            s.flipTowardsTouch(v, e);
        }
    }

    public void showLockButton() {
        gameActivity.findViewById(R.id.btn_lock).setVisibility(View.VISIBLE);
        gameActivity.findViewById(R.id.btn_weapon).setVisibility(View.VISIBLE);
        for (Spaceship s : spaceships) {
            s.stopRotate();
        }
        getCurrentShip().startRotate();
    }

    //Getters
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

    public LinearLayout getWeaponLayout() {
        return ((LinearLayout) gameActivity.findViewById(R.id.weapon_layout));
    }

    public ArrayList<Weapon> getCurrentWeapon() {
        return currentWeapon;
    }

    //Game logic
    public void spaceshipIsDead(Spaceship spaceship) {
        getMapView().removeAllViews();
        deleteReferences();
        if (spaceships == null || spaceships.size() < 1) {
            gameActivity.finished("Player 1");
        }
        if (spaceships.indexOf(spaceship) == 0) {
            gameActivity.finished("Player 2");
        } else {
            gameActivity.finished("Player 1");
        }

        //TODO:Start END sequence
    }

    private void deleteReferences() {
        gameActivity.deleteReferences();
        mapView = null;
        gravityGod = null;
        spaceship = null;
        gameActivity = null;
        spaceships = null;
        spaceObstacles = null;
    }

    public void checkCollision(SpaceEntity se) {
        for (SpaceObstacle soObstical : spaceObstacles) {
            if (se.collidesWith(soObstical)) {
                se.collides(soObstical);
                soObstical.collides(se);
            }
        }
        for (Spaceship spaceship : spaceships) {
            if (se.collidesWith(spaceship)) {
                se.collides(spaceship);
                spaceship.collides(se);

                if (!spaceship.isAlive()) {
                    spaceshipIsDead(spaceship);
                }
            }
        }
    }

    public void endTurn() {
        if (!currentWeapon.isEmpty()) {
            return;
        }
        try {
            if (spaceships.indexOf(spaceship) == 0) {
                spaceship = spaceships.get(1);
            } else {
                spaceship = spaceships.get(0);
            }
            turnInProgress = false;
            showLockButton();
        } catch (IndexOutOfBoundsException e) {
            Log.d("error", "Feil ved oppretting av spaceship");
        }
    }

    public void removeWeaponList() {
        getWeaponLayout().removeAllViews();
    }

    public void addToWeaponList(Weapon weapon, View.OnClickListener ocl) {
        WeaponLayout wl = new WeaponLayout(gameActivity.getApplicationContext());

        wl.setOnClickListener(ocl);

        wl.setWeaponImage(((BitmapDrawable) weapon.getDrawable()).getBitmap());

        wl.setDescription(weapon.getDescription());

        String shots = "(" + Character.toString('\u221e') + ")";
        if (weapon.getShots() >= 0) shots = "(" + weapon.getShots() + ")";
        String weaponText = weapon.getName() + " dmg:" + weapon.getDamage() + " " + shots;
        wl.setWeaponText(weaponText);

        ((LinearLayout) gameActivity.findViewById(R.id.weapon_layout)).addView(wl);
    }

    public void showWeaponList(ArrayList<Weapon> weapons, Spaceship spaceship) {
        if (!isWeaponListEmpty()) {
            removeWeaponList();
            return;
        }
        for (Weapon w : weapons) {
            if (w.getShots() != 0) {
                addToWeaponList(w, w.getOnClickListener(spaceship));
            }
        }
    }

    public boolean isWeaponListEmpty() {
        LinearLayout ll = (LinearLayout) gameActivity.findViewById(R.id.weapon_layout);
        return ll.getChildCount() == 0;

    }
}
