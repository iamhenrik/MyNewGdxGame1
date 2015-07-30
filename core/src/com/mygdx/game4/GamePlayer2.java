package com.mygdx.game4;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;

import java.util.List;

public class GamePlayer2 extends GameItem {

    public final static int PLAYER_SIZE = 180;
    private boolean isJumping = false;
    private final int stepValue = 3;

    private float angle = 10;
    private int groundLevel = MainGameClass.GROUND_LEVEL;

    private boolean onPlatform = false;
    private GamePlatform currentPlatform = null;

    private static final int FRAME_COLS = 8;
    private static final int FRAME_ROWS = 4;
    private float yVel = 25;
    private float gravity = 1.5f;
    private Animation walkAnimation;
    private Texture walkSheet;
    private TextureRegion[] walkFrames;
    //private SpriteBatch spriteBatch;
    private TextureRegion currentFrame;
    private float stateTime;
    private boolean HorisontalFalseVerticalTrue =  false;
    private boolean moveRight;

    public GamePlayer2(int x, int y, int width, int height, String path) {
        super(x, y, width, height, path);
        //sprite = new Sprite(new Texture(Gdx.files.internal(path)));
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

        sprite.setRegionWidth(width);
        sprite.setRegionHeight(height);
    }

    @Override
    public void render(SpriteBatch spriteBatch){
        stateTime += Gdx.graphics.getDeltaTime();

        currentFrame = walkAnimation.getKeyFrame(stateTime, true);

        if(HorisontalFalseVerticalTrue == false) {
            if (this.x + width >= MainGameClass.WORLD_WIDTH) {
                moveRight = false;
            }
            if (this.x <= 0) {
                moveRight = true;
            }
            if (moveRight == true) {
                this.x += stepValue;
            } else {
                this.x -= stepValue;
            }
        }
        spriteBatch.draw(currentFrame, this.x, this.y - 25);

        //spriteBatch.draw(currentFrame, this.x, this.y);

    }

    @Override
    public void update(List<GameItem> gameItems) {
        //Sjekker hopping, y-verdi settes her:
        if (isJumping) {

            yVel -= gravity;
            this.y += yVel;
            System.out.println(this.getWidth());

            onPlatform = false;

            if(this.y <= groundLevel){
                this.y = groundLevel;
                isJumping = false;
                yVel = 25f;
                }
        } else {
            this.y -= 17;
            if(this.y <= groundLevel) {
                this.y = groundLevel;
            }
            angle = 0;
            isJumping = false;
        }

        //Dersom spiller paa platform, flytt innafor denne:
        if (this.onPlatform) {
            if (this.on(currentPlatform)) {
                this.move(currentPlatform);  //Justerer enten groundLevel eller this.x
            } else {
                this.onPlatform = false;
                this.currentPlatform = null;
            }
        }

        //Dersom ikke paa platform, sjekk alle platformer:
        if (this.onPlatform == false) {
            //Utenfor, faller evt. ned:
            if(groundLevel >= MainGameClass.GROUND_LEVEL){
                groundLevel -= 17;
            }
            //Sjekker seg selv mot alle plattformer. Dersom paa en platform sjekkes ikke resten...
            for (int i = 0; i < gameItems.size(); i++) {
                GamePlatform pf = (GamePlatform) gameItems.get(i);
                //Paa platformen:
                if (this.on(pf)) {
                    this.onPlatform = true;
                    this.currentPlatform = pf;
                    groundLevel = (int) pf.y + (int) pf.getHeight();
                }
            }
        }
    }
    private boolean sjekkRight(GamePlatform platform){
        for(int i=0; i<90; i++) {
            int inkr = 0;
            if ((this.x += inkr) >= platform.x) {
                return true;
            }
            return false;
        }
        return false;
    }
    private boolean sjekkLeft(GamePlatform platform){
        for(int i=0; i<90; i++) {
            int inkr = 0;
            if ((this.x += inkr) >= platform.x + platform.getWidth()) {
                return true;
            }
            return false;
        }
        return false;
    }
    private boolean on(GamePlatform platform) {
        float pX1 = platform.x;                 //p = platform
        float pX2 = pX1+platform.getWidth();
        float bX1 = (this.x);                //b = blob
        float bX2 = (this.x+PLAYER_SIZE);
        //return (((this.x + this.getWidth()) >= (platform.x)) &&
          //      ((this.x + this.getWidth() <= platform.x + platform.getWidth())) &&
            //    ((this.y >= platform.y)));
        //return (sjekkRight(platform) == true &&
          //      sjekkLeft(platform) == true &&
            //    ((this.y >= platform.y)));
       //return(((bX1 >= pX1) && (bX1 <= pX2) && (this.y >= platform.y))
       //         ||
       //         ((bX2 >= pX1)&&(bX2 <= pX2) && (this.y >= platform.y)));


        boolean insideLeft = (pX1 >= bX1+50 && pX1 <= bX2-50);
        boolean insideRight = (pX2 >= bX1+50 && pX2 <= bX2-50);
        boolean onY = this.y >= platform.y;

        return (insideLeft || insideRight) && onY;
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
                groundLevel+= pf.getSpeed();    //NB! justerer paa groundLevel!!
            else
                groundLevel-= pf.getSpeed();
        }
    }

    @Override
    public void handleInput(OrthographicCamera camera) {
        if (Gdx.input.isKeyPressed(Input.Keys.Y)) {
            this.y += stepValue;
            if ((this.y + GamePlayer2.PLAYER_SIZE) >= (MainGameClass.WORLD_HEIGHT)) {
                this.y = MainGameClass.WORLD_HEIGHT - GamePlayer2.PLAYER_SIZE;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.H)) {
            this.y -= stepValue;
            if (this.y <= 0) {
                this.y = 0;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.x -= stepValue;
            if (this.x < 1500) {
                camera.translate(-MainGameClass.CAMERA_PAN_SPEED, 0, 0);
            }
            if (this.x+25 <= 0) {
                this.x = 0-25;
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.x += stepValue;
            if (this.x > 500) {
                camera.translate(MainGameClass.CAMERA_PAN_SPEED, 0, 0);
            }
            if ((this.x-25 + PLAYER_SIZE) >= MainGameClass.WORLD_WIDTH) {
                this.x = (MainGameClass.WORLD_WIDTH - PLAYER_SIZE+30);
            }
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            camera.zoom += 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.Q)) {
            camera.zoom -= 0.02;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.G)) {
            camera.translate(-9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.J)) {
            camera.translate(9, 0, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            camera.translate(0, -3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            camera.translate(0, 3, 0);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
            //if (!isJumpingDown)
            isJumping = true;
        }
//         if (Gdx.input.isKeyPressed(Keys.W)) {
//          camera.rotate(-rotationSpeed, 0, 0, 1);
//         }
//         if (Gdx.input.isKeyPressed(Keys.E)) {
//          camera.rotate(rotationSpeed, 0, 0, 1);
//         }

//         camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, 100/camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, MainGameClass.WORLD_WIDTH / camera.viewportWidth);
        camera.zoom = MathUtils.clamp(camera.zoom, 0.1f, MainGameClass.WORLD_HEIGHT / camera.viewportHeight);

        float effectiveViewportWidth = camera.viewportWidth * camera.zoom;
        float effectiveViewportHeight = camera.viewportHeight * camera.zoom;

        camera.position.x = MathUtils.clamp(camera.position.x, effectiveViewportWidth / 2f, MainGameClass.WORLD_WIDTH - effectiveViewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, effectiveViewportHeight / 2f, MainGameClass.WORLD_HEIGHT - effectiveViewportHeight / 2f);
    }


}