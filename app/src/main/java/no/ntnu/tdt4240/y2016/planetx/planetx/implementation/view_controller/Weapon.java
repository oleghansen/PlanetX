package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityGod;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityVector;

public abstract class Weapon extends SpaceEntity {

    private int shots;
    private double damage, velocityX = 0, velocityY = 0;
    private String name, description;
    private CountDownTimer cdt;

    public Weapon(Context context, GameModel gm, int shots, double damage, String name, String description) {
        super(context, gm, -1);
        this.shots = shots;
        this.damage = damage;
        this.name = name;
        this.description = description;
    }

    public void startMove() {
        final Weapon w = this;
        cdt = new CountDownTimer(900000, 10) {
            public void onTick(long l) {
                w.invalidate();
            }

            public void onFinish() {
                w.invalidate();
            }
        }.start();
    }

    public void setVelocityX(double velocity) {
        velocityX = velocity;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelocityY(double velocity) {
        velocityY = velocity;
    }

    public double getVelocityY() {
        return velocityY;
    }

    @Override
    public void collides(SpaceEntity se){
        cdt.cancel();
        setImageBitmap(null);
        //animation
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GravityVector gv = gameModel.getGravityGod().getGravityVector((int) getCenterX(), (int) getCenterY());
        velocityX += gv.getX();
        velocityY += gv.getY();
        Log.d("GravityVector","Velocity: "+velocityX+", "+velocityY);
        Log.d("GravityVector", "Gravity: " + gv.getX() + ", " + gv.getY());

        setX(getX() + (float) velocityX);
        setY(getY() + (float) velocityY);
    }
}
