package Breakout;

import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;

/**
 * Class for Blocks.
 */
public class Block {
    private static final String BLOCK_IMAGE_1 = "brick1.gif";
    private static final String BLOCK_IMAGE_2 = "brick2.gif";
    private static final String BLOCK_IMAGE_3 = "brick3.gif";

    private ImageView myBlock;
    private int hitsLeft;
    private int totalHits;
    private int powerUpType;

    /**
     * Constructs a Block.
     * The int for powerup is hard-coded, so just check what number corresponds to what.
     * @param block
     * @param hits
     * @param pwrup
     * @param x
     * @param y
     */
    public Block(ImageView block, int hits, int pwrup, double x, double y) {
        this.myBlock = block;
        this.hitsLeft = hits;
        this.totalHits = hits;
        this.powerUpType = pwrup;
        this.myBlock.setX(x);
        this.myBlock.setY(y);
    }

    /**
     * Gets the bounds for blocks so you don't have to call myBlock everytime.
     * @return Bounds
     */
    public Bounds getBlockBounds() {
        return myBlock.getLayoutBounds();
    }

    /**
     *
     * @return int the number of hits left
     */
    public int getHitsLeft() {
        return this.hitsLeft;
    }

    /**
     * Getter for myBlock
     * @return ImageView myBlock
     */
    public ImageView getBlock() {
        return myBlock;
    }

    /**
     * This is needed to know how many points to assign to a block after it is destroyed.
     * @return int the total number of hits
     */
    public int getTotalHits(){
        return this.totalHits;
    }

    /**
     * Subtracts one from the hits left on a block. Needed especially for blocks that are more than one hit.
     */
    public void takeHit () {
        this.hitsLeft -= 1;
    }

    /**
     *
     * @return Boolean true if there is a powerup, false if otherwise
     */
    public Boolean isPowerUp() {
        return (this.powerUpType != 0);
    }

    /**
     * Setter for powerup type
     * Number hardcoded to correspond to certain powerup arbitrarily, so just check which one.
     * @param num
     */
    public void setPowerUpType(int num){
        this.powerUpType = num;
    }

    /**
     * Getter for powerup.
     * Number hardcoded to correspond to certain powerup arbitrarily, so just check which one.
     * @return PowerUp
     */
    public PowerUp getPowerUpType(){
        if(this.powerUpType == 1){
            return new WiderPaddlePowerUp();
        } else if (this.powerUpType == 2){
            return new AddBouncerPowerUp();
        } else {
            return new AddPointsPowerUp();
        }
    }

    /**
     *
     * @return Boolean, true if the block has no hits left and was destroyed, false otherwise
     */
    public Boolean wasDestroyed(){
        return (hitsLeft == 0);
    }

    /**
     * Reads the block configuration file and creates the blocks on the screen, setting the positions of each block accordingly.
     * Each number in the block configuration file corresponds to one block, and each line is one line of blocks on the screen.
     * The number before the decimal point indicates how many hits it takes to break the block,
     * the number after the decimal point is the powerup type, assigned arbitrarily but stays consistent throughout program.
     * @param file
     * @param root
     * @param screenWidth
     * @param screenHeight
     * @param blockList
     */
    public static void readBlockConfiguration (String file, Group root, int screenWidth, int screenHeight, List<Block> blockList){
        //file format is number, comma, number, etc
        //each line represents a line of bricks
        //each number is the number of times the brick can get hit until it breaks
        try {
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

    /**
     * Method is used to replace multiple-hit blocks as they are hit
     * so all blocks with the same number of hits left all look the same.
     * @param root
     * @param blockList
     */
    public void replaceBlock(Group root, List<Block> blockList){
        Image imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream(BLOCK_IMAGE_1));
        if(this.getHitsLeft() == 2) {
            imageBlock = new Image(this.getClass().getClassLoader().getResourceAsStream( BLOCK_IMAGE_2 ) );
        }
        ImageView otherBlock = new ImageView(imageBlock);
        Block newBlock = new Block(otherBlock, this.getHitsLeft(), this.powerUpType, this.getBlockBounds().getMinX(),  this.getBlockBounds().getMinY());
        root.getChildren().remove(this.getBlock());
        blockList.remove(blockList.indexOf(this));
        root.getChildren().add(newBlock.getBlock());
        blockList.add(newBlock);
    }

    /**
     * Sets the position and powerup used for testing.
     * @param x
     * @param y
     * @param powerUpType
     * @param root
     * @param blockList
     */
    public static void setPosAndPowerUp(double x, double y, int powerUpType, Group root, List<Block> blockList){
        Image imageBlock = new Image(BLOCK_IMAGE_1);
        ImageView imageViewBlock = new ImageView(imageBlock);
        Block newBlock = new Block(imageViewBlock, 1, powerUpType, x, y);
        root.getChildren().add(newBlock.getBlock());
        blockList.add(newBlock);
    }


}

