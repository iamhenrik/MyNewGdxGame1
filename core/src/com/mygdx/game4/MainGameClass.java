package com.mygdx.game4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class MainGameClass extends ApplicationAdapter implements GestureDetector.GestureListener {
    SpriteBatch batch;
    private GamePlayer2 player1;

    private List<GameItem> gamePlatforms;

    private OrthographicCamera camera;
    private float rotationSpeed;
    private Sprite backGround;
    private GameFigure man;
    private GameSwirl swirl;
    private GameSwirl swirl2;
    private GameExplosion boom;
    private GameAnimations ufo;
    private ArrayList<GameNPC> gameNPCs;
    private ArrayList<GameMap> Ambient;

    //	private static final int WORLD_HEIGHT = (int)(100f*(1080f/1920));
    //public static final int WORLD_WIDTH = 2700; //1340;
    //public static final int WORLD_HEIGHT = 1500; //975;
    public static final int WORLD_WIDTH = 2700; //1340;
    public static final int WORLD_HEIGHT = 1500; //975;

    public final static int GRAVITY = 10;
    public final static float CAMERA_PAN_SPEED = 18;
    public static final int GROUND_LEVEL = 79;

    private int xValueTiles = -270;
    private int xValuePlatforms = 0;

    private BitmapFont font;
    private Vector3 touch;
    private GameFigure One;



    @Override
    public void create() {

        Ambient = new ArrayList<GameMap>();
        gamePlatforms = new ArrayList<GameItem>();
        gameNPCs = new ArrayList<GameNPC>();
        rotationSpeed = 0.5f;
        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("MapleBackGroundPng.png"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        font = new BitmapFont(Gdx.files.internal("fonts/blackOakFont.fnt"), false);

        touch = new Vector3();

        One = new GameFigure(700,500, 76,126, "numberOne.png");

        man = new GameFigure();
        swirl = new GameSwirl(2350, GROUND_LEVEL, "numberOne.png");
        swirl2 = new GameSwirl(1500, GROUND_LEVEL+10, "amy_sprite.png");
        boom = new GameExplosion(2000, 400);
        ufo = new GameAnimations(2350, 1170);

        for(int i = 0; i<10; i++){
            xValueTiles +=270;
           Ambient.add(new GameMap(xValueTiles,0,270,79,"TileWithGround.png"));
        }

        GameMap cloud1 = new GameMap(200, 1100, 810,317, "CloudOne.png");
        Ambient.add(cloud1);
        GameMap cloud2 = new GameMap(1200, 980, 605,224,"CloudTwo.png");
        Ambient.add(cloud2);
        GameMap cloud3 = new GameMap(2000, 890, 688,579,"CloudThree.png");
        Ambient.add(cloud3);
        GameMap building1 = new GameMap(400, 79, 810,325,"BuildingsOne.png");
        Ambient.add(building1);
        GameMap bigTree = new GameMap(2300, 79, 388,685,"BigTree.png");
        Ambient.add(bigTree);

        player1 = new GamePlayer2(GROUND_LEVEL, 300, GamePlayer2.PLAYER_SIZE, GamePlayer2.PLAYER_SIZE, "SlimeAniScaled.png");

        /*gamePlatforms.add(new GamePlatform(2400, 370, 81,63,"SnowPlatformSmall.png", 2, true, 900, 300));
        gamePlatforms.add(new GamePlatform(140, 800, 81,63,"SnowPlatformSmall.png", 2, true, 600, 600));
        gamePlatforms.add(new GamePlatform(2300, 1030, 340, 126, "SnowPlatformBig.png",0,false,0,0));
        gamePlatforms.add(new GamePlatform(250, 150, 81,63,"SnowPlatformSmall.png", 0, false, 0, 0));
        //gamePlatforms.add(new GamePlatform(2330, 1050, 81,63,"SnowPlatformSmall.png", 0, false, 0, 0));
        for(int i =0; i<5; i++){
            xValuePlatforms += 400;
            gamePlatforms.add(new GamePlatform(xValuePlatforms+200, 330, 81, 63, "SnowPlatformSmall.png", 0, false, 0, 0));
            gamePlatforms.add(new GamePlatform(xValuePlatforms, 620, 81, 63, "SnowPlatformSmall.png", 0, false, 0, 0));
            gamePlatforms.add(new GamePlatform(xValuePlatforms+200, 920, 81, 63, "SnowPlatformSmall.png", 0, false, 0, 0));
            System.out.println("lol " + xValuePlatforms);
        }
           */

        //GameNPC npc1 = new GameNPC(platform3,(int) platform2.x, (int)platform2.y, GameNPC.NPC_SIZE, GameNPC.NPC_SIZE, "CashSack.png");
        //gameNPCs.add(npc1);

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera(30, 30 * (h / w));
        camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
        camera.zoom = 11f;
        camera.setToOrtho(false, WORLD_WIDTH, WORLD_HEIGHT);
        camera.update();
        Gdx.input.setInputProcessor(new GestureDetector(this));   //sdfdf
    }

    @Override
    public void render() {

        handleInput();

        //Kamera:
        camera.update();
        batch.setProjectionMatrix(camera.combined);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        //for (GameItem gameItem: gamePlatforms) {
        //    gameItem.update();
        //}
        player1.update(gamePlatforms);

        //Render:
        batch.begin();
        backGround.draw(batch);
        for(GameMap GameMap: Ambient){
            GameMap.render(batch);
        }
        player1.render(batch);
        //for (GameItem gameItem: gamePlatforms) {
        //  batch.draw(gameItem.getSprite(), gameItem.x, gameItem.y);
        //}
        font.draw(batch, "HELLO WHAT IS GOING ON", 1300, 600);
        swirl.render(batch);
        batch.end();
        //generalUpdate(touch);


    }
    private void generalUpdate(Vector3 touch){
        if(Gdx.input.justTouched()){
            touch.set(Gdx.input.getX(),Gdx.input.getY(),0);
            System.out.println(touch.x + " + " + touch.y);
            System.out.println(swirl.getPosition(new Vector2()));
            System.out.println(player1.getPosition(new Vector2()));

            if(touch.x >= player1.getX() && touch.x <= (player1.getY()+ player1.getX()+76)
                    //&& touch.y >= player1.y && touch.y <= (player1.y+player1.getHeight())
                    ){

               // System.out.println("BUTTON PRESSED");
               // System.out.println(touch.x  + " + " + touch.y);
            }
        }

    }

    private void handleInput() {
        player1.handleInput(camera);

    }
    @Override
    public void dispose(){
    }

    @Override
    public void resize(int width, int height) {
       // camera.viewportWidth = 300f;
       // camera.viewportHeight = 300f * height / width;
       // camera.update();
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button){
        if(x >= player1.getX() && x <= (player1.getY()+ player1.getX()+76)){
            System.out.println("TEST");
        }
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {

        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        // TODO Auto-generated method stub
        camera.translate(deltaX, deltaY);
        camera.update();
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        // TODO Auto-generated method stub
        float differance = initialDistance - distance;
        if (differance > 0) {
            camera.zoom += 0.02;
        } else {
            camera.zoom -= 0.02;
        }
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2,
                         Vector2 pointer1, Vector2 pointer2) {
        // TODO Auto-generated method stub
        return false;
    }
}
