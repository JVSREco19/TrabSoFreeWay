package main;
import java.awt.Image;
import java.awt.Rectangle;

public class Sprite {
  protected int x, y, i_width, i_height; 
  protected Image image,image2;
  public Sprite() {

  }
 
  public int getX(){
    return x;
  }
  
  public void setX(int x) {
    this.x = x;
  }
  
  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }
  
  public int getI_width() {
    return i_width;
  }

  public void setI_width(int i_width) {
    this.i_width = i_width;
  }

  public int getI_height() {
    return i_width;
  }

  public void setI_height(int i_height) {
    this.i_height = i_height;
  }

  public Image getImage() {
    return image;
  }

  public void setImage(Image image) {
    this.image = image;
  }
  
  public Image getImage2() {
    return image2;
  }

  public void setImage2(Image image2) {
    this.image2 = image2;
  }

  public Rectangle getRect(){
    return new Rectangle(x, y, getI_width(),getI_height());
  }

}
