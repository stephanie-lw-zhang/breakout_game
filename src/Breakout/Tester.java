package Breakout;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Tester {

    public Tester(){

    }

    public static boolean configureTest(Bouncer bouncer, String fileName) throws IOException {
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
        return true;
    }

}
