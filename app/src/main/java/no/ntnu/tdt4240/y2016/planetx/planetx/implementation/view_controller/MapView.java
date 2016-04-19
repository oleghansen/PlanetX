package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.SoundManager;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.GameModel;

/**
 * Created by Anders on 16.04.2016.
 */
public class MapView extends RelativeLayout {
    private GameModel gameModel;
    private RelativeLayout parentLayout;



    public MapView(Context context, GameModel gm) {
        super(context);
        this.gameModel = gm;
        setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        OnTouchListener otl = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                gameModel.touch_map(v, e);
                return true;
            }
        };
        setOnTouchListener(otl);

    }

    public void setParentLayout(RelativeLayout parentLayout) {
        this.parentLayout = parentLayout;
    }


    public void initializeMap(ArrayList<SpaceEntity> entities) {
        for (SpaceEntity entity : entities) {
            addView(entity);
        }
    }



    public void addToView(View v) {
        v.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.addView(v);
    }

    public void fireTestShot(Missile missile) {
        SoundManager.getInstance().playSoundEffectShoot(this.getContext());
        addToView(missile);
        missile.startMove();
    }

    public void showLockButton() {
        gameModel.showLockButton();
    }
}
