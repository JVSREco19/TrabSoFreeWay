package main;

import java.util.concurrent.Semaphore;

import javax.swing.ImageIcon;

public class Car extends Sprite implements Runnable {

  private int posInitial = Utils.INIT_CAR_X;
  public boolean inGame = true;
  private int direction;
  private int speed;
  private int[][] matPosP1;
  private int[][] matPosP2;
  private Semaphore semaphore;
  private Player p1,p2;

  public Car(int x, int y, int direction, int speed, String path, int[][] matPosP1, int[][] matPosP2,
      Semaphore semaphore, Player p1, Player p2) {
        this.p1 =p1;
        this.p2 = p2;
    this.x = x;
    this.y = y;
    this.semaphore = semaphore;
    this.matPosP1 = matPosP1;
    this.matPosP2 = matPosP2;
    this.direction = direction;
    this.speed = speed;
    ImageIcon ii = new ImageIcon(getClass().getResource(path));
    image = ii.getImage();
    i_width = Utils.CAR_WIDTH;
    i_height = Utils.CAR_HEIGHT;
  }

  public int getPosInitial() {
    return posInitial;
  }

  public void setPosInitial(int posInitial) {
    this.posInitial = posInitial;
  }

  public int getSpeed() {
    return speed;
  }

  public void setSpeed(int speed) {
    this.speed = speed;
  }

  public void setState(Boolean inGame) {
    this.inGame = inGame;
  }

  public int getDirection() {
    return direction;
  }

  private void moveMatrix() {
    try {
      if (direction == -1) {// movendo pra esquerda
        if (x >= 340 && x < 388) { // caso do P1 pra adicionar pos
          if (matPosP1[((340 - x) * -1)][y] != 2 && matPosP1[((340 - x) * -1)][y + 48] != 2   ) {
            for (int i = 0; i < 48; i++) {
              matPosP1[(340 - x) * -1][y + i] = 1;
            }
          } else {
            p1.setY(Utils.INIT_PLAYER_Y);
            // colisao
          }

        } else if (x >= 640 && x < 688) { // caso do P2 pra adicionar pos

          if (matPosP2[((640 - x) * -1)][y] != 2 && matPosP2[((640 - x) * -1)][y + 48] != 2) {
            for (int i = 0; i < 48; i++) {
              matPosP2[(640 - x) * -1][y + i] = 1;
            }
          } else {
            p2.setY(Utils.INIT_PLAYER_Y);
          }

        } else if (x < 340 && x >= 292) {// caso do P1 pra remover pos

          for (int i = 0; i < 48; i++) {
            matPosP1[(292 - x) * -1][y + i] = 0;
          }

        } else if (x < 640 && x >= 592) {// caso do P2 pra remover pos

          for (int i = 0; i < 48; i++) {
            matPosP2[((592 - x) * -1)][y + i] = 0;
          }
        }
      } else {// movendo pra direita
        if (x >= 340 && x < 388) { // caso do P1 pra adicionar pos
          if (matPosP1[(340 - x)*-1 ][y] != 2 && matPosP1[(340 - x)*-1 ][y + 48] != 2) {
            for (int i = 0; i < 48; i++) {
              matPosP1[(340 - x)*-1 ][y + i] = 1;
            }
          } else {
            // colisao
            p1.setY(Utils.INIT_PLAYER_Y);
          }

        } else if (x >= 640 && x < 688) { // caso do P2 pra adicionar pos

          if (matPosP2[(640 - x)*-1 ][y] != 2 && matPosP2[(640 - x)*-1 ][y + 48] != 2) {
            for (int i = 0; i < 48; i++) {
              matPosP2[(640 - x)*-1 ][y + i] = 1;
            }
          } else {
            // colisao
            p2.setY(Utils.INIT_PLAYER_Y);
          }

        } else if (x >= 388 && x < 436) {// caso do P1 pra remover pos

          for (int i = 0; i < 48; i++) {
            matPosP1[(388 - x) * -1][y + i] = 0;
          }

        } else if (x >= 688 && x < 736) {// caso do P2 pra remover pos

          for (int i = 0; i < 48; i++) {
            matPosP2[((688 - x) * -1)][y + i] = 0;
          }
        }
      }

    } catch (Exception e) {
      
      System.out.println(e);
    }
  }

  public void setDirection(int direct) {
    this.direction = direct;
  }

  public void move() {
    try {
      semaphore.acquire();
      moveMatrix();
      int aux = x + (speed * direction);
      this.x = aux;
      if ((aux < -64 && direction == -1)) {
        this.x = posInitial + 64;
      } else if (aux > Utils.WIDTH + 64 && direction == 1) {
        this.x = -64;
      }
      
    } catch (Exception e) {
      System.out.println(e);
    }finally{
      semaphore.release();
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
