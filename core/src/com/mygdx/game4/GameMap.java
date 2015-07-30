package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public class GameMap extends Rectangle {

    private Sprite sprite;
    private boolean HorisontalFalseVerticalTrue = false;
    private boolean moveRight;

    public GameMap(int x, int y, int width, int height, String path) {
        super(x, y, width, height);
        sprite = new Sprite(new Texture(Gdx.files.internal(path)));
    }
    public GameMap(){
    }

    public Sprite getSprite() {
        return sprite;
    }

    //Overstyres ved behov i barneklassene yes ...:
    public void render(SpriteBatch spriteBatch) {
        spriteBatch.draw(sprite.getTexture(),this.x, this.y);
    }
    public void render(SpriteBatch batch, int x, int y,int length){

        if(HorisontalFalseVerticalTrue == false) {
            if (this.x >= MainGameClass.WORLD_WIDTH) {
                moveRight = false;
            }
            if (this.x <= 0) {
                moveRight = true;
            }
            if (moveRight == true) {
                this.x += 1;
            } else {
                this.x = -length;
            }
        }
        batch.draw(sprite.getTexture(), this.x,this.y);
    }

    //Overstyres ved behov i barneklassene:
    public void update() {
    }

    //Overstyres ved behov i barneklassene:
    public void update(List<GameMap> gameItems) {};

    //Overstyres ved behov i barneklassene:
    public void handleInput(OrthographicCamera camera) {};
}

