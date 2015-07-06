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

    public boolean isHorisontalFalseVerticalTrue() {
        return HorisontalFalseVerticalTrue;
    }

    public void setHorisontalFalseVerticalTrue(boolean horisontalFalseVerticalTrue) {
        HorisontalFalseVerticalTrue = horisontalFalseVerticalTrue;
    }

    //private boolean hasPlayer = false;
    private boolean HorisontalFalseVerticalTrue;

    public boolean isMoveUp() {
        return moveUp;
    }

    public void setMoveUp(boolean moveUp) {
        this.moveUp = moveUp;
    }

    private boolean moveRight = true;
    private boolean moveUp = false;


    public GamePlatform(int x, int y, int width, int height, String path, int speed, boolean HorisontalFalseVerticalTrue) {
        super(x, y, width, height, path);

        this.speed = speed;
        this.HorisontalFalseVerticalTrue = HorisontalFalseVerticalTrue;
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
            if(this.y+ height >= SummerGame4.WORLD_HEIGHT)
                moveUp = false;
            if(this.y<= SummerGame4.GROUND_LEVEL)
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