package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Map;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view.MenuActivity;

/**
 * Created by Ole on 31.03.2016.
 */
public class MainMenuController {
    private AppMenu context;
    private ViewGroup vg;
    private String selectedMapName;
    final private String[] mapNames = {"Canis Major", "Sagittarius", "Draco"}; //Temporary for testing


    public MainMenuController(AppMenu context){
        this.context = context;
    }

    public void selectMapDialog(ListView mapListView){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.maplist_item, R.id.mapName_textView, mapNames);
        mapListView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getString(R.string.choose_map))
                .setCancelable(true)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Map selectedMap = new Map(selectedMapName, null);
                        startGame(selectedMap);
                    }
                }).setView(mapListView);

        final AlertDialog alert = builder.create();



        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() { int save = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vg = (ViewGroup) view;
                TextView mapName = (TextView) vg.findViewById(R.id.mapName_textView);
                selectedMapName = mapName.getText().toString();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                parent.getChildAt(position).setBackgroundColor(Color.parseColor("#7ac5cd"));

                if(save != -1 && save != position) {
                    parent.getChildAt(save).setBackgroundColor(Color.parseColor("#00000000"));
                }
                save = position;
                Toast.makeText(context, "'" + selectedMapName + "' selected.", Toast.LENGTH_SHORT).show();
            }
        });
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }

    public void startGame(Map map){
        Toast.makeText(context, "Starting game on map " + map.getName(), Toast.LENGTH_SHORT).show();
        //TODO
    }
}
