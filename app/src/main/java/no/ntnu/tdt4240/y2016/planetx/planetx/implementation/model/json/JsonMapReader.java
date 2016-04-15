package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json;

import android.graphics.BitmapFactory;
import android.widget.Space;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.*;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.*;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonMapReader implements Serializable {
    private int width, height;

    private String name;
    private ArrayList<JsonPlanet> planets = new ArrayList<>();
    private ArrayList<JsonWormhole> wormholes = new ArrayList<>();
    private JsonShip ship1;
    private JsonShip ship2;
    //TODO: JsonWeapon, PER MAP?

    public JsonMapReader(String jsonString, int width, int height) throws JSONException {
        this.width = width;
        this.height = height;
        Planet.RADIUS = Planet.RADIUS * width / 100;

        JSONObject json = new JSONObject(jsonString);
        parseJson(json);
    }

    private void parseJson(JSONObject json) throws JSONException {
        name = json.getString("name");
        JSONArray obst = json.getJSONArray("obstacles");
        for (int i = 0; i < obst.length(); i++) {
            JSONObject ob = obst.getJSONObject(i);
            String type = ob.getString("type");
            switch (type) {
                case "planet":
                    JsonPlanet planet = new JsonPlanet(ob, width, height);
                    planets.add(planet);
                    break;
                case "wormhole":
                    JsonWormhole wormhole = new JsonWormhole(ob, width, height);
                    wormholes.add(wormhole);
                    break;
            }
        }

        ship1 = new JsonShip(json.getJSONObject("ship1"), width, height);
        ship2 = new JsonShip(json.getJSONObject("ship2"), width, height);
    }

    public String getName() {
        return name;
    }

    private Spaceship getShip(Map map, boolean shipOne) {
        JsonShip thisShip = ship1;
        if (!shipOne) {
            thisShip = ship2;
        }

        //TODO: Get weapon list from somewhere...?
        ArrayList<Weapon> weaponList = new ArrayList<>();
        weaponList.add(new Missile(map.getContext(), 15, "Missile", "Missile"));

        Spaceship ship = new Spaceship(map.getContext(), 100, weaponList);

        ship.setParameters(thisShip);
        ship.setImageBitmap(BitmapFactory.decodeResource(ship.getResources(), R.drawable.ship));

        return ship;
    }

    public Spaceship getShip1(Map map) {
        return getShip(map, true);
    }

    public Spaceship getShip2(Map map) {
        return getShip(map, false);
    }

    public ArrayList<SpaceObstacle> getObstacles(Map map) {
        ArrayList<SpaceObstacle> obstacles = new ArrayList<>();
        for (JsonPlanet p : this.planets) {
            //TODO: Determine gravity based on planet size
            double gravity = Planet.GRAVITY * p.getSize();

            Planet planet = new Planet(map.getContext(), gravity);
            planet.setParameters(p);
            obstacles.add(planet);
        }
        for (JsonWormhole w : this.wormholes) {
            //TODO: Determine gravity for wormholes, one size?
            //TODO: fix this if we need wormole....
//            Wormhole wormhole = new Wormhole()
        }

        return obstacles;
    }
}
