package com.example.gauntlet;

import android.content.Context;
import android.graphics.PointF;

class GameObjectFactory {
    private Context mContext;
    private PointF mScreenSize;
    private GameEngine mGameEngineReference;

    GameObjectFactory(Context c, PointF screenSize,
                      GameEngine gameEngine) {

        this.mContext = c;
        this.mScreenSize = screenSize;
        mGameEngineReference = gameEngine;
    }

    GameObject create(ObjectSpec spec) {

        GameObject object = new GameObject();

        int numComponents = spec.getComponents().length;

        final float HIDDEN = -2000f;

        object.setmTag(spec.getTag());

        // Configure the speed relative to the screen size
        float speed = spec.getSpeed();

        // Configure the object size relative to screen size
        PointF objectSize =
                new PointF(mScreenSize.x / spec.getScale().x,
                        mScreenSize.y / spec.getScale().y);

        // Set the location to somewhere off-screen
        PointF location = new PointF(HIDDEN, HIDDEN);

        object.setTransform(new Transform(speed, objectSize.x,
                objectSize.y, location, mScreenSize));

        // More code here next...
        // Loop through and add/initialize all the components
        for (int i = 0; i < numComponents; i++) {
            switch (spec.getComponents()[i]) {
                case "PlayerInputComponent":
                    object.setInput(new PlayerInputComponent
                            (mGameEngineReference));
                    break;

                case "ArrowGraphicsComponent":
                    object.setGraphics(new ArrowGraphicsComponent(), mContext, spec, objectSize);

                    break;

                case "StdGraphicsComponent":
                    object.setGraphics(new StdGraphicsComponent(),
                            mContext, spec, objectSize);


                    break;

                case "SpriteGraphicsComponent":
                    object.setGraphics(new SpriteGraphicsComponent(),
                            mContext, spec, objectSize);
                    break;
                case "PlayerAnimationComponent":
                    object.setAnimation(new PlayerAnimationComponent(),mContext, spec);
                    break;
                case "PlayerMovementComponent":
                    object.setMovement(new PlayerMovementComponent(mContext,mScreenSize ));
                    break;
                case "ArrowMovementComponent":
                    object.setMovement(new ArrowMovementComponent());
                    break;

                case "NPCGraphicsComponent":
                    object.setGraphics(new NPCGraphicsComponent(), mContext, spec, objectSize);

                    break;

                case "PlayerSpawnComponent":
                    object.setSpawner(new PlayerSpawnComponent());
                    break;
                case "ArrowSpawnComponent":
                    object.setSpawner(new ArrowSpawnComponent());
                    break;
                case "BackgroundGraphicsComponent":
                    object.setGraphics(new BackgroundGraphicsComponent(),
                            mContext, spec, objectSize);
                    break;
                case "BackgroundMovementComponent":
                    object.setMovement(new BackgroundMovementComponent());
                    break;

                case "SimpleMovementComponent":
                    object.setMovement(new SimpleMovementComponent());
                    break;

                case "BackgroundSpawnComponent":
                    object.setSpawner(new BackgroundSpawnComponent());
                    break;

                case "GhostChaseMovementComponent":
                    object.setMovement(new GhostChaseMovementComponent(mGameEngineReference));
                    break;

                case "GhostSpawnComponent":
                    object.setSpawner(new GhostSpawnComponent());
                    break;

                case "GoblinMovementComponent":
                    object.setMovement(new GoblinMovementComponent(mGameEngineReference));
                    break;

                case "GoblinSpawnComponent":
                    object.setSpawner(new GoblinSpawnComponent());
                    break;
                case "PassKeySpawnComponent":
                    object.setSpawner(
                            new PassKeySpawnComponent());
                    break;

                case "TrollMovementComponent":
                    object.setMovement(
                            new TrollMovementComponent(
                                    mGameEngineReference));
                    break;

                case "TrollSpawnComponent":
                    object.setSpawner(
                            new TrollSpawnComponent());
                    break;

                case "DoorSpawnComponent":
                    object.setSpawner(
                            new DoorSpawnComponent());
                    break;

                case "PowerUpSpawnComponent":
                    object.setSpawner(new PowerUpSpawnComponent());
                    break;


                default:
                    // Error unidentified component
                    System.out.println("NOT LOADEDED PROPERLY");
                    break;
            }
        }
        // Return the completed GameObject to the Level class
        return object;

    }

}

