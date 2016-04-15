package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.media.Image;

/**
 * Created by Ole on 02.04.2016.
 */
public abstract class SpaceObstacle extends SpaceEntity{
    private double gravity;
    public SpaceObstacle(Context context, double grav){
        super(context);
        gravity = grav;
    }

    public void setGravity(double x){
        this.gravity = x;
    }
    public double getGravity(){
        return this.gravity;
    }

}

