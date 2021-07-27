package com.example.gauntlet;

import android.content.Context;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.drawable.LevelListDrawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.Random;

class GameEngine extends SurfaceView implements Runnable, GameStarter, GameEngineBroadcaster, PlayerArrowSpawner,
        AlienArrowSpawner {
    private Thread mThread = null;
    private long mFPS;
    public static boolean isEnemySpawnerHit = false;
    public static int aliensSpawned = 0;

    private ArrayList<InputObserver> inputObservers = new ArrayList();
    UIController mUIController;


    private GameState mGameState;
    private SoundEngine mSoundEngine;
    HUD mHUD;
    Renderer mRenderer;
    PhysicsEngine mPhysicsEngine;
    Level mLevel;
    EnemySpawner mEnemySpawner;


    public GameEngine(Context context, Point size) {
        super(context);

        mUIController = new UIController(this);
        mGameState = new GameState(this, context);
        mSoundEngine = new SoundEngine(context);
        mHUD = new HUD(size);
        mRenderer = new Renderer(this);
        mPhysicsEngine = new PhysicsEngine();


        mLevel = new Level(context,
                new PointF(size.x, size.y), this);

    }
    // For the game engine broadcaster interface
    public void addObserver(InputObserver o) {

        inputObservers.add(o);
    }

    @Override
    public boolean spawnPlayerArrow(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        if (objects.get(Level.mNextPlayerArrow)
                .spawn(transform)) {

            Level.mNextPlayerArrow++;
            mSoundEngine.playShoot();
            if (Level.mNextPlayerArrow ==
                    Level.LAST_PLAYER_ARROW + 1) {

                // Just used the last arrow
                Level.mNextPlayerArrow = Level.FIRST_PLAYER_ARROW;

            }
        }

        return true;
    }

    public void spawnAlienArrow(Transform transform) {
        ArrayList<GameObject> objects = mLevel.getGameObjects();
        // Shoot arrow IF AVAILABLE
        // Pass in the transform of the ship
        // that requested the shot to be fired
        if (objects.get(Level.mNextAlienArrow).spawn(transform)) {
            Level.mNextAlienArrow++;
            mSoundEngine.playShoot();
            if(Level.mNextAlienArrow ==Level.LAST_ALIEN_ARROW + 1) {
                // Just used the last arrow
                Level.mNextAlienArrow = Level.FIRST_ALIEN_ARROW;
            }
        }
    }


    @Override
    public void run() {
        while (mGameState.getThreadRunning()) {
            long frameStartTime = System.currentTimeMillis();
            ArrayList<GameObject> objects = mLevel.getGameObjects();

            if (!mGameState.getPaused()) {
                // Update all the game objects here
                // in a new way

                // This call to update will evolve with the project
                if(mPhysicsEngine.update(mFPS,objects, mGameState, mSoundEngine)){

                    // Player hit
                    deSpawnReSpawn();
                }

                if (aliensSpawned <= 15) {
                    enemySpawner();
                }
            }

            // Draw all the game objects here
            // in a new way
            mRenderer.draw(objects, mGameState, mHUD);

            // Measure the frames per second in the usual way
            long timeThisFrame = System.currentTimeMillis()
                    - frameStartTime;
            if (timeThisFrame >= 1) {
                final int MILLIS_IN_SECOND = 1000;
                mFPS = MILLIS_IN_SECOND / timeThisFrame;
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        // Handle the player's input here
        // But in a new way
        for (InputObserver o : inputObservers) {
            o.handleInput(motionEvent, mGameState,
                    mHUD.getControls());
        }

        return true;
    }

    public void stopThread() {
        // New code here soon
        mGameState.stopEverything();
        try {
            mThread.join();
        } catch (InterruptedException e) {
            Log.e("Exception","stopThread()"
                    + e.getMessage());
        }
    }

    public void startThread() {
        // New code here soon
        mGameState.startThread();
        mThread = new Thread(this);
        mThread.start();
    }

    public void enemySpawner() {

        ArrayList<GameObject> objects = mLevel.getGameObjects();
        Random random = new Random();
        int SPAWN_VALUE = 5;
        int PROB_RANGE = 10;
        int GIVEN_VALUE = 0;

        GIVEN_VALUE = random.nextInt(10);


        if(GIVEN_VALUE == SPAWN_VALUE) {
            //objects.get(Level.GHOST_FIRST + aliensSpawned).spawn(objects.get(Level.PLAYER_INDEX).getTransform());


            if (aliensSpawned < 5) {
                objects.get(Level.GHOST_FIRST + aliensSpawned).spawn(objects.get(Level.PLAYER_INDEX).getTransform());

                objects.get(Level.GHOST_FIRST + aliensSpawned).getTransform().setLocation(EnemySpawner.enemySpawnerLocOne.x, EnemySpawner.enemySpawnerLocOne.y);

            }

            else if (aliensSpawned < 10) {
                objects.get(Level.GHOST_FIRST + aliensSpawned).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
                objects.get(Level.GHOST_FIRST + aliensSpawned).getTransform().setLocation(EnemySpawner.enemySpawnerLocTwo.x, EnemySpawner.enemySpawnerLocTwo.y);
            }

            else if (aliensSpawned < 15) {
                objects.get(Level.GHOST_FIRST + aliensSpawned).spawn(objects.get(Level.PLAYER_INDEX).getTransform());
                objects.get(Level.GHOST_FIRST + aliensSpawned).getTransform().setLocation(EnemySpawner.enemySpawnerLocThree.x, EnemySpawner.enemySpawnerLocThree.y);
            }


            aliensSpawned++;
        }


    }

    public void deSpawnReSpawn() {
        // Eventually this will de spawn
        // and then respawn all the game objects
        ArrayList<GameObject> objects = mLevel.getGameObjects();

        for(GameObject o : objects){
            o.setInactive();
        }
        objects.get(Level.PLAYER_INDEX)
                .spawn(objects.get(Level.PLAYER_INDEX)
                        .getTransform());

        objects.get(Level.BACKGROUND_INDEX)
                .spawn(objects.get(Level.PLAYER_INDEX)
                        .getTransform());


        Transform.resetRelativeLocation();




    }


}
