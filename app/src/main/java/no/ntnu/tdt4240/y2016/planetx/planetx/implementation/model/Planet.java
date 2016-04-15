package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.graphics.BitmapFactory;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonPlanet;


public class Planet extends SpaceObstacle {

    public static final double GRAVITY = 9.81;
    public static double RADIUS = 20;

    public Planet(Context context, double gravity) {
        super(context, gravity);
    }

    public void collides(SpaceEntity spaceO) {
        //utfør animasjon
    }

    public void setParameters(JsonPlanet json) {
        super.setParameters(json);

        int drawableId = getContext().getResources().getIdentifier(json.getImgName(), "drawable", getContext().getPackageName());
        setImageBitmap(BitmapFactory.decodeResource(getResources(), drawableId));

        setMaxWidth((int)(Planet.RADIUS * json.getSize()));
    }
}
