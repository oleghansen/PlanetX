package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.os.PowerManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ole on 31.03.2016.
 */
public class Spaceship {

    private int healthPoints;
    private ArrayList<Weapon> weapons;
    private double firDirX;
    private double firDirY;
    private double firPower;

    public Spaceship(int healthPoints, ArrayList<Weapon> weapons){
        this.healthPoints = healthPoints;
        this.weapons = weapons;
    }
    public ArrayList<Weapon> getWeapons() {
        return weapons;
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
