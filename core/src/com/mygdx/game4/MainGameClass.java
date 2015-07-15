package com.mygdx.game4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.List;

public class MainGameClass extends ApplicationAdapter implements GestureDetector.GestureListener {
    SpriteBatch batch;
    private GamePlayer2 player1;

    private List<GameItem> gamePlatforms;

    private OrthographicCamera camera;
    private float rotationSpeed;
    private Sprite backGround;
    private GameFigure man;
    private GameSwirl swirl;
    private GameSwirl swirl2;
    private GameExplosion boom;
    private GameAnimations ufo;
    private ArrayList<GameNPC> gameNPCs;
    private ArrayList<GameMap> Ambient;

    //	private static final int WORLD_HEIGHT = (int)(100f*(1080f/1920));
    public static final int WORLD_WIDTH = 2700; //1340;
    public static final int WORLD_HEIGHT = 1500; //975;

    public final static int GRAVITY = 10;
    public final static float CAMERA_PAN_SPEED = 18;
    public static final int GROUND_LEVEL = 79;


    @Override
    public void create() {
        Ambient = new ArrayList<GameMap>();
        gamePlatforms = new ArrayList<GameItem>();
        gameNPCs = new ArrayList<GameNPC>();
        rotationSpeed = 0.5f;
        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("MapleBackGroundPng.png"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        man = new GameFigure();
        swirl = new GameSwirl(2350, GROUND_LEVEL, "AlisharAni20Frames3.png");
        swirl2 = new GameSwirl(2450, GROUND_LEVEL, "CashSack.png");
        boom = new GameExplosion(2000, 400);
        ufo = new GameAnimations(2350, 1170);

        GameMap tile1 = new GameMap(0,0,270,79,"TileWithGround.png");
        Ambient.add(tile1);
        GameMap tile2 = new GameMap(270,0,270,79,"TileWithGround.png");
        Ambient.add(tile2);
        GameMap tile3 = new GameMap(540,0, 270,79,"TileWithGround.png");
        Ambient.add(tile3);
        GameMap tile4 = new GameMap(810,0,270,79,"TileWithGround.png");
        Ambient.add(tile4);
        GameMap tile5 = new GameMap(1080,0,270,79,"TileWithGround.png");
        Ambient.add(tile5);
        GameMap tile6 = new GameMap(1350,0,270,79,"TileWithGround.png");
        Ambient.add(tile6);
        GameMap tile7 = new GameMap(1620,0,270,79,"TileWithGround.png");
        Ambient.add(tile7);
        GameMap tile8 = new GameMap(1890,0,270,79,"TileWithGround.png");
        Ambient.add(tile8);
        GameMap tile9 = new GameMap(2160,0,270,79,"TileWithGround.png");
        Ambient.add(tile9);
        GameMap tile10 = new GameMap(2430,0,270,79,"TileWithGround.png");
        Ambient.add(tile10);
        GameMap tile11 = new GameMap(2700,0,270,79,"TileWithGround.png");
        Ambient.add(tile11);


        player1 = new GamePlayer2(GROUND_LEVEL, 300, GamePlayer2.PLAYER_SIZE, GamePlayer2.PLAYER_SIZE, "SlimeAni3.png");

        //GamePlatform platform1 = new GamePlatform(2350, 1200, 300, 30, "SmallPlatform.png", 0, false, 0, 0);
        //gamePlatforms.add(platform1);
        //GamePlatform platform2 = new GamePlatform(700, 440, 300, 30, "SmallPlatform.png", 4, true, 600, 100);
        //gamePlatforms.add(platform2);
        //GamePlatform platform3 = new GamePlatform(1000, 1100, 300, 30, "SmallPlatform.png", 1, false, 0, 0);
        //gamePlatforms.add(platform3);

        //gamePlatforms.add(new GamePlatform(900, 350, 300, 30, "SmallPlatform.png", 3, true, 600, 300));
        //gamePlatforms.add(new GamePlatform(1100, 350, 140, 30, "SmallPlatform.png", 4, true, 600, 400));
        //gamePlatforms.add(new GamePlatform(1300, 350, 300, 30, "SmallPlatform.png", 3, true, 600, 500));
        //gamePlatforms.add(new GamePlatform(1500, 350, 140, 30, "SmallPlatform.png", 4, true, 600, 600));
        //gamePlatforms.add(new GamePlatform(1700, 350, 300, 30, "SmallPlatform.png", 3, true, 600, 700));
        //gamePlatforms.add(new GamePlatform(1900, 350, 140, 30, "SmallPlatform.png", 4, true, 400, 700));
        //gamePlatforms.add(new GamePlatform(2150, 350, 300, 30, "SmallPlatform.png", 3, true, 200, 900));

        //GameNPC npc1 = new GameNPC(platform3,(int) platform2.x, (int)platform2.y, GameNPC.NPC_SIZE, GameNPC.NPC_SIZE, "CashSack.png");
        //gameNPCs.add(npc1);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.zoom = 11f;
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.update();
        Gdx.input.setInputProcessor(new GestureDetector(this));   //sdfdf
    }

    @Override
    public void render() {
        handleInput();
        //Kamera:
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //Flytt spiller og plattformer:

        //for (GameItem gameItem: gamePlatforms) {
        //    gameItem.update();
        //}
        player1.update(gamePlatforms);

        //for(GameNPC gameNPC: gameNPCs){
        //    gameNPC.update();
        //}

        //Render:
        batch.begin();
        backGround.draw(batch);


        //man.render(batch);
        //swirl.render(batch);

        boom.render(batch);
        //ufo.render(batch);
        player1.render(batch);
        swirl2.render(batch);
        for(GameMap GameMap: Ambient){
            GameMap.render(batch);
        }


        for(GameNPC gameNPC: gameNPCs){
            gameNPC.render(batch);
        }
        //for (GameItem gameItem: gamePlatforms) {
        //   batch.draw(gameItem.getSprite(), gameItem.x, gameItem.y);
        //}
        batch.end();

    }

    /*public void jumpspeed() {
        if (isJumpingUp && player1.y < MAX_JUMP_HEIGHT) {

        }
    }*/

    private void handleInput() {
        player1.handleInput(camera);

    }

    @Override
    public void resize(int width, int height) {
        camera.viewportWidth = 300f;
        camera.viewportHeight = 300f * height / width;
        camera.update();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // TODO Auto-generated method stub
        camera.translate(deltaX, deltaY);
        camera.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        // TODO Auto-generated method stub
        float differance = initialDistance - distance;
        if (differance > 0) {
            camera.zoom += 0.02;
        } else {
            camera.zoom -= 0.02;
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }
}
