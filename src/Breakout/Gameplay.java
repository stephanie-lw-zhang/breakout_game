package Breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static Breakout.Block.readBlockConfiguration;
import static Breakout.PowerUp.stepPowerUp;

public class Gameplay extends Application {
    public static final String TITLE = "Breakout JavaFX";
    public static final String SPLASH_IMAGE = "breakout_background.png";
    public static final String WIN_IMAGE = "breakout_win.png";
    public static final String LOSE_IMAGE = "breakout_lose.png";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    //public static final String BLOCK_IMAGE = "brick1.gif";
    //public static final int MOVER_SPEED = 10;
    public static final String LEVEL_ONE = "config1.txt";
    public static final String LEVEL_TWO = "config2.txt";
    public static final String LEVEL_THREE = "config3.txt";
    public int scoreVal;
    public int levelVal;
    public int highScoreVal;
    private boolean cornerTest = false;
    private boolean breakTest = false;
    private boolean lifeTest = false;
    public static final String CORNER_FILE = "corner.txt";
    public static final String BREAK_FILE = "break.txt";
    public static final String LIFE_FILE = "life.txt";
    private static final int TEXT_XPOS = 35;
    private static final int TEXT_SCORE_YPOS = HEIGHT - 15;

    private Scene myScene;
    private Group root;
    private Bouncer bouncer;
    private Paddle paddle;
    private ImageView myPaddle;
    private ImageView myBouncer;

    private List<Block> blockList;
    private List<PowerUp> powerUpList;
    private List<Bouncer> bouncerList;
    private Text lives;
    private Text level;
    private Text score;
    private Text highScore;
    private Stage myStage;
    private Timeline myAnimation;

    /**
    private List<Block> blockList;
    private List<PowerUp> powerUpList;
    private List<Bouncer> bouncerList;

    private static Text livesText;
    private Text level;
    private static Text scoreText;
    private static Stage myStage;
    private Timeline myAnimation;
    private int score = 0;
*/



    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        highScore = new Text();
        highScoreVal = 0;
        stage.setTitle("Load Image");

