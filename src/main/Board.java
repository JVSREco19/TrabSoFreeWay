package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel implements Runnable{
  
  
  private Player player1,player2;
  private Car[] cars;
  private boolean inGame = true;
  //private String message = "F";

  public Board() {
    initBoard();

  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    cars = new Car[Utils.NUMBER_OF_CARS];
    setDoubleBuffered(true);
    
  }

  public void addNotify() {
    super.addNotify();
    gameInit();
  }

  private void gameInit() {
    player1 = new Player(1,1);
    player2 = new Player(1,2);
    int carSpeed = 1;

    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
      int direction = -1;
      int pos = 1080;
      if (i < Utils.NUMBER_OF_CARS / 2) {
        direction = -1;
        pos = 1080;
      } else {
        direction = 1;
        pos = 0;
      }
      if(i>=Utils.NUMBER_OF_CARS/2) {
        cars[i] = new Car(pos, Utils.INIT_CAR_Y + i * Utils.CAR_HEIGHT + 70, direction, carSpeed);
      }else {
        cars[i] = new Car(pos, Utils.INIT_CAR_Y + i * Utils.CAR_HEIGHT + 40, direction, carSpeed);
      }
      
      if (i < Utils.NUMBER_OF_CARS / 2) {
        direction = -1;
        carSpeed += 2;
      } else {
        direction = 1;
        carSpeed -= 2;
      }

    }
  }

  public Player getPlayer1(){
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
    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

    if (inGame) {
      ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/estrada.png"));
      Image image = ii.getImage(); 
      g2d.drawImage(ii.getImage(), 0,0,image.getWidth(null), image.getHeight(null), this);
      drawObjects(g2d);
    } else {
      gameFinished(g2d);
    }

    Toolkit.getDefaultToolkit().sync();
  }

  private void drawObjects(Graphics2D g2d) {
    g2d.drawImage(player1.getImage(), player1.getX(), player1.getY(), player1.getI_width(), player1.getI_height(), this);
    g2d.drawImage(player2.getImage(), player2.getX(), player2.getY(), player2.getI_width(), player2.getI_height(),
        this);
    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
     g2d.drawImage(cars[i].getImage(), cars[i].getX(), cars[i].getY(), cars[i].getI_width(), cars[i].getI_height(),
          this);
    }

  }

  private void gameFinished(Graphics2D g2d){
    Font font = new Font("Verdana",Font.BOLD,18);
    g2d.setColor(Color.BLACK);
    g2d.setFont(font);
    ImageIcon ii = new ImageIcon(getClass().getResource("../imagens/wasted.png"));
    g2d.drawImage(ii.getImage(), 0, 0, 1080, 640, this);
  }

  @Override
  public void run() {
    while(inGame==true){
      player1.move();
      player2.move();
    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
      cars[i].move();
    }
    checkCollision(player1);
    checkCollision(player2);
    repaint();
    try {
      Thread.sleep(10);
    } catch (Exception e) {
      //TODO: handle exception
    }
    if(inGame == false){
      //Menu do jogo com opções de jogar novamente
    }
  }
    
  }

  private class TAdapter extends KeyAdapter{
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

  
    public void stopGame(){
      inGame = false;
      
    }

    private void checkCollision(Player player){
      for(int i = 0;i<Utils.NUMBER_OF_CARS;i++){
        if(player.getRect().intersects(cars[i].getRect())){
          player.playerLives--;
          if(player.playerLives==0){
            stopGame();
          }
          player.setY(Utils.INIT_PLAYER_Y);
        }
      }
      
    }
  }


