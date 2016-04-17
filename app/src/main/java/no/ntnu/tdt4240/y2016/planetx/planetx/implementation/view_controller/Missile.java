package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.content.Context;
import android.graphics.BitmapFactory;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;

public class Missile extends Weapon {

    public Missile(Context context) {
        super(context, -1, 15, "Missile", "Standard missile, takes 15 dmg");
        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.missile));
    }

    public void collides(SpaceEntity spaceEntity) {

        if (spaceEntity instanceof Planet) {
            //animation
            //Missile.destroy
        } else if (spaceEntity instanceof Spaceship) {
            //animation
            //do damage
            //check if player dead ?
        }
    }

}
