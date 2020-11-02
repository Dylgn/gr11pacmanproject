/*
Dylan Grinton
10/18/19
Game where you avoid ghosts and collect coins
*/
package dylan;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.util.concurrent.ThreadLocalRandom;
import javafx.event.ActionEvent;
import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;

public class FXMLController implements Initializable {
    
    @FXML private ImageView imgCoin1;
    
    @FXML private ImageView imgCoin2;

    @FXML private ImageView imgCoin3;

    @FXML private ImageView imgCoin4;

    @FXML private ImageView imgCoin5;

    @FXML private ImageView imgCoin6;

    @FXML private ImageView imgCoin7;

    @FXML private ImageView imgCoin8;


    @FXML private Rectangle rctArea1;

    @FXML private Rectangle rctArea2;

    @FXML private Rectangle rctArea3;

    @FXML private Rectangle rctArea4;

    @FXML private Rectangle rctArea5;

    @FXML private Rectangle rctArea6;

    @FXML private Rectangle rctArea7;

    @FXML private Rectangle rctArea8;


    @FXML private Rectangle rctWall1;

    @FXML private Rectangle rctWall2;

    @FXML private Rectangle rctWall3;

    @FXML private Rectangle rctWall4;

    @FXML private Rectangle rctWall5;

    @FXML private Rectangle rctWall6;

    @FXML private Rectangle rctWall7;

    @FXML private Rectangle rctWall8;

    @FXML private Rectangle rctWall9;

    @FXML private Rectangle rctWall10;

    @FXML private Rectangle rctWall11;

    @FXML private Rectangle rctWall12;

    @FXML private Rectangle rctWall13;

    @FXML private Rectangle rctWall14;

    @FXML private Rectangle rctWall15;

    @FXML private Rectangle rctWall16;

    @FXML private Rectangle rctWall17;

    @FXML private Rectangle rctWall18;

    @FXML private Rectangle rctWall19;

    @FXML private Rectangle rctWall20;

    @FXML private Rectangle rctWall21;

    @FXML private Rectangle rctWall22;

    @FXML private Rectangle rctWall23;

    @FXML private Rectangle rctWall24;

    @FXML private Rectangle rctWall25;

    @FXML private Rectangle rctWall26;

    @FXML private Rectangle rctWall27;

    @FXML private Rectangle rctWall28;

    @FXML private Rectangle rctWall29;

    @FXML private Rectangle rctWall30;

    @FXML private Rectangle rctWall31;

    @FXML private Rectangle rctWall32;

    @FXML private Rectangle rctWall33;

    @FXML private Rectangle rctWall34;

    @FXML private Rectangle rctWall35;

    @FXML private Rectangle rctWall36;

    @FXML private Rectangle rctWall37;
    
    ArrayList<Rectangle> walls = new ArrayList();
    ArrayList<ImageView> coins = new ArrayList();
    
    Timeline timer;
    MediaPlayer player = new MediaPlayer((new Media(getClass().getResource("/mainTheme.mp3").toString())));
    //Second Media Player for Coin Effects (Plays at the same time)
    MediaPlayer coinEffect;

    Image evil = new Image(getClass().getResource("/evilcoin.png").toString());

    //x any y movement for ghost
    int x = 0, y = 0;

    //x and y movement for random ghost
    int enemyTwoX = 5, enemyTwoY;
    
    int points = 0;
    int level = 1;

    //countdown used in several parts
    int countdown;

    @FXML private ImageView imgPlayer;
    
    @FXML private ImageView imgEnemyOne;
    @FXML private ImageView imgEnemyTwo;
    @FXML private ImageView imgEnemyThree;
    @FXML private ImageView imgEnemyFour;

    //music button (imageView) and its volume
    @FXML private ImageView imgMusic;
    double volume = 0.1;

    private Rectangle lastRectangle;

    @FXML private Label lblNext;

    @FXML private Label lblPoints;
    @FXML private Label lblLevel;
    @FXML private Label lblOutput;

