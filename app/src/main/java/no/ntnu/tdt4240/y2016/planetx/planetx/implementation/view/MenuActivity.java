package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.MainMenuController;

/**
 * Created by Ole on 31.03.2016.
 */
public class MenuActivity extends AppMenu {

    private MainMenuController menuController;

    private Button startGameButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuController = new MainMenuController(this);
        startGameButton = (Button)findViewById(R.id.btn_startGame);
        startGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               menuController.selectMapDialog();
            }
        });
    }
}
