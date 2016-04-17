package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders on 16.04.2016.
 */
public class JsonWeapon {
    private String type;
    private int shots;
    public JsonWeapon(JSONObject we) throws JSONException{
        type = we.getString("type");
        shots = we.getInt("shots");
    }
    public String getType(){
        return type;
    }
    public int getShots(){
        return shots;
    }
}
