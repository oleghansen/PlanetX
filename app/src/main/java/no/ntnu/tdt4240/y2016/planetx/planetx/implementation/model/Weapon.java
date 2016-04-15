package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;


public abstract class Weapon extends SpaceEntity {

    private double damage;
    private String name, description;

    public Weapon(Context context, double damage, String name, String description){
        super(context);
        this.damage = damage;
        this.name = name;
        this.description = name;
    }

}
