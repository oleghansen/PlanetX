package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.media.Image;

import java.util.List;

/**
 * Created by Ole on 02.04.2016.
 */
public class Planet extends SpaceObstacle {

    private Image planetImage;

    public Planet(double gp,Image img) {
        super(gp);
        planetImage = img;
    }

}
