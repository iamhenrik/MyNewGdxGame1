package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameFigure extends GameItem {

    private static final int FRAME_COLS = 6;
    private static final int FRAME_ROWS = 5;
    private final int RUNNING_LEFT = 1;
    private final int RUNNING_RIGHT = 2;
    private final int STANDING_STILL = 0;
    private Animation walkAnimation;
    private Animation walkAnimationMirrored;
    private Animation walkAnimationStill;
    private Texture walkSheet;
    private Texture walkSheetMirrored;
    private Texture walkSheetStill;
    private TextureRegion[] walkFrames;
    private TextureRegion[] walkFramesMirrored;
    private TextureRegion[] walkFrameStill;

    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private TextureRegion currentFrameMirrored;
    private TextureRegion currentFrameStill;
    private float stateTime;
    private float x = 500;
    private int running = STANDING_STILL;


    public GameFigure() {
        walkSheet = new Texture(Gdx.files.internal("walkFrames.png")); // #9
        walkSheetMirrored = new Texture(Gdx.files.internal("walkFramesMirrored.png"));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);              // #10
        TextureRegion[][] tmpMirrored = TextureRegion.split(walkSheetMirrored, walkSheetMirrored.getWidth() / FRAME_COLS, walkSheetMirrored.getHeight() / FRAME_ROWS);

        walkFrames = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        walkFramesMirrored = new TextureRegion[FRAME_COLS * FRAME_ROWS];
        walkFrameStill = new TextureRegion[1];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                walkFrames[index++] = tmp[i][j];
            }
        }
        int indexMirrored = 0;
        for (int i = FRAME_ROWS - 1; i >= 0; i--) {
            for (int j = FRAME_COLS - 1; j >= 0; j--) {
                walkFramesMirrored[indexMirrored++] = tmpMirrored[i][j];
            }
        }
        walkFrameStill[0] = tmp[2][1];

        walkAnimation = new Animation(0.020f, walkFrames);      // #11
        walkAnimationMirrored = new Animation(0.025f, walkFramesMirrored);
        walkAnimationStill = new Animation(0.025f, walkFrameStill);
        //spriteBatch = new SpriteBatch();                // #12

        stateTime = 0f;                         // #13

    }

    public GameFigure(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);                        // #14
        stateTime += Gdx.graphics.getDeltaTime();           // #15

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        currentFrameMirrored = walkAnimationMirrored.getKeyFrame(stateTime, true);
        currentFrameStill = walkAnimationStill.getKeyFrame(stateTime, true);

        //spriteBatch.begin();
        running = STANDING_STILL;
        KeyInput();
        switch (running) {
            case STANDING_STILL:
                spriteBatch.draw(currentFrameStill, x, MainGameClass.GROUND_LEVEL);
                break;
            case RUNNING_LEFT:
                spriteBatch.draw(currentFrame, x, MainGameClass.GROUND_LEVEL);
                break;
            case RUNNING_RIGHT:
                spriteBatch.draw(currentFrameMirrored, x, MainGameClass.GROUND_LEVEL);
                break;
        }

        if (x >= (MainGameClass.WORLD_WIDTH)) {
            x = MainGameClass.WORLD_WIDTH - MainGameClass.WORLD_WIDTH;
        }
        //spriteBatch.draw(sprite, this.x, this.y, width/2f, height/2, width, height, 1, 1, 0);
        //spriteBatch.end();
    }

    @Override
    public float getX() {
        return x;
    }
    @Override
    public float getY(){
        return y;
    }

    public void KeyInput() {

        if (Gdx.input.isKeyPressed(Input.Keys.Z)) {
            x += 5;
            running = RUNNING_LEFT;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.X)) {
            this.x -= 5;
            running = RUNNING_RIGHT;
        }
    }
}
