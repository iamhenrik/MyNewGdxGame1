package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public abstract class GameItem extends Rectangle {

    protected Sprite image;

    public GameItem(int x, int y, int width, int height, String path) {
        super(x, y, width, height);

        image = new Sprite(new Texture(Gdx.files.internal(path)));
    }
     public boolean overLaps(GameItem object){
            return x < object.x + object.width &&
                    x + width > object.x &&
                    y < object.y + object.height &&
                    y + height > object.y;
        }

    public Sprite getImage() {
        return image;
    }

    }