package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Ole on 31.03.2016.
 */
public class MainMenuController {
    private AppMenu context;

    public MainMenuController(AppMenu context){
        this.context = context;
    }

    public void selectMapDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.choose_map))
                .setCancelable(true)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Start game
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