    private void move() {
        
        //moves player based on keys pressed

        imgPlayer.setTranslateX(imgPlayer.getTranslateX() + x);
        imgPlayer.setTranslateY(imgPlayer.getTranslateY() + y);
        
        //checks if you collide with a wall
        
       if (checkCollision(imgPlayer)) {
           
           imgPlayer.setTranslateX(imgPlayer.getTranslateX() - x);
           imgPlayer.setTranslateY(imgPlayer.getTranslateY() - y);
       }

       //losing to collision with ghosts

        if (collision(imgPlayer, imgEnemyOne) || collision(imgPlayer, imgEnemyTwo) || collision(imgPlayer, imgEnemyThree)) {

            lose();

        } else if (collision(imgPlayer, imgEnemyFour) && imgEnemyFour.isVisible() == true) {

            lose();
        }

       //collisions with coin objectives
       
       coin();

        //changes visibility based on distance to player (AFTER LEVEL 3)

        if (level >= 4) {

            proximity(imgEnemyOne, 150);
            proximity(imgEnemyTwo, 150);
            proximity(imgEnemyThree, 150);
            proximity(imgEnemyFour, 150);

        }

        //coin visibility (AFTER LEVEL 4)

        if (level >= 5) {

            for (ImageView coin : coins) {

                proximity(coin, 75);
            }

        }
       //enemy movement

        enemyMovementOne();
        enemyMovementTwo();
        enemyMovementThree();

        //AFTER LEVEL 2

        if (level >= 3) {

            enemyMovementFour();
        }

        //lblOutput.setText(getY(imgPlayer) + " " + getY(imgEnemyThree) );
        //lblOutput.setText(getX(imgEnemyTwo) + " " + getY(imgEnemyTwo));
    }

    private void countdown() {

        if (countdown == 0) {

            lblOutput.setText("");

            //starts timer for game to function

            timer = new Timeline(new KeyFrame(Duration.millis(50), ae -> move()));
            timer.setCycleCount(Timeline.INDEFINITE);
            timer.play();

            //plays music

            player = new MediaPlayer((new Media(getClass().getResource("/mainTheme.mp3").toString())));
            player.setVolume(volume);
            player.setCycleCount(Timeline.INDEFINITE);
            player.play();

        } else {

            lblOutput.setText("Starting in " + countdown + "...");
            --countdown;

        }
    }

    private void deathAnimation() {

        //changes images to cycle through animations
        if (countdown == 5) {

            imageChange(new Image(getClass().getResource("/pacmanDeath1.png").toString()),
                        new Image(getClass().getResource("/pacmanPDeath1.png").toString()),
                        new Image(getClass().getResource("/pacmanGDeath1.png").toString()),
                        new Image(getClass().getResource("/pacmanBDeath1.png").toString()));

            --countdown;

        } else if (countdown == 4) {

            imageChange(new Image(getClass().getResource("/pacmanDeath2.png").toString()), new Image(getClass().getResource("/pacmanPDeath2.png").toString()),
                        new Image(getClass().getResource("/pacmanGDeath2.png").toString()), new Image(getClass().getResource("/pacmanBDeath2.png").toString()));

            --countdown;

        } else if (countdown == 3) {

            imageChange(new Image(getClass().getResource("/pacmanDeath3.png").toString()), new Image(getClass().getResource("/pacmanPDeath3.png").toString()),
                        new Image(getClass().getResource("/pacmanGDeath3.png").toString()), new Image(getClass().getResource("/pacmanBDeath3.png").toString()));

            --countdown;

        } else if (countdown == 2) {

            imageChange(new Image(getClass().getResource("/pacmanDeath4.png").toString()), new Image(getClass().getResource("/pacmanPDeath4.png").toString()),
                        new Image(getClass().getResource("/pacmanGDeath4.png").toString()), new Image(getClass().getResource("/pacmanBDeath4.png").toString()));

            --countdown;

        } else {

            //removes images

            imageChange(null, null, null, null);

            lblNext.setVisible(true);
        }
    }

    private void yourDeathAnimation() {

        //death animation for player

        imgPlayer.setOpacity(imgPlayer.getOpacity() - 0.05);

        //activates once player is invisible

        if (imgPlayer.getOpacity() <= 0) {

            lblNext.setVisible(true);
        }
    }

