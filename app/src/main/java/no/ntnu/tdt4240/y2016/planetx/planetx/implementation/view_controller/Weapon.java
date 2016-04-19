package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.SoundManager;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityGod;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityVector;

public abstract class Weapon extends SpaceEntity {

    private int shots, damage;
    private double velocityX = 0, velocityY = 0;
    private String name, description;
    private CountDownTimer cdt;
    private GameModel gameModel;
    private Animation explotion;
    
    public Weapon(Context context, GameModel gm, int shots, int damage, String name, String description) {
        super(context, gm, -1);
        this.shots = shots;
        this.damage = damage;
        this.name = name;
        this.description = description;
        this.gameModel = gm;
    }

    public void startMove() {
        final Weapon w = this;
        cdt = new CountDownTimer(5000, 20) {
            public void onTick(long l) {
                w.invalidate();
            }

            public void onFinish() {
                explode();
            }
        }.start();
    }

    public int getDamage()
    {
        return damage;
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
    public void collides(SpaceEntity se) {
        cdt.cancel();
        Log.d("COLLISION", "Weapon() collide!!");
        this.explode();
    }

    public void explode() {
        gameModel.getMapView().removeView(this);

        SoundManager.getInstance().playSoundEffectExplosion(this.getContext());

        explotion = new Animation(gameModel.getMapView(), BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 12, 45, (int)getX()-100, (int)getY()-100);
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT ,RelativeLayout.LayoutParams.WRAP_CONTENT);
        explotion.setLayoutParams(lp);
        gameModel.getMapView().addView(explotion);
        explotion.startAnimation();
        gameModel.endTurn();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GravityVector gv = gameModel.getGravityGod().getGravityVector((int) getCenterX(), (int) getCenterY());
        velocityX += gv.getX();
        velocityY += gv.getY();
        Log.d("GravityVector", "Velocity: " + velocityX + ", " + velocityY);
        Log.d("GravityVector", "Gravity: " + gv.getX() + ", " + gv.getY());

        float startx = getX();
        float starty = getY();
        float endx = getX() + (float) velocityX;
        float endy = getY() + (float) velocityY;
        addTrajectory(startx, starty, endx, endy);

        setX(endx);
        setY(endy);

        gameModel.checkCollision(this);
    }

    public void addTrajectory(float x1, float y1, float x2, float y2)
    {
        gameModel.getMapView().addTrajectory(x1, y1, x2, y2);
    }
}
