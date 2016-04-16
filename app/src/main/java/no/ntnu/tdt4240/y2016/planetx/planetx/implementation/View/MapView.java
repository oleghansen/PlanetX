package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.controller.GameController;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Missile;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.SpaceEntity;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.Weapon;

/**
 * Created by Anders on 16.04.2016.
 */
public class MapView extends RelativeLayout {
    private GameController gameController;

    private ImageView lockButton;
    private ImageView fireButton;

    public MapView(Context context, GameController gm) {
        super(context);
        gameController = gm;

        setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

        OnTouchListener otl = new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                gameController.touch_map(v, e);
                return true;
            }
        };
        setOnTouchListener(otl);

        initializeButtons();
    }

    private void initializeButtons(){
        initializeFireButton();
        initializeLockButton();
    }
    private ImageView initializeBottomButton(Bitmap bitmap, OnClickListener ocl){
        ImageView img = new ImageView(this.getContext());
//        img.setBaselineAlignBottom(true);
        img.setAdjustViewBounds(true);

        img.setMaxHeight(100);
        img.setImageBitmap(bitmap);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        img.setOnClickListener(ocl);
        img.setLayoutParams(lp);
        return img;
    }
    private void initializeFireButton() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire);
        OnClickListener ocl = new OnClickListener(){
            @Override
            public void onClick(View v) {
                gameController.click_fireButton(v);
            }
        };

        this.fireButton = initializeBottomButton(bitmap,ocl);
    }
    private void initializeLockButton(){
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);

        OnClickListener ocl = new OnClickListener(){
            @Override
            public void onClick(View v) {
                gameController.click_lockButton(v);
            }
        };
        this.lockButton = initializeBottomButton(bitmap, ocl);
    }

    public void showFireButton(){
        this.removeView(lockButton);
        this.addView(fireButton);
    }
    public void showLockButton(){
        this.removeView(fireButton);
        this.addView(lockButton);
    }

    public void initializeMap(ArrayList<SpaceEntity> entities){
        for(SpaceEntity entity: entities){
            addToView(entity);
        }
        showLockButton();
    }
    public void addToView(View v) {
        v.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.addView(v);
    }

    public void fireTestShot(Missile missile) {
        addToView(missile);
        missile.startMove();
    }
}
