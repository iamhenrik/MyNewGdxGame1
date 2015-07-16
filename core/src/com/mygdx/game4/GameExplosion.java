package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameExplosion extends GameItem {

    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 8;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private float xPosition;
    private float yPosition;
    private boolean active = false;
    //private Timer timer;

    public boolean isActive() {
        return active;
    }
    public void startExplosion(){
        active = true;

        final Timer timer = new Timer();
        System.out.println(System.currentTimeMillis());

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                active = false;
                System.out.println(System.currentTimeMillis());
                timer.cancel();
            }
        },955 );
    }
    public GameExplosion(float xPosition, float yPosition) {

        this.xPosition = xPosition;
        this.yPosition = yPosition;
        walkSheet = new Texture(Gdx.files.internal("Explosion.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        walkFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS)];


        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                    walkFrames[index++] = tmp[i][j];
            }
        }
        walkAnimation = new Animation(0.015f, walkFrames);
        //spriteBatch = new SpriteBatch();

        stateTime = 0f;

    }
    @Override
    public void handleInput(OrthographicCamera camera) {
        if(Gdx.input.isKeyJustPressed(Input.Keys.B)){
           startExplosion();
        }
    }

    public GameExplosion(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        handleInput(null);
        if(active == true) {
            stateTime += Gdx.graphics.getDeltaTime();

            currentFrame = walkAnimation.getKeyFrame(stateTime, true);

            spriteBatch.draw(currentFrame, xPosition, yPosition);
        }

        //spriteBatch.draw(sprite, this.x, this.y, width/2f, height/2, width, height, 1, 1, 0);
        //spriteBatch.end();
    }
}
