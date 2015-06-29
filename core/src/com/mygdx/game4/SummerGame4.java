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
import com.badlogic.gdx.input.GestureDetector;;

public class SummerGame4 extends ApplicationAdapter implements GestureDetector.GestureListener {
    SpriteBatch batch;
    Texture img;
    private GamePlayer player1;
    private GamePlayer platform1;
    private final int stepValue = 6;
    private OrthographicCamera camera;
    private float rotationSpeed;
    private Sprite backGround;
    //	private static final int WORLD_HEIGHT = (int)(100f*(1080f/1920));
    public static final int WORLD_WIDTH = 2048;
    public static final int WORLD_HEIGHT = 512;

    private final int GRAVITY = 10;
    private final int MAX_JUMP_HEIGHT = 400;
    private final float CAMERA_PAN_SPEED = 18;
    private final int GROUND_LEVEL = 200;
    private final int PLATFORM_HEIGHT = 30;
    private final int PLATFORM_WIDHT = 240;
    private final int MOVE_SPEED = 4;
    private boolean MOVE_RIGHT = true;

    private boolean isJumpingUp = false;
    private boolean isJumpingDown = false;

    private int jumpSpeed = GRAVITY;

    @Override
    public void create() {
        rotationSpeed = 0.5f;
        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("panormaBackGroundScaled.png"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);


        player1 = new GamePlayer(200, GROUND_LEVEL, GamePlayer.PLAYER_SIZE, GamePlayer.PLAYER_SIZE, "player2.png");
        platform1 = new GamePlayer(500, 270, PLATFORM_WIDHT, PLATFORM_HEIGHT, "SmallPlatform.png");
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
//		Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        CheckCollisions();

        batch.begin();
        backGround.draw(batch);


//		player1.x += (-MOVE_SPEED);

        batch.draw(player1.getImage(), player1.x, player1.y);
        batch.draw(platform1.getImage(), platform1.x, platform1.y);
        batch.end();
//		System.out.println("player1 x verdi: " + player1.x + "y verdi: "+ player1.y);
    }
    public void CheckCollisions(){
        if (MOVE_RIGHT == true) {
            platform1.x += MOVE_SPEED;
        } else {
            platform1.x -= MOVE_SPEED;
        }
        if (isJumpingUp) {
            player1.y += GRAVITY;
        }
//			double part1 = (player1.x/200)*Math.tan(Math.PI/2.2);
//			double part2 = 9.81*Math.pow((player1.x/200), 2);
//			double part3 = 2*(5*Math.cos(Math.PI/3));
//
//			player1.y = (int)(-1*((part1 - (part2/part3))));
//			System.out.println("player1.y: " +player1.y + "player1.x: " + player1.x);
//		}
        if (player1.y <= GROUND_LEVEL
                || player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                && (player1.x < platform1.x + PLATFORM_WIDHT)) {
            isJumpingUp = false;

            if (player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                    && (player1.x < platform1.x + PLATFORM_WIDHT)) {
                isJumpingDown = true;
            }
            //oppaa platformen
        }
        if (isJumpingUp && player1.y < MAX_JUMP_HEIGHT) {
            player1.y += GRAVITY;
//			double part1 = player1.x*Math.tan(Math.PI/2);
//			double part2 = 9.81*Math.pow(player1.x, 2);
//			double part3 = 2*(22*Math.cos(Math.PI/2));
//			player1.y = (int)(part1 - (part2/part3));
//
            if (player1.y == MAX_JUMP_HEIGHT ||
                    player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                            && (player1.x < platform1.x + PLATFORM_WIDHT)) {
                isJumpingUp = false;
                isJumpingDown = true;
            }
        }

        if (isJumpingDown && player1.y >= GROUND_LEVEL ||
                player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                        && (player1.x < platform1.x + PLATFORM_WIDHT)) {
            player1.y -= GRAVITY;
            if (player1.y <= GROUND_LEVEL) {
                isJumpingDown = false;
            }
        }
        if ((player1.y) <= (platform1.y + PLATFORM_HEIGHT)
                && player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                && (player1.x < platform1.x + PLATFORM_WIDHT)
                ) {

            player1.y = platform1.y + PLATFORM_HEIGHT;
            System.out.println(player1.x + " : " + platform1.x);

            if (player1.y == (platform1.y + PLATFORM_HEIGHT) && player1.x + GamePlayer.PLAYER_SIZE >= platform1.x
                    && (player1.x < platform1.x + PLATFORM_WIDHT)) {
//					player1.x += MOVE_SPEED;
                isJumpingUp = false;
                ;
                System.out.println(isJumpingUp);
                System.out.println(isJumpingDown);
            }
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
    }

    public void jumpspeed() {
        if (isJumpingUp && player1.y < MAX_JUMP_HEIGHT) {

        }
    }

    private void handleInput() {
        movementUpPlayer1();
        movementDownPlayer1();
        movementLeftPlayer1();
        movementRightPlayer1();
        if (Gdx.input.isKeyPressed(Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Keys.G)) {
            camera.translate(-9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.J)) {
            camera.translate(9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            if (!isJumpingDown)
                isJumpingUp = true;
        }
//	        if (Gdx.input.isKeyPressed(Keys.W)) {
//	        	camera.rotate(-rotationSpeed, 0, 0, 1);
//	        }
//	        if (Gdx.input.isKeyPressed(Keys.E)) {
//	        	camera.rotate(rotationSpeed, 0, 0, 1);
//	        }

//	        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, WORLD_WIDTH / camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, WORLD_HEIGHT / camera.viewportHeight);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

    public void movementUpPlayer1() {
        if (Gdx.input.isKeyPressed(Keys.Y)) {
            player1.y += stepValue;
            if ((player1.y + GamePlayer.PLAYER_SIZE) >= (WORLD_HEIGHT)) {
                player1.y = WORLD_HEIGHT - GamePlayer.PLAYER_SIZE;
            }
        }
    }

    public void movementDownPlayer1() {
        if (Gdx.input.isKeyPressed(Keys.H)) {
            player1.y -= stepValue;
            if (player1.y <= 0) {
                player1.y = 0;
            }
        }
    }

    public void movementLeftPlayer1() {
        if (Gdx.input.isKeyPressed(Keys.LEFT)) {
            player1.x -= stepValue;
            if (player1.x < 1500) {
                camera.translate(-CAMERA_PAN_SPEED, 0, 0);
            }
            if (player1.x < 0) {
                player1.x = 0;
            }
        }
    }

    public void movementRightPlayer1() {
        if (Gdx.input.isKeyPressed(Keys.RIGHT)) {
            player1.x += stepValue;
            if (player1.x > 500) {
                camera.translate(CAMERA_PAN_SPEED, 0, 0);
            }
            if ((player1.x + GamePlayer.PLAYER_SIZE) >= WORLD_WIDTH) {
                player1.x = (WORLD_WIDTH - GamePlayer.PLAYER_SIZE);
            }
        }
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
