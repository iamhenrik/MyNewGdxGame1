package com.mygdx.game4;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainGameClass extends ApplicationAdapter implements GestureDetector.GestureListener {
    SpriteBatch batch;
    private GamePlayer2 player1;

    private List<GameItem> gamePlatforms;

    private OrthographicCamera camera;
    private Sprite backGround;
    private GameButton startButton;
    private GameButton playAgainButton;

    private ArrayList<GameMap> Ambient;
    private ArrayList<GameInteger> gameIntgers;

    public static final int WORLD_WIDTH = 1024; //1340;
    public static final int WORLD_HEIGHT = 720; //975;

    public final static int GRAVITY = 10;
    public final static float CAMERA_PAN_SPEED = 18;
    public static final int GROUND_LEVEL = 79;

    private int xValueTiles = -270;
    private int xValueNumbers = 0;

    private int tapCounter = 0;
    private int randomNumber;

    private ArrayList<GameInteger> selectedNumbers;
    private String resultMessage = "";

    private BitmapFont font;

    private GameInteger Red;

    public enum GameStatus {
        GAME_START, GAME_PLAY, GAME_RESULT, GAME_OVER
    }
    private GameStatus gameStatus = GameStatus.GAME_START;

    @Override
    public void create() {

        Ambient = new ArrayList<GameMap>();
        gamePlatforms = new ArrayList<GameItem>();

        batch = new SpriteBatch();
        backGround = new Sprite(new Texture("MapleBackGroundPng.png"));
        backGround.setPosition(0, 0);
        backGround.setSize(WORLD_WIDTH, WORLD_HEIGHT);

        font = new BitmapFont(Gdx.files.internal("fonts/blackOakFont.fnt"), false);

        startButton = new GameButton(WORLD_WIDTH/2-75,WORLD_HEIGHT/2+75,150,150,"startGame.png");
        playAgainButton = new GameButton(WORLD_WIDTH/2-75,WORLD_HEIGHT/2-75,150,150,"playAgain.png");

        gameIntgers = new ArrayList<GameInteger>();
        selectedNumbers = new ArrayList<GameInteger>();

        for(int i=1; i<=10; i++){
            gameIntgers.add(new GameInteger(this, xValueNumbers,GROUND_LEVEL,90,130,"integer"+i+".png", i));
            xValueNumbers += 101;
        }

        for(int i = 0; i<8; i++){
            xValueTiles +=270;
           Ambient.add(new GameMap(xValueTiles,0,270,79,"TileWithGround.png"));
        }

        GameMap cloud1 = new GameMap(50, 700, 810,317, "CloudOne.png");
        Ambient.add(cloud1);
        GameMap cloud2 = new GameMap(800, 780, 605,224,"CloudTwo.png");
        Ambient.add(cloud2);
        GameMap cloud3 = new GameMap(1300, 490, 688,579,"CloudThree.png");
        Ambient.add(cloud3);
        GameMap bigTree = new GameMap(1600, 79, 388,685,"BigTree.png");
        Ambient.add(bigTree);

        player1 = new GamePlayer2(GROUND_LEVEL, 300, GamePlayer2.PLAYER_SIZE, GamePlayer2.PLAYER_SIZE, "SlimeAniScaled.png");

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

        //Render:
        batch.begin();
        backGround.draw(batch);
        for(GameMap gameMap: Ambient){
            gameMap.render(batch);
        }

        //Tegner resten avhengig av status:
        switch (gameStatus) {
            case GAME_START:
                startButton.render(batch);
                break;

            case GAME_PLAY:
                for(GameInteger gameInteger: gameIntgers){
                    gameInteger.render(batch);

                }

                break;

            case GAME_RESULT:
                font.draw(batch,resultMessage, 5, 450);
                playAgainButton.render(batch);
                for(GameInteger gameInteger: gameIntgers){
                    gameInteger.render(batch);
                    font.draw(batch, String.valueOf(randomNumber), 512, 500);
                    /*
                    for(int i=0; i<2; i++){
                        //x+=850;
                        selectedNumbers.get(0).setX(30).setY(500);
                        selectedNumbers.get(1).setX(850).setY(500);
                        selectedNumbers.get(i).render(batch);

                    }
                    */
                }
                break;
        }
        //font.draw(batch, String.valueOf(randomNumber), 400, 320);
        //gameCheck(batch);

        batch.end();
    }
    public void addToSelectedNumbers(GameInteger gameInteger){
        selectedNumbers.add(gameInteger);
    }
    public int getTapCounter() {
        return tapCounter;
    }

    public void setGameResult(){
        /*
        for(int i=0; i<selectedNumbers.size(); i++){
            font.draw(batch,String.valueOf((selectedNumbers.get(i)).getNumber()),selectedNumbers.get(i).getX(),500);
        }
        */
        check(selectedNumbers.get(0).getNumber(), selectedNumbers.get(1).getNumber());
        System.out.println(selectedNumbers.get(0).getNumber() + ", " + selectedNumbers.get(1).getNumber());
        if((selectedNumbers.get(1).getNumber()-randomNumber) == (randomNumber-selectedNumbers.get(0).getNumber())){
            System.out.println("yes");
        }

    }

    public void randTall(){
        Random random = new Random();
        randomNumber = random.nextInt(10)+1;
    }

    public void check(int redGuess, int blueGuess){
        boolean sjekk = (selectedNumbers.get(1).getNumber()-randomNumber) == (randomNumber-selectedNumbers.get(0).getNumber());
        if(redGuess == randomNumber){
            resultMessage = "Red Won, Blue Looses, Drink 2 sips! ";
        }else{
            resultMessage = "Blue Won, Red Looses, Drink 2 sips!";
        }
        if(redGuess>randomNumber  && blueGuess > randomNumber){
            if(redGuess>blueGuess){
                //showMessageDialog(null,"Blå vant, Rød var: " + (redGuess-randomNumber) +" unna.");
                resultMessage = "Blue WON, Red Looses, Drink "  + (redGuess-randomNumber) +" sips";
            }
            if(blueGuess>redGuess){
                //showMessageDialog(null,"Rød vant, Blå var: " + (blueGuess-randomNumber) +" unna.");
                resultMessage = "Red WON, Blue Looses, Drink "  + (blueGuess-randomNumber) +" sips";
            }
        }
        if(redGuess<randomNumber && blueGuess<randomNumber){
            if(redGuess>blueGuess){
                //showMessageDialog(null,"Rød vant, Blå var: " + (randomNumber-blueGuess) +" unna.");
                resultMessage = "Red WON, Blue Looses, Drink "  + (randomNumber-blueGuess) +" sips";
            }
            if(blueGuess>redGuess){
                //showMessageDialog(null,"Blå vant, Rød var: " + (randomNumber-redGuess) +" unna.");
                resultMessage = "Blue WON, Red Looses, Drink "  + (randomNumber-redGuess) +" sips";
            }
        }
        if(redGuess < randomNumber && blueGuess > randomNumber){
            if(sjekk){
                //showMessageDialog(null,"Dere var begge like langt ifra, drikk: " + (blueGuess-randomNumber) + " slurker hver");
                resultMessage = "You were both " +(blueGuess-randomNumber) +", Drink "  + (blueGuess-randomNumber) +" sips";
            }
            if((blueGuess-randomNumber) > (randomNumber-redGuess)){
                //showMessageDialog(null,"Rød vant, Blå var: " + (blueGuess-randomNumber) + " unna");
                resultMessage = "Red WON, Blue Looses, Drink "  + (blueGuess-randomNumber) +" sips";
            }
            if((blueGuess-randomNumber) < (randomNumber-redGuess))
                //showMessageDialog(null,"Blå vant, Rød var: " + (randomNumber - redGuess) + " unna");
                resultMessage = "Blue WON, Red Looses, Drink "  + (randomNumber - redGuess) +" sips";
            //}
        }
        if(blueGuess < randomNumber && redGuess > randomNumber){
            if((redGuess-randomNumber)==(randomNumber-blueGuess)){
                //showMessageDialog(null,"Dere var begge like langt ifra, drikk: " + (redGuess-randomNumber) + " slurker hver");
                resultMessage = "You were both " +(redGuess-randomNumber) +", Drink "  + (redGuess-randomNumber) +" sips";
            }
            if((redGuess-randomNumber)>(randomNumber-blueGuess)){
                //showMessageDialog(null,"Blå vant, Rød var: " + (redGuess-randomNumber) + " unna");
                resultMessage = "Blue WON, Red Looses, Drink "  + (redGuess-randomNumber) +" sips";
            }
            if((redGuess-randomNumber)<(randomNumber-blueGuess)){
                //showMessageDialog(null,"Rød vant, Blå var: " +(randomNumber - blueGuess) + " unna");
                resultMessage = "Red WON, Blue Looses, Drink "  + (randomNumber - blueGuess) +" sips";
            }
        }
    }

    public void incrementTapCounter(){
        tapCounter++;
        if (tapCounter==2) {
            gameStatus = GameStatus.GAME_RESULT;
            setGameResult();
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

        switch (gameStatus) {
            case GAME_START:
                randTall();
                for (GameInteger gameInteger : gameIntgers) {
                    gameInteger.setActivated(true);
                }
                startButton.tap(x, y, count, button);
                if (startButton.isClicked()) {
                    gameStatus = GameStatus.GAME_PLAY;
                }
                break;

            case GAME_PLAY:
                for (GameInteger gameInteger : gameIntgers) {
                    gameInteger.tap(x, y, count, button);
                }
                break;

            case GAME_RESULT:
                playAgainButton.tap(x,y,count,button);
                if (playAgainButton.isClicked()) {
                    for (GameInteger gameInteger : gameIntgers) {
                        gameInteger.setActivated(true);
                        gameInteger.setSelected(false);
                    }
                    playAgainButton.setClicked(false);
                    tapCounter  = 0;
                    randTall();
                    gameStatus = GameStatus.GAME_PLAY;
                    resultMessage = "";
                    selectedNumbers.clear();


                }
                break;
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
