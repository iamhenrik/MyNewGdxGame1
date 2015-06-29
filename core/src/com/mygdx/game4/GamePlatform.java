package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.List;


/**
 * Created by Henrik on 25.06.2015.
 */
public class GamePlatform extends GameItem {
    //public final static int PLATFORM_HEIGHT = 30;
    //public final static int PLATFORM_WIDHT = 240;
    private boolean MOVE_RIGHT = true;
    private int speed = 4;

    public GamePlatform(int x, int y, int width, int height, String path, int speed) {
        super(x, y, width, height, path);

        this.speed = speed;
        image = new Sprite(new Texture(Gdx.files.internal(path)));
        image.setRegionWidth(width);
        image.setRegionHeight(height);
    }

    @Override
    public void render(GamePlayer player1) {
        if (MOVE_RIGHT == true) {
            this.x += speed;
        } else {
            this.x -= speed;
        }

        if (this.x + width >= SummerGame4.WORLD_WIDTH) {
            MOVE_RIGHT = false;
        }
        if (this.x <= 0) {
            MOVE_RIGHT = true;
        }
        if (player1.y >= (this.y + height) && player1.x >= this.x && player1.x <= this.x + width && MOVE_RIGHT == true) {
            player1.x += speed;
        }
        if (player1.y >= (this.y + height) && player1.x >= this.x && player1.x <= this.x + width && MOVE_RIGHT == false) {
            player1.x -= speed;
        }
        if (player1.y >= (this.y + height) && player1.x >= this.x && player1.x <= this.x + width) {
//			isJumpingDown = false;
//			isJumpingUp = false;
        }

//		player1.x += (-speed);
    }

    @Override
    public void render(List<GameItem> gameItems) {

    }

    @Override
    public void handleInput(OrthographicCamera camera) {
        //nothing to do...
    }
}

