package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders on 15.04.2016.
 */
public abstract class JsonEntity {
    protected double posx = 0.0, posy = 0.0;

    public JsonEntity(JSONObject json) throws JSONException{
        posx = json.getDouble("posX");
        posy = json.getDouble("posY");
    }
    public double getX() {
        return posx;
    }

    public double getY() {
        return posy;
    }
}
