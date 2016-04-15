package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;

public class Missile extends Weapon{

    public Missile(Context context,double dmg, String name,String description) {
        super(context,dmg,name,description);

    }
    public void collides (SpaceEntity spaceEntity) {

        if(spaceEntity instanceof Planet) {
            //animation
            //Missile.destroy
        }else if(spaceEntity instanceof Spaceship) {
            //animation
            //do damage
            //check if player dead ?

        }
    }

}