        StackPane sp = new StackPane();
        Image img = new Image(SPLASH_IMAGE);
        createStage( stage, sp, img );
    }

    private void createStage(Stage stage, StackPane sp, Image img) {
        ImageView imgView = new ImageView( img );
        sp.getChildren().add( imgView );

        //Adding HBox to the scene
        Scene scene = new Scene( sp );
        stage.setScene( scene );
        stage.show();
        scene.setOnKeyPressed( e -> checkGameStart( e.getCode(), stage ) );
    }

    private void checkGameStart(KeyCode code, Stage stage){
        if(code.isArrowKey()){
            levelVal = 1;
            startGame(stage);
        }
    }

    public void startGame (Stage stage){
        // attach scene to the stage and display it
        scoreVal = 0;
        myScene = setupGame(HEIGHT, WIDTH, BACKGROUND);

        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        myAnimation = new Timeline();
        myAnimation.setCycleCount(Timeline.INDEFINITE);
        myAnimation.getKeyFrames().add(frame);
        myAnimation.play();
    }


    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);

        // x and y represent the top left corner, so center it
        Image imageBouncer = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new ImageView(imageBouncer);
        bouncer = new Bouncer(myBouncer, this, 3);
        bouncerList = new ArrayList<>();
        bouncerList.add(bouncer);

        Image imagePaddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(imagePaddle);
        paddle = new Paddle(myPaddle);
        paddle.setInitialPosition(width, height);

        initializeLevel() ;
        initializeLives();
        initializeScore();
        initializeHighScore();

        blockList = new ArrayList<>();
        readBlockConfiguration(LEVEL_ONE, root, width, height, blockList);

        root.getChildren().add(bouncer.getBouncer());
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(level);
        root.getChildren().add(lives);
        root.getChildren().add(score);
        root.getChildren().add(highScore);

        powerUpList = new ArrayList<>();

        // respond to input
        scene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        });
        return scene;
    }

    private void initializeHighScore(){
        highScore.setText("High Score:  " + highScoreVal);
        highScore.setX(TEXT_XPOS);
        highScore.setY(TEXT_SCORE_YPOS);
    }

    private void initializeScore() {
        score = new Text();
        score.setText("Score:  " + scoreVal);
        score.setX(TEXT_XPOS);
        score.setY(TEXT_SCORE_YPOS-10);
    }

    private void initializeLives() {
        lives = new Text();
        lives.setText("Lives:  " + bouncer.getNumLives());
        lives.setX(TEXT_XPOS);
        lives.setY(TEXT_SCORE_YPOS - 20);
    }

    private void initializeLevel() {
        level = new Text();
        level.setText("Level:  " + levelVal);
        level.setX(TEXT_XPOS);
        level.setY(TEXT_SCORE_YPOS - 30);
    }



    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        bouncer.stepBouncer(bouncerList, blockList, powerUpList, paddle, root, elapsedTime, this);
        stepPowerUp(powerUpList, paddle, bouncer, root, bouncerList, elapsedTime, this);

        if(blockList.isEmpty()){
            if(levelVal==1){
                levelVal++;
                level.setText("Level:  " + levelVal);
                bouncer.resetPos();
                bouncer.incrementSpeed(40);
                readBlockConfiguration(LEVEL_TWO, root, SIZE, SIZE, blockList);
            }
            else if(levelVal ==2) {
                levelVal++;
                level.setText("Level:  " + levelVal);
                bouncer.resetPos();
                bouncer.incrementSpeed(40);
                readBlockConfiguration(LEVEL_THREE, root, SIZE, SIZE, blockList);
            }
            else {
                outcomeScreen(myStage, true);
            }
        }
        if(cornerTest){
            if(bouncer.getBouncer().getX()== bouncer.getBouncer().getY() && bouncer.getBouncer().getY()>30){
                System.out.println("Corner Test Passes!");
                myAnimation.pause();
            }
        }

        if(breakTest){

        }

        if(lifeTest){
            if(bouncer.getNumLives()==2){
                System.out.println("Lives Test Passes!");
                myAnimation.pause();
            }
        }
    }

    public void changeLives(int numLives){
        lives.setText("Lives:  "+ numLives);
        if(numLives == 0) {
            outcomeScreen(myStage, false);
        }
    }

    public int getScore(){
        return this.scoreVal;
    }

    public void changeScore(int newScore){
        this.scoreVal = newScore;
        score.setText("Score:  " + scoreVal);
    }

    // What to do each time a key is pressed
    private void handleKeyInput(KeyCode code) throws IOException {
        paddle.handleKeyInput(code, HEIGHT);

        if(code.equals(KeyCode.L)){
            bouncer.increaseNumLives();
            changeLives(bouncer.getNumLives());
        }
        else if(code.equals(KeyCode.R)){
            bouncer.resetPos();
        }
        //else if(code.equals(KeyCode.COMMA)){
        //    cornerTest = Tester.configureTest(bouncer, CORNER_FILE);
        //}
        //else if(code.equals(KeyCode.PERIOD)){
        //    breakTest = Tester.configureTest(bouncer, BREAK_FILE);
        //}
        else if(code.equals(KeyCode.SPACE)){
            Block firstBlock = blockList.get(0);
            root.getChildren().remove(firstBlock.getBlock());
            blockList.remove(firstBlock);
        }
    }

    private void outcomeScreen(Stage stage, boolean win){
        if(scoreVal>highScoreVal){ highScoreVal = scoreVal; }
        myAnimation.pause();
        stage.setTitle("Outcome Screen");
        StackPane sp = new StackPane();
        Image img;
        if(win) img = new Image(WIN_IMAGE);
        else img = new Image(LOSE_IMAGE);
        createStage( stage, sp, img );
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

