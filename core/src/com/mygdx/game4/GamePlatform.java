package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

/**
 * Created by Henrik on 25.06.2015.
 */
public class GamePlatform extends GameItem {
    public GamePlatform(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        image = new Sprite(new Texture(Gdx.files.internal(path)));
    }

    @Override
    public void render() {

    }
}

