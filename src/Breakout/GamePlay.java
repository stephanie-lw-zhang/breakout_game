package Breakout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class GamePlay extends Application {
    public static final String TITLE = "Example JavaFX";
    public static final String SPLASH_IMAGE = "breakout_background.png";
    public static final String LOSE_IMAGE = "breakout_lose.png";
    public static final String LEVEL = "Level 1";
    public static final int WIDTH = 400;
    public static final int HEIGHT = 400;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 5;

    // some things we need to remember during our game
    //private Stage stage;
    private Scene myScene;
    private Group root;
    private Bouncer bouncer;
    private Rectangle myPaddle;
    private Text level;
    private static Text lives;
    private static Stage myStage;


    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        myStage = stage;
        //this.stage = stage;
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
            startGame(stage);
        }
    }

    public void startGame (Stage stage){
        // attach scene to the stage and display it
        myScene = setupGame(HEIGHT, WIDTH, BACKGROUND);
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

    // Create the game's "scene": what shapes will be in the game and their starting properties
    private Scene setupGame (int width, int height, Paint background) {
        // create one top level collection to organize the things in the scene
        root = new Group();
        // create a place to see the shapes
        var scene = new Scene(root, width, height, background);
        // make some shapes and set their properties
        var image = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        ImageView myBouncer = new ImageView(image);
        bouncer = new Bouncer(myBouncer);
        myPaddle = new Rectangle(width / 2, height / 2 + 175, MOVER_SIZE, MOVER_SIZE/8);
        myPaddle.setFill(MOVER_COLOR);
        level = new Text();
        level.setText(LEVEL);
        level.setX(50);
        level.setY(HEIGHT-25);

        lives = new Text();
        lives.setText(Integer.toString(bouncer.getNumLives()));
        lives.setX(50);
        lives.setY(HEIGHT-15);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(bouncer.getView());
        root.getChildren().add(myPaddle);
        root.getChildren().add(level);
        root.getChildren().add(lives);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        return scene;
    }

    // Change properties of shapes to animate them
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start.
    private void step (double elapsedTime) {
        bouncer.move(elapsedTime);
        bouncer.checkIntersectPaddle(myPaddle);
    }

    public void changeLives(Bouncer bouncer){
        lives.setText(Integer.toString(bouncer.getNumLives()));
        if(lives.getText().equals("0")) loseScreen(myStage);
    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myPaddle.setX(myPaddle.getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myPaddle.setX(myPaddle.getX() - MOVER_SPEED);
        }
        else if(code.getName().equalsIgnoreCase("L")){
            bouncer.increaseNumLives();
            changeLives(bouncer);
        }
        else if(code.getName().equalsIgnoreCase("R")){
            bouncer.setPos(HEIGHT/2- bouncer.getView().getBoundsInLocal().getWidth() / 2,WIDTH/2 -bouncer.getView().getBoundsInLocal().getWidth() / 2);
        }
    }

    private void loseScreen(Stage stage){
        stage.setTitle("Load Image");
        StackPane sp = new StackPane();
        Image img = new Image(LOSE_IMAGE);
        ImageView imgView = new ImageView(img);
        sp.getChildren().add(imgView);

        //Adding HBox to the scene
        Scene scene = new Scene(sp);
        stage.setScene(scene);
        stage.show();
        //scene.setOnKeyPressed(e -> checkGameStart(e.getCode(), stage));
    }
    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}

