package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.media.Image;
import android.widget.ImageView;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonShip;


public abstract class SpaceEntity extends ImageView {

    protected double radius;

    public SpaceEntity(Context context, double radius) {
        super(context);
        this.radius = radius;
        setAdjustViewBounds(true);
        setMaxWidth((int) (radius * 2));
    }

    public boolean collidesWith(SpaceEntity object) {
        Rect rc1 = new Rect();
        this.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        object.getDrawingRect(rc2);
        if (Rect.intersects(rc1, rc2)) {
            //Bitmap bm=((BitmapDrawable) this.getDrawable()).getBitmap();
            //Bitmap bmTwo=((BitmapDrawable) object.getDrawable()).getBitmap();
            return true;
        }
        return false;
    }

    public abstract void collides(SpaceEntity object);

    public void setParameters(JsonEntity json) {
        setX((float) json.getX());
        setY((float) json.getY());
    }

    public void setRadius(double r) {
        radius = r;
    }

    public double getRadius() {
        return radius;
    }

    public double getCenterX() {
        return getX() + getWidth() / 2;
    }

    public double getCenterY() {
        return getY() + getHeight() / 2;
    }
}

