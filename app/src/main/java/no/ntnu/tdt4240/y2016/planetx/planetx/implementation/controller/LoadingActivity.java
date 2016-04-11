package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.os.Bundle;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Ole on 31.03.2016.
 */
public class LoadingActivity extends AppMenu {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /* Use this space to do all loading/preparation needed before accessing main menu */
        //
        //
        //
        //
        //
        //
        //
        goTo(MenuActivity.class);
    }
}
