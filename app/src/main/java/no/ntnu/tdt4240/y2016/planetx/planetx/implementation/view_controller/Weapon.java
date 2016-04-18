package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Canvas;
import android.os.CountDownTimer;


public abstract class Weapon extends SpaceEntity {

    private int shots;
    private double damage, velocityX = 0, velocityY = 0;
    private String name, description;
    private CountDownTimer cdt;

    public Weapon(Context context, int shots, double damage, String name, String description) {
        super(context, -1);
        this.shots = shots;
        this.damage = damage;
        this.name = name;
        this.description = description;
    }

    public void startMove(){
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
        setX(getX() + (float) velocityX);
        setY(getY()+(float)velocityY);
         //gameModel.checkCollision(this);
    }
}
