package Breakout;

import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.input.KeyCode;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.util.List;

public class Tester {

    public static final String CORNER_FILE = "corner.txt";
    public static final String BREAK_FILE = "break.txt";
    public static final String LIFE_FILE = "life.txt";
    public static final String BOUNCER_POWERUP_FILE = "bouncerPowerUpTest.txt";
    public static final String POINTS_POWERUP_FILE = "pointsPowerUpTest.txt";
    public static final String PADDLE_POWERUP_FILE = "paddlePowerUpTest.txt";
    public static final String PADDLE_BOUNCE_FILE = "paddleBounce.txt";
    public static final String WALL_BOUNCE_FILE = "wallBounce.txt";
    public static final String SIDE_BLOCK_FILE = "sideBlock.txt";
    public static final int SIZE = 400;

    private boolean cornerTest;
    private boolean breakTest;
    private boolean lifeTest;
    private boolean bouncerPowerUpTest;
    private boolean pointsPowerUpTest;
    private boolean paddlePowerUpTest;

    private PowerUp currentPowerUp;


    private boolean paddleBounceTest;
    private boolean wallBounceTest;
    private boolean sideBlockBounce;
    private int blockListSize;
    private int levelNum;


    public Tester(int levelNum) {
        this.levelNum = levelNum;
        cornerTest = false;
        breakTest = false;
        lifeTest = false;
        paddleBounceTest = false;
        wallBounceTest = false;
        sideBlockBounce = false;
    }

    public void handleKeyInput(KeyCode code, Bouncer bouncer, Paddle paddle, Group root, List<PowerUp> powerUpList, Gameplay game, List<Block> blockList) throws IOException {
        if (levelNum == 1) {
            if (code.equals( KeyCode.COMMA )) {
                cornerTest = true;
                this.configureTest( bouncer, paddle, CORNER_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.PERIOD )) {
                breakTest = true;
                this.configureTest( bouncer, paddle, BREAK_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.SLASH )) {
                lifeTest = true;
                this.configureTest( bouncer, paddle, LIFE_FILE,root, powerUpList, game, blockList );
            }
        } else if (levelNum == 2) {
            if (code.equals( KeyCode.COMMA )) {
                bouncerPowerUpTest = true;
                this.configureTest( bouncer, paddle, BOUNCER_POWERUP_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.PERIOD )) {
                pointsPowerUpTest = true;
                this.configureTest( bouncer, paddle, POINTS_POWERUP_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.SLASH )) {
                paddlePowerUpTest = true;
                this.configureTest( bouncer, paddle, PADDLE_POWERUP_FILE, root, powerUpList, game, blockList );
            }
        } else if (levelNum == 3) {
            if (code.equals( KeyCode.COMMA )) {
                paddleBounceTest = true;
                this.configureTest( bouncer, paddle, PADDLE_BOUNCE_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.PERIOD )) {
                wallBounceTest = true;
                this.configureTest( bouncer, paddle, WALL_BOUNCE_FILE, root, powerUpList, game, blockList );
            } else if (code.equals( KeyCode.SLASH )) {
                sideBlockBounce = true;
                this.configureTest( bouncer, paddle, SIDE_BLOCK_FILE, root, powerUpList, game, blockList );
            }
        }
    }



    public void configureTest(Bouncer bouncer, Paddle paddle, String fileName, Group root, List<PowerUp> powerUpList, Gameplay game, List<Block> blockList) throws IOException {
        String rootPath = "resources/";
        FileReader in = new FileReader( rootPath + fileName );
        BufferedReader br = new BufferedReader( in );
        String line;
        String[] splitLine = null;
        while ((line = br.readLine()) != null) {
            splitLine = line.split( "," );
        }
        if (fileName.equalsIgnoreCase( LIFE_FILE )) bouncer.setNumLives( 3 );
        else if (fileName.equalsIgnoreCase( BREAK_FILE ) | fileName.equalsIgnoreCase( SIDE_BLOCK_FILE )) {
            Block.setPosAndPowerUp( 170, 200, 0, root, blockList );
            blockListSize = blockList.size();
        }
        bouncer.setPos( Double.parseDouble( splitLine[0] ), Double.parseDouble( splitLine[1] ) );
        bouncer.setVelocities( Double.parseDouble( splitLine[2] ), Double.parseDouble( splitLine[3] ) );
        paddle.setPos( Double.parseDouble( splitLine[4] ) );
        game.changeScore( 0 );
        currentPowerUp = currentPowerUp.createPowerUpForTest( Double.parseDouble( splitLine[5] ), Double.parseDouble( splitLine[6] ), Integer.parseInt( splitLine[7] ), powerUpList, root );
    }


