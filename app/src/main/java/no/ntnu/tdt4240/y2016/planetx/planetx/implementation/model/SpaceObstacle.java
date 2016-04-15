package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

/**
 * Created by Ole on 02.04.2016.
 */
public abstract class SpaceObstacle extends SpaceEntity{

    private double gravity;

    public SpaceObstacle(double gp) {
        gravity = gp;
    }


    public void setGravity(double gp) {
        this.gravity = gp;
    }
    public double getGravity() {
        return this.gravity;
    }

}
