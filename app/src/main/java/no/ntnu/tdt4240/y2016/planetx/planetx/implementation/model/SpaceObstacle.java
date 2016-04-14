package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

/**
 * Created by Ole on 02.04.2016.
 */
public abstract class SpaceObstacle extends SpaceEntity{
    private double gravity;

    public void setGravity(double x){
        this.gravity = x;
    }
    public double getGravity(){
        return this.gravity;
    }

}

