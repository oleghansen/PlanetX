package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Matrix;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.google.android.gms.games.Game;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;


public class Spaceship extends SpaceEntity {

    private int healthPoints;
    private ArrayList<Weapon> weapons;

    private double fireAngle = 0;
    private SeekBar helthBar;

    private final int spinDelay = 45;
    private final int spinDeg = 5;
    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        public void run() {
            incrementAngle();
            handler.postDelayed(this, spinDelay);
        }
    };
    public void startRotate() {
        handler.postDelayed(runnable, spinDelay);
    }
    public void stopRotate(){
        handler.removeCallbacks(runnable);
    }
    private void incrementAngle() {
        fireAngle += spinDeg;
        if (fireAngle >= 360) fireAngle -= 360;
        setAngle(fireAngle);
    }


    public Spaceship(Context context, GameModel gm, double radius, int healthPoints, ArrayList<Weapon> weapons) {
        super(context, gm, radius);
        this.healthPoints = healthPoints;
        this.weapons = weapons;
        this.radius = radius;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void collides(SpaceEntity spaceEntity) {
        if (spaceEntity instanceof Weapon) {
            this.damageSpaceship(((Weapon) spaceEntity).getDamage());
        }
        //  healthPoints = setHealthPoints(healthPoints-damage);
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public boolean isAlive() {

        if (healthPoints <= 0) {
            return false;
        } else return true;
    }

    public void setHealthPoints(int hp) {
        healthPoints = hp;
    }

    public void damageSpaceship(int damage) {
        healthPoints = getHealthPoints() - damage;
        helthBar.setProgress(healthPoints);

    }

    /*
    public void reduceHPwith(double hp) {
        this.healthPoints -= hp;

        if(this.healthPoints<=0) {
            GameModel.spaceshipIsDead(this);
        }
    }
*/
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

    public Weapon fireShot(int power) {
        double pow = power * 0.50;
        Missile m = new Missile(getContext(), gameModel);

        m.setX((float) (getCenterX() + getWidth() * Math.cos((fireAngle - 90) * 2 * 3.14 / 360)));
        m.setY((float) (getCenterY() + getHeight() * Math.sin((fireAngle - 90) * 2 * 3.14 / 360)));

        double fireRad = (fireAngle - 90) * 2 * 3.14 / 360;
        double xVel = Math.cos(fireRad) * pow;
        double yVel = Math.sin(fireRad) * pow;

        m.setVelocityX(xVel);
        m.setVelocityY(yVel);
        return m;
    }

    public void setHelthBar(SeekBar helthBar) {
        this.helthBar = helthBar;
    }
}
