package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;


public abstract class Weapon extends SpaceEntity {

    private int shots;
    private double damage, velocityX = 0, velocityY = 0;
    private String name, description;

    public Weapon(Context context, int shots, double damage, String name, String description) {
        super(context);
        this.shots = shots;
        this.damage = damage;
        this.name = name;
        this.description = description;
    }

    public void setVelocityX(double velocity) {
        velocityX = velocity;
    }

    public double getVelocityX() {
        return velocityX;
    }

    public void setVelY(double velocity) {
        velocityY = velocity;
    }

    public double getVelocityY() {
        return velocityY;
    }


}
