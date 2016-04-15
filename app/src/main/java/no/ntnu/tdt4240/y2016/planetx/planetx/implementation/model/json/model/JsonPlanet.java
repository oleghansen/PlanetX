package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonPlanet extends JsonEntity {
    private int size = 0;
    private boolean normal = false;

    public JsonPlanet(JSONObject json) throws JSONException {
        super(json);
        size = json.getInt("size");
        normal = json.getBoolean("normal");
    }

    public int getSize() {
        return size;
    }

    public boolean isNormal() {
        return normal;
    }
}
