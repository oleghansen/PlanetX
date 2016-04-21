package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import com.google.android.gms.games.Game;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;


public class Spaceship extends SpaceEntity implements View.OnClickListener {

    private int healthPoints;
    private ArrayList<Weapon> weapons;
    private Weapon selectedWeapon;
    private double fireAngle = 0;
    private SeekBar helthBar;

    private boolean isRotating = false;
    private final int spinDelay = 45;
    private final int spinDeg = 5;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            incrementAngle();
            handler.postDelayed(this, spinDelay);
        }
    };

    public Spaceship(Context context, GameModel gm, double radius, int healthPoints, ArrayList<Weapon> weapons) {
        super(context, gm, radius);
        setOnClickListener(this);

        this.healthPoints = healthPoints;
        this.weapons = weapons;
        this.radius = radius;

        if (weapons.size() > 0) {
            selectedWeapon = weapons.get(0);
        }
    }

    //Handlers
    @Override
    public void onClick(View v) {
        if (!isRotating) return;

        gameModel.showWeaponList(weapons, this);
    }

    public void setSelectedWeapon(Weapon w) {
        selectedWeapon = w;
    }

    public void startRotate() {
        isRotating = true;
        handler.postDelayed(runnable, spinDelay);
    }

    public void stopRotate() {
        isRotating = false;
        handler.removeCallbacks(runnable);
    }

    private void incrementAngle() {
        fireAngle += spinDeg;
        if (fireAngle >= 360) fireAngle -= 360;
        setAngle(fireAngle);
    }

    public void flipTowardsTouch(View v, MotionEvent e) {
        double difX = e.getX() - getCenterX();
        double difY = e.getY() - getCenterY();
        fireAngle = Math.atan(difY / difX) * 360 / (2 * 3.14) + 90;
        if (difX < 0) fireAngle += 180;
        setAngle(fireAngle);
    }

    public void setAngle(double angle) {
        fireAngle = angle;
        Matrix matrix = new Matrix();
        matrix.preRotate((float) fireAngle, getWidth() / 2, getHeight() / 2);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setImageMatrix(matrix);
    }

    //Game logic
    public void collides(SpaceEntity spaceEntity) {
        if (spaceEntity instanceof Weapon) {
            this.damageSpaceship(((Weapon) spaceEntity).getDamage());
        }
        //  healthPoints = setHealthPoints(healthPoints-damage);
    }

    public boolean isAlive() {

        if (healthPoints <= 0) {
            return false;
        } else return true;
    }

    public void damageSpaceship(int damage) {
        healthPoints = getHealthPoints() - damage;
        helthBar.setProgress(healthPoints);
    }

    public ArrayList<Weapon> fireShot(int power) {
        double pow = power * 0.50;
        Weapon w;
        if (selectedWeapon != null && selectedWeapon.getShots() != 0) {
            w = selectedWeapon;
            w.setShots(w.getShots() - 1);
        } else {
            w = new Missile(getContext(), gameModel);
        }
        if (selectedWeapon.getShots() == 0) {
            if (weapons.size() > 0) {
                selectedWeapon = weapons.get(0);
            }
        }

        w.setStartRad((fireAngle - 90) * 2 * 3.14 / 360);

        w.setX((float) (getCenterX() + getWidth() * Math.cos((fireAngle - 90) * 2 * 3.14 / 360)));
        w.setY((float) (getCenterY() + getHeight() * Math.sin((fireAngle - 90) * 2 * 3.14 / 360)));

        double fireRad = (fireAngle - 90) * 2 * 3.14 / 360;
        double xVel = Math.cos(fireRad) * pow;
        double yVel = Math.sin(fireRad) * pow;

        w.setVelocityX(xVel);
        w.setVelocityY(yVel);

        return w.getWeapon();

    }

    public void setHelthBar(SeekBar helthBar) {
        this.helthBar = helthBar;
    }

    public int getHealthPoints() {
        return healthPoints;
    }
}
