package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.multiplayer.Multiplayer;
import com.google.android.gms.games.multiplayer.realtime.RoomConfig;
import com.google.android.gms.games.multiplayer.turnbased.TurnBasedMatchConfig;

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
public class MenuActivity extends AppMenu implements GoogleApiClient.ConnectionCallbacks,GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private ListView mapListView;
    private ViewGroup vg;
    private GoogleApiClient mGoogleApiClient;
    private String selectedMapName;
    private ArrayList<String> mapNames = new ArrayList<>();
    final static int RC_SELECT_PLAYERS=12345; //Usikker på om denne er valgfri - Christian

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        mapNames = JsonMapReader.getMapList(getApplicationContext());
        mapListView = new ListView(this);

        // Create the Google Api Client with access to the Play Games services
        mGoogleApiClient = new GoogleApiClient.Builder(this, this, this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API)
                        // add other APIs and scopes here as needed
                .build();

        mGoogleApiClient.connect();
    }

    public void click_startGame(View view) {
        findPlayer();
        selectMapDialog();
    }

    public void click_signOut(View view) {
        //LoadingActivity.mGoogleApiClient.disconnect();
        LoadingActivity.mExplicitSignOut=true;
        setContentView(R.layout.activity_main);
        goTo(LoadingActivity.class);
    }

    public void startGame(String mapName) {
        Toast.makeText(this, "Starting game on map " + mapName, Toast.LENGTH_SHORT).show();

        goToWithMap(GameActivity.class, mapName);
    }

    /**
     * This method opens the default google UI for selecting players
     */
    public void findPlayer(){
        if(mGoogleApiClient.isConnected()==false)mGoogleApiClient.connect();
        Intent intent = Games.TurnBasedMultiplayer.getSelectOpponentsIntent(mGoogleApiClient,1,7,true);
        startActivityForResult(intent, RC_SELECT_PLAYERS);
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

    /**
     * Callback method for startActivityForResult
     * @param request
     * @param response
     * @param data
     */
    @Override
    public void onActivityResult(int request, int response, Intent data) {
        super.onActivityResult(request, response, data);


        if (request == RC_SELECT_PLAYERS) {
            if (response != Activity.RESULT_OK) {
                // user canceled
                return;
            }

            // Get the invitee list.
            final ArrayList<String> invitees =
                    data.getStringArrayListExtra(Games.EXTRA_PLAYER_IDS);

            // Get auto-match criteria.
            Bundle autoMatchCriteria = null;
            int minAutoMatchPlayers = data.getIntExtra(
                    Multiplayer.EXTRA_MIN_AUTOMATCH_PLAYERS, 0);
            int maxAutoMatchPlayers = data.getIntExtra(
                    Multiplayer.EXTRA_MAX_AUTOMATCH_PLAYERS, 0);
            if (minAutoMatchPlayers > 0) {
                autoMatchCriteria = RoomConfig.createAutoMatchCriteria(
                        minAutoMatchPlayers, maxAutoMatchPlayers, 0);
            } else {
                autoMatchCriteria = null;
            }

            TurnBasedMatchConfig tbmc = TurnBasedMatchConfig.builder()
                    .addInvitedPlayers(invitees)
                    .setAutoMatchCriteria(autoMatchCriteria)
                    .build();

            // Create and start the match.
          /*  Games.TurnBasedMultiplayer
                    .createMatch(LoadingActivity.mGoogleApiClient, tbmc)
                    .setResultCallback(new MatchInitiatedCallback());*/
        }
    }

    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
