package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.*;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Bazooka;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Lazer;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Missile;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Planet;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Spaceship;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Weapon;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonMapReader implements Serializable {
    private int width, height;

    private GameModel gameModel;

    private String name;
    private ArrayList<JsonWeapon> weapons = new ArrayList<>();
    private ArrayList<JsonPlanet> planets = new ArrayList<>();
    private ArrayList<JsonWormhole> wormholes = new ArrayList<>();
    private JsonShip ship1;
    private JsonShip ship2;

    /*
     * Parsing JSON
     */
    public JsonMapReader(String jsonString, int width, int height) throws JSONException {
        this.width = width;
        this.height = height;
        GameModel.UNIT_RADIUS = GameModel.INIT_RADIUS * height;

        JSONObject json = new JSONObject(jsonString);
        parseJson(json);
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

    private ArrayList<Weapon> getWeaponList(Context context, GameModel gm) {
        ArrayList<Weapon> weps = new ArrayList<>();
        for (JsonWeapon w : weapons) {
            switch (w.getType()) {
                case "bazooka":
                    Bazooka b = new Bazooka(context, gm, w.getShots());
                    weps.add(b);
                    break;
                case "lazer":
                    Lazer l = new Lazer(context, gm, w.getShots());
                    weps.add(l);
                    break;

                default: //missile
                    Missile m = new Missile(context, gm);
                    weps.add(m);
                    break;
            }
        }
        return weps;
    }

    /*
     * Getters
     */
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getName() {
        return name;
    }

    public ArrayList<SpaceObstacle> getObstacles(Context context, GameModel gm) {
        ArrayList<SpaceObstacle> obstacles = new ArrayList<>();
        for (JsonPlanet p : this.planets) {
            double gravity = GameModel.UNIT_GRAVITY * p.getSize();
            double radius = GameModel.UNIT_RADIUS * p.getSize();

            Planet planet = new Planet(context, gm, gravity, radius);
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

    public ArrayList<Spaceship> getSpaceships(Context context, GameModel gm) {
        ArrayList<Spaceship> list = new ArrayList<>();
        list.add(getShip(context, gm, true));
        list.add(getShip(context, gm, false));
        return list;
    }

    private Spaceship getShip(Context context, GameModel gm, boolean shipOne) {
        JsonShip thisShip = ship1;
        Bitmap b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship);

        if (!shipOne) {
            thisShip = ship2;
            b = BitmapFactory.decodeResource(context.getResources(), R.drawable.ship2);
        }

        ArrayList<Weapon> weaponList = getWeaponList(context, gm);

//        double radius = width * 0.05;
        double radius = -1;
        Spaceship ship = new Spaceship(context, gm, radius, 100, weaponList);
        ship.setParameters(thisShip);
        ship.setImageBitmap(b);

        return ship;
    }

    /*
     * Get list of names of maps from JSON
     */
    public static ArrayList<String> getMapList(Context context) {
        ArrayList<String> mapList = new ArrayList<>();

        InputStream inputStream = context.getResources().openRawResource(R.raw.maps);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JSONError", "Error with StreamReader");
            return mapList;
        }

        try {
            JSONArray array = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < array.length(); i++) {
                mapList.add(array.getJSONObject(i).getString("name"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSONError", "Error with JsonMapReader");
        }

        return mapList;
    }

    /*
     * Return the JsonMapReader object used for the specific map name
     */
    public static JsonMapReader getMapReader(String mapName, Context context, WindowManager windowManager) {

        InputStream inputStream = context.getResources().openRawResource(R.raw.maps);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Read from maps.json file.
        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JSONError", "Error with StreamReader");
            return null;
        }

        // Find the map by name, and return object
        try {
            JSONArray array = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < array.length(); i++) {
                if (array.getJSONObject(i).getString("name").equals(mapName)) {

                    Display display = windowManager.getDefaultDisplay();
                    Point size = new Point();
                    display.getSize(size);
                    int width = size.x;
                    int height = size.y;
                    return new JsonMapReader(array.getString(i), width, height);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSONError", "Error with JsonMapReader");
            return null;
        }
        return null;
    }

    public Spaceship getSpaceship1(Context context, GameModel gameModel) {
        return getShip(context, gameModel, true);
    }

    public Spaceship getSpaceship2(Context context, GameModel gameModel) {
        return getShip(context, gameModel, false);
    }
}
