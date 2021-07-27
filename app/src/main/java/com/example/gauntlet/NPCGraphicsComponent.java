package com.example.gauntlet;

import android.content.Context;
import android.graphics.*;

public class NPCGraphicsComponent implements GraphicsComponent {
    private Bitmap mBitmap;
    private Bitmap mBitmapReversed;

    Canvas canvas2;
    @Override
    public void initialize(Context context, ObjectSpec spec, PointF objectSize) {
        int resID = context.getResources().getIdentifier(spec.getBitmapName(), "drawable", context.getPackageName());

        mBitmap = BitmapFactory.decodeResource(context.getResources(), resID);

        // Scaling the bitmap object to the correct size for the game object.
        mBitmap = Bitmap.createScaledBitmap(mBitmap, (int)objectSize.x, (int)objectSize.y, false);

        Matrix matrix = new Matrix();
        matrix.setScale(-1, 1);

        // Now we can show any game object instance in a left or right-facing state.
        mBitmapReversed = Bitmap.createBitmap(mBitmap, 0, 0, mBitmap.getWidth(), mBitmap.getHeight(), matrix, true);


    }

    @Override
    public void setBitmap(Bitmap bitmap) {

    }

    @Override
    public void draw(Canvas canvas, Paint paint, Transform t) {
        // Below is best for current movement..

        if (Renderer.numGhostsDrawn == GameEngine.aliensSpawned) {

        }

        Canvas canvas1 = new Canvas(GameData.mainBitmap);

        canvas1.setBitmap(GameData.mainBitmap);


        if (!t.getFacingRight()) {
            canvas1.drawBitmap(mBitmap, t.getLocation().x, t.getLocation().y, paint);
        }

        else {
            canvas1.drawBitmap(mBitmapReversed, t.getLocation().x, t.getLocation().y, paint);
        }

    }


}
