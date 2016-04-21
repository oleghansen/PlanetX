package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;


public abstract class SpaceObstacle extends SpaceEntity {
    protected double gravity;

    public SpaceObstacle(Context context, GameModel gm, double gravity, double radius) {
        super(context, gm, radius);
        this.gravity = gravity;
    }

    public double getGravity() {
        return this.gravity;
    }
}

