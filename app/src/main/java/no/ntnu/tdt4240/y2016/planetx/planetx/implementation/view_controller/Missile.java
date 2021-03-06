package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.Random;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

public class Missile extends Weapon {

    public Missile(Context context, GameModel gm) {
        super(context, gm, -1, 15, "Missile", "Standard missile, takes 15 dmg");
        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.missile));
    }
}
