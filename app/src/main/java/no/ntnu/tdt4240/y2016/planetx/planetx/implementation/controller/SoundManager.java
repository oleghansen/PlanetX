package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;



public class SoundManager {

    private static SoundManager instance = new SoundManager();
    private MediaPlayer mainTheme = new MediaPlayer(); //The main theme
    private boolean mutedMusic; //Is true if sound is muted
    private boolean mutedSoundEffects; //Is true if sound is muted

    private SoundManager() {
        mutedMusic = false;
        mutedSoundEffects = false;
    }

    /**
     * Return the sound manager as a singleton
     *
     * @return SoundManager
     */
    public static SoundManager getInstance() {
        return instance;
    }

    /**
     * Starts playing the main theme of the game.
     *
     * @param context the state of the application
     */
    public void playMainTheme(Context context) {

        //Return if sound is muted
        if(mutedMusic) {
            return;
        }

        try {
            //Chooses audio file
            mainTheme.setDataSource(context,
                    Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.main_theme));
        } catch (Exception e) {
            Log.d("Sound", e.toString());
        }

        //Starts playing song
        mainTheme.start();

        //Make sure the song is looping
        mainTheme.setLooping(true);
    }

    /**
     * Stops playing the main theme of the game if it is playing.
     */
    public void stopMainTheme() {
        //Stops playing if already playing
        if(mainTheme.isPlaying()) {
            mainTheme.stop();
        }
    }

    /**
     * Plays a shot sound effect if sound effects is not muted.
     *
     * @param context the state of the application
     */
    public void playSoundEffectShoot(Context context) {

        //Return if sound effects is muted
        if(mutedSoundEffects) {
            return;
        }

        //Create a new sound effect
        MediaPlayer mpSound = new MediaPlayer();

        try {
            //Chooses sound effect
            mpSound.setDataSource(context,
                   Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.short2));
        } catch (Exception e) {
            Log.d("Sound", e.toString());
        }

        //Start playing
        //Stops when audio is finished playing
        mpSound.start();
    }

    /**
     * Mutes or unmutes the music of the game.
     * Starts the song frm the start if unmuted, hence the
     * method needs a context.
     *
     * @param context the state of the application
     */
    public void muteMusic(Context context) {


        if(mutedMusic) {
            //Starts playing the main theme when unmuting the sound
            this.playMainTheme(context);
        } else {
            //Stops playing
            this.stopMainTheme();
        }

        //Inverts muting variable
        mutedMusic = !mutedMusic;
    }

    /**
     * Mutes og unmutes sound effects.
     *
     */
    public void muteSoundeffects() {

        //Inverts muting variable
        mutedSoundEffects = !mutedSoundEffects;
    }
}
