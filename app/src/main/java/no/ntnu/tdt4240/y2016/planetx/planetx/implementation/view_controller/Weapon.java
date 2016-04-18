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

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.SoundManager;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityGod;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GravityVector;

public abstract class Weapon extends SpaceEntity {

    private int shots;
    private double damage, velocityX = 0, velocityY = 0;
    private String name, description;
    private CountDownTimer cdt;
    private GameModel gameModel;
    private Animation explotion;

    public Weapon(Context context, GameModel gm, int shots, double damage, String name, String description) {
        super(context, gm, -1);
        this.shots = shots;
        this.damage = damage;
        this.name = name;
        this.description = description;
        this.gameModel = gm;
    }

    public void startMove() {
        final Weapon w = this;
        cdt = new CountDownTimer(5000, 10) {
            public void onTick(long l) {
                w.invalidate();
            }

            public void onFinish() {
                explode();
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

    public double getDamage() {
        return this.damage;
    }

    @Override
    public void collides(SpaceEntity se) {
        cdt.cancel();
        setImageBitmap(null);
        this.explode();
    }

    public void explode() {
        SoundManager.getInstance().playSoundEffectExplosion(this.getContext());

        explotion = new Animation(getContext(), BitmapFactory.decodeResource(getResources(), R.drawable.explosion), 201, 201, 12, 45, 20, 20);
        RelativeLayout.LayoutParams lp =
                new RelativeLayout.LayoutParams(201 ,201);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.CENTER_VERTICAL);

        explotion.setLayoutParams(lp);
        gameModel.getMapView().addView(explotion);
        explotion.startAnimation();
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        GravityVector gv = gameModel.getGravityGod().getGravityVector((int) getCenterX(), (int) getCenterY());
        velocityX += gv.getX();
        velocityY += gv.getY();
        Log.d("GravityVector", "Velocity: " + velocityX + ", " + velocityY);
        Log.d("GravityVector", "Gravity: " + gv.getX() + ", " + gv.getY());

        setX(getX() + (float) velocityX);
        setY(getY() + (float) velocityY);
        gameModel.checkCollision(this);
    }
}
