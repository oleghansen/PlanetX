package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

/**
 * Created by Anders on 20.04.2016.
 */
public class Bazooka extends Weapon {
    public Bazooka(Context context, GameModel gm, int shots) {
        super(context, gm, shots, 30, "Bazooka", "Bazooka takes 30 hp of opponent");
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.bazooka));
//        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.bazooka));
    }

}
