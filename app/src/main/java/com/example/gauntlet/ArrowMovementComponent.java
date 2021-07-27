package com.example.gauntlet;

import android.graphics.PointF;
import android.graphics.RectF;

class ArrowMovementComponent implements MovementComponent {

    @Override
    public boolean move(long fps,
                        Transform t,
                        Transform playerTransform) {

        // Arrow can only travel two screen widths
        //float rightRange = playerTransform.getLocation().x + (GameData.IMAGE_RESOLUTION_X / 10);
        //float leftRange = playerTransform.getLocation().x - (GameData.IMAGE_RESOLUTION_X / 10);
        int quadrant = 0;
        float range = 5120;
        RectF localCollider = t.getCollider();

        // Where is the arrow
        PointF location = t.getLocation();

        // How fast is it going
        float speed = 5120 / 20;
        float drawSpeed = t.getmScreenSize().x / 12;

        for (int i = 0; i < SpatialCollision.gridRects.size(); i++) {
            if (RectF.intersects(t.getCollider(), SpatialCollision.gridRects.get(i))) {
                quadrant = i;
                break;
            }
        }


        if(t.headingRight()){
            location.x += speed / fps;
            t.drawableLocation.x += drawSpeed / fps;
        }
        else if(t.headingLeft()){
            location.x -= speed / fps;
            t.drawableLocation.x -= drawSpeed / fps;
        }

        localCollider.top = t.getLocation().y;

        localCollider.bottom = localCollider.top + t.getObjectHeight();

        localCollider.left = t.getLocation().x;

        localCollider.right = localCollider.left + t.getSize().x;

        // Has the arrow gone out of range
        if(location.x < -range|| location.x > range){
            // disable the arrow
            return false;
        }


        return true;
    }
}
