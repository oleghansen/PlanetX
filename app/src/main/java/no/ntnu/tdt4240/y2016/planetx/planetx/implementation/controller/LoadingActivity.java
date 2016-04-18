package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.os.Bundle;

import android.view.View;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.example.games.basegameutils.BaseGameUtils;

import android.view.Window;


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
        goTo(MenuActivity.class);


    }


}
