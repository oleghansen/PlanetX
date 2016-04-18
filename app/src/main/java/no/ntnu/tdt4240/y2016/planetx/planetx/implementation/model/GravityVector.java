package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

/**
 * Created by Anders on 18.04.2016.
 */
public class GravityVector {
    private double X = 0, Y = 0;

    public double getX() {
        return X;
    }

    public double getY() {
        return Y;
    }

    public void add(GravityVector v) {
        X += v.getX();
        Y += v.getY();
    }

    public void add(double x, double y) {
        X += x;
        Y += y;
    }
}
