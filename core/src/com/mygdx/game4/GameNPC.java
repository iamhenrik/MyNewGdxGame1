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
    private boolean MovingUp = false;
    private static final int FRAME_COLS = 4;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private GamePlatform platform;

    //public GameNPC(int x, int y, int width, int height, String path){
    //    super(x,y,width,height,path);
   // }
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
        walkAnimation = new Animation(0.063f, walkFrames);

        stateTime = 0f;
    }

    public void move(){
        /*
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
        */
    }
    public void moveUpDown() {
        if (this.y >= 84) {
            MovingUp = false;
        }
        if (this.y <= 78) {
            MovingUp = true;
        }
        if (MovingUp == true) {
            this.y += 1;
        }
        if (MovingUp == false) {
            this.y -= 1;
        }
    }

    @Override
    public void render(SpriteBatch spriteBatch){

        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        spriteBatch.draw(currentFrame, this.x - 180, this.y-50);

    }
    @Override
    public void update() {
        move();
        //this.x = platform.x;
        //this.y = platform.y + platform.getHeight();
    }
}