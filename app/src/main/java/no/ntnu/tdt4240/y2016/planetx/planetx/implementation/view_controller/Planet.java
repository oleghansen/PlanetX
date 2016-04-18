package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.content.Context;
import android.graphics.BitmapFactory;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonPlanet;


public class Planet extends SpaceObstacle {

    public Planet(Context context, GameModel gm, double gravity, double radius) {
        super(context, gm, gravity, radius);
    }

    public void collides(SpaceEntity spaceO) {
        //utfør animasjon
    }

    public void setParameters(JsonPlanet json) {
        super.setParameters(json);

        int drawableId = getContext().getResources().getIdentifier(json.getImgName(), "drawable", getContext().getPackageName());
        setImageBitmap(BitmapFactory.decodeResource(getResources(), drawableId));

        setMaxWidth((int) (radius * 2));
    }
}
