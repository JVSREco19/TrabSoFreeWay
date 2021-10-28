package main;
import javax.swing.ImageIcon;

public class Car extends Sprite {

  private int posInitial;
  private int ID;
  private int direction;
  private int speed;

  public Car(int x, int y,String path,int ID, int direction, int speed){
    this.ID = ID;
    this.x = x;
    this.y = y;
    this.direction = direction;
    this.speed = speed;
    ImageIcon ii = new ImageIcon(path);
    image = ii.getImage();
    i_width = image.getWidth(null);
    i_height = image.getHeight(null);
  }

  public int getPosInitial() {
    return posInitial;
  }

  public void setPosInitial(int posInitial) {
    this.posInitial = posInitial;
  }

  public int getID() {
    return ID;
  }

  public void setID(int iD) {
    this.ID = iD;
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
    if((aux <= 0 && direction == -1) || aux>= 1080 && direction == 1 ){
      this.x = posInitial;
    }else{
      this.x = aux;
    }
    
  }
  
}
