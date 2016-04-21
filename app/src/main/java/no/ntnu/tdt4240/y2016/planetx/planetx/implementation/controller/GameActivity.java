package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;

import java.util.Random;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

/**
 * Created by Ole on 11.04.2016.
 */
public class GameActivity extends AppMenu {
    private GameModel gameModel;
    private RelativeLayout gameLayout;
    private int[] mapBackgrounds = {R.drawable.background, R.drawable.background2, R.drawable.background3, R.drawable.background4};
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

        gameLayout = (RelativeLayout) findViewById(R.id.game_content_layout);
        gameLayout.setBackgroundResource(mapBackgrounds[new Random().nextInt(mapBackgrounds.length)]);

        Intent i = getIntent();
        String mapName = (String) i.getSerializableExtra("MapName");

        JsonMapReader jmr = JsonMapReader.getMapReader(mapName, getApplicationContext(), getWindowManager());

        if (jmr == null) {
            Toast.makeText(this, "An error occurred with the map: " + mapName, Toast.LENGTH_LONG).show();
            finish();
        }

        gameModel = new GameModel(getApplicationContext(), jmr, this);
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.game_content_layout);
        rl.addView(gameModel.getMapView());
        gameModel.initializeMap();

        initializeSeekBars();
    }

    private void initializeSeekBars() {
        SeekBar powerBar = (SeekBar) findViewById(R.id.bar_powerbar);
        powerBar.getProgressDrawable().setColorFilter(ContextCompat.getColor(this, R.color.black), android.graphics.PorterDuff.Mode.SRC_IN);
        powerBar.getThumb().setColorFilter(ContextCompat.getColor(this, R.color.black), PorterDuff.Mode.MULTIPLY);
        powerBar.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        SeekBar hpBar1 = (SeekBar) findViewById(R.id.bar_healthbar1);
        hpBar1.getProgressDrawable().setColorFilter(Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        hpBar1.getThumb().setColorFilter(Color.BLUE, PorterDuff.Mode.MULTIPLY);
        hpBar1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        SeekBar hpBar2 = (SeekBar) findViewById(R.id.bar_healthbar2);
        hpBar2.getProgressDrawable().setColorFilter(Color.MAGENTA, android.graphics.PorterDuff.Mode.SRC_IN);
        hpBar2.getThumb().setColorFilter(Color.MAGENTA, PorterDuff.Mode.MULTIPLY);
        hpBar2.setOnTouchListener(new View.OnTouchListener() {
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

    public void finished(String winner) {

        goToCelebrationScreen(Celebration.class, winner);
    }

    public void click_fire(View view) {
        findViewById(R.id.btn_lock).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_fire).setVisibility(View.INVISIBLE);
        findViewById(R.id.btn_weapon).setVisibility(View.INVISIBLE);
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

    public void click_weapon(View view) {
        gameModel.getCurrentShip().onClick(view);
    }

    public void deleteReferences() {
        gameModel = null;
        gameLayout = null;
    }

    @Override
    public void onBackPressed() {
        System.gc();
        super.onBackPressed();
    }
}