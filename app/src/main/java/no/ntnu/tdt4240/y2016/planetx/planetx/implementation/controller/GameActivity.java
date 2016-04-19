package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

/**
 * Created by Ole on 11.04.2016.
 */
public class GameActivity extends AppMenu {
    private GameModel gameModel;
    private final Handler handler = new Handler();
    private final int powerBarDelay = 10;
    private int addProgress = 3;
    private final Runnable runnable = new Runnable() {
        public void run() {
            incrementPowerbar();
            handler.postDelayed(this, powerBarDelay);
        }
    };

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

        gameModel = new GameModel(getApplicationContext(), jmr);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game_content_layout);
        gameModel.getMapView().setParentLayout(rl);
        rl.addView(gameModel.getMapView());
        gameModel.initializeMap();

        SeekBar seekBar = (SeekBar) findViewById(R.id.bar_powerbar);
        seekBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        seekBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SoundManager.getInstance().isMusicMuted()) {
            SoundManager.getInstance().playInGameSong(this);
        }
    }

    public void click_fire(View view) {
        findViewById(R.id.btn_lock).setVisibility(View.VISIBLE);
        findViewById(R.id.btn_fire).setVisibility(View.INVISIBLE);
        findViewById(R.id.bar_powerbar).setVisibility(View.INVISIBLE);

        stopPowerbar();

        SeekBar powerbar = ((SeekBar) findViewById(R.id.bar_powerbar));
        gameModel.click_fireButton(powerbar.getProgress());
        powerbar.setProgress(0);


//        gameModel.getMapView().click_fire();
    }

    public void click_lock(View view) {
        findViewById(R.id.btn_lock).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_fire).setVisibility(View.VISIBLE);
        findViewById(R.id.bar_powerbar).setVisibility(View.VISIBLE);

        startPowerbar();

        gameModel.click_lockButton(view);

//        gameModel.getMapView().click_lock();
    }

    private void incrementPowerbar() {
        SeekBar seekbar = (SeekBar) findViewById(R.id.bar_powerbar);
        int next = seekbar.getProgress();
        if (next > 99 || next < 1) {
            addProgress *= -1;
        }
        next = next + addProgress;
        if (next > 99) {
            next = 100;
        }
        if (next < 1) {
            next = 0;
        }
        seekbar.setProgress(next);
    }

    private void startPowerbar() {
        handler.postDelayed(runnable, powerBarDelay);
    }

    private void stopPowerbar() {
        handler.removeCallbacks(runnable);
    }
}