package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

/**
 * Created by Ole on 19.04.2016.
 */
public class Trajectory{
    private float x1;
    private float x2;
    private float y1;
    private float y2;

    public Trajectory(float x1, float y1, float x2, float y2)
    {
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }

    public float getX1()
    {
        return x1;
    }

    public float getY1()
    {
        return y1;
    }

    public float getX2()
    {
        return x2;
    }

    public float getY2()
    {
        return y2;
    }

    public String toString()
    {
        return "x1: " + x1 + " y1: " + y1 + " x2: " + x2 + " y2: " + y2;
    }
}
