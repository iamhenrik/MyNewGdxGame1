package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;

public class GameFigure extends Rectangle {

    private Sprite image;

    public GameFigure(int x, int y, int width, int height, String path) {
        super(x, y, width, height);

        image = new Sprite(new Texture(Gdx.files.internal(path)));
    }


    public boolean overLaps(GameFigure object) {
        return x < object.x + object.width &&
                x + width > object.x &&
                y < object.y + object.height &&
                y + height > object.y;
    }

    public Sprite getImage() {
        return image;
    }
}
