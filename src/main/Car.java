package main;
import javax.swing.ImageIcon;

public class Car extends Sprite implements Runnable{

  private int posInitial = Utils.INIT_CAR_X;
  
  private int direction;
  private int speed;

  public Car(int x, int y, int direction, int speed,String path){
    this.x = x;
    this.y = y;
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

  public int getDirection() {
    return direction;
  }

  public void setDirection(int direct) {
    this.direction = direct;
  }
  public void move(){
    
    int aux = x + (speed*direction) ;
    this.x = aux;
    if((aux < -64 && direction == -1)){
      this.x = posInitial + 64;
    }else if(aux > Utils.WIDTH+64 && direction == 1){
      this.x = -64;
    } 
      
    
    
  }
  

  @Override
  public void run() {
    while(true){
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
