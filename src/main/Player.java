package main;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Runnable{
  
  public int dy;
  public int speed;
  public int playerCode;
  public int points=0;
  public boolean walking = false;
  public int playerLives = Utils.LIFE;
  public boolean inGame = true;
  
  
  public Player(int speed,int playerCode){
    this.speed = speed;
    this.playerCode = playerCode;
    ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/ChickenStop.png"));
    image = ii.getImage();
    ii = new ImageIcon(getClass().getResource("../imagens/ChickenWalk.png"));
    image2 = ii.getImage();
    i_width = 42;
    i_height = 24;
    resetState();
  }

  public void setState(Boolean inGame) {
    this.inGame = inGame;
  }

  public int getPoints() {
    return points;
  }

  public void setPoints(int points) {
    this.points = points;
  }

  public void move(){
    y +=(dy*speed);

    if(y<0){
      y = Utils.INIT_PLAYER_Y;
      countPoint();
    }else
    if (y> Utils.INIT_PLAYER_Y){
      y = Utils.INIT_PLAYER_Y;
    }
    
  }

  public void countPoint() {
      this.points += 1;
  }

  public void keyPressed(KeyEvent e){
    int key = e.getKeyCode();
    if(playerCode == 1){
      if (key == KeyEvent.VK_UP){
        this.walking=true;
      dy = -1;
    }else
    if(key == KeyEvent.VK_DOWN){
      dy = 1;
      this.walking = true;
    }
    }else if(playerCode ==2){
      if (key == KeyEvent.VK_W) {
        dy = -1;
        this.walking = true;
      } else if (key == KeyEvent.VK_S) {
        dy = 1;
        this.walking = true;
      }
    }
    
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if (playerCode == 1) {
      if (key == KeyEvent.VK_UP) {
        dy = 0;
        this.walking = false;
      } else if (key == KeyEvent.VK_DOWN) {
        dy = 0;
        this.walking = false;
      }
    } else if (playerCode == 2) {
      if (key == KeyEvent.VK_W) {
        dy = 0;
        this.walking = false;
      } else if (key == KeyEvent.VK_S) {
        dy = 0;
        this.walking = false;
      }
    }

  }

  public void resetState() {
    if(playerCode== 1){
      y = Utils.INIT_PLAYER_Y;
      x = Utils.INIT_PLAYER_X;
    }else if(playerCode==2){
      y = Utils.INIT_PLAYER_Y;
      x = Utils.INIT_PLAYER_X + 300;
    }

  }

  @Override
  public void run() {
    while(inGame){
      move();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
    
  }

}
