package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameSwirl extends GameItem {

    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private float x;
    private float y;
    private String path;

    public GameSwirl(float x, float y, String path) {
        this.x = x;
        this.y = y;
        this.path = path;
        walkSheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, (walkSheet.getWidth() / FRAME_COLS), (walkSheet.getHeight() / FRAME_ROWS));

        walkFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS - 0)];


        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                //if (index<14) {
                    walkFrames[index++] = tmp[i][j];
                //}
            }
        }
        walkAnimation = new Animation(0.1f, walkFrames);
        //spriteBatch = new SpriteBatch();

        stateTime = 0f;

    }
    public void ScaleShit(float value){
        getSprite().scale(value);
    }

    public GameSwirl(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);

    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);
        handleInput();
        spriteBatch.draw(currentFrame, x, y);


        //spriteBatch.draw(sprite, this.x, this.y, width/2f, height/2, width, height, 1, 1, 0);
        //spriteBatch.end();
    }
    public void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            this.x -= 15;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            this.x += 15;
        }
    }
}
