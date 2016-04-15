package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonPlanet;

/**
 * Created by Ole on 31.03.2016.
 */
public class Map extends View {
    private String name;
    private ArrayList<Spaceship> spaceships;
    private ArrayList<SpaceObstacle> spaceObstacles;

    public Map(Context context) {
        super(context);
    }
    public void readJson(JsonMapReader json){
        name = json.getName();
//        spaceships.add(new Spaceship(json.getShip1()));
//        spaceships.add(new Spaceship(json.getShip2()));
        for(JsonPlanet jp: json.getPlanets()){
//            spaceObstacles.add(new Planet(jp));
        }
    }

    public String getName(){
        return name;
    }
}
