package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.View.MapView;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
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
    private GameModel gameModel;
    private GameController gameController;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_game);

        Intent i = getIntent();
        String mapName = (String) i.getSerializableExtra("MapName");

        JsonMapReader jmr = JsonMapReader.getMapReader(mapName, getApplicationContext(), getWindowManager());

        if(jmr == null){
            Toast.makeText(this, "An error occurred with the map " +mapName, Toast.LENGTH_LONG).show();
            finish();
        }

        gameModel = new GameModel(getApplicationContext(), jmr);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_layout);
        rl.addView(gameModel.getMapView());
        gameModel.initializeMap();
    }
}
