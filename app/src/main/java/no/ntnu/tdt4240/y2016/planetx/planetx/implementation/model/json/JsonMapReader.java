package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Map;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Spaceship;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonPlanet;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonShip;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonMapReader implements Serializable {
    private String name;
    private ArrayList<JsonPlanet> planets = new ArrayList<>();
    private JsonShip ship1;
    private JsonShip ship2;

    public JsonMapReader(String jsonString) throws JSONException {
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
                    JsonPlanet planet = new JsonPlanet(ob);
                    planets.add(planet);
                    break;
                case "wormhole":
                    break;
            }
        }

        ship1 = new JsonShip(json.getJSONObject("ship1"));
        ship2 = new JsonShip(json.getJSONObject("ship2"));
    }

    public String getName() {
        return name;
    }

    public Spaceship getShip1(Map map) {
        return null;
    }

    public Spaceship getShip2(Map map) {
        return null;
    }

    public ArrayList<SpaceObstacle> getObstacles(Map map){
        return null;
    }
}
