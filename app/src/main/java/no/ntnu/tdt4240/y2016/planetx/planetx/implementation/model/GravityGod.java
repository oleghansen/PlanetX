package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import java.util.ArrayList;
import java.util.List;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;


public class GravityGod {
    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();

    public GravityGod(ArrayList<SpaceObstacle> obstacles) {
        spaceObstacles = obstacles;
    }

    public GravityVector getGravityVector(int posX, int posY) {
        GravityVector vector = new GravityVector();
        for (SpaceObstacle so : spaceObstacles) {
            vector.add(getGravityVector(so, posX, posY));
        }
        return vector;
    }

    private GravityVector getGravityVector(SpaceObstacle so, int posX, int posY) {
        GravityVector vector = new GravityVector();

        double difX = posX - so.getCenterX();
        double difY = posY - so.getCenterY();

        double dist = Math.sqrt(difX * difX + difY * difY);

        double unit_radius = dist / GameModel.UNIT_RADIUS;
        double unit_x = difX / dist;
        double unit_y = difY / dist;

        double gravity = so.getGravity() / (unit_radius);

        vector.add(-gravity * unit_x, -gravity * unit_y);

        return vector;
    }
}
