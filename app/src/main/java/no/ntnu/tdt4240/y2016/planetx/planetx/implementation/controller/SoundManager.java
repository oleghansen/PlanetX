package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.util.Log;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;


public class SoundManager {

    private static SoundManager instance = new SoundManager();
    private MediaPlayer mainTheme = new MediaPlayer(); //The in game theme
    private MediaPlayer menuTheme = new MediaPlayer(); //The in game theme
    private MediaPlayer soundEffects = new MediaPlayer();
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
    public void playInGameSong(Context context) {

        //Return if sound is muted
        if(mutedMusic) {
            return;
        }

        if(menuTheme.isPlaying()) {
            menuTheme.isPlaying();
        }

        mainTheme = MediaPlayer.create(context, R.raw.ingamesong);

        //Starts playing song
        mainTheme.start();

        //Make sure the song is looping
        mainTheme.setLooping(true);
    }

    /**
     * Stops playing the main theme of the game if it is playing.
     */
    public void stopInGameSong() {
        //Stops playing if already playing
        if(mainTheme.isPlaying()) {
            mainTheme.stop();
        }
    }

    /**
     * Plays the song of the main menu
     *
     * @param context
     */
    public void playMenuSong(Context context) {

        //Return if sound is muted
        if(mutedMusic) {
            return;
        }

        if(mainTheme.isPlaying()) {
            mainTheme.stop();
        }

        menuTheme = MediaPlayer.create(context, R.raw.ingamesong);

        //Starts playing song
        menuTheme.start();

        //Make sure the song is looping
        menuTheme.setLooping(true);
    }

    /**
     * Stops playing the song of the manu
     */
    public void stopMenuSong() {
        //Stops playing if already playing
        if(menuTheme.isPlaying()) {
            menuTheme.stop();
        }
    }

    /**
     * Plays a shot sound effect of a shoot beaing fired, if sound effects is not muted.
     *
     * @param context the state of the application
     */
    public void playSoundEffectShoot(Context context) {

        //Return if sound effects is muted
        if(mutedSoundEffects) {
            return;
        }

        //Chooses sound effect
        soundEffects = MediaPlayer.create(context, R.raw.shoot);

        //Start playing
        //Stops when audio is finished playing
        soundEffects.start();
    }

    /**
     * Plays a sound effect of an explosion, if sound effects is not muted
     *
     * @param context
     */
    public void playSoundEffectExplosion(Context context) {

        //Return if sound effects is muted
        if(mutedSoundEffects) {
            return;
        }

        soundEffects = MediaPlayer.create(context, R.raw.explosion);

        //Start playing
        //Stops when audio is finished playing
        soundEffects.start();
    }

    /**
     * Mutes or unmutes the music of the game.
     * If you are unmuting you need to call the song you need to
     * start if playing
     *
     */
    public void muteMusic() {
        if(mainTheme.isPlaying()) {
            mainTheme.stop();
        } else if(menuTheme.isPlaying()) {
            menuTheme.stop();
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
