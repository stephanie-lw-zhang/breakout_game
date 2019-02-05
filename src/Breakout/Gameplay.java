package Breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Gameplay extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final int SIZE = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final String BLOCK_IMAGE = "brick1.gif";
    public static final String SIZEPWR_IMAGE = "sizepower.gif";




    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 10;

    // some things we need to remember during our game
    private Scene myScene;
    private Group root;
    private Bouncer bouncer;
    private Paddle paddle;
    private Block block;
    private PowerUp sizePowerUp;


    private ImageView myPaddle;
    private ImageView myBouncer;
    private ArrayList<Block> blockList;
    private ArrayList<PowerUp> powerUpList;


    private ImageView imagePowerUp;

//    private ImageView myBlock;


//    private Boolean lost = Boolean.FALSE;
//    private Boolean won = Boolean.FALSE;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        myScene = setupGame(SIZE, SIZE, BACKGROUND);
        stage.setScene(myScene);
        stage.setTitle(TITLE);
        stage.show();
        // attach "game loop" to timeline to play it
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        var animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void readBlockConfiguration (String file, Group root, int screenWidth, int screenHeight){
        //file format is number, comma, number, etc
        //each line represents a line of bricks
        //each number is the number of times the brick can get hit until it breaks
        try {
            blockList = new ArrayList<Block>();
            var imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE));

            String rootPath = "resources/";
            FileReader in = new FileReader(rootPath + file);
            BufferedReader br = new BufferedReader(in);

            int y = 0;
            String line;
            while ((line = br.readLine()) != null) {
                String[] splitLine = line.split(",");
                for(int i = 0; i < Math.min(splitLine.length, screenWidth/imageBlock.getWidth()); i++){
                    double x = i * imageBlock.getWidth();
                    int value = Integer.valueOf(splitLine[i]);
                        if(value == 0){
                            continue;
                        }
                    ImageView myBlock = new ImageView( imageBlock);
                    Block block;
                    if (value == -1){
                        block = new Block(myBlock, 1, TRUE,i * imageBlock.getWidth(),  y);
                    } else {
                        block = new Block(myBlock, value, FALSE, i * imageBlock.getWidth(), y);
                    }
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
        // make some shapes and set their properties
        var imageBouncer = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new ImageView(imageBouncer);
        bouncer = new Bouncer(myBouncer);

        // x and y represent the top left corner, so center it

        var imagePaddle = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myPaddle = new ImageView(imagePaddle);
        paddle = new Paddle(myPaddle);
        myPaddle.setX(width / 2 - myPaddle.getBoundsInLocal().getWidth() / 2);
        myPaddle.setY(height - myPaddle.getBoundsInLocal().getHeight());


        readBlockConfiguration("config1.txt", root, SIZE, SIZE);


        // order added to the group is the order in which they are drawn
        root.getChildren().add(bouncer.getBouncer());
        root.getChildren().add(paddle.getPaddle());

        powerUpList = new ArrayList<PowerUp>();


        // respond to input
        scene.setOnKeyPressed(e -> paddle.handleKeyInput(e.getCode()));
        return scene;
    }



    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        bouncer.move(elapsedTime);
        bouncer.checkIntersectPaddle(paddle.getPaddle());
        for(Block each: blockList){
            bouncer.checkIntersectBlock(each, root);
            if(bouncer.intersectsBlock(each) && each.getPowerUp()){
                PowerUp pwrUp = new PowerUp(each);
                root.getChildren().add(pwrUp.getMyPowerUp());
                powerUpList.add(pwrUp);
            }
        }
        for(PowerUp each: powerUpList){
            each.move(elapsedTime);
            paddle.checkIntersectPowerUp(each.getMyPowerUp());
        }

    }

    // What to do each time a key is pressed

    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

