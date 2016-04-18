package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import no.ntnu.tdt4240.y2016.planetx.planetx.R;

/**
 * Class for animations, e.g. explosion.
 *
 * @author www.gametutorial.net & Hans
 */

public class Animation extends ImageView {
    private CountDownTimer cdt;

    private final Bitmap bitmap;
    private final int frameHeight;
    private int frameWidth;
    private int numberOfFrames;

    private long frameTime;
    private int currentFrameNumber;

    private int startingXOfFrameInImage;

    public Animation(Context context, Bitmap b, int frameWidth, int frameHeight, int numberOfFrames, long frameTime, int x, int y) {
        super(context);
        this.bitmap = b;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        this.numberOfFrames = numberOfFrames;
        this.frameTime = frameTime;

        startingXOfFrameInImage = 0;

        currentFrameNumber = 0;

        this.setImageBitmap(Bitmap.createBitmap(b, 0, 0, frameWidth, frameHeight));

        this.setX(x);
        this.setY(y);

    }

    public void startAnimation() {
        cdt = new CountDownTimer(numberOfFrames * frameTime, frameTime) {
            public void onTick(long l) {
                nextImage();
                invalidate();
            }

            public void onFinish() {
                stopAnimation();
            }
        }.start();
    }

    private void stopAnimation() {

        setImageBitmap(null);
    }

    private void nextImage() {
        currentFrameNumber++;
        if (currentFrameNumber >= numberOfFrames) {
            currentFrameNumber = 0;
        }
        startingXOfFrameInImage = currentFrameNumber * frameWidth;
        Bitmap tmpB = Bitmap.createBitmap(bitmap, startingXOfFrameInImage, 0, frameWidth, frameHeight);
        setImageBitmap(tmpB);
    }
}
