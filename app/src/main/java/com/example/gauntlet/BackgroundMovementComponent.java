package com.example.gauntlet;

import android.graphics.PointF;

class BackgroundMovementComponent implements MovementComponent {
    PointF mPlayerPadding;
    public static boolean atEdge = false;

    BackgroundMovementComponent() {
        mPlayerPadding = new PointF(0, 0);
    }

    @Override
    public boolean move(long fps,
                        Transform t,
                        Transform playerTransform) {

        PointF screenSize = t.getmScreenSize();
        mPlayerPadding.x = GameData.IMAGE_RESOLUTION_X / 5;
        mPlayerPadding.y = GameData.IMAGE_RESOLUTION_Y / 5;
        GameData.visibleScreenRect = t.getCollider();

        int width = 5120;
        int height = 5120;
        PointF playerLocation = playerTransform.getLocation();
        System.out.println("Player Location: " + playerTransform.getLocation().x + "," + playerTransform.getLocation().y);
        int left = 0;
        int right = 0;
        int top = 0;
        int bottom = 0;

        // TO DO: Redefine how we are going about below with player location in 5120 map..
        /*

         */

        /*
         if ((playerLocation.x - mPlayerPadding.x) < 0) {
            left = 0;
            right = (int)(mPlayerPadding.x);

        }

        else if ((playerLocation.x + mPlayerPadding.x) > width) {
            right = width;
            left = (int)(right - (mPlayerPadding.x * 2));
        }

        else {
             right = (int)(playerLocation.x + mPlayerPadding.x);
             left = (int)(playerLocation.x - mPlayerPadding.x);

         }



            if ((playerLocation.y + mPlayerPadding.y) > height) {
            bottom = height;
            top = (int)(bottom - (mPlayerPadding.y * 2));
//
        }



        else if ((playerLocation.y - mPlayerPadding.y) < 0) {
            top = 0;
            bottom = (int)(mPlayerPadding.y * 2);
//
        }

        else {
                top = (int)(playerLocation.y - mPlayerPadding.y);
                bottom = (int)(playerLocation.y + mPlayerPadding.y);
//
            }

         */

            right = (int)(playerLocation.x + mPlayerPadding.x);
            left = (int)(playerLocation.x - mPlayerPadding.x);
            top = (int)(playerLocation.y - mPlayerPadding.y);
            bottom = (int)(playerLocation.y + mPlayerPadding.y);



        GameData.visibleScreenRect.top = top;
        GameData.visibleScreenRect.bottom = bottom;
        GameData.visibleScreenRect.left = left;
        GameData.visibleScreenRect.right = right;

        return true;
    }
}
