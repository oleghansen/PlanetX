package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

import org.json.JSONException;
import org.json.JSONObject;


import android.util.Log;


import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.SpaceObstacle;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller.Spaceship;

public class GameModel {


    public static int fireangle;
    public static int power;
    public static int turnCounter;
    public static final double INIT_RADIUS = 0.2;

    public static final double UNIT_GRAVITY = 9.81;
    public static double UNIT_RADIUS = 0.0;


    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();
    private ArrayList<Spaceship> spaceships = new ArrayList<>();


    public static Context c;
   static JsonMapReader j;
    private MapView mapView;
    private GravityGod gravityGod;

    public GameModel(Context context, JsonMapReader jmr) {
        c=context;
        j=jmr;
        for (SpaceObstacle so : jmr.getObstacles(context, this)) {

            spaceObstacles.add(so);
        }
        for (Spaceship sp : jmr.getSpaceships(context, this)) {
            spaceships.add(sp);
        }

        gravityGod = new GravityGod(spaceObstacles);
        mapView = new MapView(context, this);
    }


    public GravityGod getGravityGod() {
        return gravityGod;
    }


    public ArrayList<SpaceEntity> getEntities() {
        ArrayList<SpaceEntity> entities = new ArrayList<>();
        entities.addAll(spaceObstacles);
        entities.addAll(spaceships);
        return entities;
    }

    public MapView getMapView() {
        return mapView;
    }

    public void initializeMap() {
        mapView.initializeMap(getEntities());
    }

    public Spaceship getCurrentShip() {
        //TODO: Implement turn logic and return correct ship
        return spaceships.get(0);
    }

    public void click_fireButton(int progress) {
        isLocked = false;
        Spaceship s = getCurrentShip();
        mapView.fireTestShot(s.fireTestShot(progress));

        mapView.showLockButton();
    }

    public void click_lockButton(View v) {
        isLocked = true;
        mapView.showFireButton();
    }

    private boolean isLocked = false;

    public void touch_map(View v, MotionEvent e) {
        if (isLocked) return;
        Spaceship s = getCurrentShip();
        s.flipTowardsTouch(v, e);
    }

    // This is the byte array we will write out to the TBMP API.
    public byte[] persist(){

        JSONObject retval = new JSONObject();

        try{
            retval.put("fireangle", fireangle);
            retval.put("power", power);
            retval.put("turnCounter", turnCounter);
        }
        catch (JSONException e){
            e.printStackTrace();
        }
        String st=retval.toString();

        return st.getBytes(Charset.forName("UTF-8"));

    }

    // Creates a new instance of GameModel
    static public GameModel unpersist(byte[] byteArray){
        if(byteArray==null){
          return null;
        }

        String st=null;

        try{
            st=new String(byteArray, "UTF-8");

        }
        catch (UnsupportedEncodingException e1){
            e1.printStackTrace();
            return null;
        }

        GameModel retVal = new GameModel(c,j);
        try{

            JSONObject obj = new JSONObject(st);

            if (obj.has("power")) {
                retVal.power = obj.getInt("power");
            }
            if (obj.has("fireangle")) {
                retVal.fireangle = obj.getInt("fireangle");
            }
            if (obj.has("turnCounter")) {
                retVal.turnCounter = obj.getInt("turnCounter");
            }
        } catch (JSONException e){
            e.printStackTrace();
        }
        return retVal;
    }
/*
    public void finishRound(){

        gach.sendThisRoundInformation(fireAngle, firePower);
    }
    public void receiveRoundData(int fireAngle, firePower){

    }
    */

    
    public void checkCollision (SpaceEntity se){
        for (SpaceObstacle soObstical: spaceObstacles) {
            if(se.collidesWith(soObstical)){
                se.collides(soObstical);
                soObstical.collides(se);
            }
        }
        for (Spaceship ssObstical: spaceships) {
            if(se.collidesWith(ssObstical)){
                se.collides(ssObstical);
                ssObstical.collides(se);
            }
        }
    }

}
