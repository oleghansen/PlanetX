package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
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

/**
 * Created by Ole on 31.03.2016.
 */
public class MenuActivity extends AppMenu {

    private Button startGameButton;
    private ListView mapListView;
    private ViewGroup vg;
    private String selectedMapName;
    final private String[] mapNames = {"Canis Major", "Sagittarius", "Draco"}; //Temporary for testing

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mapListView = new ListView(this);

        startGameButton = (Button)findViewById(R.id.btn_startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMapDialog();
            }
        });
    }

    /**
     * This method starts a new game.
     * @param map
     */
    public void startGame(Map map){
        Toast.makeText(this, "Starting game on map " + map.getName(), Toast.LENGTH_SHORT).show();

        goTo(GameActivity.class);
        //TODO: create new game instance
    }
    /**
     * This method opens the map selection dialog.
     */
    public void selectMapDialog(){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.maplist_item, R.id.mapName_textView, mapNames);
        mapListView.setAdapter(adapter);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(this.getString(R.string.choose_map))
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
            }
        });
        alert.show();
        alert.getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
    }
}
