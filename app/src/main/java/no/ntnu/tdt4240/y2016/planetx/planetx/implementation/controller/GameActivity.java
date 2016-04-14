package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.os.Bundle;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Ole on 11.04.2016.
 */
public class GameActivity extends AppMenu {
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
        setContentView(R.layout.activity_game);
    }
}
