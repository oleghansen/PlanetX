package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonWormhole extends JsonEntity {
    double posx2, posy2;

    public JsonWormhole(JSONObject json, int width, int height) throws JSONException {
        super(json, width, height);
        posx2 = json.getInt("posX2");
        posy2 = json.getInt("posY2");
    }

    public double getPosx2() {
        return posx2;
    }

    public double getPosy2() {
        return posy2;
    }
}
