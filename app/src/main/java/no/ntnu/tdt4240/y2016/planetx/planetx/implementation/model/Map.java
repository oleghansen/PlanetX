package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonPlanet;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.model.JsonShip;

/**
 * Created by Ole on 31.03.2016.
 */
public class Map extends RelativeLayout {
    private String name;
    private ArrayList<Spaceship> spaceships = new ArrayList<>();
    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();

    public Map(Context context) {
        super(context);
    }

    public void initializeMap(JsonMapReader jrm){
        for(SpaceObstacle so: jrm.getObstacles(this)){
            spaceObstacles.add(so);
//            addToView(so);
        }

        Spaceship ship1 = jrm.getShip1(this);
        Spaceship ship2 = jrm.getShip2(this);

        spaceships.add(ship1);
//        addToView(ship1);
        spaceships.add(ship2);
//        addToView(ship2);


        ImageView img = new ImageView(this.getContext());
        img.setX(50);
        img.setY(50);
        img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.earth));
        img.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.addView(img);
    }

    private void addToView(SpaceEntity se) {
//        se.setLayoutParams(new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT));
//        this.addView(se);
    }

    public String getName(){
        return name;
    }
}
