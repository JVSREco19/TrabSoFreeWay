package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Image;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable {

  private Player player1, player2;
  private Car[] cars;
  private boolean inGame = true;
  private int cont = 0;
  private Image[] numbers = new Image[10];
  // private String message = "F";

  public Board() {
    initBoard();
  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    cars = new Car[Utils.NUMBER_OF_CARS];
  }

  public void addNotify() {
    super.addNotify();
    gameInit();
  }

  private void gameInit() {
    player1 = new Player(4, 1);
    player2 = new Player(2, 2);
    cars[0] = new Car(Utils.INIT_CAR_X, 70, -1, 1, "../imagens/Car1.png");
    cars[1] = new Car(Utils.INIT_CAR_X, 80 * 2, -1, 2, "../imagens/Car2.png");
    cars[2] = new Car(Utils.INIT_CAR_X, 80 * 3, -1, 3, "../imagens/Car3.png");
    cars[3] = new Car(0, 20 + 80 * 4, 1, 3, "../imagens/Car4.png");
    cars[4] = new Car(0, 20 + 80 * 5, 1, 2, "../imagens/Car5.png");
    cars[5] = new Car(0, 30 + 80 * 6, 1, 1, "../imagens/Car6.png");
    for (int i = 0; i < 10; i++) {
      ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/" + i + ".png"));
      numbers[i] = ii.getImage();
    }

  }

  public Player getPlayer1() {
    return this.player1;
  }

  public Player getPlayer2() {
    return this.player2;
  }

  public Car[] getCars() {
    return this.cars;
  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2d = (Graphics2D) g;

    if (inGame) {
      ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/estrada.png"));
      g2d.drawImage(ii.getImage(), 0, 0, 1475, 643, this);
      if (player1.points >= 10) {
     int auxInt = player1.points/10;
        g2d.drawImage(numbers[auxInt], 140, 10, numbers[auxInt].getWidth(null) * 3,
            numbers[auxInt].getHeight(null) * 3, this);
            g2d.drawImage(numbers[player1.points% 10], 180, 10, numbers[player1.points% 10].getWidth(null) * 3,
            numbers[player1.points % 10].getHeight(null) * 3, this);
      }else{
         g2d.drawImage(numbers[player1.points], 180, 10, numbers[player1.points].getWidth(null) * 3,
          numbers[player1.points].getHeight(null) * 3, this);
      }
     

      if (player2.points >= 10) {
        int auxInt = player2.points/10;
        g2d.drawImage(numbers[auxInt], 810, 10, numbers[auxInt].getWidth(null) * 3,numbers[auxInt].getHeight(null) * 3, this);
        g2d.drawImage(numbers[player2.points%10], 850, 10, numbers[player2.points%10].getWidth(null) * 3,
            numbers[player2.points%10].getHeight(null) * 3, this);
      }else{
        g2d.drawImage(numbers[player2.points], 850, 10, numbers[player2.points].getWidth(null) * 3,
            numbers[player2.points].getHeight(null) * 3, this);
      }
      
      drawObjects(g2d);
      cont++;
      if (cont == 30) {
        cont = 0;
      }
    } else {
      gameFinished(g2d);
    }

    Toolkit.getDefaultToolkit().sync();
  }

  private void drawObjects(Graphics2D g2d) {

    if (player1.walking == true) {
      if (cont > 15) {
        g2d.drawImage(player1.getImage2(), player1.getX(), player1.getY(), player1.getI_width(), player1.getI_height(),
            this);

      } else {
        g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), player1.getI_width(), player1.getI_height(),
            this);
      }

    } else {
      g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), player1.getI_width(), player1.getI_height(),
          this);
    }

    if (player2.walking == true) {
      if (cont > 15) {
        g2d.drawImage(player2.getImage2(), player2.getX(), player2.getY(), player2.getI_width(), player2.getI_height(),
            this);

      } else {
        g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), player2.getI_width(), player2.getI_height(),
            this);
      }
    } else {
      g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), player2.getI_width(), player2.getI_height(),
          this);
    }
    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
      g2d.drawImage(cars[i].getImage(), cars[i].getX(), cars[i].getY(), cars[i].getI_width(), cars[i].getI_height(),
          this);
    }

  }
  public void playMusic(String path){

      File musicFile;
      try {
        musicFile = new File(path);
        AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicFile);
        Clip clip = AudioSystem.getClip();
        clip.open(audioInput);
        clip.start();
      } catch (Exception e) {
        System.out.println(e);
      }

  }

  private void gameFinished(Graphics2D g2d) {
    g2d.setColor(Color.BLACK);

    ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/wasted.png"));
    g2d.drawImage(ii.getImage(), 0, 0, 1080, 640, this);
  }

  @Override
  public void run() {
    while (inGame == true) {
      /*player1.move();
      player2.move();
      for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
        cars[i].move();
      }*/
      checkCollision(player1);
      checkCollision(player2);
      repaint();
      try {
        Thread.sleep(10);
      } catch (Exception e) {
        // TODO: handle exception
        e.printStackTrace();
      }
      if (inGame == false) {
        // Menu do jogo com opções de jogar novamente
      }
    }

  }

  private class TAdapter extends KeyAdapter {
    @Override
    public void keyPressed(KeyEvent e) {
      player1.keyPressed(e);
      player2.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
      player1.keyReleased(e);
      player2.keyReleased(e);
    }

  }

  public void stopGame() {
    player1.setState(false);
    player2.setState(false);
    for(int i = 0; i<Utils.NUMBER_OF_CARS; i++){
      cars[i].setState(false);
    }
    
    inGame = false;
  }

  private void checkCollision(Player player) {
    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
      if (player.getRect().intersects(cars[i].getRect())) {
        // player.playerLives--;
        if (player.playerLives == 0) {
          stopGame();
        }
        player.setY(Utils.INIT_PLAYER_Y);
      }
    }

  }
}
