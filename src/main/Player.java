package main;

import java.awt.event.KeyEvent;

import javax.swing.ImageIcon;

public class Player extends Sprite {
  
  public int dy;
  public int speed;
  public int points=0;
  public Player(int speed){
    this.speed = speed;
    ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/p1.png"));
    image = ii.getImage();
    i_width = image.getWidth(null);
    i_height = image.getHeight(null);
    resetState();
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
    if (y>Utils.BOTTON_EDGE){
      y = Utils.INIT_PLAYER_Y;
    }
    
  }

  public void countPoint() {
      this.points += 1;
  }

  public void keyPressed(KeyEvent e){
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP){
      dy = -1;
    }else
    if(key == KeyEvent.VK_DOWN){
      dy = 1;
    }
  }

  public void keyReleased(KeyEvent e) {
    int key = e.getKeyCode();
    if (key == KeyEvent.VK_UP) {
      dy = 0;
    } else if (key == KeyEvent.VK_DOWN) {
      dy = 0;
    }
  }

  public void resetState() {
    y = Utils.INIT_PLAYER_Y;
    x = Utils.INIT_PLAYER_X;

  }

}
