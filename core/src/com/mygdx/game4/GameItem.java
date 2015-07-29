package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;

import java.util.List;

public abstract class GameItem extends Rectangle {

    protected Sprite sprite;
    protected ShapeRenderer debugRenderer;

    public GameItem(int x, int y, int width, int height, String path) {
        super(x, y, width, height);
        sprite = new Sprite(new Texture(Gdx.files.internal(path)));
        debugRenderer = new ShapeRenderer();
    }
    public GameItem(){
    }

    public Sprite getSprite() {
        return sprite;
    }

    //Overstyres ved behov i barneklassene yes ...:
    public void render(SpriteBatch batch) {
    }

    //Overstyres ved behov i barneklassene:
    public void update() {
    }

    //Overstyres ved behov i barneklassene:
    public void update(List<GameItem> gameItems) {};

    //Overstyres ved behov i barneklassene:
    public void handleInput(OrthographicCamera camera) {};

    public void drawDebug(OrthographicCamera camera){
        debugRenderer.setProjectionMatrix(camera.combined);
        debugRenderer.begin(ShapeRenderer.ShapeType.Line);
        debugRenderer.setColor(new Color(0, 1, 0, 1));
        debugRenderer.rect(this.x, this.y, this.getWidth(), this.getHeight());
        debugRenderer.end();
    }
}

