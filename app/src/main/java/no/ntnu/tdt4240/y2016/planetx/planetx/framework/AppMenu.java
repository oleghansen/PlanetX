package no.ntnu.tdt4240.y2016.planetx.planetx.framework;

/**
 * Created by Ole on 31.03.2016.
 */

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

public abstract class AppMenu extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceBundle){
        super.onCreate(savedInstanceBundle);
    }
    /**
     * Sends the user to the specified screen.
     * @param javaClass
     */

    public void goTo(Class javaClass) {
        Intent intent = new Intent(this, javaClass);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        finish(); //Ends the previous activity
        overridePendingTransition(0, 0);
    }

    public void goToWithMap(Class javaClass, String mapName){
        Intent intent = new Intent(this, javaClass);
        intent.putExtra("MapName",mapName);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
        overridePendingTransition(0, 0);
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }
}