    private void enemyMovementOne() {
        
        //checks collision to set last collision
        
        oneCollision(rctWall1, "up");
        oneCollision(rctWall37, "right");
        oneCollision(rctWall15, "down");
        oneCollision(rctWall36, "left");
        
        //moves based on the last rectangle that it hit

        if (lastRectangle == rctWall1) {
            
            imgEnemyOne.setTranslateY(imgEnemyOne.getTranslateY() - 6);
            
        } else if (lastRectangle == rctWall37) {
            
            imgEnemyOne.setTranslateX(imgEnemyOne.getTranslateX() + 6);
            
        } else if (lastRectangle == rctWall15) {
            
            imgEnemyOne.setTranslateY(imgEnemyOne.getTranslateY() + 6);
            
        } else if (lastRectangle == rctWall36) {
            
            imgEnemyOne.setTranslateX(imgEnemyOne.getTranslateX() - 6);
            
        } else {
            
            imgEnemyOne.setTranslateY(imgEnemyOne.getTranslateY() - 6);
            imgEnemyOne.setRotate(270);
        }
    }
    
    private void enemyMovementTwo() {

        int ranNum;
        //determines direction randomly once reaching a certain spot
        if (getX(imgEnemyTwo) == 350 && getY(imgEnemyTwo) == 385) {
            //middle right
            ranNum = ThreadLocalRandom.current().nextInt(1,3+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
                case 2:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
                default:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 280 && getY(imgEnemyTwo) == 245) {
            //top middle
            ranNum = ThreadLocalRandom.current().nextInt(1,3+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
                case 2:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
                default:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 90 && getY(imgEnemyTwo) == 245) {
            //top left
            ranNum = ThreadLocalRandom.current().nextInt(1,2+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
                default:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 200 && getY(imgEnemyTwo) == 385) {
            //middle left
            ranNum = ThreadLocalRandom.current().nextInt(1,3+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
                case 2:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
                default:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 90 && getY(imgEnemyTwo) == 540) {
            //bottom left
            ranNum = ThreadLocalRandom.current().nextInt(1,2+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
                default:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 280 && getY(imgEnemyTwo) == 540) {
            //bottom middle
            ranNum = ThreadLocalRandom.current().nextInt(1,3+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
                case 2:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
                default:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 280 && getY(imgEnemyTwo) == 385) {
            //middle
            ranNum = ThreadLocalRandom.current().nextInt(1,4+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
                case 2:
                    imgEnemyTwo.setRotate(0);
                    enemyTwoX = 5;
                    enemyTwoY = 0;
                    break;
                case 3:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
                default:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
            }
        } else if (getX(imgEnemyTwo) == 455 && getY(imgEnemyTwo) == 540) {
            //bottom right
            ranNum = ThreadLocalRandom.current().nextInt(1,2+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(270);
                    enemyTwoX = 0;
                    enemyTwoY = -5;
                    break;
                default:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
            } 
        }
        
        else if (getX(imgEnemyTwo) == 455 && getY(imgEnemyTwo) == 245) {
            //top right
            ranNum = ThreadLocalRandom.current().nextInt(1,2+1);
            switch (ranNum) {
                case 1:
                    imgEnemyTwo.setRotate(90);
                    enemyTwoX = 0;
                    enemyTwoY = 5;
                    break;
                default:
                    imgEnemyTwo.setRotate(180);
                    enemyTwoX = -5;
                    enemyTwoY = 0;
                    break;
            }
        }
        
        setX(imgEnemyTwo, getX(imgEnemyTwo) + enemyTwoX);
        setY(imgEnemyTwo, getY(imgEnemyTwo) + enemyTwoY);
        
        //changes directions after a collision based on what rectangle was hit

        if (collision(imgEnemyTwo, rctWall8) || collision(imgEnemyTwo, rctWall31)) {
            //going up then moving right

            directionChangeEnemyTwo(5, 0);
            imgEnemyTwo.setRotate(0);

        } else if (collision(imgEnemyTwo, rctWall2) || collision(imgEnemyTwo, rctWall25)) {
            //going up then moving left

            directionChangeEnemyTwo(-5, 0);
            imgEnemyTwo.setRotate(180);
            
        } else if (collision(imgEnemyTwo, rctWall10) || collision(imgEnemyTwo, rctWall17)) {
            //going right then moving up

            directionChangeEnemyTwo(0, -5);
            imgEnemyTwo.setRotate(270);

        } else if (collision(imgEnemyTwo, rctWall4) || collision(imgEnemyTwo, rctWall23)) {
            //going right then moving down

            directionChangeEnemyTwo(0, 5);
            imgEnemyTwo.setRotate(90);
            
        } else if (collision(imgEnemyTwo, rctWall29) || collision(imgEnemyTwo, rctWall32)) {
            //going down then moving right

            directionChangeEnemyTwo(5, 0);
            imgEnemyTwo.setRotate(0);

        } else if (collision(imgEnemyTwo, rctWall30) || collision(imgEnemyTwo, rctWall13)) {
            //going down then moving left

            directionChangeEnemyTwo(-5, 0);
            imgEnemyTwo.setRotate(180);
            
        } else if (collision(imgEnemyTwo, rctWall7) || collision(imgEnemyTwo, rctWall26)) {
            //going left then moving up

            directionChangeEnemyTwo(0, -5);
            imgEnemyTwo.setRotate(270);

        } else if (collision(imgEnemyTwo, rctWall19) || collision(imgEnemyTwo, rctWall14)) {
            //going left then moving down

            directionChangeEnemyTwo(0, 5);
            imgEnemyTwo.setRotate(90);
        }
    }
    
    private void enemyMovementThree() {

        //follows you
        boolean directionChange = false;

        if (getY(imgPlayer) > getY(imgEnemyThree)) {
            //goes down if player is down

            imgEnemyThree.setTranslateY(imgEnemyThree.getTranslateY() + 5);
            imgEnemyThree.setRotate(90);

            //collision check

            if (checkCollision(imgEnemyThree)) {

                imgEnemyThree.setTranslateY(imgEnemyThree.getTranslateY() - 5.5);
            }

            //also goes left to get out of area rectangle

            if (Math.abs(getY(imgPlayer) - getY(imgEnemyThree)) > 20) {

                if (collision(imgEnemyThree, rctArea1) || collision(imgEnemyThree, rctArea3) || collision(imgEnemyThree, rctArea5) || collision(imgEnemyThree, rctArea7)) {

                    imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5);
                    imgEnemyThree.setRotate(180);
                    directionChange = true;

                    //collision check

                    if (checkCollision(imgEnemyThree)) {

                        imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5.5);
                    }

                    //right for certain area rectangles

                } else if (collision(imgEnemyThree, rctArea2) || collision(imgEnemyThree, rctArea4) || collision(imgEnemyThree, rctArea6) || collision(imgEnemyThree, rctArea8)) {

                    imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5);
                    imgEnemyThree.setRotate(0);
                    directionChange = true;

                    //collision check

                    if (checkCollision(imgEnemyThree)) {

                        imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5.5);
                    }
                }
            }

        } else if (getY(imgPlayer) < getY(imgEnemyThree)) {
            //goes up if player is up

            //only goes up if far away from y value of player
            if (Math.abs(getY(imgPlayer) - getY(imgEnemyThree)) > 5) {

                imgEnemyThree.setTranslateY(imgEnemyThree.getTranslateY() - 5);
            }

            imgEnemyThree.setRotate(270);

            //collision check

            if (checkCollision(imgEnemyThree)) {

                imgEnemyThree.setTranslateY(imgEnemyThree.getTranslateY() + 5.5);
            }

            //also goes right to get out of certain area rectangles

            if (Math.abs(getY(imgPlayer) - getY(imgEnemyThree)) > 20) {

                if (collision(imgEnemyThree, rctArea1) || collision(imgEnemyThree, rctArea3) || collision(imgEnemyThree, rctArea5) || collision(imgEnemyThree, rctArea7)) {

                    imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5);
                    imgEnemyThree.setRotate(0);
                    directionChange = true;

                    //collision check

                    if (checkCollision(imgEnemyThree)) {

                        imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5.5);
                    }

                    //left for certain area rectangles

                } else if (collision(imgEnemyThree, rctArea2) || collision(imgEnemyThree, rctArea4) || collision(imgEnemyThree, rctArea6) || collision(imgEnemyThree, rctArea8)) {

                    imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5);
                    imgEnemyThree.setRotate(180);
                    directionChange = true;

                    //collision check

                    if (checkCollision(imgEnemyThree)) {

                        imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5.5);
                    }
                }
            }
        }

        if (getX(imgPlayer) > getX(imgEnemyThree) && directionChange == false) {
            //goes right if player is right

            imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5);

            //will only rotate when further than 5 units on x value

            if (Math.abs(getX(imgPlayer) - getX(imgEnemyThree)) > 5) {

                imgEnemyThree.setRotate(0);
            }

            //collision check

            if (checkCollision(imgEnemyThree)) {

                imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5.5);
            }

        } else if (getX(imgPlayer) < getX(imgEnemyThree) && directionChange == false) {
            //goes left if player is left

            //will only rotate & move when further than 5 units (prevent buggy movement)

            if (Math.abs(getX(imgPlayer) - getX(imgEnemyThree)) > 5) {

                imgEnemyThree.setRotate(180);
                imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() - 5);

            }

            //collision check

            if (checkCollision(imgEnemyThree)) {

                imgEnemyThree.setTranslateX(imgEnemyThree.getTranslateX() + 5.5);
            }
        }
    }

    private void enemyMovementFour() {
        
        //follows you
        
        if (getY(imgPlayer) > getY(imgEnemyFour)) {
            //goes down if player is down
            
            if (Math.abs(getY(imgPlayer) - getY(imgEnemyFour)) <= 200) {
                //slows down if he is close

                    imgEnemyFour.setTranslateY(imgEnemyFour.getTranslateY() + 1);

            } else {

                imgEnemyFour.setTranslateY(imgEnemyFour.getTranslateY() + 2.5);

            }
            imgEnemyFour.setRotate(90);

        } else if (getY(imgPlayer) < getY(imgEnemyFour)) {
            //goes up if player is up
            
            if (Math.abs(getY(imgPlayer) - getY(imgEnemyFour)) <= 200) {
                //slows down if he is close
                
                imgEnemyFour.setTranslateY(imgEnemyFour.getTranslateY() - 1);

            } else {
                
                imgEnemyFour.setTranslateY(imgEnemyFour.getTranslateY() - 2.5);

            }

            imgEnemyFour.setRotate(270);
        }

        if (getX(imgPlayer) > getX(imgEnemyFour)) {
            //goes right if player is right

            if (Math.abs(getX(imgPlayer) - getX(imgEnemyFour)) <= 200) {
                //slows down if he is close

                    imgEnemyFour.setTranslateX(imgEnemyFour.getTranslateX() + 1);

            } else {

                imgEnemyFour.setTranslateX(imgEnemyFour.getTranslateX() + 2.5);

            }
            imgEnemyFour.setRotate(0);

        } else if (getX(imgPlayer) < getX(imgEnemyFour)) {
            //goes left if player is left

            if (Math.abs(getX(imgPlayer) - getX(imgEnemyFour)) <= 200) {
                //slows down if he is close

                imgEnemyFour.setTranslateX(imgEnemyFour.getTranslateX() - 1);

            } else {

                imgEnemyFour.setTranslateX(imgEnemyFour.getTranslateX() - 2.5);

            }
            imgEnemyFour.setRotate(180);
        }
    }

    private void proximity(ImageView enemy, int proximity) {

        //only visible if enemy is close (within 100 units)
        if (Math.abs(getX(imgPlayer) - getX(enemy)) < proximity && Math.abs(getY(imgPlayer) - getY(enemy)) < proximity) {

            //disabled is only for coins
            if (enemy.isDisabled() == false) {

                enemy.setVisible(true);

            }
        } else {

            enemy.setVisible(false);

        }

    }

    private void coin() {
        int gotCoins = 0;

        //checks for collisions with coins
        
        for (ImageView coin : coins) {  
           
              if (collision(imgPlayer, coin)) {
                  
                  if (coin.getImage() == evil) {
                      
                      lose();

                  } else {

                      if (coin.isDisabled() == false && coin.isVisible() == true) {
                          points = points + 100;
                          coinEffect = new MediaPlayer((new Media(getClass().getResource("/coinSound.mp3").toString())));
                          coinEffect.setVolume(volume);
                          coinEffect.play();
                          coin.setDisable(true);
                      }

                      coin.setVisible(false);
                      lblPoints.setText("Points: " + points);
                  }
            }
              //checks for no good coins left (winning)

              if (coin.getImage().equals(evil)) {
                  ++gotCoins;
              } else if (coin.isDisabled() == true) {
                  ++gotCoins;
              }
       }

        if (gotCoins == 8) {

            //WIN CODE
            lblOutput.setText("You Win!");
            timer.stop();

            //point update

            points = points + 200;
            ++level;

            //death animations

            imgEnemyOne.setVisible(true);
            imgEnemyTwo.setVisible(true);
            imgEnemyThree.setVisible(true);

            if (level >= 3) {
                imgEnemyFour.setVisible(true);
            }

            //enemy death animation

            countdown = 5;
            timer = new Timeline(new KeyFrame(Duration.millis(200), ae -> deathAnimation()));
            timer.setCycleCount(5);
            timer.play();

            //music

            player = new MediaPlayer((new Media(getClass().getResource("/intermission.mp3").toString())));
            player.setVolume(volume);
            player.play();
        }
    }

    @FXML
    void startLevel(MouseEvent event) {
        //updates buttons and labels

        lblNext.setVisible(false);
        lblNext.setText("Next Level");

        lblLevel.setText("Level: " + level);
        lblPoints.setText("Points: " + points);

        //adds new ghost if at certain level

        if (level == 3) {

            imgEnemyFour.setVisible(true);
            imgEnemyFour .setDisable(false);
        }

        //coin setup

        int ranNum = ThreadLocalRandom.current().nextInt(0,2+1);
        int i = ranNum;

        for (ImageView coin : coins) {
            //removes images from coins
            coin.setDisable(false);
            coin.setImage(null);
        }

        //setups evil coins (between 0-2)

        while (i > 0) {
            ranNum = ThreadLocalRandom.current().nextInt(0,7+1);

            //if an evil coin is already there
            if (coins.get(ranNum).getImage() == evil) {
                //will increase so it goes into while with same integer
                i++;
            } else {
                //creates the evil coin
                coins.get(ranNum).setImage(evil);
            }
            i--;
        }

        //sets all other coins to normal image
        for (ImageView coin : coins) {

            //only reveals coins before level 5 (Proximity)
            if (level < 5) {

                coin.setVisible(true);

            } else {

                coin.setVisible(false);

            }

            if (coin.getImage() != evil) {

                coin.setImage(new Image(getClass().getResource("/coin.png").toString()));
            }
       }
        //returns enemies to starting positions

        setX(imgPlayer, 275); setY(imgPlayer, 385);
        imgPlayer.setOpacity(1);

        setX(imgEnemyOne, 20); setY(imgEnemyOne, 684);
        imgEnemyOne.setRotate(270);

        setX(imgEnemyTwo, 175); setY(imgEnemyTwo, 245);
        imgEnemyTwo.setRotate(0);

        setX(imgEnemyFour, 20); setY(imgEnemyFour, 100);
        imgEnemyFour.setRotate(0);

        setX(imgEnemyThree, 525); setY(imgEnemyThree, 100);
        imgEnemyThree.setRotate(180);

        enemyTwoX = 5;
        enemyTwoY = 0;

        lastRectangle = rctWall1;

        imgEnemyOne.setImage(new Image(getClass().getResource("/pacman.png").toString()));
        imgEnemyTwo.setImage(new Image(getClass().getResource("/pacmanPink.png").toString()));
        imgEnemyThree.setImage(new Image(getClass().getResource("/pacmanGreen.png").toString()));
        imgEnemyFour.setImage(new Image(getClass().getResource("/pacmanBlue.png").toString()));

        //starts the game

        lblOutput.setText("Starting in 3...");
        countdown = 2;
        timer = new Timeline(new KeyFrame(Duration.seconds(1), ae -> countdown()));
        timer.setCycleCount(3);
        timer.play();

        //stops music if it is playing

        if (level != 1) {

            player.stop();
        }
    }

    private void lose() {

        //changes label to lose and allows to restart

        lblOutput.setText("You Lose!");

        lblNext.setText("Restart");

        timer.stop();

        //resets score
        //score won't be updated on interface until you click lblNext
        points = 0;
        level = 1;

        x = 0;
        y = 0;

        imgEnemyOne.setVisible(true);
        imgEnemyTwo.setVisible(true);
        imgEnemyThree.setVisible(true);

        //death animation

        timer = new Timeline(new KeyFrame(Duration.millis(50), ae -> yourDeathAnimation()));
        timer.setCycleCount(20);
        timer.play();

        //resets enemy that is only in later levels

        imgEnemyFour.setVisible(false);
        imgEnemyFour.setDisable(true);

        // music

        player.stop();
        player = new MediaPlayer((new Media(getClass().getResource("/gameOver.mp3").toString())));
        player.setVolume(volume);
        player.play();
    }

    private boolean checkCollision(ImageView img) {
        
        //returns true if a collision happens
        
        for (Rectangle wall : walls) {  
           
              if (collision(img, wall)) {
                  
                return true;
            }
       }
        return false;
    }
    private void oneCollision (Rectangle wall , String direction ) {
        if (collision(imgEnemyOne, wall)) {
            
            lastRectangle = wall;
            
            switch (direction) {
                //changes movement and direction based on direction string
                case "up":
                    imgEnemyOne.setTranslateX(imgEnemyOne.getTranslateX() + 6);
                    imgEnemyOne.setRotate(270);
                    break;
                case "right":
                    imgEnemyOne.setTranslateY(imgEnemyOne.getTranslateY() + 6);
                    imgEnemyOne.setRotate(0);
                    break;
                case "down":
                    imgEnemyOne.setTranslateX(imgEnemyOne.getTranslateX() - 6);
                    imgEnemyOne.setRotate(90);
                    break;
                case "left":
                    imgEnemyOne.setTranslateY(imgEnemyOne.getTranslateY() - 6);
                    imgEnemyOne.setRotate(180);
                    break;
                default:
                    break;
            }
        }
    }

    @FXML
    void musicButton(MouseEvent event) {

        if (volume == 0.1) {

            volume = 0;
            imgMusic.setImage(new Image(getClass().getResource("/volume0.png").toString()));

        } else if (volume == 0.25) {

            volume = 0.1;
            imgMusic.setImage(new Image(getClass().getResource("/volume1.png").toString()));

        } else if (volume == 0) {

            volume = 0.25;
            imgMusic.setImage(new Image(getClass().getResource("/volume2.png").toString()));
        }

        player.setVolume(volume);
    }

    private void imageChange(Image img1, Image img2, Image img3, Image img4) {

        imgEnemyOne.setImage(img1);
        imgEnemyTwo.setImage(img2);
        imgEnemyThree.setImage(img3);
        imgEnemyFour.setImage(img4);

    }

    private void directionChangeEnemyTwo(int x, int y) {

        //bounces back before new movement

        setX(imgEnemyTwo, getX(imgEnemyTwo) - enemyTwoX);
        setY(imgEnemyTwo, getY(imgEnemyTwo) - enemyTwoY);

        //new movement variables

        enemyTwoX = x;
        enemyTwoY =  y;
    }

    public boolean collision(Node block1, Node block2) {
        //returns true if the areas intersect, false if they dont
        return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
    }
    public boolean imgCollision(ImageView block1, ImageView block2) {
        //returns true if the areas intersect, false if they dont
        return (block1.getBoundsInParent().intersects(block2.getBoundsInParent()));
    }
    
    public void setX(Node block1, double locX) {
        block1.setTranslateX(locX - block1.getLayoutX());
    }
    
    public void setY(Node block1, double locY) {
        block1.setTranslateY(locY - block1.getLayoutY());
    }
    
    public double getX(Node block1) {
        return block1.getTranslateX() + block1.getLayoutX();
    }
    
    public double getY(Node block1) {
        return block1.getTranslateY() + block1.getLayoutY();
    }
    
    @FXML
    public void keyPressed(KeyEvent event) {
        
        //moves player using W A S D
        
        if ((event.getCode() == KeyCode.W)) {
            y = -5;
            x = 0;
        }
        if ((event.getCode() == KeyCode.A)) {
            y = 0;
            x = -5;

            if (timer.getCycleCount() == timer.INDEFINITE) {
                imgPlayer.setRotationAxis(new Point3D(0, 90, 1));
                imgPlayer.setRotate(180);
            }
        }
        if ((event.getCode() == KeyCode.S)) {
            y = 5;
            x = 0;
        }
        if ((event.getCode() == KeyCode.D)) {
            y = 0;
            x = 5;

            if (timer.getCycleCount() == timer.INDEFINITE) {
                imgPlayer.setRotationAxis(new Point3D(0, 90, 1));
                imgPlayer.setRotate(0);
            }
        }
        if (event.getCode() == KeyCode.ENTER) {

            //starts game without pressing start button

            if (lblNext.isVisible() == true) {
                MouseEvent mouseEvent = null;
                startLevel(mouseEvent);
            }
        }
        if (event.getCode()  == KeyCode.DIGIT1 && getY(imgPlayer) > 650) {

            for (ImageView coin : coins) {

                coin.setDisable(true);
                coin.setVisible(true);
            }

        }
    }


    @FXML
    public void keyReleased(KeyEvent event) {
        
        //stops movement when releasing key
        
        if ((event.getCode() == KeyCode.W) || (event.getCode() == KeyCode.S)) {
            y = 0;
        }
        if ((event.getCode() == KeyCode.A) || (event.getCode() == KeyCode.D)) {
            x = 0;
        }

    }

    @FXML
    void quitGame(MouseEvent event) { System.exit(0); }

    @FXML
    void info(MouseEvent event) {
        Alert info = new Alert(Alert.AlertType.INFORMATION);
        info.setTitle("Information Dialog");
        info.setHeaderText(null);
        info.setContentText("> In Pacghost, you are the ghost and you must avoid\n" +
                "the pacmen while trying to collect the good coins.\n" +
                "> Get all good coins to beat the level.\n" +
                "> Getting coins and winning levels award points.\n" +
                "> You must also avoid the bad coins.\n" +
                "> Hitting a bad coin or pacman will make you lose.\n" +
                "> There are infinite levels so you must last\n" +
                "as long as possible before losing.");
        info.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //starts movement timer

        timer = new Timeline(new KeyFrame(Duration.seconds(0.2), ae -> countdown()));
        timer.setCycleCount(3); //3
        countdown = 2; //2

        //assigns values to wall array
        
        walls.add(rctWall1);
        walls.add(rctWall2);
        walls.add(rctWall3);
        walls.add(rctWall4);
        walls.add(rctWall5);
        walls.add(rctWall6);
        walls.add(rctWall7);
        walls.add(rctWall8);
        walls.add(rctWall9);
        walls.add(rctWall10);
        walls.add(rctWall11);
        walls.add(rctWall12);
        walls.add(rctWall13);
        walls.add(rctWall14);
        walls.add(rctWall15);
        walls.add(rctWall16);
        walls.add(rctWall17);
        walls.add(rctWall18);
        walls.add(rctWall19);
        walls.add(rctWall20);
        walls.add(rctWall21);
        walls.add(rctWall22);
        walls.add(rctWall23);
        walls.add(rctWall24);
        walls.add(rctWall25);
        walls.add(rctWall26);
        walls.add(rctWall27);
        walls.add(rctWall28);
        walls.add(rctWall29);
        walls.add(rctWall30);
        walls.add(rctWall31);
        walls.add(rctWall32);
        walls.add(rctWall33);
        walls.add(rctWall34);
        walls.add(rctWall35);
        walls.add(rctWall36);
        walls.add(rctWall37);
        
        coins.add(imgCoin1);
        coins.add(imgCoin2);
        coins.add(imgCoin3);
        coins.add(imgCoin4);
        coins.add(imgCoin5);
        coins.add(imgCoin6);
        coins.add(imgCoin7);
        coins.add(imgCoin8);
    }    
}
