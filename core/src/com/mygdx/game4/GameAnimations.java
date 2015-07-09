package com.mygdx.game4;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.util.List;

/**
 * Created by Henrik on 08.07.2015.
 */

public class GameAnimations extends GameItem {

    private static final int FRAME_COLS = 2;
    private static final int FRAME_ROWS = 1;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private float x;
    private float y;


    public final static int PLAYER_SIZE = 32;
    private boolean isJumping = false;
    private final int stepValue = 12;
    private float angle = 0;
    private int groundLevel = MainGameClass.GROUND_LEVEL;

    private boolean onPlatform = false;
    private GamePlatform currentPlatform = null;


    public GameAnimations(float x, float y) {
        this.x = x;
        this.x = y;
        walkSheet = new Texture(Gdx.files.internal("Steely.png"));

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

    public GameAnimations(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
    }

    @Override
    public void render(SpriteBatch spriteBatch) {
        handleInput();
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        spriteBatch.draw(currentFrame, x, y);

    }
    private boolean on(GamePlatform platform) {
        return (((this.x + this.getWidth()/2f) >= (platform.x)) && ((this.x + this.getWidth()/2f <= platform.x + platform.getWidth())) &&
                ((this.y >= platform.y)));
    }
    @Override
    public void update(List<GameItem> gameItems) {
        //Sjekker hopping, y-verdi settes her:
        if (isJumping && angle < Math.PI) {
            this.y = groundLevel + (int)((270f) * Math.sin(angle));
            angle += (8)*(Math.PI/180f);
            onPlatform = false;
        } else {
            this.y = groundLevel;
            angle = 0;
            isJumping = false;
        }

        //Dersom spiller på platform, flytt innafor denne:
        if (this.onPlatform) {
            if (this.on(currentPlatform)) {
                this.move(currentPlatform);  //Justerer enten groundLevel eller this.x
            } else {
                this.onPlatform = false;
                this.currentPlatform = null;
            }
        }

        //Dersom ikke på platform, sjekk alle platformer:
        if (this.onPlatform == false) {
            //Utenfor, faller evt. ned:
            if(groundLevel >= MainGameClass.GROUND_LEVEL){
                groundLevel -= 8;
            }
            //Sjekker seg selv mot alle plattformer. Dersom på en platform sjekkes ikke resten...
            for (int i = 0; i < gameItems.size(); i++) {
                GamePlatform pf = (GamePlatform) gameItems.get(i);
                //På platformen:
                if (this.on(pf)) {
                    this.onPlatform = true;
                    this.currentPlatform = pf;
                    groundLevel = (int) pf.y + (int) pf.getHeight();
                }
            }
        }
    }
    private void move(GamePlatform pf) {
        //Spilleren skal bevege seg etter platformen:
        if(pf.isHorisontalFalseVerticalTrue()==false){
            if (pf.getMoveRight())
                this.x += pf.getSpeed();
            else
                this.x -= pf.getSpeed();
        }else {
            if (pf.isMoveUp())
                groundLevel+= pf.getSpeed();    //NB! justerer på groundLevel!!
            else
                groundLevel-= pf.getSpeed();
        }
    }
    public void handleInput(){
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= stepValue;
            if (this.x < 1500) {
            }
            if (this.x < 0) {
                this.x = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += stepValue;
            if (this.x > 500) {
            }
            if ((this.x + GamePlayer.PLAYER_SIZE) >= MainGameClass.WORLD_WIDTH) {
                this.x = (MainGameClass.WORLD_WIDTH - GamePlayer.PLAYER_SIZE);
            }
        }
    if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
        //if (!isJumpingDown)
        isJumping = true;
    }
    }

}
