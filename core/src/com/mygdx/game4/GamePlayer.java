package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class GamePlayer extends GameItem {

    public final static int PLAYER_SIZE = 32;
    private boolean isJumping = false;
    private final int stepValue = 6;
    private float angle = 0;
    private int groundLevel = SummerGame4.GROUND_LEVEL;

    private float rotationAngle = 0;

    private int lives = 1;

    public GamePlayer(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        sprite = new Sprite(new Texture(Gdx.files.internal(path)));
    }

    /*
    private boolean overlaps(GamePlatform platform) {
        return (this.x + this.getWidth()/2f >= platform.x && this.x + this.getWidth()/2f <= platform.x + platform.getWidth());
    }*/

    @Override
    public void render(SpriteBatch batch) {
        //Roterer spilleren kontinuerlig:
        float delta = Gdx.graphics.getDeltaTime();
        float speed = 200; //grader per sek?
        rotationAngle = rotationAngle + (speed * delta);
        //Setter vinkelen tilbake til 0 dersom størren enn eller lik 360:
        rotationAngle = rotationAngle % (360);

        batch.draw(sprite, this.x, this.y, width/2f, height/2, width, height, 1, 1, rotationAngle);
        //batch.draw(sprite, this.x, this.y); //Tidligere...
    }

    @Override
    public void update(List<GameItem> gameItems) {
        if (isJumping && angle < Math.PI) {
            this.y = groundLevel + (int)((430f - groundLevel) * Math.sin(angle));
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
                if (pf.getMoveRight()) {
                    this.x += pf.getSpeed();
                    //System.out.println("HEEER!!");
                } else {
                    this.x -= pf.getSpeed();
                    //System.out.println("HEEEEEEEEEEEEEEEEER!!");
                }

                break; //NB!!! ut av løkka!
            } else {
                if (groundLevel >= SummerGame4.GROUND_LEVEL) {
                    groundLevel -= 2;// SummerGame4.GROUND_LEVEL;
                }
                lives--;
            }
        }
    }

    public int getLives() {
        return lives;
    }

    //Sjekker om spilleren er opp på gitt platform:
    private boolean on(GamePlatform platform) {
        return (((this.x + this.getWidth()/2f) >= (platform.x)) && ((this.x + this.getWidth()/2f <= platform.x + platform.getWidth())) &&
                ((this.y >= platform.y)));
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
            //System.out.println("Keys.LEFT");
            if (this.x < 1500) {
                camera.translate(-SummerGame4.CAMERA_PAN_SPEED, 0, 0);
            }
            if (this.x < 0) {
                this.x = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += stepValue;
            //System.out.println("Keys.RIGHT");
            if (this.x > 500) {
                camera.translate(SummerGame4.CAMERA_PAN_SPEED, 0, 0);
            }
            if ((this.x + GamePlayer.PLAYER_SIZE) >= SummerGame4.WORLD_WIDTH) {
                this.x = (SummerGame4.WORLD_WIDTH - GamePlayer.PLAYER_SIZE);
            }
        }

        //Hopp:
        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            //if (!isJumpingDown)
            isJumping = true;
        }


        //Kamerastyring:
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
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, SummerGame4.WORLD_WIDTH / camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, SummerGame4.WORLD_HEIGHT / camera.viewportHeight);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, SummerGame4.WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, SummerGame4.WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }

}
