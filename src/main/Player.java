package main;

import java.awt.event.KeyEvent;
import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class Player extends Sprite implements Runnable {

  public int dy, contAux =0;
  public int speed;
  public int playerCode;
  public int points = 0;
  public boolean walking = false;
  public int playerLives = Utils.LIFE;
  public boolean inGame = true;
  private int[][] matPos;
  private Semaphore[] semaphores = new Semaphore[Utils.NUMBER_OF_CARS];

  public Player(int speed, int playerCode, int[][] matPos) {
    this.speed = speed;
    this.playerCode = playerCode;
    this.matPos = matPos;

    ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/ChickenStop.png"));
    image = ii.getImage();
    ii = new ImageIcon(getClass().getResource("../imagens/ChickenWalk.png"));
    image2 = ii.getImage();
    i_width = Utils.PLAYER_WIDTH;
    i_height = Utils.PLAYER_HEIGHT;
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

  public void setSemaphores(Semaphore[] semaphores) {
    this.semaphores = semaphores;
  }

  private void resetMatPos() {
    for (int i = 0; i < Utils.PLAYER_WIDTH; i++) {
      for (int j = 0; j < Utils.HEIGHT; j++) {
        matPos[i][j] = 0;
      }
    }
  }
  private void moveMatrix() {

    if(y>0){
      for (int i = 0; i < 42; i++) {
      if (matPos[i][y] == 1) {
        resetMatPos();

        y = Utils.INIT_PLAYER_Y;
        break;
      } else {
        matPos[i][y] = 2;
      }

    }
    }
    if(y<560){
      for (int i = 0; i < 42; i++) {
         matPos[i][y+24] = 2;
         matPos[i][y + 25] = 0;

      }
    }
      


  }

  public void move() {
    Semaphore semAux = semaphores[0];
    try {

      if (y < 150) {
        semAux = semaphores[0];
      } else if (y < 230 && y > 150) {
        semAux = semaphores[1];
      } else if (y < 330 && y > 230) {
        semAux = semaphores[2];
      } else if (y < 410 && y > 330) {
        semAux = semaphores[3];
      } else if (y < 510 && y > 410) {
        semAux = semaphores[4];
      } else if (y > 510) {
        semAux = semaphores[5];
      }
      semAux.acquire();
      moveMatrix();
      y += (dy * speed);

      if (y < 0) {
        y = Utils.INIT_PLAYER_Y;
        resetMatPos();
        countPoint();
      } else if (y > Utils.INIT_PLAYER_Y) {
        y = Utils.INIT_PLAYER_Y;
      }

      
    } catch (Exception e) {
      System.out.println(e);
    } finally {
      semAux.release();
    }
  }

  public void countPoint() {
    this.points += 1;
  }

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    if (playerCode == 1) {
      if (key == KeyEvent.VK_UP) {
        this.walking = true;
        dy = -1;
      } else if (key == KeyEvent.VK_DOWN) {
        dy = 1;
        this.walking = true;
      }
    } else if (playerCode == 2) {
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
    if (playerCode == 1) {
      y = Utils.INIT_PLAYER_Y;
      x = Utils.INIT_PLAYER_X;
    } else if (playerCode == 2) {
      y = Utils.INIT_PLAYER_Y;
      x = Utils.INIT_PLAYER_X + 300;
    }

  }

  @Override
  public void run() {
    while (inGame) {
      move();
      try {
        Thread.sleep(10);
      } catch (InterruptedException e) {

        e.printStackTrace();
      }
    }

  }

}
