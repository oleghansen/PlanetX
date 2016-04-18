package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;


public class Wormhole extends SpaceObstacle {

    public Wormhole(Context context, GameModel gm, double gravity, double radius){
        super(context, gm, gravity, radius);
    }

    public void collides(SpaceEntity spaceO){
        //utfør animasjon
    }
}
