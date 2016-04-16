package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Space;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Array;
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
    private ArrayList<JsonWeapon> weapons = new ArrayList<>();

    private ArrayList<JsonPlanet> planets = new ArrayList<>();
    private ArrayList<JsonWormhole> wormholes = new ArrayList<>();
    private JsonShip ship1;
    private JsonShip ship2;
    //TODO: JsonWeapon, PER MAP?

    public JsonMapReader(String jsonString, int width, int height) throws JSONException {
        this.width = width;
        this.height = height;
        SpaceObstacle.RADIUS = SpaceObstacle.RADIUS * height / 100;

        JSONObject json = new JSONObject(jsonString);
        parseJson(json);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void parseJson(JSONObject json) throws JSONException {
        name = json.getString("name");

        JSONArray weap = json.getJSONArray("weapons");
        for (int i = 0; i < weap.length(); i++) {
            JSONObject we = weap.getJSONObject(i);
            JsonWeapon weapon = new JsonWeapon(we);
            weapons.add(weapon);
        }

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

    private ArrayList<Weapon> getWeaponList(Context context) {
        ArrayList<Weapon> weps = new ArrayList<>();
        for (JsonWeapon w : weapons) {
            switch (w.getType()) {
                case "bazooka":
//                    Bazooka b = new Bazooka(context, w.getShots());
//                    weps.add(b);
                    break;

                default: //missile
                    Missile m = new Missile(context);
                    weps.add(m);
                    break;
            }
        }
        return weps;
    }

    private Spaceship getShip(Map map, boolean shipOne) {
        JsonShip thisShip = ship1;
        if (!shipOne) {
            thisShip = ship2;
        }

        ArrayList<Weapon> weaponList = getWeaponList(map.getContext());

        double radius = width * 0.05;
        Spaceship ship = new Spaceship(map.getContext(), radius, 100, weaponList);
        Bitmap b = BitmapFactory.decodeResource(map.getResources(), R.drawable.ship);
        ship.setParameters(thisShip);
        ship.setImageBitmap(b);

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
            double gravity = SpaceObstacle.GRAVITY * p.getSize();
            double radius = SpaceObstacle.RADIUS * p.getSize() / 2;

            Planet planet = new Planet(map.getContext(), gravity, radius);
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
