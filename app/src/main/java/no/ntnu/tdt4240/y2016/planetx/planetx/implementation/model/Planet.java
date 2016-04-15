package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.media.Image;

/**
 * Created by Ole on 02.04.2016.
 */
public class Planet extends SpaceObstacle {

    public Planet(Context context, double gravity){
        super(context, gravity);
    }

    public void collides(SpaceEntity spaceO){
        //utfør animasjon
    }

}
