package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
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
    private int save;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mapNames = JsonMapReader.getMapList(getApplicationContext());

        save = -1;
        mapListView = new ListView(this);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main_menu, menu);
//
//        for(String s: JsonMapReader.getMapList(getApplicationContext())){
//            MenuItem item = menu.add(s);
//            final String s2 = s;
//            item.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//                @Override
//                public boolean onMenuItemClick(MenuItem item) {
//                    startGame(s2);
//                    return true;
//                }
//            });
//        }
//        return true;
//    }

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

//    public void click_signOut(View view) {
//        //LoadingActivity.mGoogleApiClient.disconnect();
//        LoadingActivity.mExplicitSignOut = true;
//        setContentView(R.layout.activity_main);
//        goTo(LoadingActivity.class);
//    }

    public void startGame(String mapName) {
        Toast.makeText(this, "Starting game on map " + mapName, Toast.LENGTH_SHORT).show();

        goToWithMap(GameActivity.class, mapName);
    }

    public void initiateMapAdapter() {

    }

    /**
     * This method opens the map selection dialog.
     */
    public void selectMapDialog() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.maplist_item, R.id.mapName_textView, mapNames);
        mapListView.setAdapter(adapter);

        final AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final AlertDialog alertd = alert.create();

        if (mapListView.getParent() == null) {
            mapListView.setAdapter(adapter);
            alert.setView(mapListView);
        } else {
            mapListView = null;
            mapListView = new ListView(this);
            mapListView.setAdapter(adapter);
            alert.setView(mapListView);
        }

        alert.setMessage(this.getString(R.string.choose_map))
                .setCancelable(true)
                .setPositiveButton("Start", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startGame(selectedMapName);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
                alertd.dismiss();
            }
        });


        mapListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.gc();
                try {
                    vg = (ViewGroup) view;
                    TextView mapName = (TextView) vg.findViewById(R.id.mapName_textView);
                    selectedMapName = mapName.getText().toString();
                    parent.getChildAt(position).setBackgroundColor(Color.parseColor("#7ac5cd"));
                    if (save != -1 && save != position) {
                        parent.getChildAt(save).setBackgroundColor(Color.parseColor("#00000000"));
                    }
                    save = position;
                } catch (NullPointerException np) {
                    Log.d("NullPointerException", np.toString());
                }
            }
        });
        alert.show();
    }


}