package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.media.Image;
import android.os.PowerManager;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonShip;


public class Spaceship extends SpaceEntity {

    private int healthPoints;
    private ArrayList<Weapon> weapons;
    private double firDirX;
    private double firDirY;
    private double firPower;

    public Spaceship(Context context,int healthPoints, ArrayList<Weapon> weapons){
        super(context);
        this.healthPoints = healthPoints;
        this.weapons = weapons;
    }
    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }
    public void collides(SpaceEntity spaceEntity) {

    }
    public double getFirDirX(){
        return firDirX;
    }
    public double getFirDirY() {
        return firDirY;
    }
    public double getFirPower() {
        return firPower;
    }


}
