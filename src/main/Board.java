package main;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import java.util.Timer;
import java.util.TimerTask;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.Color;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class Board extends JPanel {

  private int playerLives = 3;
  
  private Timer timer;
  private Player player;
  private Car[] cars;
  private boolean inGame = true;
  private String message = "VOCÊ É PODRE, SEU IMUNDO";

  public Board() {
    initBoard();

  }

  private void initBoard() {
    addKeyListener(new TAdapter());
    setFocusable(true);
    cars = new Car[Utils.NUMBER_OF_CARS];
    setDoubleBuffered(true);
    timer = new Timer();
    timer.scheduleAtFixedRate(new ScheduleTask(), Utils.DELAY, Utils.PERIOD);
  }

  public void addNotify() {
    super.addNotify();
    gameInit();
  }

  private void gameInit() {
    player = new Player(2);
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
    g2d.drawImage(player.getImage(), player.getX(), player.getY(), player.getI_width(), player.getI_height(), this);
    for (int i = 0; i < Utils.NUMBER_OF_CARS; i++) {
     g2d.drawImage(cars[i].getImage(), cars[i].getX(), cars[i].getY(), cars[i].getI_width(), cars[i].getI_height(),
          this);
    }

  }

  private void gameFinished(Graphics2D g2d){
    Font font = new Font("Verdana",Font.BOLD,18);
    g2d.setColor(Color.BLACK);
    g2d.setFont(font);
    g2d.drawString(message, Utils.WIDTH/2, Utils.HEIGHT/2);
  }

  private class TAdapter extends KeyAdapter{
    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
      }
    
    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
      }

  }

  private class ScheduleTask extends TimerTask{
    @Override
    public void run(){
      player.move();
      for(int i =0; i< Utils.NUMBER_OF_CARS;i++){
        cars[i].move();
      }
      checkCollision();
      repaint();
    }
    public void stopGame(){
      inGame = false;
      timer.cancel();
    }

    private void checkCollision(){
      for(int i = 0;i<Utils.NUMBER_OF_CARS;i++){
        if(player.getRect().intersects(cars[i].getRect())){
          playerLives--;
          if(playerLives==0){
            stopGame();
          }
          player.setY(Utils.INIT_PLAYER_Y);
        }
      }
      
    }
  }

}
