package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;


import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.View.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.GameController;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

public class GameModel {
    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();
    private ArrayList<Spaceship> spaceships = new ArrayList<>();

    private MapView mapView;

    public GameModel(Context context, JsonMapReader jmr) {


        for (SpaceObstacle so : jmr.getObstacles(context)) {
            spaceObstacles.add(so);
        }
        for (Spaceship sp : jmr.getSpaceships(context)) {
            spaceships.add(sp);
        }

        GameController gameController = new GameController(this);
        mapView = new MapView(context, gameController);
    }

    public MapView getMapView() {
        return mapView;
    }

    public void initializeMap() {
        ArrayList<SpaceEntity> entities = new ArrayList<>();
        entities.addAll(spaceObstacles);
        entities.addAll(spaceships);
        mapView.initializeMap(entities);
    }

    public ArrayList<Spaceship> getShips() {
        return spaceships;
    }
}
