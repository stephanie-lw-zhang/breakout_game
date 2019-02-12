package Breakout;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class Tester {

    public static final String CORNER_FILE = "corner.txt";
    public static final String BREAK_FILE = "break.txt";
    public static final String LIFE_FILE = "life.txt";
    public static final String BOUNCER_POWERUP_FILE = "bouncerPowerUpTest.txt";
    public static final String POINTS_POWERUP_FILE = "pointsPowerUpTest.txt";
    public static final String PADDLE_POWERUP_FILE = "paddlePowerUpTest.txt";

    private boolean cornerTest;
    private boolean breakTest;
    private boolean lifeTest;
    private boolean bouncerPowerUpTest;
    private boolean pointsPowerUpTest;
    private boolean paddlePowerUpTest;

    private PowerUp currentPowerUp;
    private PowerUp powerUp;


    private int levelNum;



    public Tester(int levelNum){
        this.levelNum = levelNum;
        cornerTest = false;
        breakTest = false;
        lifeTest = false;
    }

    public void handleKeyInput(KeyCode code, Bouncer bouncer, Paddle paddle, Block block, Group root, List<PowerUp> powerUpList, List<Bouncer> bouncerList, Timeline timeline, Gameplay game) throws IOException {
        if(levelNum==1){
//            if(code.equals(KeyCode.COMMA)){
//                cornerTest = true;
//                this.configureTest(bouncer, CORNER_FILE);
//            }
//            else if(code.equals(KeyCode.PERIOD)){
//                breakTest = true;
//                this.configureTest(bouncer, BREAK_FILE);
//            }
//            else if(code.equals(KeyCode.SLASH)){
//                lifeTest = true;
//                this.configureTest(bouncer, LIFE_FILE);
//            }
        }
        else if(levelNum==2){
            if(code.equals(KeyCode.COMMA)){
                bouncerPowerUpTest = true;
                currentPowerUp = this.configureTestPowerUp(bouncer,paddle, BOUNCER_POWERUP_FILE, root, powerUpList, game);
            }
            else if(code.equals(KeyCode.PERIOD)){
                pointsPowerUpTest = true;
                currentPowerUp = this.configureTestPowerUp(bouncer, paddle, POINTS_POWERUP_FILE, root, powerUpList, game);
            }
            else if(code.equals(KeyCode.SLASH)){
                paddlePowerUpTest = true;
                currentPowerUp = this.configureTestPowerUp(bouncer,paddle, PADDLE_POWERUP_FILE, root, powerUpList, game);

            }
        }
        else if(levelNum==3){

        }
        else{

        }
    }


    public void configureTest(Bouncer bouncer, Paddle paddle, String fileName, Gameplay game) throws IOException {
        splitLineSetObjects( bouncer, paddle, fileName, game );
    }

    private String[] splitLineSetObjects(Bouncer bouncer, Paddle paddle, String fileName, Gameplay game) throws IOException {
        String rootPath = "resources/";
        FileReader in = new FileReader(rootPath + fileName);
        BufferedReader br = new BufferedReader(in);
        String line;
        String[] splitLine = null;
        while ((line = br.readLine()) != null) {
            splitLine = line.split(",");
        }
        game.changeScore(0);
        bouncer.setPos(Double.parseDouble(splitLine[0]), Double.parseDouble(splitLine[1]));
        bouncer.setVelocities(Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]));
        paddle.setPos(Double.parseDouble(splitLine[4]));
        return splitLine;
    }

    public PowerUp configureTestPowerUp(Bouncer bouncer, Paddle paddle, String fileName, Group root, List<PowerUp> powerUpList, Gameplay game) throws IOException {
        String [] splitLine = splitLineSetObjects( bouncer, paddle, fileName, game );
        PowerUp currentPowerUp = powerUp.createPowerUpForTest(Double.parseDouble(splitLine[5]), Double.parseDouble(splitLine[6]), Integer.parseInt(splitLine[7]), powerUpList, root);
        return currentPowerUp;
    }


    public void step(Bouncer bouncer, Paddle paddle, Block block, List<Bouncer> bouncerList, Timeline myAnimation, Gameplay game) {
        if (levelNum == 2) {
            if (bouncerPowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if (bouncerList.size() == 2) {
                        System.out.println( "Bouncer Power Up Test: " + "PASS" );
                    } else {
                        System.out.println( "Bouncer Power Up Test: " + "FAIL" );
                    }
                    myAnimation.pause();
                }
            }
            if (pointsPowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if (game.getScore() == 50) {
                        System.out.println( "Points Power Up Test: " + "PASS" );
                    } else {
                        System.out.println( "Points Power Up Test: " + "FAIL" );
                    }
                    myAnimation.pause();

                }
            }
            if (paddlePowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if(paddle.getPaddleBounds().getWidth() == 60*1.5){
                        System.out.println("Paddle Power Up Test: " + "PASS");
                    } else {
                        System.out.println("Paddle Power Up Test: " + "FAIL");
                    }
                    myAnimation.pause();

                }

            }

        }
    }
}
