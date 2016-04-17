package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;


public abstract class SpaceObstacle extends SpaceEntity {

    public static final double GRAVITY = 9.81;
    public static double RADIUS = 20;

    protected double gravity;


    public SpaceObstacle(Context context, double grav, double radius) {
        super(context, radius);
        gravity = grav;
    }

    public void setGravity(double x) {
        this.gravity = x;
    }

    public double getGravity() {
        return this.gravity;
    }
}

