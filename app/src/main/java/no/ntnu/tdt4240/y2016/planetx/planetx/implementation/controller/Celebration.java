package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Christian on 19.04.2016.
 */
public class Celebration extends AppMenu {


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebration);


        Intent i = getIntent();
        String winner = (String) i.getSerializableExtra("winner");
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Planet X");
        builder.setMessage(winner + " is the winner!");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // You don't have to do anything here if you just want it dismissed when clicked
                goTo(MenuActivity.class);
            }
        });

        builder.create();
        builder.show();



        //TextView test = (TextView) findViewById(R.id.thisWinner);
        //test.setText("The winner is " + winner);

    }



}
