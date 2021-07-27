package com.example.gauntlet;

import android.content.Context;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Hashtable;

public class GameMap {

    // 0 - movable space
    // 1 - walls
    // 5 - enemy spawner
    public static int[][] mMapMatrix;
    private static  int MAP_ROWS;
    private static  int MAP_COLS;

    public static ArrayList<Obstacle> obstacleContainer = new ArrayList<Obstacle>();
    public static ArrayList<Obstacle> enemySpawnerContainer = new ArrayList<Obstacle>();
    private Point lowResFactor;
    private SpatialCollision localCollisionComponent;


    GameMap(Context c) {
        lowResFactor = new Point(GameData.LOWRES_CONV_FACTOR_X,
                                 GameData.LOWRES_CONV_FACTOR_X);

        MAP_ROWS = GameData.ROWS;
        MAP_COLS = GameData.COLS;
        mMapMatrix = new int[MAP_ROWS][MAP_COLS];

        localCollisionComponent = new SpatialCollision();

        int currentRow = 0;
        String line;
        int count=0;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    c.getAssets().open(
                            Level.dungeonMaps[1])));

            while( (line = reader.readLine()) != null){
                String[] stringArray = line.split(" ");

                RectF obstacleRect;
                for(int i=0; i<stringArray.length; i++){
                    mMapMatrix[currentRow][i] = Integer.parseInt(stringArray[i]);
                    if (mMapMatrix[currentRow][i] == 1) {
                                obstacleRect = new RectF(((i * lowResFactor.x)), (currentRow * lowResFactor.y),
                                        (i * lowResFactor.x) + 160, (currentRow * lowResFactor.y) + 160);

                                SpatialCollision.loadQuadrants(obstacleRect);



//                        if(count++ == 0){
                        // Pass in the RectF and Point
                        obstacleContainer.add(new Obstacle(obstacleRect));
                        //gameHashTable.put(new Point(currentRow,i), obstacleRect);

//                        }

                    }

                    else if(mMapMatrix[currentRow][i] == 5){
                        enemySpawnerContainer.add(new Obstacle(new RectF(((i * lowResFactor.x)), (currentRow * lowResFactor.y),
                                (i * lowResFactor.x) + 160, (currentRow * lowResFactor.y) + 160)));

                        if (EnemySpawner.numEnemySpawners == 0) {
                            EnemySpawner.enemySpawnerLocOne.x = i * lowResFactor.x;
                            EnemySpawner.enemySpawnerLocOne.y = currentRow * lowResFactor.y;
                        }

                        else if (EnemySpawner.numEnemySpawners == 1) {
                            EnemySpawner.enemySpawnerLocTwo.x = i * lowResFactor.x;
                            EnemySpawner.enemySpawnerLocTwo.y = currentRow * lowResFactor.y;
                        }

                        else if (EnemySpawner.numEnemySpawners == 2) {
                            EnemySpawner.enemySpawnerLocThree.x = i * lowResFactor.x;
                            EnemySpawner.enemySpawnerLocThree.y = currentRow * lowResFactor.y;
                        }

                        EnemySpawner.numEnemySpawners++;



                    }

                }


                currentRow++;


            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
