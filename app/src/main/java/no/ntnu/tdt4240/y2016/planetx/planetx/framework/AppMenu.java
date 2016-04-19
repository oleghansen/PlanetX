package no.ntnu.tdt4240.y2016.planetx.planetx.framework;

/**
 * Created by Ole on 31.03.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;
import android.widget.ToggleButton;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.SoundManager;

public abstract class AppMenu extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceBundle) {
        super.onCreate(savedInstanceBundle);
    }

    @Override
    public void onResume() {
        super.onResume();
        ToggleButton music = (ToggleButton) findViewById(R.id.btn_music_effect);
        ToggleButton sound = (ToggleButton) findViewById(R.id.btn_sound_effect);

        SoundManager sm = SoundManager.getInstance();
        if (music != null) {
            if (sm.isMusicMuted()) {
                music.setChecked(true);
            } else {
                music.setChecked(false);
            }
        }

        if (sound != null) {
            if (sm.isSoundEffectsMuted()) {
                sound.setChecked(true);
            } else {
                sound.setChecked(false);
            }
        }
        Toast.makeText(this, "ONRESUME", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        SoundManager.getInstance().stopMusic();
    }

    public void goTo(Class javaClass) {
        Intent intent = new Intent(this, javaClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish(); //Ends the previous activity
        overridePendingTransition(0, 0);
    }

    public void goToWithMap(Class javaClass, String mapName) {
        Intent intent = new Intent(this, javaClass);
        intent.putExtra("MapName", mapName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    public void goToAndFinishCurrent(Class javaClass) {
        Intent intent = new Intent(this, javaClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish();
        overridePendingTransition(0, 0);
    }

    public void toggle_music(View view) {
        SoundManager.getInstance().muteMusic();
    }

    public void toggle_sound(View view) {
        SoundManager.getInstance().muteSoundeffects();
    }
}