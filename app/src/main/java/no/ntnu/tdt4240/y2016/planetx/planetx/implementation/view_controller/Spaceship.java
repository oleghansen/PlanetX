package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;


public class Spaceship extends SpaceEntity {

    private int healthPoints;
    private ArrayList<Weapon> weapons;

    private double fireAngle = 0;
    private double firDirX;
    private double firDirY;
    private double firPower;

    public Spaceship(Context context, GameModel gm, double radius, int healthPoints, ArrayList<Weapon> weapons) {
        super(context, gm, radius);
        this.healthPoints = healthPoints;
        this.weapons = weapons;
        this.radius = radius;
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

    public void flipTowardsTouch(View v, MotionEvent e) {
        double difX = e.getX() - getCenterX();
        double difY = e.getY() - getCenterY();
        fireAngle = Math.atan(difY / difX) * 360 / (2 * 3.14) + 90;
        if (difX < 0) fireAngle += 180;

        Matrix matrix = new Matrix();
        matrix.preRotate((float) fireAngle, getWidth() / 2, getHeight() / 2);
        this.setScaleType(ImageView.ScaleType.MATRIX);
        this.setImageMatrix(matrix);
    }

    public Missile fireTestShot(int power) {
        double pow = power * 0.50;
        Missile m = new Missile(getContext(), gameModel);

        m.setX((float) (getCenterX() + getWidth() * Math.cos((fireAngle - 90) * 2 * 3.14 / 360)));
        m.setY((float) (getCenterY() + getHeight() * Math.sin((fireAngle - 90) * 2 * 3.14 / 360)));

//        Matrix matrix = new Matrix();
//        matrix.preRotate((float) fireAngle, m.getWidth() / 2, m.getHeight() / 2);
//        m.setScaleType(ImageView.ScaleType.MATRIX);
//        m.setImageMatrix(matrix);

        double fireRad = (fireAngle - 90) * 2 * 3.14 / 360;
        double xVel = Math.cos(fireRad) * pow;
        double yVel = Math.sin(fireRad) * pow;

        m.setVelocityX(xVel);
        m.setVelocityY(yVel);
        return m;
    }
}
