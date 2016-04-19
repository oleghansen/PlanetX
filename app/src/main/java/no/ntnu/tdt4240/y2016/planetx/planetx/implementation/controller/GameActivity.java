package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

/**
 * Created by Ole on 11.04.2016.
 */
public class GameActivity extends AppMenu {
    private static GameModel gameModel;

    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_game);


        Intent i = getIntent();
        String mapName = (String) i.getSerializableExtra("MapName");

        JsonMapReader jmr = JsonMapReader.getMapReader(mapName, getApplicationContext(), getWindowManager());

        if (jmr == null) {
            Toast.makeText(this, "An error occurred with the map: " + mapName, Toast.LENGTH_LONG).show();
            finish();
        }

       // GoogleApiCommunicationHandler gach =
        gameModel = new GameModel(getApplicationContext(), jmr);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.activity_layout);
        gameModel.getMapView().setParentLayout(rl);
        rl.addView(gameModel.getMapView());
        gameModel.initializeMap();
    }

    public static GameModel getModel(){
        return gameModel;

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuActivity.initializeSoundMenu(menu, this);
        return true;
    }

    public void click_toggleSound(MenuItem item) {
        item.setChecked(!item.isChecked());
        SoundManager.getInstance().muteSoundeffects();
    }

    public void click_toggleMusic(MenuItem item) {
        item.setChecked(!item.isChecked());
        SoundManager.getInstance().muteMusic();
    }
}
