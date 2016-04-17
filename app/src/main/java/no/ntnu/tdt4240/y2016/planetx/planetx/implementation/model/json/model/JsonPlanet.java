package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Anders on 15.04.2016.
 */
public class JsonPlanet extends JsonEntity {
    private double size = 0;
    private boolean normal = false;
    private String imgName = "";

    public JsonPlanet(JSONObject json, int width, int height) throws JSONException {
        super(json, width, height);
        size = json.getDouble("size");
        normal = json.getBoolean("normal");
        imgName = json.getString("img");
    }

    public double getSize() {
        return size;
    }
    public String getImgName(){
        return imgName;
    }

    public boolean isNormal() {
        return normal;
    }
}
