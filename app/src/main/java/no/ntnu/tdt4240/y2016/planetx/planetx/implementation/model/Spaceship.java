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

    public Spaceship(int healthPoints, ArrayList<Weapon> weapons){
        this.healthPoints = healthPoints;
        this.weapons = weapons;
    }
}
