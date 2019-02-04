import javafx.scene.image.ImageView;

public class Block {
    private ImageView myBlock;
    private int hitsLeft;


    public Block (ImageView block, int totalHits, double x, double y){
        this.myBlock = block;
        this.hitsLeft = totalHits;
        this.myBlock.setX(x);
        this.myBlock.setY(y);
    }

    public void gotHit(){
        this.hitsLeft -= 1;
    }

}
