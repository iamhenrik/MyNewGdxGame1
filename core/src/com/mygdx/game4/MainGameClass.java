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
    private GamePlayer player1;

    private List<GameItem> gameItems;

    private OrthographicCamera camera;
    private float rotationSpeed;
    private Sprite backGround;
    private GameFigure man;
    private GameSwirl swirl;
    private GameExplosion boom;
    private GameAnimations ufo;
    //	private static final int WORLD_HEIGHT = (int)(100f*(1080f/1920));
    public static final int WORLD_WIDTH = 3000;
    public static final int WORLD_HEIGHT = 1500;

    public final static int GRAVITY = 10;
    public final static float CAMERA_PAN_SPEED = 18;
    public static final int GROUND_LEVEL = 125;


    @Override
    public void create() {
        gameItems = new ArrayList<GameItem>();

        rotationSpeed = 0.5f;
        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("BackGround.jpg"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        man = new GameFigure();
        swirl = new GameSwirl(2350, 1205);
        boom = new GameExplosion(800, 700);
        ufo = new GameAnimations(600, 1000);
        player1 = new GamePlayer(750, 900, GamePlayer.PLAYER_SIZE, GamePlayer.PLAYER_SIZE, "player2.png");
        gameItems.add(new GamePlatform(2350, 1200, 300, 30, "SmallPlatform.png", 0, false,0,0));
        //gameItems.add(new GamePlatform(1000, 1100, 300, 30, "SmallPlatform.png", 6, false,0,0));

        //gameItems.add(new GamePlatform(300, 350, 140, 30, "SmallPlatform.png", 5, true, 1300));
        //gameItems.add(new GamePlatform(500, 350, 140, 30, "SmallPlatform.png", 8, true, 1300));
        gameItems.add(new GamePlatform(700, 440, 140, 30, "SmallPlatform.png", 4, true, 600,100));
        gameItems.add(new GamePlatform(900, 350, 140, 30, "SmallPlatform.png", 3, true, 600, 300));
        gameItems.add(new GamePlatform(1100, 350, 140, 30, "SmallPlatform.png", 4, true, 600, 400));
        gameItems.add(new GamePlatform(1300, 350, 140, 30, "SmallPlatform.png", 3, true, 600, 500));
        gameItems.add(new GamePlatform(1500, 350, 140, 30, "SmallPlatform.png", 4, true, 600, 600));
        gameItems.add(new GamePlatform(1700, 350, 140, 30, "SmallPlatform.png", 3, true, 600, 700));
        gameItems.add(new GamePlatform(1900, 350, 140, 30, "SmallPlatform.png", 4, true, 400, 700));
        gameItems.add(new GamePlatform(2150, 350, 140, 30, "SmallPlatform.png", 3, true, 200, 900));

        //gameItems.add(new GamePlatform(270, 280, 240, 30, "SmallPlatform.png", 4, false));
        //gameItems.add(new GamePlatform(130, 230, 180, 30, "SmallPlatform.png", 2, false));



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

        for (GameItem gameItem: gameItems) {
            gameItem.update();
        }
        player1.update(gameItems);

        //Render:
        batch.begin();
        backGround.draw(batch);


        man.render(batch);
        swirl.render(batch);
        boom.render(batch);
        ufo.render(batch);
        player1.render(batch);
        for (GameItem gameItem: gameItems) {
           batch.draw(gameItem.getSprite(), gameItem.x, gameItem.y);
        }
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
