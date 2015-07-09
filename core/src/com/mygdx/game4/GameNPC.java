package com.mygdx.game4;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class GameNPC extends GameItem {

    public final static int NPC_SIZE = 0;
    private final int stepValue = 2;
    private int groundLevel = MainGameClass.GROUND_LEVEL;

    private boolean movingRight = false;
    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 4;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private GamePlatform platform;

    public GameNPC(GamePlatform platform, int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        this.platform = platform;
        walkSheet = new Texture(Gdx.files.internal(path));

        TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);

        walkFrames = new TextureRegion[(FRAME_COLS * FRAME_ROWS)];

        int index = 0;
        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {

                walkFrames[index++] = tmp[i][j];

            }
        }
        walkAnimation = new Animation(0.033f, walkFrames);

        stateTime = 0f;
    }

    public void move(){
        if (this.x >= platform.x + platform.getWidth()) {
            movingRight = false;
        }
        if(this.x <= platform.x){
            movingRight = true;
        }
        if(movingRight == true){
            this.x +=1+platform.getSpeed();
        }
        if(movingRight == false){
            this.x -=1+platform.getSpeed();
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch){

        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        spriteBatch.draw(currentFrame, this.x - 180, this.y-50);



    }

    public void update() {
        move();
        //this.x = platform.x;
        this.y = platform.y + platform.getHeight();
    }
}