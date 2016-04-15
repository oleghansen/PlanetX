package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

/**
 * Created by Ole on 02.04.2016.
 */
public abstract class SpaceEntity{

    public abstract void collides(SpaceEntity other);

    public boolean colliesWith() {
        //check
        return true;

    }
}
