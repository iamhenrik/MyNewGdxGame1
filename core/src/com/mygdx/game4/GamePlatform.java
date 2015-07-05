package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

import java.util.ArrayList;

/**
 * Created by Henrik on 25.06.2015.
 */


public class GamePlatform extends GameItem {
    
    private boolean moveRight = true;
    private int moveSpeed = 4;
    private boolean hasPlayer = false;
    
    public GamePlatform(int x, int y, int width, int height, String path, int moveSpeed) {
        super(x, y, width, height, path);
        this.moveSpeed = moveSpeed;
        image = new Sprite(new Texture(Gdx.files.internal(path)));
        image.setRegionWidth(width);
        image.setRegionHeight(height);

    }
    public void update(GamePlayer player1) {
        if (moveRight == true) {
            this.x += moveSpeed;

        } else {
            this.x -= moveSpeed;
        }
        if (this.x + this.getWidth()>= SummerGame4.WORLD_WIDTH) {
            moveRight = false;
        }
        if (this.x <= 0) {
            moveRight = true;
        }
        if (player1.y >= (this.y + this.height) && player1.x >= this.x && player1.x <= this.x + this.width && moveRight == true) {
            player1.x += moveSpeed;
        }
        if (player1.y >= (this.y + this.height) && player1.x >= this.x && player1.x <= this.x + this.width && moveRight == false) {
            player1.x -= moveSpeed;
        }
        if (player1.y >= (this.y + this.height) && player1.x >= this.x && player1.x <= this.x + this.width) {
//			isJumpingDown = false;
//			isJumpingUp = false;
        }

    }

    public boolean hasPlayer() {
        return hasPlayer;
    }

    public void setPlayer(boolean hasPlayer) {
        this.hasPlayer = hasPlayer;
    }

    @Override
    public void update(ArrayList<GameItem> gameItems) {

    }
}

