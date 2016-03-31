package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view;

import android.os.Bundle;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.framework.AppMenu;

/**
 * Created by Ole on 31.03.2016.
 */
public class MenuActivity extends AppMenu {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }
}
