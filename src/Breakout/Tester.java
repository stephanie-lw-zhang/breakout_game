package Breakout;

import javafx.scene.input.KeyCode;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public static final String CORNER_FILE = "corner.txt";
    public static final String BREAK_FILE = "break.txt";
    public static final String LIFE_FILE = "life.txt";

    private boolean cornerTest;
    private boolean breakTest;
    private boolean lifeTest;


    private int levelNum;


    public Tester(int levelNum){
        this.levelNum = levelNum;
        cornerTest = false;
        breakTest = false;
        lifeTest = false;
    }

    public void handleKeyInput(KeyCode code, Bouncer bouncer) throws IOException{
        if(levelNum==1){
            if(code.equals(KeyCode.COMMA)){
                cornerTest = true;
                this.configureTest(bouncer, CORNER_FILE);
            }
            else if(code.equals(KeyCode.PERIOD)){
                breakTest = true;
                this.configureTest(bouncer, BREAK_FILE);
            }
            else if(code.equals(KeyCode.SLASH)){
                lifeTest = true;
                this.configureTest(bouncer, LIFE_FILE);
            }
        }
        else if(levelNum==2){

        }
        else if(levelNum==3){

        }
        else{

        }
    }

    public void configureTest(Bouncer bouncer, String fileName) throws IOException {
        String rootPath = "resources/";
        FileReader in = new FileReader(rootPath + fileName);
        BufferedReader br = new BufferedReader(in);
        String line;
        String[] splitLine = null;
        while ((line = br.readLine()) != null) {
            splitLine = line.split(",");
        }
        bouncer.setPos(Double.parseDouble(splitLine[0]), Double.parseDouble(splitLine[1]));
        bouncer.setVelocities(Double.parseDouble(splitLine[2]), Double.parseDouble(splitLine[3]));
    }

    public void step(Bouncer bouncer){
        if(levelNum==1){
            if(cornerTest){

            }
            if(breakTest){

            }
            if(lifeTest){

            }

        }
        else if(levelNum==2){

        }
        else if(levelNum==3){

        }
        else{

        }
    }

}
