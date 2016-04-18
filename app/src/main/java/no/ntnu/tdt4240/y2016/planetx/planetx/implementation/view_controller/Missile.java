package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.content.Context;
import android.graphics.BitmapFactory;

import java.util.Random;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

public class Missile extends Weapon {

    private Animation explotion;
    private Random random;

    public Missile(Context context, GameModel gm) {
        super(context, gm, -1, 15, "Missile", "Standard missile, takes 15 dmg");
            random = new Random();

        setImageBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.missile));
        explotion = new Animation(BitmapFactory.decodeResource(context.getResources(), R.drawable.explosion),134,134,12,45,false,(int)this.getX(),(int)this.getY()- (100),200+ random.nextInt(100),context);
    }

    public void collides(SpaceEntity spaceEntity) {
        //super.collides(spaceEntity);
        if (spaceEntity instanceof Planet) {
            //animation
            explotion.invalidate();
            //Missile.destroy

        } else if (spaceEntity instanceof Spaceship) {
            //animation
            explotion.invalidate();
            //do damage
            ((Spaceship) spaceEntity).reduceHPwith(this.getDamage());
        }

    }
}
