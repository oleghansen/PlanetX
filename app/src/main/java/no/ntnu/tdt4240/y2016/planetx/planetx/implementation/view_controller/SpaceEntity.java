package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonEntity;


public abstract class SpaceEntity extends ImageView {

    protected double radius;

    public SpaceEntity(Context context, double radius) {
        super(context);
        this.radius = radius;
        setAdjustViewBounds(true);
        this.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));

        if (radius > 0) {
            setMaxWidth((int) (radius * 2));
            getLayoutParams().width = (int) (radius * 2);
            getLayoutParams().height = (int) (radius * 2);
        }
    }

    public boolean collidesWith(SpaceEntity object) {
        Rect rc1 = new Rect();
        this.getDrawingRect(rc1);
        Rect rc2 = new Rect();
        object.getDrawingRect(rc2);
        if (Rect.intersects(rc1, rc2)) {
            double dist = Math.sqrt(Math.pow(this.getCenterX()-object.getCenterX(), 2) + Math.pow(this.getCenterY()-object.getCenterY(), 2));
            if(dist <= this.getRadius() + object.getRadius())
                return true;
        }
        return false;
    }

    public abstract void collides(SpaceEntity object);

    public void setParameters(JsonEntity json) {
        setX((float) json.getX());
        setY((float) json.getY());
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

