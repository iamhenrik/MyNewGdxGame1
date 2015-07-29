package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import sun.rmi.runtime.Log;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameInteger extends GameItem {

    private static final int FRAME_COLS = 1;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private boolean selected = false;
    private int number;
    private BitmapFont font;
    private boolean activated = false;
    private MainGameClass parent;
    public int getNumber() {
        return number;
    }

    public GameInteger(MainGameClass parent, int x, int y, int width, int height, String path, int number) {
        super(x, y, width, height, path);
        this.number = number;
        this.parent = parent;
        walkSheet = new Texture(Gdx.files.internal(path));
        font = new BitmapFont(Gdx.files.internal("fonts/blackOakFont.fnt"), false);

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, (walkSheet.getWidth() / FRAME_COLS), (walkSheet.getHeight() / FRAME_ROWS));

        walkFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS)];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                //if (index<60) {
                walkFrames[index++] = tmp[i][j];
                //}
            }
        }
        walkAnimation = new Animation(0.035f, walkFrames);
        stateTime = 0f;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public boolean isActivated() {

        return activated;
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        if(activated == true) {
            stateTime += Gdx.graphics.getDeltaTime();

            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            handleInput();
            spriteBatch.draw(currentFrame, x, y);

            if (selected) {
                font.draw(spriteBatch, String.valueOf(number), x, 300);
            }
        }
    }

    public void handleInput(){
        if(Gdx.input.isKeyPressed(Input.Keys.N)){
            this.x -= 15;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.M)){
            this.x += 15;
        }
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public boolean tap(float xTapped, float yTapped, int count, int button){

        if(activated==true) {
            float justertY = MainGameClass.WORLD_HEIGHT - this.getY();

            if (xTapped >= this.getX() && xTapped <= (this.getX() + this.getWidth()) && yTapped <= justertY && yTapped >= (justertY - this.getHeight())) {

                this.setSelected(true);
                Gdx.app.log("mygame", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
                this.parent.addToSelectedNumbers(this);
                this.parent.incrementTapCounter();

                //playerNumber++;
                //this.setPlayerNumber(playerNumber);
            }
        }
        return false;

    }
}
