package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Ole on 31.03.2016.
 */
public class MainMenuController {
    private AppMenu context;
    private ListView mapListView;
    public MainMenuController(AppMenu context){
        this.context = context;
    }

    public void selectMapDialog(){
        mapListView = new ListView(context);
        String[] mapNames = {"Map1", "Map2", "Map3"}; //Temporary for testing
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.maplist_item, R.id.mapName_textView, mapNames);
        mapListView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.choose_map))
                .setCancelable(true)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Start game
                    }
                }).setView(mapListView);
        AlertDialog alert = builder.create();
        alert.show();
    }
}
