package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;

/**
 * Created by Anders on 20.04.2016.
 */
public class WeaponLayout extends LinearLayout {
    private final int DEFAULT_COLOR = ContextCompat.getColor(getContext(), R.color.default_weapon_list);
    private final int TOUCH_COLOR = ContextCompat.getColor(getContext(), R.color.click_weapon_list);
    private final int TEXT_COLOR = ContextCompat.getColor(getContext(), R.color.white);

    private int padding_in_px = (int) (getResources().getDisplayMetrics().density + 0.5f);

    public WeaponLayout(Context context) {
        super(context);

        setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.HORIZONTAL);

        final WeaponLayout wl = this;

        this.setBackgroundColor(DEFAULT_COLOR);

        setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        wl.setBackgroundColor(TOUCH_COLOR);
                        break;
                    case MotionEvent.ACTION_UP:

                        //set color back to default
                        wl.setBackgroundColor(DEFAULT_COLOR);
                        break;
                }
                return false;
            }
        });


        int padding = 10 * padding_in_px;
        setPadding(padding, padding / 2, padding, padding / 2);
    }

    public void setDescription(final String description) {
        setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Toast.makeText(getContext(), description, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    public void setWeaponImage(Bitmap imageBitmap) {
        ImageView iv = new ImageView(getContext());
        iv.setImageBitmap(imageBitmap);
        iv.setPadding(0, 0, 10 * padding_in_px, 0);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        iv.setLayoutParams(layoutParams);

        addView(iv);
    }

    public void setWeaponText(String weaponText) {
        TextView tw = new TextView(getContext());
        tw.setTextColor(TEXT_COLOR);
        tw.setText(weaponText);
        tw.setTextSize(padding_in_px * 8);
        addView(tw);
    }
}