    public void step(Bouncer bouncer, Paddle paddle, List<Block> blockList, List<Bouncer> bouncerList, Timeline myAnimation, Gameplay game) {
        if (levelNum == 1) {
            if (cornerTest) {
                if (bouncer.getBouncer().getX() == bouncer.getBouncer().getY() && bouncer.getBouncer().getY() > 30) {
                    System.out.println( "Corner Test Passed!" );
                    myAnimation.pause();
                } else if (bouncer.getBouncer().getX() != bouncer.getBouncer().getY() && bouncer.getBouncer().getY() > 30) {
                    System.out.println( "Corner Test Failed!" );
                    myAnimation.pause();
                }
            } else if (breakTest) {
                if (blockListSize > blockList.size()) {
                    System.out.println( "Block Break Test Passed!" );
                    myAnimation.pause();
                } else if (bouncer.getBouncer().getY() < 200) {
                    System.out.println( "Block Break Test Failed!" );
                    myAnimation.pause();
                }
            } else if (lifeTest) {
                if (bouncer.getNumLives() == 2) {
                    System.out.println( "Lives Test Passed!" );
                    myAnimation.pause();
                } else {
                    System.out.println( "Lives Test Failed!" );
                    myAnimation.pause();
                }
            }
        }
        if (levelNum == 2) {
            if (bouncerPowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if (bouncerList.size() == 2) {
                        System.out.println( "Bouncer Power Up Test Passed!");
                    } else {
                        System.out.println( "Bouncer Power Up Test Failed!");
                    }
                    myAnimation.pause();
                }
            }
            if (pointsPowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if (game.getScore() == 50) {
                        System.out.println( "Points Power Up Test Passed!");
                    } else {
                        System.out.println( "Points Power Up Test Failed!");
                    }
                    myAnimation.pause();

                }
            }
            if (paddlePowerUpTest) {
                if (currentPowerUp.intersectsPaddle( paddle )) {
                    if (paddle.getPaddleBounds().getWidth() == 60 * 1.5) {
                        System.out.println( "Paddle Power Up Test Passed!");
                    } else {
                        System.out.println( "Paddle Power Up Test Failed!");
                    }
                    myAnimation.pause();

                }
            }

        } else if (levelNum == 3) {
            if (paddleBounceTest) {
                if (bouncer.getBouncer().getX() == bouncer.getBouncer().getY() && bouncer.getBouncer().getY() > 150) {
                    System.out.println( "Paddle Bounce Test Passed!" );
                    myAnimation.pause();
                } else if (bouncer.getBouncer().getY() > SIZE) {
                    System.out.println( "Paddle Bounce Test Failed!" );
                    myAnimation.pause();
                }
            } else if (wallBounceTest) {
                if (bouncer.getBouncer().getX() > 200) {
                    System.out.println( "Wall Bounce Test Passed!" );
                    myAnimation.pause();
                } else if (bouncer.getBouncer().getX() < -1) {
                    System.out.println( "Wall Bounce Test Failed!" );
                    myAnimation.pause();
                }
            } else if (sideBlockBounce) {
                if (bouncer.getBouncer().getX() < 20) {
                    System.out.println( "Side Block Bounce Test Passed!" );
                    myAnimation.pause();
                } else if (bouncer.getBouncer().getX() > 170) {
                    System.out.println( "Side Block Bounce Test Failed!" );
                    myAnimation.pause();
                }
            }
        }
    }
}

