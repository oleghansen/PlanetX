package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
    private ImageView arrow;
    private GameModel gameModel;
    private RelativeLayout parentLayout;
    private ArrayList<Trajectory> trajectories = new ArrayList<>();

    public MapView(Context context, GameModel gm) {
        super(context);
        this.gameModel = gm;
        setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT));

//        setTouchListener();

        arrow = new ImageView(context);
        arrow.setLayoutParams(new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        addView(arrow);
        setWillNotDraw(false);
    }

    private void setTouchListener() {
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

    public void fireShot(Weapon weapon) {
        SoundManager.getInstance().playSoundEffectShoot(this.getContext());
        addToView(weapon);
        weapon.startMove();
    }

    public void showArrow(float x, float y) {
        float w = getWidth();
        float h = getHeight();
        float dx = 0, dy = 0;
        if (x >= 0 && x <= w && y >= 0 && y <= h) {
            arrow.setImageBitmap(null);
            return;
        }

        if (x < 0) {
            if (y < 0) {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_lu));
                dy = 0;
            } else if (y > h) {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_ld));
                dy = h - arrow.getHeight();
            } else {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_l));
                dy = y;
            }
            dx = 0;
        } else if (x > w) {
            if (y < 0) {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_ru));
                dy = 0;
            } else if (y > h) {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_rd));
                dy = h - arrow.getHeight();
            } else {
                arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_r));
                dy = y;
            }
            dx = w - arrow.getWidth();
        } else if (y < 0) {
            arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_u));
            dx = x;
            dy = 0;
        } else if (y > h) {
            arrow.setImageBitmap(BitmapFactory.decodeResource(getContext().getResources(), R.mipmap.arrow_d));
            dx = x;
            dy = h - arrow.getHeight();
        }
        arrow.setX(dx);
        arrow.setY(dy);
    }

    public void addTrajectory(float x1, float y1, float x2, float y2) {
        Trajectory trajectory = new Trajectory(x1, y1, x2, y2);
        trajectories.add(trajectory);
    }

    public void clearTrajectoryArray() {
        trajectories.clear();
    }

    /**
     * Draws weapon trajectories
     *
     * @param canvas
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.CYAN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2.5f);
        paint.setAlpha(100);

        for (Trajectory trajectory : trajectories) {
            canvas.drawLine(trajectory.getX1(), trajectory.getY1(), trajectory.getX2(), trajectory.getY2(), paint);
            // Log.d("TRAJECTORY" , trajectories.size() + " | " + trajectory.toString());
        }
    }
}
