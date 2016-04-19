package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
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

        mapNames = JsonMapReader.getMapList(getApplicationContext());
        mapListView = new ListView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_menu, menu);

        for(String s: JsonMapReader.getMapList(getApplicationContext())){
            MenuItem item = menu.add(s);
            final String s2 = s;
            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    startGame(s2);
                    return true;
                }
            });
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!SoundManager.getInstance().isMusicMuted()) {
            SoundManager.getInstance().playMenuSong(this);
        }
    }

    public void click_startGame(View view) {
        selectMapDialog();
    }

    public void click_settings(View view) {
        startGame("Crab Nebula");
    }

    public void click_signOut(View view) {
        //LoadingActivity.mGoogleApiClient.disconnect();
        LoadingActivity.mExplicitSignOut = true;
        setContentView(R.layout.activity_main);
        goTo(LoadingActivity.class);
    }

    public void startGame(String mapName) {
        Toast.makeText(this, "Starting game on map " + mapName, Toast.LENGTH_SHORT).show();

        goToWithMap(GameActivity.class, mapName);
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