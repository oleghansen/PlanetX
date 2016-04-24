package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v4.content.ContextCompat;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

/**
 * Created by Anders on 20.04.2016.
 */
public class Lazer extends Weapon {
    public Lazer(Context context, GameModel gm, int shots) {
        super(context, gm, shots, 50, "Lazer", "Gravity has less effect on lazer");
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.lazer));
//        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.lazer));
    }


    @Override
    protected double getGravityFactor() {
        return super.getGravityFactor() / 2;
    }

    @Override
    protected double getSpeedFactor() {
        return super.getSpeedFactor();
    }
}
