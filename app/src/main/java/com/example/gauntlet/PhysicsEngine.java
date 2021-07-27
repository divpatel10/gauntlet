package com.example.gauntlet;

import android.graphics.PointF;
import android.graphics.RectF;
import android.util.Log;

import java.util.ArrayList;

class PhysicsEngine {

    // This signature and much more will
    //change later in the project
    boolean update(long fps, ArrayList<GameObject> objects,
                   GameState gs, SoundEngine se){

        // Update all the GameObjects
        for (GameObject object : objects) {
            if (object.checkActive()) {
                object.update(fps, objects.get(Level.PLAYER_INDEX)
                        .getTransform());
            }
        }

        return detectCollisions(gs, objects, se);
    }


    // Collision detection method will go here
    private boolean detectCollisions(
            GameState mGameState,
            ArrayList<GameObject> objects,
            SoundEngine se){

        boolean playerHit = false;

        for (GameObject go1 : objects) {
            if (go1.checkActive()) {
                for (GameObject go2 : objects) {
                    if (go2.checkActive()) {
                        if (RectF.intersects(go1.getTransform().getCollider(), go2.getTransform().getCollider())) {
                            switch (go1.getTag() + " with " + go2.getTag()) {

                                case "Player with Goblin":
                                    //playerHit = true;
                                    mGameState.loseLife(se);

                                    break;

                                case "Player Arrow with Ghost":

                                    mGameState.increaseScore();

                                    go2.setInactive();
                                    go2.spawn(objects.get(Level
                                            .PLAYER_INDEX).getTransform());

                                    Log.d("Collision-arrow", ""+
                                            go1.getTransform().getCollider().top + "\t" +
                                            go1.getTransform().getCollider().right + "\t"
                                    );
                                    Log.d("Collision-troll", ""+
                                            go2.getTransform().getCollider().top + "\t" +
                                            go2.getTransform().getCollider().left + "\t"
                                    );

                                    //go1.setInactive();
                                    se.playAlienExplode();
                                    break;

                                case "Player Arrow with Goblin":

                                    break;

                                case "Player Arrow with Troll":
                                    break;

                                case "Player with PassKey":
                                    Log.d("Player", " ");
                                    Level.isLevelFinished = true;
                                    break;

                                case "Player with PowerUp":
                                    go2.setInactive();
                                    //increase score by 100
                                        mGameState.increaseScore();
                                    break;

                                default:
                                    break;
                            }
                        }
                    }
                }
            }
        }
        return playerHit;
    }


}

