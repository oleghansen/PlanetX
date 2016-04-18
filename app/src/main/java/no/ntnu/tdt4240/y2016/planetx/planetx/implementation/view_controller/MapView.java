package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    private ImageView lockButton;
    private ImageView fireButton;
    private SeekBar powerBar;
    private TextView powerText;

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

        initializeButtons();
    }

    public void setParentLayout(RelativeLayout parentLayout) {
        this.parentLayout = parentLayout;
    }

    private void initializeButtons() {
        initializeFireButton();
        initializeLockButton();
        initializePowerText();
        initializePowerBar();
    }

    private ImageView initializeBottomButton(Bitmap bitmap, OnClickListener ocl) {
        ImageView img = new ImageView(this.getContext());
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
        OnClickListener ocl = new OnClickListener() {
            @Override
            public void onClick(View v) {
                gameModel.click_fireButton(powerBar.getProgress());
            }
        };

        this.fireButton = initializeBottomButton(bitmap, ocl);
    }

    private void initializeLockButton() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.lock);

        OnClickListener ocl = new OnClickListener() {
            @Override
            public void onClick(View v) {
                gameModel.click_lockButton(v);
            }
        };
        this.lockButton = initializeBottomButton(bitmap, ocl);
    }

    private void initializePowerText() {
        powerText = new TextView(getContext());
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_HORIZONTAL);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        powerText.setLayoutParams(lp);
        powerText.setText("0%");
    }

    private void initializePowerBar() {
        powerBar = new SeekBar(getContext());
        powerBar.setMax(100);

        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.width = 200;
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

        powerBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                powerText.setText("" + progress + "%");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        powerBar.setLayoutParams(lp);
        powerBar.setProgress(0);
    }

    public void showFireButton() {
        this.removeView(lockButton);
        this.addView(fireButton);
        this.addView(powerText);
        this.addView(powerBar);
    }

    public void showLockButton() {
        this.removeView(fireButton);
        this.removeView(powerText);
        this.removeView(powerBar);
        this.addView(lockButton);
    }

    public void initializeMap(ArrayList<SpaceEntity> entities) {
        for (SpaceEntity entity : entities) {
            addView(entity);
        }
        showLockButton();
    }

    public void addToView(View v) {
        v.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.addView(v);
    }

    private void zoomOut() {
        setScaleX(0.5f);
        setScaleY(0.5f);
    }

    public void fireTestShot(Missile missile) {
        SoundManager.getInstance().playSoundEffectShoot(this.getContext());
        addToView(missile);
        missile.startMove();
    }
}
