package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;

public class GamePlayer extends GameItem {

    public final static int PLAYER_SIZE = 32;
    private boolean isJumpingUp = false;
    private boolean isJumpingDown = false;
    private final int MAX_JUMP_HEIGHT = 400;
    private final int stepValue = 6;
    private final float CAMERA_PAN_SPEED = 18;


    public GamePlayer(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        image = new Sprite(new Texture(Gdx.files.internal(path)));
    }

    @Override
    public void update(GamePlayer player1) {
    }

    public void update(ArrayList<GameItem> gameItems) {
        for (int i = 0; i < gameItems.size(); i++) {

            GamePlatform platform1 = (GamePlatform) gameItems.get(i);
            if (isJumpingUp) {
                this.y += SummerGame4.GRAVITY;
            }
            if (this.y <= SummerGame4.GROUND_LEVEL
                    || this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                    && (this.x < platform1.x + platform1.getWidth())) {
                isJumpingUp = false;

                if (this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                        && (this.x < platform1.x + platform1.getWidth())) {
                    isJumpingDown = true;
                }
                //oppaa platformen
            }
            if (isJumpingUp && this.y < MAX_JUMP_HEIGHT) {
                this.y += SummerGame4.GRAVITY;

                if (this.y == MAX_JUMP_HEIGHT ||
                        this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                                && (this.x < platform1.x + platform1.getWidth())) {
                    isJumpingUp = false;
                    isJumpingDown = true;
                }
            }

            if (isJumpingDown && this.y >= SummerGame4.GROUND_LEVEL ||
                    this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                            && (this.x < platform1.x + platform1.getWidth())) {
                this.y -= SummerGame4.GRAVITY;
                if (this.y <= SummerGame4.GROUND_LEVEL) {
                    isJumpingDown = false;
                }
            }
            if ((this.y) <= (platform1.y + platform1.getHeight())
                    && this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                    && (this.x < platform1.x + platform1.getWidth())
                    ) {

                this.y = platform1.y + platform1.getHeight();
                System.out.println(this.x + " : " + platform1.x);

                if (this.y == (platform1.y + platform1.getHeight()) && this.x + GamePlayer.PLAYER_SIZE >= platform1.x
                        && (this.x < platform1.x + platform1.getWidth())) {
//					this.x += MOVE_SPEED;
                    isJumpingUp = false;
                    ;
                    System.out.println(isJumpingUp);
                    System.out.println(isJumpingDown);
                }
            }
        }
    }
    public void handleInput(OrthographicCamera camera) {
        movementUpthis();
        movementDownthis();
        movementLeftthis(camera);
        movementRightthis(camera);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            camera.translate(-9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            camera.translate(9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
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
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, SummerGame4.WORLD_WIDTH / camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, SummerGame4.WORLD_HEIGHT / camera.viewportHeight);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, SummerGame4.WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, SummerGame4.WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

    public void movementUpthis() {
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            this.y += stepValue;
            if ((this.y + GamePlayer.PLAYER_SIZE) >= (SummerGame4.WORLD_HEIGHT)) {
                this.y = SummerGame4.WORLD_HEIGHT - GamePlayer.PLAYER_SIZE;
            }
        }
    }

    public void movementDownthis() {
        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.y -= stepValue;
            if (this.y <= 0) {
                this.y = 0;
            }
        }
    }

    public void movementLeftthis(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= stepValue;
            if (this.x < 1500) {
                camera.translate(-CAMERA_PAN_SPEED, 0, 0);
            }
            if (this.x < 0) {
                this.x = 0;
            }
        }
    }

    public void movementRightthis(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += stepValue;
            if (this.x > 500) {
                camera.translate(CAMERA_PAN_SPEED, 0, 0);
            }
            if ((this.x + GamePlayer.PLAYER_SIZE) >= SummerGame4.WORLD_WIDTH) {
                this.x = (SummerGame4.WORLD_WIDTH - GamePlayer.PLAYER_SIZE);
            }
        }
    }
}
