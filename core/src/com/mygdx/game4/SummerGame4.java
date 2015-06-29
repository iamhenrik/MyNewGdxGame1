package com.mygdx.game4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.input.GestureDetector;

import java.util.ArrayList;
import java.util.List;

public class SummerGame4 extends ApplicationAdapter implements GestureDetector.GestureListener {
    SpriteBatch batch;
    Texture img;
    private GamePlayer player1;
    private GamePlayer platform1;

    private List<GameItem> gameItems;

    private OrthographicCamera camera;
    private float rotationSpeed;
    private Sprite backGround;
    //	private static final int WORLD_HEIGHT = (int)(100f*(1080f/1920));
    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_HEIGHT = 512;

    public final static int GRAVITY = 10;

    private final float CAMERA_PAN_SPEED = 18;
    public static final int GROUND_LEVEL = 200;

    private final int MOVE_SPEED = 4;
    private boolean MOVE_RIGHT = true;


    private int jumpSpeed = GRAVITY;

    @Override
    public void create() {
        gameItems = new ArrayList<GameItem>();

        rotationSpeed = 0.5f;
        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("panormaBackGroundScaled.png"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);


        player1 = new GamePlayer(200, GROUND_LEVEL, GamePlayer.PLAYER_SIZE, GamePlayer.PLAYER_SIZE, "player2.png");
        platform1 = new GamePlayer(500, 270, PLATFORM_WIDHT, PLATFORM_HEIGHT, "SmallPlatform.png");
        gameItems.add(platform1);

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
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        player1.render(gameItems);

        batch.begin();
        backGround.draw(batch);
        if (MOVE_RIGHT == true) {
            platform1.x += MOVE_SPEED;
        } else {
            platform1.x -= MOVE_SPEED;
        }

        if (platform1.x + PLATFORM_WIDHT >= WORLD_WIDTH) {
            MOVE_RIGHT = false;
        }
        if (platform1.x <= 0) {
            MOVE_RIGHT = true;
        }
        if (player1.y >= (platform1.y + PLATFORM_HEIGHT) && player1.x >= platform1.x && player1.x <= platform1.x + PLATFORM_WIDHT && MOVE_RIGHT == true) {
            player1.x += MOVE_SPEED;
        }
        if (player1.y >= (platform1.y + PLATFORM_HEIGHT) && player1.x >= platform1.x && player1.x <= platform1.x + PLATFORM_WIDHT && MOVE_RIGHT == false) {
            player1.x -= MOVE_SPEED;
        }
        if (player1.y >= (platform1.y + PLATFORM_HEIGHT) && player1.x >= platform1.x && player1.x <= platform1.x + PLATFORM_WIDHT) {
//			isJumpingDown = false;
//			isJumpingUp = false;
        }

//		player1.x += (-MOVE_SPEED);

        batch.draw(player1.getImage(), player1.x, player1.y);
        batch.draw(platform1.getImage(), platform1.x, platform1.y);
        batch.end();
//		System.out.println("player1 x verdi: " + player1.x + "y verdi: "+ player1.y);
    }

    public void jumpspeed() {
        if (isJumpingUp && player1.y < MAX_JUMP_HEIGHT) {

        }
    }

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
