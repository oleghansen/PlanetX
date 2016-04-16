package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.graphics.Matrix;
import android.media.Image;
import android.os.PowerManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonShip;


public class Spaceship extends SpaceEntity {

    private int healthPoints;
    private ArrayList<Weapon> weapons;
    private double firDirX;
    private double firDirY;
    private double firPower;

    public Spaceship(Context context, double radius, int healthPoints, ArrayList<Weapon> weapons) {
        super(context, radius);
        this.healthPoints = healthPoints;
        this.weapons = weapons;
    }

    public ArrayList<Weapon> getWeapons() {
        return weapons;
    }

    public void collides(SpaceEntity spaceEntity) {

    }

    public double getFirDirX() {
        return firDirX;
    }

    public double getFirDirY() {
        return firDirY;
    }

    public double getFirPower() {
        return firPower;
    }


    private float flipDeg = 0;

    public void flipTowardsTouch(View v, MotionEvent e) {
        double difX = e.getX() - getCenterX();
        double difY = e.getY() - getCenterY();
        double angle = Math.atan(difY / difX) * 360 / (2 * 3.14) + 90;
        if (difX < 0) angle += 180;

        Matrix matrix = new Matrix();
        matrix.preRotate((float) angle, getWidth() / 2, getHeight() / 2);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setImageMatrix(matrix);
    }
}
