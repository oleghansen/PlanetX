package no.ntnu.tdt4240.y2016.planetx.planetx.implementation.view_controller;


import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.Layout;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class Animation extends ImageView {
    private ViewGroup parentViewGroup;

    private CountDownTimer cdt;

    private final Bitmap bitmap;
    private final int frameHeight;
    private int frameWidth;
    private int numberOfFrames;

    private long frameTime;
    private int currentFrameNumber;

    private int startingXOfFrameInImage;

    public Animation(ViewGroup vg, Bitmap b, int numberOfFrames, long frameTime, int x, int y) {
        super(vg.getContext());

        parentViewGroup = vg;
        this.bitmap = b;
        this.frameHeight = b.getHeight();
        this.frameWidth = b.getHeight();

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
        bitmap.recycle();
        parentViewGroup.removeView(this);
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
