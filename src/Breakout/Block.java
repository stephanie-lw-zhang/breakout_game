package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

public class Block {
    private static final String BLOCK_IMAGE_1 = "brick1.gif";
    private static final String BLOCK_IMAGE_2 = "brick2.gif";
    private static final String BLOCK_IMAGE_3 = "brick3.gif";

    private ImageView myBlock;
    private int hitsLeft;
    private int totalHits;
    private int powerUpType;


    public Block(ImageView block, int hits, int pwrup, double x, double y) {
        this.myBlock = block;
        this.hitsLeft = hits;
        this.totalHits = hits;
        this.powerUpType = pwrup;
        this.myBlock.setX(x);
        this.myBlock.setY(y);
    }

    public Bounds getBlockBounds() {
        return myBlock.getLayoutBounds();
    }

    public int getHitsLeft() {
        return this.hitsLeft;
    }

    public ImageView getBlock() {
        return myBlock;
    }

    public int getTotalHits(){
        return this.totalHits;
    }


    public void gotHit() {
        this.hitsLeft -= 1;
    }

    public Boolean isPowerUp() {
        return (this.powerUpType != 0);
    }

    public PowerUp getPowerUpType(){
        if(this.powerUpType == 1){
            return new WiderPaddlePowerUp();
        } else if (this.powerUpType == 2){
            return new AddBouncerPowerUp();
        } else {
            return new AddPointsPowerUp();
        }

    }

    public Boolean wasDestroyed(){
        return (hitsLeft == 0);
    }

    public static void readBlockConfiguration (String file, Group root, int screenWidth, int screenHeight, List<Block> blockList){
        //file format is number, comma, number, etc
        //each line represents a line of bricks
        //each number is the number of times the brick can get hit until it breaks
        try {

//            Image imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));
            Image imageBlock = new Image(BLOCK_IMAGE_1);


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
//                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));
                        imageBlock = new Image(BLOCK_IMAGE_1);

                    } else if (blockHits == 2){
//                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_2));
                        imageBlock = new Image(BLOCK_IMAGE_2);

                    } else {
//                        imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_3));
                        imageBlock = new Image(BLOCK_IMAGE_3);

                    }
                    ImageView otherBlock = new ImageView(imageBlock);
                    Block block;
                    block = new Block(otherBlock, blockHits, powerUpType,i * imageBlock.getWidth(),  y);
                    root.getChildren().add(block.getBlock());
                    blockList.add(block);
                }
                y += imageBlock.getHeight();
            }
        } catch (Exception ex){
            System.out.println("file not found");
        }
    }
}

