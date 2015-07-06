package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class GamePlayer extends GameItem {

    public final static int PLAYER_SIZE = 32;
    private boolean isJumping = false;
    private final int stepValue = 12;

    public GamePlayer(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        image = new Sprite(new Texture(Gdx.files.internal(path)));
    }

    @Override
    public void update(GamePlayer player1) {

    }

    private float angle = 0;
    private int groundLevel = SummerGame4.GROUND_LEVEL;

    /*
    private boolean overlaps(GamePlatform platform) {
        return (this.x + this.getWidth()/2f >= platform.x && this.x + this.getWidth()/2f <= platform.x + platform.getWidth());
    }*/

    private boolean on(GamePlatform platform) {
        return (((this.x + this.getWidth()/2f) >= (platform.x)) && ((this.x + this.getWidth()/2f <= platform.x + platform.getWidth())) &&
                ((this.y >= platform.y)));
    }

    @Override
    public void update(List<GameItem> gameItems) {
        if (isJumping && angle < Math.PI) {
            this.y = groundLevel + (int)((330f) * Math.sin(angle));
            angle += (8)*(Math.PI/180f);
        } else {
            this.y = groundLevel;
            angle = 0;
            isJumping = false;
        }

        //Sjekker seg selv mot alle plattformer. Dersom på en platform sjekkes ikke resten...
        for (int i = 0; i < gameItems.size(); i++) {
            GamePlatform pf = (GamePlatform) gameItems.get(i);

            //På platformen:
            if (this.on(pf)) {
                groundLevel = (int) pf.y + (int) pf.getHeight();

                //Spilleren skal bevege seg etter platformen:
                if(pf.isHorisontalFalseVerticalTrue()==false){
                    if (pf.getMoveRight())
                        this.x += pf.getSpeed();
                    else
                        this.x -= pf.getSpeed();

                    break; //NB!!! ut av løkka!
                }else {
                    if (pf.isMoveUp())
                        this.y += pf.getSpeed();
                    else
                        this.y -= pf.getSpeed();

                    break; //NB!!! ut av løkka!
                }
            } else {
                if(groundLevel >= SummerGame4.GROUND_LEVEL){
                    groundLevel -= 5;
                }
            }
        }
    }

    @Override
    public void handleInput(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            this.y += stepValue;
            if ((this.y + GamePlayer.PLAYER_SIZE) >= (SummerGame4.WORLD_HEIGHT)) {
                this.y = SummerGame4.WORLD_HEIGHT - GamePlayer.PLAYER_SIZE;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.y -= stepValue;
            if (this.y <= 0) {
                this.y = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= stepValue;
            if (this.x < 1500) {
                camera.translate(-SummerGame4.CAMERA_PAN_SPEED, 0, 0);
            }
            if (this.x < 0) {
                this.x = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += stepValue;
            if (this.x > 500) {
                camera.translate(SummerGame4.CAMERA_PAN_SPEED, 0, 0);
            }
            if ((this.x + GamePlayer.PLAYER_SIZE) >= SummerGame4.WORLD_WIDTH) {
                this.x = (SummerGame4.WORLD_WIDTH - GamePlayer.PLAYER_SIZE);
            }
        }

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
            //if (!isJumpingDown)
            isJumping = true;
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

}
