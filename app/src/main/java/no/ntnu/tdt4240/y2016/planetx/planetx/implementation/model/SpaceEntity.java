package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;


/**
 * Created by Ole on 02.04.2016.
 */

public abstract class SpaceEntity extends ImageView {

    public SpaceEntity(Context context) {
        super(context);
    }

    public boolean collidesWith(SpaceEntity object){
        Rect rc1 = new Rect();
        this.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        object.getDrawingRect(rc2);
        if (Rect.intersects(rc1, rc2)){
            //Bitmap bm=((BitmapDrawable) this.getDrawable()).getBitmap();
            //Bitmap bmTwo=((BitmapDrawable) object.getDrawable()).getBitmap();
            return true;
        }
        return false;
    }

    public abstract void collides(SpaceEntity object);


    }

