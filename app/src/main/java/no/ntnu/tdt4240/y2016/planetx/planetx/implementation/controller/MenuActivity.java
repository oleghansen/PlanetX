package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Map;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

/**
 * Created by Ole on 31.03.2016.
 */
public class MenuActivity extends AppMenu {

    private ListView mapListView;
    private ViewGroup vg;
    private String selectedMapName;
    private ArrayList<String> mapNames = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        readMaps();

        mapListView = new ListView(this);
    }

    private void readMaps() {
        InputStream inputStream = getResources().openRawResource(R.raw.maps);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        int ctr;
        try {
            ctr = inputStream.read();
            while (ctr != -1) {
                byteArrayOutputStream.write(ctr);
                ctr = inputStream.read();
            }
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("JSONError", "Error with StreamReader");
            return;
        }

        try {
           JSONArray array = new JSONArray(byteArrayOutputStream.toString());
            for (int i = 0; i < array.length(); i++) {
                    JsonMapReader jmr = new JsonMapReader(array.getString(i));
                    mapNames.add(jmr.getName());
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("JSONError","Error with JsonMapReader");
        }
    }

    public void click_startGame(View view) {
        selectMapDialog();
    }

    /**
     * This method starts a new game.
     *
     * @param map
     */
    public void startGame(Map map) {
        Toast.makeText(this, "Starting game on map " + map.getName(), Toast.LENGTH_SHORT).show();

        goTo(GameActivity.class);
        //TODO: create new game instance
    }

    public void startGame(String mapName) {
        Toast.makeText(this, "Starting game on map " + mapName, Toast.LENGTH_SHORT).show();

        goTo(GameActivity.class);
        //TODO: create new game instance
    }

    /**
     * This method opens the map selection dialog.
     */
    public void selectMapDialog() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.maplist_item, R.id.mapName_textView, mapNames);
        mapListView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getString(R.string.choose_map))
                .setCancelable(true)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Map selectedMap = new Map(selectedMapName, null);
                        //startGame(selectedMap);
                        startGame(selectedMapName);
                    }
                }).setView(mapListView);

        final AlertDialog alert = builder.create();
        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            int save = -1;

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                vg = (ViewGroup) view;
                TextView mapName = (TextView) vg.findViewById(R.id.mapName_textView);
                selectedMapName = mapName.getText().toString();
                alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);

                parent.getChildAt(position).setBackgroundColor(Color.parseColor("#7ac5cd"));

                if (save != -1 && save != position) {
                    parent.getChildAt(save).setBackgroundColor(Color.parseColor("#00000000"));
                }
                save = position;
            }
        });
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
}
