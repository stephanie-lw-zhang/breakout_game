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
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Gameplay extends Application {
    public static final String TITLE = "Breakout JavaFX";
    public static final String SPLASH_IMAGE = "breakout_background.png";
    public static final String WIN_IMAGE = "breakout_win.png";
    public static final String LOSE_IMAGE = "breakout_lose.png";
    public static final int WIDTH = 408;
    public static final int HEIGHT = 612;
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String BLOCK_IMAGE_1 = "brick1.gif";
    public static final String BLOCK_IMAGE_2 = "brick2.gif";
    public static final String BLOCK_IMAGE_3 = "brick3.gif";
    public static final int MOVER_SPEED = 10;
    public static final String LEVEL_ONE = "config1.txt";
    public static final String LEVEL_TWO = "config2.txt";
    public static final String LEVEL_THREE = "config3.txt";
    public int scoreVal;
    public int levelVal;
    public int highScoreVal;

    private boolean cornerTest = false;
    private boolean breakTest = false;
    private boolean lifeTest = false;


    // some things we need to remember during our game
    private Scene myScene;
    private Group root;
    private Bouncer bouncer;
    private Paddle paddle;
    private ImageView myPaddle;
    private ImageView myBouncer;
    private ArrayList<Block> blockList;
    private ArrayList<PowerUp> powerUpList;
    private ArrayList<Bouncer> bouncerList;
    private Text lives;
    private Text level;
    private Text score;
    private Text highScore;
    private Stage myStage;
    private Timeline myAnimation;


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
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);

        //Adding HBox to the scene
        Scene scene = new Scene(sp);
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(e -> checkGameStart(e.getCode(), stage));
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

    private void readBlockConfiguration (String file, Group root, int screenWidth, int screenHeight){
        //file format is number, comma, number, etc
        //each line represents a line of bricks
        //each number is the number of times the brick can get hit until it breaks
        try {
            blockList = new ArrayList<>();

            Image imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));

            String rootPath = "resources/";
            FileReader in = new FileReader(rootPath + file);
            BufferedReader br = new BufferedReader(in);

            int y = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(",");
                for(int i = 0; i < splitLine.length; i++){
                    double x = i * imageBlock.getWidth();
                    int blockHits = Integer.valueOf(splitLine[i].split("\\.")[0]);
                    int powerUpType = Integer.valueOf(splitLine[i].split( "\\." )[1]);
                    if(blockHits == 0){
                        continue;
                    }
                    else if(blockHits == 1){
                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));
                    } else if (blockHits == 2){
                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_2));
                    } else {
                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_3));
                    }
                    ImageView myBlock = new ImageView(imageBlock);
                    Block block;
                    block = new Block(myBlock, blockHits, powerUpType,i * imageBlock.getWidth(),  y);
                    root.getChildren().add(block.getBlock());
                    blockList.add(block);
                }
                y += imageBlock.getHeight();
            }
        } catch (Exception ex){
            System.out.println("file not found");
        }
    }


    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);

        // x and y represent the top left corner, so center it
        var imageBouncer = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new ImageView(imageBouncer);
        bouncer = new Bouncer(myBouncer, this);

        var imagePaddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(imagePaddle);
        paddle = new Paddle(myPaddle);
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());
        readBlockConfiguration(LEVEL_ONE, root, SIZE, SIZE);

        level = new Text();
        level.setText("Level:  " + levelVal);
        level.setX(50);
        level.setY(HEIGHT-45);

        lives = new Text();
        lives.setText("Lives:  " + bouncer.getNumLives());
        lives.setX(50);
        lives.setY(HEIGHT-35);

        score = new Text();
        score.setText("Score:  " + scoreVal);
        score.setX(50);
        score.setY(HEIGHT-25);

        highScore.setText("High Score:  " + highScoreVal);
        highScore.setX(50);
        highScore.setY(HEIGHT-15);

        root.getChildren().add(bouncer.getBouncer());
        root.getChildren().add(paddle.getPaddle());
        root.getChildren().add(level);
        root.getChildren().add(lives);
        root.getChildren().add(score);
        root.getChildren().add(highScore);

        powerUpList = new ArrayList<>();
        bouncerList = new ArrayList<>();
        bouncerList.add(bouncer);

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

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        for(int i = 0; i < bouncerList.size(); i++){
            Bouncer currentBouncer = bouncerList.get(i);
            currentBouncer.move(elapsedTime);
            currentBouncer.checkIntersectPaddle(paddle);

            for(int j=0; j<blockList.size(); j++){
                Block currentBlock = blockList.get(j);
                currentBouncer.checkIntersectBlock(currentBlock, powerUpList, root, elapsedTime);
                if(currentBlock.getHitsLeft()==0){
                    blockList.remove(j);
                }
            }
        }

        for(int i = 0; i<powerUpList.size(); i++){
            PowerUp currentPowerUp = powerUpList.get(i);
            currentPowerUp.stepPowerUp(elapsedTime);
            if(currentPowerUp.intersectsPaddle(paddle)){
                currentPowerUp.applyPowerUp(paddle, bouncer, root, bouncerList);
                powerUpList.remove(i);
            }
        }
        if(blockList.isEmpty()){
            if(levelVal==1){
                levelVal++;
                level.setText("Level:  " + levelVal);
                bouncer.resetPos();
                bouncer.incrementSpeed(40);
                readBlockConfiguration(LEVEL_TWO, root, SIZE, SIZE);
            }
            else if(levelVal ==2) {
                levelVal++;
                level.setText("Level:  " + levelVal);
                bouncer.resetPos();
                bouncer.incrementSpeed(40);
                readBlockConfiguration(LEVEL_THREE, root, SIZE, SIZE);
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

    public void changeScore(){
        score.setText("Score:  " + scoreVal);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) throws IOException {
        paddle.handleKeyInput(code);

        if(code.equals(KeyCode.L)){
            bouncer.increaseNumLives();
            changeLives(bouncer.getNumLives());
        }
        else if(code.equals(KeyCode.R)){
            bouncer.resetPos();
        }
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
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);
        //Adding HBox to the scene
        Scene scene = new Scene(sp);
        stage.setScene(scene);
        stage.show();
        scene.setOnKeyPressed(e -> checkGameStart(e.getCode(), stage));
    }

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

