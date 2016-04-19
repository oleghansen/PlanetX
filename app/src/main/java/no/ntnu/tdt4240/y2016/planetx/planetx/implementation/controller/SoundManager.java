package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.content.Context;
import android.media.MediaPlayer;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;


public class SoundManager {

    private static SoundManager instance = new SoundManager();
    private MediaPlayer music = new MediaPlayer(); //The in game theme
    private MediaPlayer soundEffectsShoot = new MediaPlayer();
    private MediaPlayer soundEffectsExplode = new MediaPlayer();
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
        if(music.isPlaying()) {
            music.release();
        }

        music = MediaPlayer.create(context, R.raw.ingamesong);

        //Starts playing song
        if(!mutedMusic) {
            music.start();
        }

        //Make sure the song is looping
        music.setLooping(true);
    }

    /**
     * Stops playing the main theme of the game if it is playing.
     */
    public void stopMusic() {
        //Stops playing if already playing
        if(music.isPlaying()) {
            music.stop();
        }
    }

    /**
     * Plays the song of the main menu
     *
     * @param context
     */
    public void playMenuSong(Context context) {
        if(music.isPlaying()) {
            music.release();
        }

        music = MediaPlayer.create(context, R.raw.ingamesong);

        //Starts playing song
        if(!mutedMusic) {
            music.start();
        }

        //Make sure the song is looping
        music.setLooping(true);
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
        soundEffectsShoot = MediaPlayer.create(context, R.raw.shoot);

        //Start playing
        //Stops when audio is finished playing
        soundEffectsShoot.start();
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

        if(soundEffectsExplode.isPlaying()) {
            soundEffectsExplode.release();
        }

        soundEffectsExplode = MediaPlayer.create(context, R.raw.explosion);

        //Start playing
        //Stops when audio is finished playing
        soundEffectsExplode.start();
    }

    /**
     * Mutes or unmutes the music of the game.
     * If you are unmuting you need to call the song you need to
     * start if playing
     *
     */
    public void muteMusic() {
        if(music.isPlaying()) {
            music.pause();
        } else {
            music.start();
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

    public boolean isMusicMuted() {
        return mutedMusic;
    }

    public boolean isSoundEffectsMuted() {
        return mutedSoundEffects;
    }
}
