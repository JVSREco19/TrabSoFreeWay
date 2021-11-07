package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.concurrent.Semaphore;
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
  public int[][] matPosP1 = new int[Utils.CAR_WIDTH][Utils.HEIGHT]; // Matriz de posição
  public int[][] matPosP2 = new int[Utils.CAR_WIDTH][Utils.HEIGHT]; // Matriz de posição
  public Semaphore[] vetSemaphore = new Semaphore[Utils.NUMBER_OF_CARS]; //1 semaforo por carro


  public Board() {
    initBoard();
    initMatPos(matPosP1);
    initMatPos(matPosP2);
    initSemaphores();
  }

  private void initMatPos(int[][] mat) {
    for(int i =0; i<Utils.PLAYER_WIDTH; i++){
      for(int j = 0; j<Utils.HEIGHT; j++){
          mat[i][j] = 0;
      }
    }
  }
  private void initSemaphores(){
    for(int i = 0; i<Utils.NUMBER_OF_CARS;i++){
      vetSemaphore[i] = new Semaphore(1,true);
    }
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
    player1 = new Player(1, 1,matPosP1);
    player2 = new Player(1, 2,matPosP2);
    cars[0] = new Car(Utils.INIT_CAR_X, 70, -1, 1, "../imagens/Car1.png",matPosP1,matPosP2, vetSemaphore[0],player1,player2);
    cars[1] = new Car(Utils.INIT_CAR_X, 80 * 2, -1, 2, "../imagens/Car2.png", matPosP1, matPosP2, vetSemaphore[1],
        player1, player2);
    cars[2] = new Car(Utils.INIT_CAR_X, 80 * 3, -1, 3, "../imagens/Car3.png", matPosP1, matPosP2, vetSemaphore[2],
        player1, player2);
    cars[3] = new Car(0, 20 + 80 * 4, 1, 3, "../imagens/Car4.png", matPosP1, matPosP2, vetSemaphore[3], player1,
        player2);
    cars[4] = new Car(0, 20 + 80 * 5, 1, 2, "../imagens/Car5.png", matPosP1, matPosP2, vetSemaphore[4], player1,
        player2);
    cars[5] = new Car(0, 30 + 80 * 6, 1, 1, "../imagens/Car6.png", matPosP1, matPosP2, vetSemaphore[5], player1,
        player2);
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

  public Semaphore[] getSemaphores() {
    return this.vetSemaphore;
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
      if (cont == 20) {
        cont = 0;
      }
    } else {
      gameFinished(g2d);
    }

    Toolkit.getDefaultToolkit().sync();
  }

  private void drawObjects(Graphics2D g2d) {

    if (player1.walking == true) {
      if (cont > 10) {
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
      if (cont > 10) {
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
        clip.loop(Clip.LOOP_CONTINUOUSLY);

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
 
      repaint();
      try {
        Thread.sleep(10);
      } catch (Exception e) {
        
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

}
