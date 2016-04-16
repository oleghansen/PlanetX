package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.util.ArrayList;
import no.ntnu.tdt4240.y2016.planetx.planetx.R;
import no.ntnu.tdt4240.y2016.planetx.planetx.implementation.model.json.JsonMapReader;

/**
 * Created by Ole on 31.03.2016.
 */
public class Map extends RelativeLayout {
    private String name;
    private ArrayList<Spaceship> spaceships = new ArrayList<>();
    private ArrayList<SpaceObstacle> spaceObstacles = new ArrayList<>();
    private ImageView fireButton;

    public Map(Context context) {
        super(context);

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                spaceships.get(0).flipTowardsTouch(v, e);
                spaceships.get(1).flipTowardsTouch(v, e);
                return true;
            }
        });

    }

    public void initializeMap(JsonMapReader jrm){
        for(SpaceObstacle so: jrm.getObstacles(getContext())){
            spaceObstacles.add(so);
            addToView(so);
        }

//        Spaceship ship1 = jrm.getShip1(this);
//        spaceships.add(ship1);
//        addToView(ship1);
//
//        Spaceship ship2 = jrm.getShip2(this);
//        spaceships.add(ship2);
//        addToView(ship2);

        addFireButton();
    }

    private void addFireButton() {
        ImageView img = new ImageView(this.getContext());
        img.setBaselineAlignBottom(true);
        img.setAdjustViewBounds(true);
        final Map m = this;
        img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(m.getContext(), "FIRE!", Toast.LENGTH_LONG).show();
            }
        });
        img.setMaxHeight(100);
        img.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.fire));
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.addView(img, lp);
        this.fireButton = img;
    }

    private void addToView(SpaceEntity se) {
        se.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        this.addView(se);
    }

    public String getName(){
        return name;
    }

    //TODO: read method
    //TODO: draw method

}
