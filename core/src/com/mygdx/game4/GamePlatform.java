package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.List;


/**
 * Created by Henrik on 25.06.2015.
 */
public class GamePlatform extends GameItem {
    private int speed = 4;
    //private boolean hasPlayer = false;
    private boolean moveRight = true;

    public GamePlatform(int x, int y, int width, int height, String path, int speed) {
        super(x, y, width, height, path);

        this.speed = speed;
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
    public void render(SpriteBatch batch) {
        batch.draw(this.getSprite(), this.x, this.y);
    }

    @Override
    public void update() {
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
    }
}