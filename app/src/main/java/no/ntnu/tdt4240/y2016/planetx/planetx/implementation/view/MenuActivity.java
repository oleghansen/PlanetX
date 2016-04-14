package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view;

import android.os.Bundle;
import android.view.View;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.MainMenuController;

public class MenuActivity extends AppMenu {

    private MainMenuController menuController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        menuController = new MainMenuController(this);

    }
    public void click_startGame(View view) {
        menuController.selectMapDialog();
    }

    public void click_settings(View view) {
    }
}
