package se233.chapter4.model;

import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se233.chapter4.Launcher;
import se233.chapter4.view.Platform;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.TimeUnit;

public class Character extends Pane {
    public int CHARACTER_WIDTH = 32;
    public int CHARACTER_HEIGHT = 64;
    private Image characterImg;
    private AnimatedSprite imageView;
    private int x, y;
    private int startX , startY;
    private int offSetX , offSetY ;
    private KeyCode leftKey;
    private KeyCode rightKey;
    private KeyCode upKey;
    int yVelocity = 0;
    int xVelocity = 0;
    int xAcceleration = 1;
    int yAcceleration = 1;
    int xMaxVelocity = 7;
    int yMaxVelocity = 13;
    boolean isFalling = true;
    boolean canJump = false;
    boolean isJumping = false;
    boolean isMovingRight = false;
    boolean isMovingLeft = false;
    private int score = 0 ;

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass()) ;

    public Character(int x, int y, int offsetX, int offsetY, KeyCode leftKey, KeyCode rightKey, KeyCode upKey, String img , int Xacc , int yAcc , int xMaxVel , int yMaxVel) {

        this.xAcceleration = Xacc ;
        this.yAcceleration = yAcc;
        this.xMaxVelocity = xMaxVel;
        this.yMaxVelocity = yMaxVel;

        this.startX = x ;
        this.startY = y ;
        this.offSetX = offsetX ;
        this.offSetY = offsetY ;

        this.x = x;
        this.y = y;
        this.setTranslateX(x);
        this.setTranslateY(y);


        this.characterImg = new Image(Launcher.class.getResourceAsStream(String.format("assets/%s.png",img)));
        if(img.equals("MegamanSheet")){
            this.CHARACTER_WIDTH = 64 ;
            this.imageView = new AnimatedSprite(characterImg, 5, 5, 1, offsetX, offsetY, 550, 500);
        }else{
            this.imageView = new AnimatedSprite(characterImg, 4, 4, 1, offsetX, offsetY, 16, 32);
        }

        this.imageView.setFitHeight(CHARACTER_HEIGHT);
        this.imageView.setFitWidth(CHARACTER_WIDTH);
        this.leftKey = leftKey;
        this.rightKey = rightKey;
        this.upKey = upKey;
        this.getChildren().addAll(this.imageView);
    }

    public void repaint() {
        moveY();
        moveX();
    }

    public void jump() {
        if (canJump) {
            yVelocity = yMaxVelocity;
            canJump = false;
            isJumping = true;
            isFalling = false;
        }
    }

    public void checkReachHighest() {
        if (isJumping && yVelocity <= 0) {
            isJumping = false;
            isFalling = true;
            yVelocity = 0;
        }
    }

    public void checkReachFloor() {
        if (isFalling && y >= Platform.GROUND - CHARACTER_HEIGHT) {
            isFalling = false;
            canJump = true;
            yVelocity = 0;
        }
    }

    public void checkReachGameWall() {
        if (x <= 0) {
            x = 0;
        } else if (x + this.getWidth() >= Platform.WIDTH) {
            x = Platform.WIDTH - (int) (this.getWidth());
        }
    }

    public void moveLeft() {
        this.isMovingLeft = true;
        this.isMovingRight = false;
    }

    public void moveRight() {
        this.isMovingLeft = false;
        this.isMovingRight = true;
    }

    public void stop() {
        this.isMovingLeft = false;
        this.isMovingRight = false;
    }

    public void moveX() {
        setTranslateX(x);

        if (isMovingLeft) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xMaxVelocity + xAcceleration;
            x = x - xVelocity;
        }
        if (isMovingRight) {
            xVelocity = xVelocity >= xMaxVelocity ? xMaxVelocity : xMaxVelocity + xAcceleration;
            x = x + xVelocity;
        }

    }

    public void moveY() {
        setTranslateY(y);
        if (isFalling) {
            yVelocity = yVelocity >= yMaxVelocity ? yMaxVelocity : yMaxVelocity + yAcceleration;
            y = y + yVelocity;
        } else if (isJumping) {
            yVelocity = yVelocity <= 0 ? 0 : yVelocity - yAcceleration;
            y = y - yVelocity;
        }
    }

    public void collided(Character c) {
        if(isMovingLeft){
            x = c.getX() + CHARACTER_WIDTH + 1 ;
            stop();
        }else if (isMovingRight){
            x = c.getX() - CHARACTER_WIDTH - 1 ;
            stop();
        }

        if(y < Platform.GROUND - CHARACTER_HEIGHT){
            if(isFalling && y < c.getY() && (Math.abs(y-c.getY()) <= this.getCharacterHeight()+1) ){
                score++ ;
                y = Platform.GROUND - this.getCharacterHeight() - 5 ;
                c.collapsed() ;
                c.respawn() ;
            }
        }
    }

    public void respawn() {
        x = startX ;
        y = startY ;
        imageView.setFitWidth(this.getCharacterWidth());
        imageView.setFitHeight(this.getCharacterHeight());
        isMovingRight = false ;
        isMovingLeft = false ;
        isFalling = true ;
        canJump = false ;
        isJumping = false ;
    }

    public void collapsed() {
        imageView.setFitHeight(5);
        y = Platform.GROUND - 5 ;
        repaint();

        try{
            TimeUnit.MILLISECONDS.sleep(500);
        }catch (InterruptedException e){
            throw  new RuntimeException(e);
        }

    }

    public KeyCode getLeftKey() {
        return leftKey;
    }

    public KeyCode getRightKey() {
        return rightKey;
    }

    public KeyCode getUpKey() {
        return upKey;
    }

    public AnimatedSprite getImageView() {
        return imageView;
    }

    public  int getCharacterWidth() {
        return CHARACTER_WIDTH;
    }

    public  int getCharacterHeight() {
        return CHARACTER_HEIGHT;
    }

    public int getScore() {
        return score;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCHARACTER_WIDTH() {
        return CHARACTER_WIDTH;
    }

    public int getOffSetX() {
        return offSetX;
    }

    public int getOffSetY() {
        return offSetY;
    }

    public void trace() {
        logger.info("x:{} y:{} vx:{} vy:{}", x, y, xVelocity, yVelocity);
    }
}