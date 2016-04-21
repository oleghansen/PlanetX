package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.CountDownTimer;
import android.widget.Toast;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

/**
 * Created by Anders on 21.04.2016.
 */
public class ClusterBomb extends Weapon {
    private ArrayList<Missile> missiles = new ArrayList<>();
    private static final double RAD_DIF = 0.26; //15 deg

    public ClusterBomb(Context context, GameModel gm, int shots) {
        super(context, gm, shots, 15, "Cluster bomb", "Fires three missiles");
        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.missile));

        fireMultiple = true;

        missiles.add(new Missile(context, gameModel));
        missiles.add(new Missile(context, gameModel));
        missiles.add(new Missile(context, gameModel));
    }

    @Override
    public void setX(float x) {
        for (Missile m : missiles) {
            m.setX(x);
        }
    }

    @Override
    public void setY(float y) {
        for (Missile m : missiles) {
            m.setY(y);
        }
    }

    @Override
    public void setVelocityX(double velocity) {
        double power = 0;
        if (velocity != 0 && Math.cos(startRad) != 0) {
            power = velocity / Math.cos(startRad);
        }
        missiles.get(0).velocityX = (power * Math.cos(startRad + RAD_DIF)) * missiles.get(0).getSpeedFactor();
        missiles.get(1).velocityX = velocity * missiles.get(1).getSpeedFactor();
        missiles.get(2).velocityX = (power * Math.cos(startRad - RAD_DIF)) * missiles.get(2).getSpeedFactor();
    }

    @Override
    public void setVelocityY(double velocity) {
        double power = 0;
        if (velocity != 0 && Math.sin(startRad) != 0) {
            power = velocity / Math.sin(startRad);
        }
        missiles.get(0).velocityY = (power * Math.sin(startRad + RAD_DIF)) * missiles.get(0).getSpeedFactor();
        missiles.get(1).velocityY = velocity * missiles.get(1).getSpeedFactor();
        missiles.get(2).velocityY = (power * Math.sin(startRad - RAD_DIF)) * missiles.get(2).getSpeedFactor();
    }

    @Override
    public ArrayList<Weapon> getWeapon() {
        ArrayList<Weapon> weps = new ArrayList<>();
        for (Missile m : missiles) {
            weps.add(m);
        }
        return weps;
    }
}
