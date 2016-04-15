package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Map;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;


import com.google.android.gms.*;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

/**
 * Created by Ole on 11.04.2016.
 */
public class GameActivity extends AppMenu {
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        String mapName = (String) i.getSerializableExtra("MapName");
        JsonMapReader jmr = getJsonMapByName(mapName);

        if(jmr == null){
            // TODO: Map med det navnet finnes ikke, gi feilmelding og gå til MenuActivity
        }

        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_layout);
        Map map = new Map(this);
        map.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));
        rl.addView(map);

        map.initializeMap(jmr);
    }

    private JsonMapReader getJsonMapByName(String mapName) {

        InputStream inputStream = getResources().openRawResource(R.raw.maps);
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
            return null;
        }

        try {
            JSONArray array = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < array.length(); i++) {
                if(array.getJSONObject(i).getString("name").equals(mapName)){
                    return new JsonMapReader(array.getString(i));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSONError","Error with JsonMapReader");
            return null;
        }
        return null;
    }
}
