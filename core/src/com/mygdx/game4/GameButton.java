package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameButton extends GameItem {

    private static final int FRAME_COLS = 1;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private boolean clicked = false;

    public boolean isClicked() {
        return clicked;
    }

    public GameButton(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        walkSheet = new Texture(Gdx.files.internal(path));
        TextureRegion[][] tmp = TextureRegion.split(walkSheet, (walkSheet.getWidth() / FRAME_COLS), (walkSheet.getHeight() / FRAME_ROWS));
        walkFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS)-0];

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

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }
    @Override
    public void render(SpriteBatch spriteBatch) {
        if (!clicked) {
            stateTime += Gdx.graphics.getDeltaTime();
            currentFrame = walkAnimation.getKeyFrame(stateTime, true);
            //handleInput();
            spriteBatch.draw(currentFrame, x, y);
        }
    }
    public void backAndForth(){

    }
    public void handleInput(){
        //if(this.x >= MainGameClass.WORLD_WIDTH){
            this.x -= 15;
        //if(this.x +150 <= MainGameClass.WORLD_WIDTH){
                this.x += 15;
          //  }
        //}
    }

    public boolean tap(float xTapped, float yTapped, int count, int button){
        float justertY = MainGameClass.WORLD_HEIGHT - this.getY();
        //font.draw(spriteBatch, "HELLO");

        if(xTapped >= this.getX() && xTapped <= (this.getX()+this.getWidth()) && yTapped <= justertY && yTapped >= (justertY - this.getHeight())) {

            this.clicked = true;
            //System.out.println("X Y " + number);
            Gdx.app.log("mygame", "@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
            //playerNumber++;
            //this.setPlayerNumber(playerNumber);
        }
        return false;
    }
}
