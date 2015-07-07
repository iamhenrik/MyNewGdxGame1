package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;


/**
 * Created by Henrik on 25.06.2015.
 */
public class GamePlatform extends GameItem {
    private int speed = 4;
    private boolean HorisontalFalseVerticalTrue;
    private int maxHeight;
    private int minHeight;

    private boolean moveRight = true;
    private boolean moveUp = false;

    public int getMinHeight() {
        return minHeight;
    }
    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }
    public boolean isHorisontalFalseVerticalTrue() {
        return HorisontalFalseVerticalTrue;
    }
    public void setHorisontalFalseVerticalTrue(boolean horisontalFalseVerticalTrue) {
        HorisontalFalseVerticalTrue = horisontalFalseVerticalTrue;
    }
    public int getMaxHeight() {
        return maxHeight;
    }



    public GamePlatform(int x, int y, int width, int height, String path, int speed, boolean HorisontalFalseVerticalTrue, int maxHeight, int minHeight) {
        super(x, y, width, height, path);

        this.speed = speed;
        this.HorisontalFalseVerticalTrue = HorisontalFalseVerticalTrue;
        this.maxHeight = maxHeight;
        this.minHeight = minHeight;
        sprite = new Sprite(new Texture(Gdx.files.internal(path)));
        sprite.setRegionWidth(width);
        sprite.setRegionHeight(height);
    }

    public int getSpeed() {
        return speed;
    }

    public boolean getMoveRight() {
        return moveRight;
    }

    @Override
    public void update() {
        if(HorisontalFalseVerticalTrue == false){
            if (this.x + width >= SummerGame4.WORLD_WIDTH) {
                moveRight = false;
            }
            if (this.x <= 0) {
                moveRight = true;
            }
            if (moveRight == true) {
                this.x += speed;
            } else {
                this.x -= speed;
            }
        }else{
            if(this.y+ height >= (SummerGame4.WORLD_HEIGHT -getMaxHeight()))
                moveUp = false;
            if((this.y)<= (SummerGame4.GROUND_LEVEL+getMinHeight()))
                moveUp = true;
            if(moveUp == true){
                this.y += speed;
            }else{
                this.y -= speed;
            }
        }
    }

    @Override
    public void update(List<GameItem> gameItems) {

    }

    @Override
    public void handleInput(OrthographicCamera camera) {
        //nothing to do...
    }
}